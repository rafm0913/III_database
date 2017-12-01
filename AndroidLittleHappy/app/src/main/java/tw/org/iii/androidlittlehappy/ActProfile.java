package tw.org.iii.androidlittlehappy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;

public class ActProfile extends AppCompatActivity {

    private SeekBar.OnSeekBarChangeListener seekBarChange=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actprofile);
        findViews();
    }

    protected void onResume() {
        super.onResume();
        //拿取firebase背景之包裹
        if (getIntent().getExtras() != null) {
            /*
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d("FCM", "Key: " + key + " Value: " + value);
                //清除key
                getIntent().removeExtra(key);
            }*/
            /*
            getIntent().getExtras().getString("google.sent_time");//時間
            getIntent().getExtras().getString("from");//firebase 寄件者ID
            getIntent().getExtras().getString("google.message_id");//firebase messageid
            getIntent().getExtras().getString("collapse_key");//packageName*/
            String action = getIntent().getExtras().getString("actNotification");
            String id = getIntent().getExtras().getString("id");
            String userName = getIntent().getExtras().getString("userName");
            Log.d("FCM", String.format("action:%s, id:%s, userName:%s", action, id, userName));
        }else{
            Log.d("FCM", "沒有包裹");
        }
    }

    private void findViews() {
        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        profile_pic.setImageResource(R.drawable.banana);

        ratingBar =(RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize((float) 0.5);
        ratingBar.setRating((float) 2.5);

        seekBar=(SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChange);
    }
    ImageView profile_pic;
    RatingBar ratingBar;
    SeekBar seekBar;
}
