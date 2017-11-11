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

    }
    EditText txtuserId;
    EditText txtpassWD;
    Button btnLogin;
    Button btnLoginByGoogle;
    Button btnLoginByFacebook;
    Button btnCancel;


}
