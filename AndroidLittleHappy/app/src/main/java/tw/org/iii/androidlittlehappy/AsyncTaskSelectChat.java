package tw.org.iii.androidlittlehappy;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kirisolin on 2017/12/1.
 */

public class AsyncTaskSelectChat extends AsyncTask<String, Void, String> {


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
        Log.i("Async", output);
        JsonFactoryForChat factoryForChat = new JsonFactoryForChat();
        CPublicParameters.Hashtable_UserNameToCMessage = factoryForChat.parseToHashtable(output);
        CPublicParameters.List_CMessage = factoryForChat.parseToList(output);
        //Log.i("Async", CPublicParameters.List_CMessage.get(0).toString());



//        //將工廠內的database更新為撈下來的data
        CMessageFactory cMessageFactory = new CMessageFactory();

        ChatAdapter chatAdapter = new ChatAdapter();



        if (CPublicParameters.List_CMessage.size()>0)
        {

            for (int i =0; i<CPublicParameters.List_CMessage.size();i++)
            {
                String user2Name =
                        CPublicParameters.List_CMessage.get(i).getfChatFrom().equals(CPublicParameters.user.getfUserName())?CPublicParameters.List_CMessage.get(i).getfChatTo():CPublicParameters.List_CMessage.get(i).getfChatFrom();
                String URLwithName = "http://52.198.163.90:8080/DemoServer/UrlCustController?action=selectUserByName&username=" + user2Name;
                //String URLwithName = "http://52.198.163.90:8080/DemoServer/UrlCustController?action=selectUserByName&username=" + "model002";
                AsyncTaskSelectUserReturnCustObject task = new AsyncTaskSelectUserReturnCustObject();
                task.execute(new String[]{URLwithName});
            }
        }

    }
}
