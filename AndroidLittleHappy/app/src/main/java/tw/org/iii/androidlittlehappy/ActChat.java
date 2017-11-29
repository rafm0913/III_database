package tw.org.iii.androidlittlehappy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

public class ActChat extends AppCompatActivity {

    CMessageFactory CMF = new CMessageFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actchat);

        setRecycleVIew(CMF.GetAllCM());
    }


    private void setRecycleVIew(List<CMessage> myList){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
        ChatAdapter adapter = new ChatAdapter(myList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //設置監聽器
        adapter.setOnItemClickListener(new ChatAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("UI", "點擊短" + position);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d("UI", "點擊長" + position);
            }
        });
    }
}
