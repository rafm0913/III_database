package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewActivity extends AppCompatActivity {
    //"http://192.168.10.11:8080/DemoServer/UrlController?action=" + "test1";
    //"http://52.198.163.90:8080/DemoServer/UrlController?action=" + "test1";
    public static final String URL = "http://52.198.163.90:8080/DemoServer/UrlController?action=" + "test1";
    CActivityFactory factory = new CActivityFactory();

    private View.OnClickListener btnBack_Click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    View.OnClickListener btnNewActivity_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Job1 task = new Job1();
            task.execute(new String[] { URL });
            //庭翊
            Mapfragment2.activityTitle = txtTitle.getText().toString();

            Intent intent = new Intent(NewActivity.this,ActMain.class);
            startActivity(intent);



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
            URL url = new URL(urlString);
            try {
                conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setDoOutput(true);//預設false
                conn.setDoInput(true); //預設true
                conn.setRequestMethod("POST");//POST必須大寫


                CActivitys act1 = new CActivitys();
                act1.setTitle(txtTitle.getText().toString());
                act1.setContent(txtContent.getText().toString());
                //-----------傳送JSON字串給Web Server(JSONServer3.jsp)-------------//
                //使用org.json API 製作 JSON字串
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                JSONArray ary = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("id", 1);
                obj.put("type", 1);
                obj.put("title", act1.getTitle());
                obj.put("content", act1.getContent());
                obj.put("createTime", sdf.format(cal.getTime()));
                obj.put("limitTime", "10");
                obj.put("limitStar", "5");
                obj.put("gpsX", x);
                obj.put("gpsY", y);
                obj.put("state", "活動已發起");
                obj.put("creator", "1");
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
            Toast.makeText(NewActivity.this, "背景工作開始執行", Toast.LENGTH_LONG).show();
        }

        //背景工作執行之後
        @Override
        protected void onPostExecute(String output) {
            //super.onPostExecute(output);
            Toast.makeText(NewActivity.this, "背景工作執行完成\n" + output, Toast.LENGTH_SHORT).show();
        }
    }

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
        Bundle bundle = getIntent().getExtras();
        x = bundle.getDouble("gpsX");
        y = bundle.getDouble("gpsY");
        Toast.makeText(NewActivity.this, "x:" + x  + "  y:" + y, Toast.LENGTH_SHORT).show();
    }

    private void InitialComponet() {
        ActivityType=(Spinner) findViewById(R.id.ActivityType);
        final String[] typelist = {"咖啡", "電影", "飲料", "書", "交通"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(NewActivity.this,
                R.layout.myspinner,
                typelist);
        ActivityType.setAdapter(lunchList);



        txtTitle = (EditText)findViewById(R.id.txtTitle);
        txtContent = (EditText)findViewById(R.id.txtContent);
        btnBack=(Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(btnBack_Click);
        btnNewActivity = (Button) findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(btnNewActivity_click);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(seekBar_change);
        lblValidTime=(TextView)findViewById(R.id.lblValidTime);


    }
    double x =0;
    double y=0;
    EditText txtTitle;
    EditText txtContent;
    Spinner ActivityType;
    Button btnBack;
    Button btnNewActivity;
    SeekBar seekBar;
    TextView lblValidTime;
    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

}
