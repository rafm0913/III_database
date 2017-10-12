package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class NewActivity extends AppCompatActivity {

    public static final String URL = "http://52.196.15.186:8080/MyServerTest/MyServlet";
    CActivityFactory factory = new CActivityFactory();
    CActivitys act1;
    int count = 0;

    private View.OnClickListener btnBack_Click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    View.OnClickListener btnNewActivity_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String URLTest = null;
            try {
                inputToAct1();
                URLTest = URL + "?id=" + act1.getId() + "&title=" + act1.getTitle() + "&content=" + act1.getContent();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Job1 task = new Job1();
            task.execute(new String[] { URLTest });
        }
    };

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

        //串流轉換
        private String getOutputFromUrl(String url) {
            StringBuffer output = new StringBuffer("");
            try {
                InputStream stream = getHttpConnection(url);
                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(stream, "utf-8"));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output.append(s);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return output.toString();
        }

        //接收從Server端傳回來的值
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString) throws IOException {
            InputStream stream = null;
            java.net.URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }


        @Override
        protected void onPostExecute(String output) {
            Toast.makeText(NewActivity.this,output, Toast.LENGTH_SHORT).show();
        }
    }

    void  inputToAct1() throws UnsupportedEncodingException {
        String title = URLEncoder.encode(txtTitle.getText().toString(), "utf-8");
        String content = URLEncoder.encode(txtContent.getText().toString(), "utf-8");
        count++;
        act1 = factory.GetCurrent(count, title, content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        InitialComponet();
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

    EditText txtTitle;
    EditText txtContent;
    Spinner ActivityType;
    Button btnBack;
    Button btnNewActivity;
    SeekBar seekBar;
    TextView lblValidTime;
}
