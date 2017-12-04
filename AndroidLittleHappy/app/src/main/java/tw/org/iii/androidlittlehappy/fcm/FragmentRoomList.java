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

        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list_of_rooms);
        adapter = new FragmentRoomListAdapter(getContext(),cMessageList);

        //listView.setAdapter(arrayAdapter);
        listView.setAdapter(adapter);

        request_user_name();

        add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                map.put(room_name.getText().toString(),"");
                root.updateChildren(map);

            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                list_of_rooms.clear();
                list_of_rooms.addAll(set);
                arrayAdapter.notifyDataSetChanged();

                cMessageList.clear();
                cMessageList.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(),Chat_Room.class);

                TextView txtview = ((TextView) view.findViewById(R.id.act_Title));
                String room_name = txtview.getText().toString();
                intent.putExtra("room_name",room_name );

                //intent.putExtra("room_name",((TextView)view).getText().toString() );
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
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    //private List<CMessage> cMessageList = new ArrayList<CMessage>();
    //firebase先用String
    private ArrayList<String> cMessageList = new ArrayList<String>();
    private FragmentRoomListAdapter adapter;
    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
}
