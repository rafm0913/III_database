package tw.org.iii.androidlittlehappy;

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
 * Created by samblow2000 on 2017/11/3.
 */

public class SearchAct extends AsyncTask<String, Void, String> {
    //"http://192.168.10.11:8080/DemoServer/UrlController?action=" + "select";
    //"http://52.198.163.90:8080/DemoServer/UrlController?action=" + "select";
    public static final String URL = "http://52.198.163.90:8080/DemoServer/UrlController?action=" + "select";
    JsonFactory jFactory = new JsonFactory();
    CActivityFactory factory = new CActivityFactory();

    double gpsx;
    double gpsY;

    public SearchAct(double myGpsX, double myGpsY){
        this.gpsx = myGpsX;
        this.gpsY = myGpsY;
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
            String params = jFactory.searchSringfy(CPublicParameters.user.getfUserName(), String.valueOf(this.gpsx), String.valueOf(this.gpsY), "5", "10");
            params = String.format("json=%s", params);
            bw.write(params);
            bw.flush();
            bw.close();
            System.out.printf("傳送JSON字串給Web Server => %s\n", params);
            //Log.d("test", params);

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
        //Log.d("search", output);


        //判斷串列是否空值
        if (ActMain.iv_activitylist_I_initiate.isEmpty() && ActMain.iv_activitylist_I_join.isEmpty() && ActMain.iv_activitylist_I_can_see.isEmpty()) {
            //json轉型完成後丟進公有靜態串列
            jFactory.searchParse(output, ActMain.iv_activitylist_I_initiate, ActMain.iv_activitylist_I_join, ActMain.iv_activitylist_I_can_see);
        }
        else{
            ActMain.iv_activitylist_I_initiate.clear();
            ActMain.iv_activitylist_I_join.clear();
            ActMain.iv_activitylist_I_can_see.clear();
            jFactory.searchParse(output, ActMain.iv_activitylist_I_initiate, ActMain.iv_activitylist_I_join, ActMain.iv_activitylist_I_can_see);
        }

        Log.d("search", "我發起的:" + String.valueOf(ActMain.iv_activitylist_I_initiate.size()));
        Log.d("search", "我參加的:" +String.valueOf(ActMain.iv_activitylist_I_join.size()));
        Log.d("search", "我看的見加星等限制:" +String.valueOf(ActMain.iv_activitylist_I_can_see.size()));

        //取得"iv_activitylist_I_can_see"中發起人的Name轉NickName的dictionary
        ActMain.Hashtable_UserNameToCust.clear();
        if (ActMain.iv_activitylist_I_can_see.size()>0)
        {

            for (int i =0; i<ActMain.iv_activitylist_I_can_see.size();i++)
            {
                String URLwithName = "http://52.198.163.90:8080/DemoServer/UrlCustController?action=selectUserByName&username=" + ActMain.iv_activitylist_I_can_see.get(i).getCreator().toString();
                //String URLwithName = "http://52.198.163.90:8080/DemoServer/UrlCustController?action=selectUserByName&username=" + "model002";
                AsyncTaskSelectUserReturnCustObject task = new AsyncTaskSelectUserReturnCustObject();
                task.execute(new String[]{URLwithName});
            }
        }

        
        /*
        //json轉型完成後回傳List<CA...>串列，再將串列丟進工廠裡的串列
        factory.SetAll(jFactory.parseList(output));

        //log測試輸出
        for (int i = 0; i < factory.GetAll().size(); i++) {
            Log.d("test4", "標題:" + factory.GetAll().get(i).getCreator());
        }
        ActMain.iv_activitylist = factory.GetAll();


        //測試我參加的活動、我看到的活動、我發起的活動
        ActMain.iv_activitylist_I_can_see.add(ActMain.iv_activitylist.get(9));
        Log.d("test5", "標題:" +ActMain.iv_activitylist_I_can_see.get(0).getTitle());
        ActMain.iv_activitylist_I_initiate.add(ActMain.iv_activitylist.get(7));
        ActMain.iv_activitylist_I_join.add(ActMain.iv_activitylist.get(8));
        //測試我參加的活動、我看到的活動、我發起的活動 END
        */


    }
}
