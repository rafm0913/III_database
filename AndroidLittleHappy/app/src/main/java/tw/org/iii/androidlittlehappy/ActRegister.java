package tw.org.iii.androidlittlehappy;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActRegister extends AppCompatActivity {

    public static final String URL = "http://52.198.163.90:8080/DemoServer/UrlController?action=" + "userregister";

    //按下吉祥物，換吉祥物圖片
    private View.OnClickListener imgLuckyPic_click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentIcons fi = new FragmentIcons();
            fi.show(getFragmentManager(),"Tag");
        }
    };

    //結束第二次輸入密碼時，進行前後密碼比對
    private View.OnFocusChangeListener txtpassWDCheck_endEdit = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (!hasFocus)
            {
                if ("".equals(txtpassWD.getText()))
                {

                    Toast.makeText(ActRegister.this, "請輸入密碼", Toast.LENGTH_SHORT).show();
                }
                else if (txtpassWD.getText().equals(txtpassWDCheck.getText()))
                {
                    Toast.makeText(ActRegister.this, "重複輸入密碼與密碼不相符", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    //有效時間
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



    private View.OnClickListener btnRegister_click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Job1 task = new Job1();
            task.execute(new String[] { URL });
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
                cust.setfEmail(txtEmail.getText().toString());
                cust.setfNickName(txtuserNickName.getText().toString());
                cust.setfMascot(txtLuckyPic.getText().toString());
                cust.setfDefaultStar((int)rtbStar.getRating());
                cust.setfDefaultTime(seekBar.getProgress());

                //-----------傳送JSON字串給Web Server(JSONServer3.jsp)-------------//
                //使用org.json API 製作 JSON字串

                JSONArray ary = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("fID", 1);
                obj.put("fUserName", cust.getfUserName());
                obj.put("fPassword", cust.getfPassword());
                obj.put("fEmail", cust.getfEmail());
                obj.put("fNickName", cust.getfNickName());
                obj.put("fMascot", cust.getfMascot());
                obj.put("fDefaultStar", cust.getfDefaultStar());
                obj.put("fDefaultTime", cust.getfDefaultTime());
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
            Toast.makeText(ActRegister.this, "背景工作開始執行", Toast.LENGTH_LONG).show();
        }

        //背景工作執行之後
        @Override
        protected void onPostExecute(String output) {
            //super.onPostExecute(output);
            Toast.makeText(ActRegister.this, "背景工作執行完成\n" + output, Toast.LENGTH_SHORT).show();
        }
    }


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
        txtUserName = (EditText)findViewById(R.id.txtUserName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtpassWD = (EditText)findViewById(R.id.txtpassWD);
        txtpassWDCheck = (EditText)findViewById(R.id.txtpassWDCheck);
        txtpassWDCheck.setOnFocusChangeListener(txtpassWDCheck_endEdit);
        txtuserNickName = (EditText)findViewById(R.id.txtuserNickName);

        imgLuckyPic = (ImageView)findViewById(R.id.imgLuckyPic);
        imgLuckyPic.setOnClickListener(imgLuckyPic_click);
        txtLuckyPic = (TextView)findViewById(R.id.txtLuckyPic);


        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(btnRegister_click);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(btnCancel_click);

        rtbStar =(RatingBar)findViewById(R.id.rtbStar);
        rtbStar.setRating(3);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(seekBar_change);
        lblValidTime=(TextView)findViewById(R.id.lblValidTime);

    }
    EditText txtUserName;
    EditText txtEmail;
    EditText txtpassWD;
    EditText txtpassWDCheck;
    EditText txtuserNickName;

    ImageView imgLuckyPic;
    TextView txtLuckyPic;

    Button btnRegister;
    Button btnCancel;

    RatingBar rtbStar;
    SeekBar seekBar;
    TextView lblValidTime;

}
