package tw.org.iii.androidlittlehappy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
