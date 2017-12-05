package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActivityInfo extends AppCompatActivity {

    private View.OnClickListener btnBack_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    View.OnClickListener btnJoin_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /*
            JoinAct joinAct = new JoinAct(actId, ActivityInfo.this);
            Log.d("initiate", "new 成功");
            joinAct.execute(new String[] { JoinAct.URL });
            Log.d("initiate", "執行續");*/

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityinfo);
        InitialComponet();


        Intent intent = getIntent();
        Bundle bund = intent.getExtras();

        this.actId = bund.getInt("actId");
        Log.d("initiate", String.valueOf(actId));
        String userName = CPublicParameters.user.getfUserName();
        Log.d("initiate", userName);
    }

    private void InitialComponet() {

//        btnBack=(Button) findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(btnBack_Click);
        btnJoin = (Button)findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(btnJoin_Click);
    }

    int actId;
    Button btnBack;
    Button btnInterest;
    Button btnJoin;
}
