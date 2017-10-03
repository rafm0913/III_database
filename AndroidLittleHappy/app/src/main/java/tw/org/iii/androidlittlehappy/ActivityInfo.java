package tw.org.iii.androidlittlehappy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityInfo extends AppCompatActivity {

    private View.OnClickListener btnBack_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityinfo);
        InitialComponet();
    }

    private void InitialComponet() {

        btnBack=(Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(btnBack_Click);
    }

    Button btnBack;
    Button btnInterest;
}
