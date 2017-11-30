package tw.org.iii.androidlittlehappy.fcm;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import tw.org.iii.androidlittlehappy.ActMain;

/**
 * Created by samblow2000 on 2017/11/29.
 */

public class MyInstanceIDService extends FirebaseInstanceIdService {
    public static String token;
    /*
    The registration token may change when:
        The app deletes Instance ID
        The app is restored on a new device
        The user uninstalls/reinstall the app
        The user clears app data.
    */
    @Override
    public void onTokenRefresh() {

        // Get updated InstanceID token.
        this.token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences setting=getSharedPreferences(ActMain.KEY,MODE_PRIVATE);
        saveInShare(setting);
        Log.d("FCM", "Refreshed token: " + this.token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }

    void saveInShare(SharedPreferences setting){
        setting.edit()
                .putString("Token",this.token)
                .commit();
    }

}
