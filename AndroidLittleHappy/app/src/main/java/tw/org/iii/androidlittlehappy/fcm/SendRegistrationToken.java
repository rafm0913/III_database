package tw.org.iii.androidlittlehappy.fcm;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import tw.org.iii.androidlittlehappy.CPublicParameters;

/**
 * Created by samblow2000 on 2017/11/30.
 */

public class SendRegistrationToken extends AsyncTask<String, Void, String> {
    //"http://192.168.10.11:8080/DemoServer/UrlController?action=" + "select";
    //"http://52.198.163.90:8080/DemoServer/UrlController?action=" + "select";
    public static final String URL = "http://52.198.163.90:8080/DemoServer/FCMController?action=" + "compare";

    String token;

    public SendRegistrationToken(String myToken){
        this.token = myToken;
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
            FCMFactory fcmFactory = new FCMFactory();
            String params = fcmFactory.StringfyUT(CPublicParameters.user.getfUserName(), token);
            params = String.format("json=%s", params);
            bw.write(params);
            bw.flush();
            bw.close();
            //System.out.printf("傳送JSON字串給Web Server => %s\n", params);
            Log.d("FCM", params);

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
        Log.d("FCM", output);
    }
}

