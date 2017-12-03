package tw.org.iii.androidlittlehappy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by iii on 2017/11/27.
 */

public class ActHistorySeen extends AppCompatActivity {


    TextView lblinitiatetitle;
    TextView lblinitiatecontent;
    TextView lblinitiatecreator;
    ImageView imginitiatecreator;


    @Override
    public void onBackPressed() {
        finish();
        Log.v("onback", "onback(ActHistorySeen)");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acthistoryinitiate);
        lblinitiatetitle = (TextView) findViewById(R.id.lblinitiatetitle);
        lblinitiatecontent = (TextView) findViewById(R.id.lblinitiatecontent);
        lblinitiatecreator = (TextView) findViewById(R.id.lblinitiatecreator);
        imginitiatecreator = (ImageView) findViewById(R.id.imginitiatemember);


        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {


            lblinitiatetitle.setText(bundle.getString("789"));


            for (int i = 0; i < ActMain.iv_activitylist_I_can_see.size(); i++) {
                if (lblinitiatetitle.getText().toString().equalsIgnoreCase(ActMain.iv_activitylist_I_can_see.get(i).getTitle())) {

                    lblinitiatetitle.setText(ActMain.iv_activitylist_I_can_see.get(i).getTitle());

                    lblinitiatecreator.setText(ActMain.iv_activitylist_I_can_see.get(i).getCreator());

                    lblinitiatecontent.setText(ActMain.iv_activitylist_I_can_see.get(i).getContent());


                }


            }



        }
    }

}

