package tw.org.iii.androidlittlehappy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class ActWelcome extends AppCompatActivity {

    private static final int GoActMain = 0;

    private Handler mHandler = new Handler(){
      public void handleMessage(android.os.Message msg){
          switch (msg.what){
              case GoActMain:
                  Intent intent = new Intent();
                  intent.setClass(ActWelcome.this, ActAppLogo.class);
                  startActivity(intent);
                  finish();
                  break;
              default:
                  break;
          }
      }
    };

    //check internet method
    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actwelcome);
        Initialcomponent();

        mHandler.sendEmptyMessageDelayed(GoActMain, 2000);

    }
    private void Initialcomponent(){
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnRegister=(Button)findViewById(R.id.btnRegister);


    }
    Button btnLogin;
    Button btnRegister;
}
