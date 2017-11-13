package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Objects;

public class NewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //"http://192.168.10.11:8080/DemoServer/UrlController?action=" + "test1";
    //"http://52.198.163.90:8080/DemoServer/UrlController?action=" + "test1";
    public static final String URL = "http://52.198.163.90:8080/DemoServer/UrlController?action=" + "test1";
    String inputActivityDetailMethod = "";

    CActivityFactory factory = new CActivityFactory();

    private View.OnClickListener btnSpeech_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            inputActivityDetailMethod = "speech";
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"請說出活動名稱....");
            try {
                startActivityForResult(intent,REQ_CODE_SPEECH_OUTPUT);
            }catch (RemoteViews.ActionException tim){
                //////just put an toast if google mic is not opened.

            }

        }
    };

    private View.OnClickListener btnQRcode_Click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            inputActivityDetailMethod = "QRcode";
            IntentIntegrator scanIntegrator = new IntentIntegrator(NewActivity.this);
            scanIntegrator.initiateScan();
        }
    };
//    public void onActivityResult(int requestCode, int resultCode, Intent intent){
//        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//        if(scanningResult!=null){
//
//            JsonFactory jFactory = new JsonFactory();
//            CActivityFactory factory = new CActivityFactory();
//
//            String scanContent=scanningResult.getContents();
//            String scanFormat=scanningResult.getFormatName();
//            String strQRcodeMsg = scanContent;
//            CActivitys activity =jFactory.parse(strQRcodeMsg);
//            txtTitle.setText(activity.getTitle());
//            txtContent.setText(activity.getContent());
//        }else{
//            Toast.makeText(getApplicationContext(),"nothing",Toast.LENGTH_SHORT).show();
//        }
//    }




    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



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
            //Mapfragment2.activityTitle = txtTitle.getText().toString();





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
                act1.setType(spinActivityType.getSelectedItemPosition());
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
            Intent intent = new Intent(NewActivity.this,ActMain.class);
            startActivity(intent);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if  (inputActivityDetailMethod.equals("speech")) {

            switch (requestCode) {
                case REQ_CODE_SPEECH_OUTPUT: {
                    if (resultCode == RESULT_OK && null != data) {
//                    String resultsString = "";
//
//                    // 取得 STT 語音辨識的結果段落
//                    ArrayList results = data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
//
//                    // 語音辨識的每個段落
//                    for( int i = 0; i < results.size(); i++ )
//                    {
//                        // 一個段落可拆解為多個字組
//                        String[] resultWords = results.get(i).split(" ");
//
//                        for( int j = 0; j < resultWords.length; j++ )
//                        {
//                            resultsString += resultWords[j] + ":";
//                        }
//                    }
                        ArrayList<String> voiceText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        txtTitle.setText(voiceText.get(0));
                        // ShowText2.setText(voiceText.get(1));
                    }
                    break;
                }
                case REQ_CODE_SPEECH_OUTPUT2: {
                    if (resultCode == RESULT_OK && null != data) {
//                    String resultsString = "";
//
//                    // 取得 STT 語音辨識的結果段落
//                    ArrayList results = data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
//
//                    // 語音辨識的每個段落
//                    for( int i = 0; i < results.size(); i++ )
//                    {
//                        // 一個段落可拆解為多個字組
//                        String[] resultWords = results.get(i).split(" ");
//
//                        for( int j = 0; j < resultWords.length; j++ )
//                        {
//                            resultsString += resultWords[j] + ":";
//                        }
//                    }
                        ArrayList<String> voiceText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        txtContent.setText(voiceText.get(0));
                    }
                    break;
                }
            }
            if ("".equals(txtContent.getText().toString())) {
                Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent2.putExtra(RecognizerIntent.EXTRA_PROMPT, "請說出活動內容....");
                try {
                    startActivityForResult(intent2, REQ_CODE_SPEECH_OUTPUT2);
                } catch (RemoteViews.ActionException tim) {
                    //////just put an toast if google mic is not opened.

                }
            }
        }
        else if (inputActivityDetailMethod.equals("QRcode")) {

            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {

                JsonFactory jFactory = new JsonFactory();
                CActivityFactory factory = new CActivityFactory();

                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                String strQRcodeMsg = scanContent;

                String[] items = new String[1];
                try
                {
                    items = strQRcodeMsg.split("@");
                }
                catch (Exception ex)
                {

                }
                if(items.length == 2)
                {
                    txtTitle.setText(items[0]);
                    txtContent.setText(items[1]);
                }

            }
            else {
                Toast.makeText(getApplicationContext(), "nothing", Toast.LENGTH_SHORT).show();
            }
        }
    }


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
        spinActivityType =(Spinner) findViewById(R.id.spinActivityType);
        //final String[] typelistString = {"共乘", "分享", "吃", "咖啡", "折扣","服飾","買一送一","電影"};

        Hashtable test = new Hashtable();
        test.put("共乘",String.valueOf(R.drawable.type_sharetaxi));
        test.put("分享",String.valueOf(R.drawable.type_share));
        test.put("吃",String.valueOf(R.drawable.type_eat));
        test.put("咖啡",String.valueOf(R.drawable.type_coffee));
        test.put("折扣",String.valueOf(R.drawable.type_discount));
        test.put("服飾",String.valueOf(R.drawable.type_dress));
        test.put("買一送一",String.valueOf(R.drawable.type_50percentoff));
        test.put("電影",String.valueOf(R.drawable.type_movie));


        String[] typelistString = new String[test.size()];
        int[] typelistImg = new int[test.size()];
        int i = 0;

        Log.d("test", String.valueOf(test.size()));


        for (Object key : test.keySet()){

            typelistImg[i] = Integer.valueOf(test.get(key).toString());
            typelistString[i] = key.toString();
            i++;
            Log.d("test", key + " : " + test.get(key));

        }


//        int[] typelistImg = {R.drawable.type_sharetaxi,R.drawable.type_share,R.drawable.type_eat,
//                R.drawable.type_coffee,R.drawable.type_discount,R.drawable.type_dress,
//                R.drawable.type_50percentoff,R.drawable.type_movie};




//        ArrayAdapter<String> typeList = new ArrayAdapter<>(NewActivity.this,
//                R.layout.myspinner,
//                typelistString);
//        spinActivityType.setAdapter(typeList);



        // spinActivityType.setOnItemSelectedListener(this);
        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),typelistImg,typelistString);


        spinActivityType.setAdapter(customAdapter);





        txtTitle = (EditText)findViewById(R.id.txtTitle);
        txtContent = (EditText)findViewById(R.id.txtContent);
        btnBack=(Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(btnBack_Click);
        btnNewActivity = (Button) findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(btnNewActivity_click);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(seekBar_change);
        lblValidTime=(TextView)findViewById(R.id.lblValidTime);
        btnSpeech = (Button)findViewById(R.id.btn_speech);
        btnSpeech.setOnClickListener(btnSpeech_Click);
        btnQRcode = (Button)findViewById(R.id.btnQRcode);
        btnQRcode.setOnClickListener(btnQRcode_Click);




    }
    double x =0;
    double y=0;
    EditText txtTitle;
    EditText txtContent;
    Spinner spinActivityType;
    Button btnBack;
    Button btnNewActivity;
    SeekBar seekBar;
    TextView lblValidTime;
    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    Button btnSpeech;
    Button btnQRcode;
    private  final  int REQ_CODE_SPEECH_OUTPUT = 143;
    private  final  int REQ_CODE_SPEECH_OUTPUT2 = 145;
}
