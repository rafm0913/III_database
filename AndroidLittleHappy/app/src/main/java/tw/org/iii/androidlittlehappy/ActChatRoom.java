package tw.org.iii.androidlittlehappy;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

public class ActChatRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actchatroom);
        //chatroomToolbar=(Toolbar)findViewById(R.id.chat_user_NickName);
       // chatroomToolbar.setTitle("小甜甜");
        btnSend = (Button)findViewById(R.id.btnSend);
        editMessege=(EditText)findViewById(R.id.editMessege);
        listView =(ListView)findViewById(R.id.listView_chatroom);
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right_messege);
        listView.setAdapter(chatArrayAdapter);
        editMessege.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);


        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }
    private boolean sendChatMessage() {
        chatArrayAdapter.add(new CMessage(side, editMessege.getText().toString()));
        editMessege.setText("");
        side = !side;
        return true;
    }

    //Toolbar chatroomToolbar;
    Button btnSend;
    EditText editMessege;
    ListView listView;
    boolean side = false;
    ChatArrayAdapter chatArrayAdapter;
}
