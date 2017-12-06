package tw.org.iii.androidlittlehappy.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import tw.org.iii.androidlittlehappy.ActMain;
import tw.org.iii.androidlittlehappy.CPublicParameters;
import tw.org.iii.androidlittlehappy.R;

/**
 * Created by samblow2000 on 2017/11/29.
 */
//若螢幕是亮著的 前景
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String actId ="";
    String roomName ="";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        //Log.d("FCM", "From: " + remoteMessage.getFrom());
        Log.d("FCM", "前景通知");
        // Check if message contains a data payload.
        String action ="";
        if (remoteMessage.getData().size() > 0) {
            //Log.d("FCM", "Message data payload: " + remoteMessage.getData());
            FCMFactory fcmFactory = new FCMFactory();
            action = fcmFactory.chkAction(remoteMessage.getData());
            if(action.equals("活動通知")){
                String id = remoteMessage.getData().get("id");
                actId = id;
                String userName = remoteMessage.getData().get("userName");
                roomName = userName;
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
        if (remoteMessage.getNotification() != null && action.equals("活動通知")) {
            Log.d("FCM", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }else if(remoteMessage.getNotification() != null && action.equals("wantJoin通知")){
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
            Log.d("FCM", "wantJoin通知 Message Notification Body: " + remoteMessage.getNotification().getBody());
        }else if(remoteMessage.getNotification() != null && action.equals("agree通知")){
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
            Log.d("FCM", "agree通知 Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        else if(remoteMessage.getNotification() != null && action.equals("refuse通知")){
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
            Log.d("FCM", "refuse通知 Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    private void sendNotification(String messageTitle,String messageBody) {
        Intent intent = new Intent(this, Chat_Room.class);
        intent.putExtra("act_id", actId);
        intent.putExtra("room_name", roomName);
        intent.putExtra("user_name",CPublicParameters.user.getfUserName());


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.applogo2)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }



    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
