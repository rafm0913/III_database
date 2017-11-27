package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
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

/**
 * Created by kirisolin on 2017/11/27.
 */

public class AsyncTaskSelectUserReturnCustObject extends AsyncTask<String, Void, String> {

    CCustomers returnCust = null;
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
        returnCust = jFactoryFCust.parse(output);
        ActMain.Dictionary_UserNameToCust.put(returnCust.getfUserName(),returnCust);
    }
}
