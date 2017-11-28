package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class ActAppLogo extends AppCompatActivity {

    private View.OnClickListener btnLogin_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActAppLogo.this, ActLogin.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener btnRegister_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActAppLogo.this, ActRegister.class);
            startActivity(intent);
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actapplogo);
        Initialcomponent();
        SharedPreferences setting = getSharedPreferences("loginInfo", MODE_PRIVATE);
        if(setting.getString(CDictionary.BK_LOGIN_INFOR_ID,"")!="")
        {
            JsonFactoryForCust JFFC = new JsonFactoryForCust();
            CPublicParameters.user = JFFC.parse(setting.getString(CDictionary.BK_JSON_LOGIN_INFOR_CUST,""));
            //CPublicParameters.user.setfUserName(setting.getString(CDictionary.BK_LOGIN_INFOR_ID,""));
            Intent intent = new Intent(ActAppLogo.this, ActMain.class);
            startActivity(intent);
        }

    }

    private void Initialcomponent()
    {
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnLogin_click);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(btnRegister_click);

    }
    Button btnLogin;
    Button btnRegister;


}
