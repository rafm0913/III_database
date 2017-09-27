package tw.kirisolin.androidlittlehappy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class NewActivity extends AppCompatActivity {

    private View.OnClickListener btnBack_Click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private SeekBar.OnSeekBarChangeListener seekBar_change = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            lblValidTime.setText((i*12/100)+"小時");
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
        setContentView(R.layout.activity_new);
        InitialComponet();
    }

    private void InitialComponet() {
        ActivityType=(Spinner) findViewById(R.id.ActivityType);
        final String[] typelist = {"咖啡", "電影", "飲料", "書", "交通"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(NewActivity.this,
                R.layout.myspinner,
                typelist);
        ActivityType.setAdapter(lunchList);

        btnBack=(Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(btnBack_Click);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(seekBar_change);
        lblValidTime=(TextView)findViewById(R.id.lblValidTime);

    }

    Spinner ActivityType;
    Button btnBack;
    SeekBar seekBar;
    TextView lblValidTime;
}
