package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActLogin extends AppCompatActivity {
    String URL = "http://52.198.163.90:8080/DemoServer/UrlCustController?action=" + "UserLogin";

    public static String loginState = "";
    CCustomerFactory factory = new CCustomerFactory();

    private View.OnClickListener btnLogin_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            //按下登入
            if (txtUserName.getText().toString().length()<1 || txtpassWD.getText().toString().length()<1)
            {
                Toast.makeText(ActLogin.this, "登入測試用空白帳號，11/28移除空白帳號，如未刪除call伯誠", Toast.LENGTH_SHORT).show();
                //空白登入帳號，11/28移除 start
                Intent intent=new Intent(ActLogin.this,ActMain.class);
                String userID = txtUserName.getEditableText().toString();
                SharedPreferences setting=getSharedPreferences("loginInfo",MODE_PRIVATE);
                setting.edit()
                        .putString(CDictionary.BK_LOGIN_INFOR_ID,userID)
                        .commit();
                CPublicParameters.user.setfUserName(userID);
                startActivity(intent);
                //空白登入帳號，11/28移除 end
                //以下正式版本 11/28移除註解
//                Toast.makeText(ActLogin.this, "帳號/密碼不得為空，請確認", Toast.LENGTH_SHORT).show();
            }
            else {
                Job1 task = new Job1();
                task.execute(new String[]{URL});
            }



            //這段之後應該改為SQL語法
//            for (CCustomers C:factory.GetAll())
//            {
//                if (C.getfUserName().equals(txtUserName.getText().toString()))
//                {
//                    if (C.getfPassword().equals(txtpassWD.getText().toString()))
//                    {
//                        Intent intent=new Intent(ActLogin.this,ActMain.class);
//
//                        String userID = "";
//                        userID = txtUserName.getEditableText().toString();
//                        SharedPreferences setting=getSharedPreferences("loginInfo",MODE_PRIVATE);
//                        setting.edit()
//                                .putString(CDictionary.BK_LOGIN_INFOR_ID,userID)
//                                .commit();
//                        CPublicParameters.user.setfUserName(userID);
//                        startActivity(intent);
//                    }
//                    break;
//                }
//                txtpassWD.setText("");
//                Toast.makeText(ActLogin.this, "使用者帳號/密碼不符",Toast.LENGTH_SHORT).show();
//            }


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


    //AsynkTask背景程式
    class  Job1 extends AsyncTask<String, Void, String> {

        //背景工作方法
        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url: urls) {
                output = getOutputFromUrl(url);
            }

            return output;
        }

        private String getOutputFromUrl(String url) {
            StringBuffer output = new StringBuffer("");
            try {
                InputStream stream = getHttpConnection(url);
                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(stream));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output.append(s);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return output.toString();
        }

        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            HttpURLConnection conn = null;
            BufferedWriter bw = null;
            java.net.URL url = new URL(urlString);
            try {
                conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setDoOutput(true);//預設false
                conn.setDoInput(true); //預設true
                conn.setRequestMethod("POST");//POST必須大寫

                CCustomers cust = new CCustomers();
                cust.setfUserName(txtUserName.getText().toString());
                cust.setfPassword(txtpassWD.getText().toString());


                //-----------傳送JSON字串給Web Server(JSONServer3.jsp)-------------//
                //使用org.json API 製作 JSON字串

                JSONArray ary = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("fID", 99);
                obj.put("fUserName", cust.getfUserName());
                obj.put("fPassword", cust.getfPassword());
                obj.put("fEmail", "");
                obj.put("fNickName", "");
                obj.put("fMascot", "");
                obj.put("fDefaultStar", "");
                obj.put("fDefaultTime", "");
                obj.put("fStar", "");

                ary.put(obj);

                String params = String.format("json=%s", ary.toString());

                OutputStream os =  conn.getOutputStream();
                bw = new BufferedWriter( new OutputStreamWriter(os) );
                bw.write(params);
                bw.flush();
                bw.close();
                System.out.printf("傳送JSON字串給Web Server(JSONServer3.jsp) => %s\n", params);
                Log.i("test", params);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = conn.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

        //背景工作執行之前
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            //Toast.makeText(ActLogin.this, "背景工作開始執行", Toast.LENGTH_LONG).show();
        }

        //背景工作執行之後
        @Override
        protected void onPostExecute(String output) {
            //super.onPostExecute(output);

            //Toast.makeText(ActLogin.this, "背景工作執行完成\n" + output, Toast.LENGTH_SHORT).show();
            JsonFactoryForCust jFactoryFCust = new JsonFactoryForCust();
            CPublicParameters.user = jFactoryFCust.parse(output);

            loginState = String.valueOf(CPublicParameters.user.getfID());
            if ("1".equals(loginState)) {
                Toast.makeText(ActLogin.this, "登入成功，轉接中，請稍後", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActLogin.this, ActMain.class);
                String userID = txtUserName.getEditableText().toString();
                SharedPreferences setting = getSharedPreferences("loginInfo", MODE_PRIVATE);
                setting.edit()
                        .putString(CDictionary.BK_LOGIN_INFOR_ID, userID)
                        .putString(CDictionary.BK_JSON_LOGIN_INFOR_CUST,output)
                        .commit();
                CPublicParameters.user.setfUserName(userID);
                startActivity(intent);
            } else {
                Toast.makeText(ActLogin.this, "登入失敗，請確認帳號/密碼", Toast.LENGTH_SHORT).show();
            }
        }
    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actlogin);
        Initialcomponent();
    }

    private void Initialcomponent()
    {
        txtUserName = (EditText)findViewById(R.id.txtUserName);
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
    EditText txtUserName;
    EditText txtpassWD;
    Button btnLogin;
    Button btnLoginByGoogle;
    Button btnLoginByFacebook;
    Button btnCancel;

}
