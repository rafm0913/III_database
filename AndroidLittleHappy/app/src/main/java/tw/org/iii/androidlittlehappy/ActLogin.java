package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActLogin extends AppCompatActivity {

    CCustomerFactory factory = new CCustomerFactory();

    private View.OnClickListener btnLogin_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //這段之後應該改為SQL語法
            for (CCustomers C:factory.GetAll())
            {
                if (C.getfUserName().equals(txtuserId.getText().toString()))
                {
                    if (C.getfPassword().equals(txtpassWD.getText().toString()))
                    {
                        Intent intent=new Intent(ActLogin.this,ActMain.class);

                        String userID = "";
                        userID = txtuserId.getEditableText().toString();
                        SharedPreferences setting=getSharedPreferences("loginInfo",MODE_PRIVATE);
                        setting.edit()
                                .putString(CDictionary.BK_LOGIN_INFOR_ID,userID)
                                .commit();
                        //CPublicParameters.user.setfUserName(userID);
                        startActivity(intent);
                    }
                    break;
                }
                txtpassWD.setText("");
                Toast.makeText(ActLogin.this, "使用者帳號/密碼不符",Toast.LENGTH_SHORT).show();
            }


        }
    };
    private View.OnClickListener btnLoginByGoogle_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    private View.OnClickListener btnLoginByFacebook_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    private View.OnClickListener btnCancel_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    //Model logIn =========11/26 刪除 Start
    private View.OnClickListener btnLoginModel002_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(ActLogin.this,ActMain.class);
            SharedPreferences setting=getSharedPreferences("loginInfo",MODE_PRIVATE);
            setting.edit()
                    .putString(CDictionary.BK_LOGIN_INFOR_ID,"model002")
                    .commit();
            CPublicParameters.user.setfUserName("model002");
            startActivity(intent);
        }
    };
    private View.OnClickListener btnLoginModel003_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(ActLogin.this,ActMain.class);
            SharedPreferences setting=getSharedPreferences("loginInfo",MODE_PRIVATE);
            setting.edit()
                    .putString(CDictionary.BK_LOGIN_INFOR_ID,"model003")
                    .commit();
            CPublicParameters.user.setfUserName("model003");
            startActivity(intent);
        }
    };
    private View.OnClickListener btnLoginModel004_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(ActLogin.this,ActMain.class);
            SharedPreferences setting=getSharedPreferences("loginInfo",MODE_PRIVATE);
            setting.edit()
                    .putString(CDictionary.BK_LOGIN_INFOR_ID,"model004")
                    .commit();
            CPublicParameters.user.setfUserName("model004");
            startActivity(intent);
        }
    };
    private View.OnClickListener btnLoginModel005_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(ActLogin.this,ActMain.class);
            SharedPreferences setting=getSharedPreferences("loginInfo",MODE_PRIVATE);
            setting.edit()
                    .putString(CDictionary.BK_LOGIN_INFOR_ID,"model005")
                    .commit();
            CPublicParameters.user.setfUserName("model005");
            startActivity(intent);
        }
    };
//Model logIn =========11/26 刪除 End


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actlogin);
        Initialcomponent();
    }

    private void Initialcomponent()
    {
        txtuserId = (EditText)findViewById(R.id.txtuserId);
        txtpassWD = (EditText)findViewById(R.id.txtpassWD);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnLogin_click);
        btnLoginByGoogle = (Button)findViewById(R.id.btnLoginByGoogle);
        btnLoginByGoogle.setOnClickListener(btnLoginByGoogle_click);
        btnLoginByFacebook = (Button)findViewById(R.id.btnLoginByFacebook);
        btnLoginByFacebook.setOnClickListener(btnLoginByFacebook_click);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(btnCancel_click);

        btnLoginModel002 = (Button)findViewById(R.id.btnLoginModel002);
        btnLoginModel002.setOnClickListener(btnLoginModel002_click);
        btnLoginModel003 = (Button)findViewById(R.id.btnLoginModel003);
        btnLoginModel003.setOnClickListener(btnLoginModel003_click);
        btnLoginModel004 = (Button)findViewById(R.id.btnLoginModel004);
        btnLoginModel004.setOnClickListener(btnLoginModel004_click);
        btnLoginModel005 = (Button)findViewById(R.id.btnLoginModel005);
        btnLoginModel005.setOnClickListener(btnLoginModel005_click);


    }
    EditText txtuserId;
    EditText txtpassWD;
    Button btnLogin;
    Button btnLoginByGoogle;
    Button btnLoginByFacebook;
    Button btnCancel;

    Button btnLoginModel002;
    Button btnLoginModel003;
    Button btnLoginModel004;
    Button btnLoginModel005;


}
