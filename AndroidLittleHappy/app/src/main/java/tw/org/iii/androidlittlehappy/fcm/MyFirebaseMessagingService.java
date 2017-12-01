package tw.org.iii.androidlittlehappy.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by samblow2000 on 2017/11/29.
 */
//若螢幕是亮著的 前景
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        //Log.d("FCM", "From: " + remoteMessage.getFrom());
        Log.d("FCM", "前景通知");
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            //Log.d("FCM", "Message data payload: " + remoteMessage.getData());
            FCMFactory fcmFactory = new FCMFactory();
            String action = fcmFactory.chkAction(remoteMessage.getData());
            if(action.equals("活動通知")){
                String id = remoteMessage.getData().get("id");
                String userName = remoteMessage.getData().get("userName");
                Log.d("FCM", String.format("action:%s, id:%s, userName:%s", action, id, userName));
            }
            else if(action.equals("聊天通知")){
            }
            else{
            }

            /*
            if ( Check if data needs to be processed by long running job  true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }
            */

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("FCM", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
