package tw.org.iii.androidlittlehappy;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by iii on 2017/11/27.
 */

public class ActHistoryInitiate extends AppCompatActivity {


    TextView lblinitiatetitle;
    TextView lblinitiatecontent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acthistoryinitiate);
        lblinitiatetitle = (TextView)findViewById(R.id.lblinitiatetitle);
        lblinitiatecontent = (TextView)findViewById(R.id.lblinitiatecontent);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            lblinitiatetitle.setText(bundle.getString("title"));

            for (int i = 0; i < ActMain.iv_activitylist_I_can_see.size(); i++) {
                if (lblinitiatetitle.getText().toString().equalsIgnoreCase(ActMain.iv_activitylist_I_can_see.get(i).getTitle())) {

                    lblinitiatecontent.setText(ActMain.iv_activitylist_I_can_see.get(i).getContent());






                }


            }

        }
    }
}


