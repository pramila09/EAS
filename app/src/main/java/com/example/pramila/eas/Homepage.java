package com.example.pramila.eas;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class Homepage extends AppCompatActivity {

        private static final int CONNECTION_TIMEOUT = 10000;
        private static final int READ_TIMEOUT = 15000;

        GridLayout mainGrid;
        int backButtonCount = 0;
        String sessionid;
        Button logout;
        //UserSessionManager session;

        private SharedPreferences preferences;
        private SharedPreferences.Editor editor;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_homepage);

          //  session = new UserSessionManager(getApplicationContext());

                mainGrid = (GridLayout) findViewById(R.id.mainGrid);

                Intent i = getIntent();
                sessionid = i.getStringExtra("sessionid");

                /*String cookies = preferences.getString("cookies",null);
                if(cookies!=null){
                       Intent intent = new Intent(Homepage.this,MainActivity.class);
                       startActivity(intent);
                }*/

                //Set Event
                setSingleEvent(mainGrid);
                //setToggleEvent(mainGrid);

               // if(session.checkLogin())
                      //  finish();
                /*Toast.makeText(getApplicationContext(),
                       "User Login Status: " + session.isUserLoggedIn(),
                        Toast.LENGTH_LONG).show();
                if(session.checkLogin())
                       finish();

                HashMap<String, String> user = session.getUserDetails();

                // get name
                String sessionid = user.get(UserSessionManager.KEY_NAME);*/

        }

        private void setToggleEvent(GridLayout mainGrid) {
                //Loop all child item of Main Grid
                for (int i = 0; i < mainGrid.getChildCount(); i++) {
//You can see , all child item is CardView , so we just cast object to CardView
                        final CardView cardView = (CardView) mainGrid.getChildAt(i);
                        cardView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                                                //Change background color
                                                cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                                                Toast.makeText(Homepage.this, "State : True", Toast.LENGTH_SHORT).show();

                                        } else {
                                                //Change background color
                                                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                                Toast.makeText(Homepage.this, "State : False", Toast.LENGTH_SHORT).show();
                                        }
                                }
                        });
                }
        }

        private void setSingleEvent(GridLayout mainGrid) {
                //Loop all child item of Main Grid
                for (int i = 0; i < mainGrid.getChildCount(); i++) {
                        //You can see , all child item is CardView , so we just cast object to CardView
                        CardView cardView = (CardView) mainGrid.getChildAt(i);
                        final int finalI = i;
                        cardView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                        if (finalI == 0) {

                                                Intent intent = new Intent(Homepage.this, EventActivity.class);
                                                intent.putExtra("info", "This is activity from card item index  " + finalI);
                                                startActivity(intent);

                                        } else if (finalI == 1) {
                                                Intent intent = new Intent(Homepage.this, Attendance.class);
                                                startActivity(intent);
                                        } else if (finalI == 2) {
                                                Intent intent = new Intent(Homepage.this, profile.class);
                                            intent.putExtra("sessionid", sessionid);
                                                startActivity(intent);
                                        } else if (finalI == 3) {
                                                Intent intent = new Intent(Homepage.this, leave.class);
                                                intent.putExtra("sessionid", sessionid);
                                                startActivity(intent);
                                        } else if (finalI == 4) {
                                                Intent intent = new Intent(Homepage.this, notification.class);
                                                startActivity(intent);

                                        }
                                }
                        });
                }
        }

        @Override
        public void onBackPressed() {
                if (backButtonCount >= 1) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                } else {
                        Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
                        backButtonCount++;
                }
        }
}



