package tw.org.iii.androidlittlehappy;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by samblow2000 on 2017/11/22.
 */

public class JoinAct extends AsyncTask<String, Void, String> {
    //"http://192.168.1.67:8080/DemoServer/UrlController?action=" + "join";
    //"http://52.198.163.90:8080/DemoServer/UrlController?action=" + "join";

    public static final String URL = "http://52.198.163.90:8080/DemoServer/UrlController?action=" + "join";
    JsonFactory jFactory = new JsonFactory();
    CActivityFactory factory = new CActivityFactory();
    int actId;
    private Activity mContext;

    public JoinAct(int myAid, Activity context){
        this.actId = myAid;
        mContext = context;
    }

    //背景工作方法
    @Override
    protected String doInBackground(String... urls) {
        String output = null;
        for (String url : urls) {
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

            OutputStream os =  conn.getOutputStream();
            bw = new BufferedWriter( new OutputStreamWriter(os) );
            String params = jFactory.joinStringfy(actId, CPublicParameters.user.getfUserName());
            params = String.format("json=%s", params);
            bw.write(params);
            bw.flush();
            bw.close();
            Log.d("initiate", params);

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
        //Toast.makeText(ActMain.this, "背景工作開始執行", Toast.LENGTH_LONG).show();
    }

    //背景工作執行之後
    @Override
    protected void onPostExecute(String output) {
        //super.onPostExecute(output);
        //Toast.makeText(NewActivity.this, "背景工作執行完成\n" + output, Toast.LENGTH_SHORT).show();
        Log.d("initiate", output);
       // Toast.makeText(, "參加活動完成", Toast.LENGTH_LONG).show();

        Toast.makeText(mContext, "我有興趣成功", Toast.LENGTH_SHORT).show();

        mContext.finish();




        //json轉型完成後回傳List<CA...>串列，再將串列丟進工廠裡的串列

        //log測試輸出




    }
}
