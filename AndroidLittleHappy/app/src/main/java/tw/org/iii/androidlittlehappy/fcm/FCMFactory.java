package tw.org.iii.androidlittlehappy.fcm;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import tw.org.iii.androidlittlehappy.Dictionary;

/**
 * Created by samblow2000 on 2017/11/30.
 */

public class FCMFactory {

    public FCMFactory(){

    }


    //User and Token
    public String StringfyUT(String myUserName, String myToken){
        JSONObject objInsert = new JSONObject();
        JSONObject chkKey = new JSONObject();
        try {
            //
            objInsert.put("userName", myUserName);
            objInsert.put("token", myToken);

            //Key
            chkKey.put(Dictionary.key, objInsert);
        }
        catch (JSONException e){
            System.out.println("錯誤" +  e.toString());
        }
        return chkKey.toString();
    }

    public String chkAction(Map<String, String> myFCMMap){
        return myFCMMap.get("actNotification");
    }

}
