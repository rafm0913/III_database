package tw.org.iii.androidlittlehappy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by iii on 2017/11/27.
 */

public class ActHistoryJoin extends AppCompatActivity {


    TextView lblinitiatetitle;
    TextView lblinitiatecontent;
    TextView lblinitiatecreator;
    ImageView imginitiatecreator;
    TextView lbltime;
    ImageView imgtype;


    @Override
    public void onBackPressed() {
        finish();
        Log.v("onback", "onback(ActHistoryJoin)");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acthistoryinitiate);
        lblinitiatetitle = (TextView) findViewById(R.id.lblinitiatetitle);
        lblinitiatecontent = (TextView) findViewById(R.id.lblinitiatecontent);
        lblinitiatecreator = (TextView) findViewById(R.id.lblinitiatecreator);
        imginitiatecreator = (ImageView) findViewById(R.id.imginitiatemember);
        imgtype = (ImageView)findViewById(R.id.imgtype);
        lbltime = (TextView)findViewById(R.id.lbltime);


        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {


            lblinitiatetitle.setText(bundle.getString("456"));


            for (int i = 0; i < ActMain.iv_activitylist_I_join.size(); i++) {
                if (lblinitiatetitle.getText().toString().equalsIgnoreCase(ActMain.iv_activitylist_I_join.get(i).getTitle())) {

                    String initiatorID = ActMain.iv_activitylist_I_join.get(i).getCreator().toString();
                    int mascotID = Integer.valueOf(ActMain.Hashtable_UserNameToCust.get(initiatorID).getfMascot());
                    imginitiatecreator.setImageResource(CPublicParameters.images[mascotID]);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = null;
                    try {
                        date = sdf.parse(ActMain.iv_activitylist_I_join.get(i).getCreateTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    lbltime.setText(sdf.format(date));
                    int picTypeIndex = Integer.parseInt(ActMain.iv_activitylist_I_join.get(i).getType());
                    int picTypeImgID = ActMain.typelistImg[picTypeIndex-1];

                    imgtype.setImageResource(picTypeImgID);

                    lblinitiatetitle.setText(ActMain.iv_activitylist_I_join.get(i).getTitle());

                    lblinitiatecreator.setText(ActMain.iv_activitylist_I_join.get(i).getCreator());

                    lblinitiatecontent.setText("活動備註: "+""+ActMain.iv_activitylist_I_join.get(i).getContent());


                }


            }



        }
    }

}


