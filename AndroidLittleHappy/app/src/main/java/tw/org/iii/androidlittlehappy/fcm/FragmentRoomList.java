package tw.org.iii.androidlittlehappy.fcm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tw.org.iii.androidlittlehappy.ActMain;
import tw.org.iii.androidlittlehappy.AsyncTaskSelectChat;
import tw.org.iii.androidlittlehappy.CMessage;
import tw.org.iii.androidlittlehappy.CPublicParameters;
import tw.org.iii.androidlittlehappy.ChatAdapter;
import tw.org.iii.androidlittlehappy.FragmentRoomListAdapter;
import tw.org.iii.androidlittlehappy.JsonFactoryForChat;
import tw.org.iii.androidlittlehappy.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRoomList extends Fragment implements FragmentBackHandler {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_room_list, container, false);

        add_room = (Button) view.findViewById(R.id.btn_add_room);
        room_name = (EditText) view.findViewById(R.id.room_name_edittext);
        listView = (ListView) view.findViewById(R.id.listView);

        //arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list_of_rooms);
        adapter = new FragmentRoomListAdapter(getContext(),msgList);

        //listView.setAdapter(arrayAdapter);
        listView.setAdapter(adapter);

        request_user_name();

        add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---------
                /*
                Map<String,Object> map = new HashMap<String, Object>();
                map.put(room_name.getText().toString(),"");
                root.updateChildren(map);*/

                //第一層node之key
                //自定義key 活動ID
                Map<String,Object> map = new HashMap<String, Object>();
                map.put(room_name.getText().toString(),"");
                root.updateChildren(map);

                //第二層node之key
                //自定義key UserName
                DatabaseReference room_root = root.child(room_name.getText().toString());
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put(name,"");
                room_root.updateChildren(map2);

            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                msgList.clear();
                for (int i = 0; i < ActMain.iv_activitylist_I_initiate.size(); i++) {
                    String actId = String.valueOf(ActMain.iv_activitylist_I_initiate.get(i).getId());
                    //是否存在node child
                    if (dataSnapshot.hasChild(actId)) {
                        DataSnapshot shot = dataSnapshot.child(actId);
                        Iterator iterator = shot.getChildren().iterator();
                        while (iterator.hasNext()){
                            String roomKey = ((DataSnapshot)iterator.next()).getKey();
                            Msg msg1 = new Msg();
                            msg1.setActId(actId);
                            msg1.setRoomName(roomKey);
                            msg1.setUpdateTime("2017_03_02 05:00");
                            msgList.add(msg1);
                        }
                        Log.d("list", "發起活動測試" + actId + "。");
                    }
                }

                for (int j = 0; j < ActMain.iv_activitylist_I_join.size(); j++) {
                    String actId = String.valueOf(ActMain.iv_activitylist_I_join.get(j).getId());
                    if (dataSnapshot.hasChild(actId)){
                        DataSnapshot shot = dataSnapshot.child(actId);
                        Iterator iterator = shot.getChildren().iterator();
                        while (iterator.hasNext()){
                            String roomKey = ((DataSnapshot)iterator.next()).getKey();
                            if(roomKey.equals(CPublicParameters.user.getfUserName())){
                                Msg msg2 = new Msg();
                                msg2.setActId(actId);
                                msg2.setRoomName(roomKey);
                                msg2.setUpdateTime("2017_03_02 05:00");
                                msgList.add(msg2);
                            }
                        }
                        Log.d("list", "參加活動測試" + actId + "。");
                    }
                }
                adapter.notifyDataSetChanged();


                //---------
                /*msgList.clear();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()){
                    Msg msg1 = new Msg();
                    String key = ((DataSnapshot)i.next()).getKey();
                    msg1.setRoomName(key);
                    msg1.setUpdateTime("2017_03_02 05:00");
                    msgList.add(msg1);
                }
                adapter.notifyDataSetChanged();*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*
                Intent intent = new Intent(getContext(),Chat_Room.class);
                TextView txtview = ((TextView) view.findViewById(R.id.act_Title));
                String room_name = txtview.getText().toString();
                intent.putExtra("room_name",room_name );

                //intent.putExtra("room_name",((TextView)view).getText().toString() );
                intent.putExtra("user_name",name);
                startActivity(intent);*/
                Intent intent = new Intent(getContext(),Chat_Room.class);
                String act_id = msgList.get(i).getActId();
                String room_name = msgList.get(i).getRoomName();

                intent.putExtra("act_id", act_id );
                intent.putExtra("room_name",room_name );
                intent.putExtra("user_name",name);
                startActivity(intent);
            }
        });


        return view;
    }

    private void request_user_name() {
        name = CPublicParameters.user.getfUserName();
    }


    @Override
    public boolean onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            //外理返回键
            ActMain.navigation.setSelectedItemId(R.id.navigation_home);
            return true;
        } else {
            // 如果不包含子Fragment
            // 或子Fragment没有外理back需求
            // 可如直接 return false;
            // 注：如果Fragment/Activity 中可以使用ViewPager 代替 this
            //
            return BackHandlerHelper.handleBackPress(this);
        }
    }



    private Button add_room;
    private EditText room_name;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    //private List<CMessage> cMessageList = new ArrayList<CMessage>();
    //firebase先用String
    //private ArrayList<String> cMessageList = new ArrayList<String>();
    private List<Msg> msgList = new ArrayList<Msg>();
    private FragmentRoomListAdapter adapter;
    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
}
