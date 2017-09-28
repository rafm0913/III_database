package tw.kirisolin.androidlittlehappy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActMain extends AppCompatActivity {

    private View.OnClickListener btnNewActivity_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActMain.this,NewActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener btnProfile_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActMain.this,ActProfile.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener btnLogOut_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences setting=getSharedPreferences("loginInfo",MODE_PRIVATE);

            setting.edit()
                    .clear()
                    .commit();
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actmain);

        Initialcomponent();


    }

    private void Initialcomponent()
    {
        btnLogOut = (Button)findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(btnLogOut_click);
        btnNewActivity=(Button) findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(btnNewActivity_Click);
        btnProfile=(Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(btnProfile_Click);


    }
    Button btnLogOut;
    Button btnNewActivity;
    Button btnProfile;

}
