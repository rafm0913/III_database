package tw.org.iii.androidlittlehappy.fcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tw.org.iii.androidlittlehappy.ActMain;
import tw.org.iii.androidlittlehappy.CPublicParameters;
import tw.org.iii.androidlittlehappy.ChatRoomAsyncTask;
import tw.org.iii.androidlittlehappy.JoinAct;
import tw.org.iii.androidlittlehappy.R;

/**
 * Created by filipp on 6/28/2016.
 */
public class Chat_Room extends AppCompatActivity {

    private Button btn_send_msg;
    private Button btnAgree;
    private Button btnWantJoin;
    private Button btnRefuse;
    private EditText input_msg;
    private TextView chat_conversation;
    /////新的
    private ListView listView;
    boolean isMine = true;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;


    private String act_id, user_name,room_name;
    private DatabaseReference root ;
    private String temp_key;

    private View.OnClickListener btnWantJoin_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            ChatRoomAsyncTask wantTask = new ChatRoomAsyncTask(Integer.valueOf(act_id),Chat_Room.this,"wantjoin");
            wantTask.execute(new String[] { ChatRoomAsyncTask.URL });

        }
    };
    private View.OnClickListener btnAgree_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            ChatRoomAsyncTask agreeTask = new ChatRoomAsyncTask(Integer.valueOf(act_id),Chat_Room.this,"agree", room_name);
            String url = "http://52.198.163.90:8080/DemoServer/FCMController?action=" + "agree";
            agreeTask.execute(new String[] { url });

            String myActName = "";
            for(int i = 0 ; i<ActMain.iv_activitylist_I_can_see.size();i++){
                if(String.valueOf(ActMain.iv_activitylist_I_can_see.get(i).getId()).equals(act_id)){
                    myActName = ActMain.iv_activitylist_I_can_see.get(i).getTitle();
                    break;
                }
            }

            Toast.makeText(getApplicationContext(),myActName+"活動媒合成功",Toast.LENGTH_LONG);

        }
    };
    private View.OnClickListener btnRefuse_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            ChatRoomAsyncTask refuseTask = new ChatRoomAsyncTask(Integer.valueOf(act_id),Chat_Room.this,"refuse", room_name);
            String url = "http://52.198.163.90:8080/DemoServer/FCMController?action=" + "refuse";
            refuseTask.execute(new String[] { url });
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);

        btn_send_msg = (Button) findViewById(R.id.btn_send);
        btnAgree = (Button) findViewById(R.id.btn_agree);
        btnWantJoin = (Button) findViewById(R.id.btn_wantjoin);
        btnRefuse = (Button) findViewById(R.id.btn_refuse);
        input_msg = (EditText) findViewById(R.id.msg_input);
        chat_conversation = (TextView) findViewById(R.id.textView);

        ///////
        chatMessages = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_msg);
        //set ListView adapter first
        adapter = new MessageAdapter(Chat_Room.this, R.layout.chat_room, chatMessages);
        listView.setAdapter(adapter);
        Log.v("chat","listview_setAdapter");

        act_id = getIntent().getExtras().get("act_id").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        user_name = getIntent().getExtras().get("user_name").toString();


        //設定聊天室根據參加者或發起者決定可以看到的按鈕
        if(user_name.equals(room_name)){
            btnWantJoin.setVisibility(View.VISIBLE);
            btnAgree.setVisibility(View.INVISIBLE);
            btnRefuse.setVisibility(View.INVISIBLE);
        }else{
            btnWantJoin.setVisibility(View.INVISIBLE);
            btnAgree.setVisibility(View.VISIBLE);
            btnRefuse.setVisibility(View.VISIBLE);
        }

        btnWantJoin.setOnClickListener(btnWantJoin_Click);
        btnAgree.setOnClickListener(btnAgree_Click);
        btnRefuse.setOnClickListener(btnRefuse_Click);


        //建立活動房間
        final DatabaseReference root0 = FirebaseDatabase.getInstance().getReference().getRoot();
        /*
        //第一層node之key
        //自定義key 活動ID
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(act_id,"");
        root0.updateChildren(map);

        //第二層node之key
        //自定義key UserName
        DatabaseReference room_root = root0.child(room_name.toString());
        Map<String,Object> map2 = new HashMap<String, Object>();
        map2.put(room_name,"");
        room_root.updateChildren(map2);
        Log.d("FCM", "房間建立成功");*/

        root0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //是否存在node child
                if (!dataSnapshot.hasChild(act_id)) {
                    if(!dataSnapshot.child(act_id).hasChild(room_name)){
                        //第一層node之key
                        //自定義key 活動ID
                        Map<String,Object> map = new HashMap<String, Object>();
                        map.put(act_id,"");
                        root0.updateChildren(map);

                        //第二層node之key
                        //自定義key UserName
                        DatabaseReference room_root = root0.child(act_id);
                        Map<String,Object> map2 = new HashMap<String, Object>();
                        map2.put(room_name,"");
                        room_root.updateChildren(map2);
                        Log.d("list", "Act房間建立成功，建立UserName key成功");

                    }else{Log.d("list", "Act沒有key=>" + act_id + "但有聊天室 就是不可能");}
                }else{
                    if(!dataSnapshot.child(act_id).hasChild(room_name)){
                        //第二層node之key
                        //自定義key UserName
                        DatabaseReference room_root = root0.child(act_id);
                        Map<String,Object> map2 = new HashMap<String, Object>();
                        map2.put(room_name,"");
                        room_root.updateChildren(map2);
                        Log.d("list", "Act有Key=>" + act_id + "但沒有UesrName Key");
                    }else{
                        Log.d("list", "Act有Key=>" + act_id + "也有UesrName Key");
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setTitle(" Room - "+room_name);
        Log.v("chat","getname&roomname");
        root = FirebaseDatabase.getInstance().getReference().child(act_id).child(room_name);
        Log.v("chat","getroot");
        /////new
        //event for button SEND
        /*
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_msg.getText().toString().trim().equals("")) {
                    Toast.makeText(Chat_Room.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    //add message to list
                    ChatMessage chatMessage = new ChatMessage(input_msg.getText().toString(), isMine);
                    chatMessages.add(chatMessage);
                    adapter.notifyDataSetChanged();
                    input_msg.setText("");
                    if (isMine) {
                        isMine = false;
                    } else {
                        isMine = true;
                    }
                }
            }
        });*/
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg",input_msg.getText().toString());

                message_root.updateChildren(map2);
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            conunt++;
                Log.v("chat","conunt"+String.valueOf(conunt));
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                change_conunt++;
                Log.v("chat","change_count"+String.valueOf(change_conunt));
                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this,ActMain.class);
//        startActivity(intent);
        finish();
    }

    private String chat_msg,chat_user_name;
    Integer conunt=0;
    Integer change_conunt=0;
    private void append_chat_conversation(DataSnapshot dataSnapshot) {


        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()){
            chat_msg = String.valueOf(((DataSnapshot)i.next()).getValue());
            chat_user_name = String.valueOf(((DataSnapshot)i.next()).getValue());

            ChatMessage chatMessage = new ChatMessage(chat_user_name,chat_msg);
            chatMessages.add(chatMessage);

            //adapter.notifyDataSetInvalidated();
            adapter.notifyDataSetChanged();

            //listView.setAdapter(adapter);

            input_msg.setText("");
              // if (isMine) {
               //    isMine = false;
               //} else {
               //    isMine = true;
               //}
            Log.v("chat",chat_msg+""+chat_user_name);
            //chat_conversation.append(chat_user_name +" : "+chat_msg +" \n");
        }


    }
}
