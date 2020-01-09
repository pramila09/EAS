package com.example.pramila.eas;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

   // private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        //String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN),recentToken);
        editor.commit();
        //Log.d(TAG, "Refreshed token: " + refreshedToken);
       // sendRegistrationToServer(recentToken);
    }

   /* private void storeToken(String token) {
        //saving the token on shared preferences
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }*/
   private void sendRegistrationToServer(String token)
   {

   }
}