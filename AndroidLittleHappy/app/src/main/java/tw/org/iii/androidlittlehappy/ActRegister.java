package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActRegister extends AppCompatActivity {

    private View.OnClickListener btnLuckyPic_click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private void changeStarstate() {
        

    }

    private View.OnClickListener btnStar1_click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeStarstate();
        }
    };

    private View.OnClickListener btnStar2_click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener btnStar3_click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener btnStar4_click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener btnStar5_click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener btnLogin_click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener btnCancel_click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actregister);
        Initialcomponent();
    }

    private void Initialcomponent()
    {
        txtuserId = (EditText)findViewById(R.id.txtuserId);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtpassWD = (EditText)findViewById(R.id.txtpassWD);
        txtpassWDCheck = (EditText)findViewById(R.id.txtpassWDCheck);
        txtuserName = (EditText)findViewById(R.id.txtuserName);

        btnLuckyPic = (Button)findViewById(R.id.btnLogin);
        btnLuckyPic.setOnClickListener(btnLuckyPic_click);
        btnStar1 = (Button)findViewById(R.id.btnStar1);
        btnStar1.setOnClickListener(btnStar1_click);
        btnStar2 = (Button)findViewById(R.id.btnStar2);
        btnStar2.setOnClickListener(btnStar2_click);
        btnStar3 = (Button)findViewById(R.id.btnStar3);
        btnStar3.setOnClickListener(btnStar3_click);
        btnStar4 = (Button)findViewById(R.id.btnStar4);
        btnStar4.setOnClickListener(btnStar4_click);
        btnStar5 = (Button)findViewById(R.id.btnStar5);
        btnStar5.setOnClickListener(btnStar5_click);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnLogin_click);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(btnCancel_click);

    }
    EditText txtuserId;
    EditText txtEmail;
    EditText txtpassWD;
    EditText txtpassWDCheck;
    EditText txtuserName;

    Button btnLuckyPic;
    Button btnStar1;
    Button btnStar2;
    Button btnStar3;
    Button btnStar4;
    Button btnStar5;
    Button btnLogin;
    Button btnCancel;
}
