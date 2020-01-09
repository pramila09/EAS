package com.example.pramila.eas;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Belal on 03/11/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String notificationString;
    public static String dato;
    String type = "";
    private MyNotificationManager myNotificationManager;

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       /* Bundle bundle = new Bundle();
        bundle.putString("msgBody", remoteMessage.getNotification().getBody());

        Intent new_intent = new Intent();
        new_intent.setAction("ACTION_STRING_ACTIVITY");
        new_intent.putExtra("msg", bundle);

        sendBroadcast(new_intent);


        super.onMessageReceived(remoteMessage);
        Map<String, String> data= remoteMessage.getData();
        Log.d("FROM", remoteMessage.getFrom());
       // sendNotification(notification,d)
        String message = remoteMessage.getNotification().getBody();
        String title = remoteMessage.getNotification().getTitle();
        //String click_action = remoteMessage.getNotification().getClickAction();

*/

        //  sendNotification(remoteMessage.getNotification().getBody());

        Map<String, String> data = remoteMessage.getData();
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                String storedData = remoteMessage.getData().toString();
                //  JSONObject json = new JSONObject(storedData);

                JSONArray arr = new JSONArray(storedData);
                JSONObject jObj = arr.getJSONObject(0);
                String title = jObj.getString("title");
                String message = jObj.getString("body");
                sendPushNotification(jObj);

                final String MY_PREFS_NAME = "MyFCMFile";
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("Title", title);
                editor.putString("Message", message);
                editor.apply();

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        } else {

            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            String click_action = remoteMessage.getNotification().getClickAction();

            // Intent intent = new Intent(this, notification.class);

            Intent intent = new Intent(click_action);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setContentTitle(remoteMessage.getData().get("title"));
            notificationBuilder.setContentText(remoteMessage.getData().get("message"));
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setContentIntent(pendingIntent);

            final String MY_PREFS_NAME = "MyFCMFile";
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("Title", title);
            editor.putString("Message", message);
            editor.apply();

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());


        }
    }

    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            final String MY_PREFS_NAME = "MyFCMFile";

            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("Title", title);
            editor.putString("Message", message);
            editor.apply();

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
            // CREATE CLICK ACTION

            //creating an intent for the notification

            Intent intent = new Intent(getApplicationContext(), notification.class);

            //if there is no image
            if (imageUrl.equals("null")) {
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            } else {
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}
      /* if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                //JSONObject json = new JSONObject(remoteMessage.getData().toString());
                //sendPushNotification(json);
                String storedData = remoteMessage.getData().toString();
                JSONArray arr = new JSONArray(storedData);
                JSONObject jObj = arr.getJSONObject(0);
                String title=jObj.getString("title");
                String message = jObj.getString("body");
                sendPushNotification(jObj);
                final String MY_PREFS_NAME="MyFCMFile";
                SharedPreferences.Editor editor=getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
                editor.putString("Title",title);
                editor.putString("Message",message);
                editor.apply();
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
       else{
           String title = remoteMessage.getNotification().getTitle();
           String message = remoteMessage.getNotification().getBody();
           String click_action=remoteMessage.getNotification().getClickAction();

          // Intent in = new Intent(click_action);
           Intent in = new Intent("intentKey");

           in.putExtra("Key",messageB);
           LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);

           in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, in, PendingIntent.FLAG_ONE_SHOT);
           NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
           notificationBuilder.setContentTitle(title);
           notificationBuilder.setContentText(message);
           notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
           notificationBuilder.setAutoCancel(true);
           notificationBuilder.setContentIntent(pendingIntent);
          final String MY_PREFS_NAME="MyFCMFile";
          SharedPreferences.Editor editor=getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
          editor.putString("Title",title);
          editor.putString("Message",message);
          editor.apply();
          NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
          notificationManager.notify(0,notificationBuilder.build());
       }
    }
    */


    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
   /* private void sendNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            //if there is no image
            if(imageUrl.equals("null")){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            }else{
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
*/
    /*@Override
    public void onMessgaeReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();

        Intent intent = new Intent(this, notification.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
*/

  /*  private void sendNotification(String messageBody)
    {
        Intent intent = new Intent(this, notification.class);
        //intent.putExtra("meesage",messageBody);
       // startActivity(intent);
       // TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //stackBuilder.addParentStack(notification.class);
        //stackBuilder.addNextIntent(intent);

        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1001,intent,0);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSmallIcon(R.drawable.ic_stat_name);
        notificationBuilder.setContentTitle("Employee Attendance");
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());


    }*/
     /*   if(remoteMessage.getData().size() >0){
            type="json";
            sendNotification(remoteMessage.getData().toString());
        }
        if(remoteMessage.getNotification() !=null) {
            type = "message";
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }
    public void sendNotification(String messageBody) {
        String id="";
        String message="";
        String title="";

        if (type.equals("json")){
            try {
                JSONObject jsonObject = new JSONObject(messageBody);
                id=jsonObject.getString("id");
                message=jsonObject.getString("message");
                title= jsonObject.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (type.equals("message")) {
            message= messageBody;
        }
        Intent i = new Intent(MyFirebaseMessagingService.this,notification.class);
        i.putExtra("id",id);
        i.putExtra("message",message);
        i.putExtra("title",title);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
        Log.d("LOGTA", "NOTIFICACION RECIBIDA");
        Log.d("LOGTAG", "Título:" + title);
        Log.d("LOGTAG", "Texto: " + message);
        String dato = message;
        i.putExtra("MENSAJE", dato);
        Log.e("Mensajito", dato);

    }

}*/
