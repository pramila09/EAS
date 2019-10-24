package com.example.pramila.eas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class profile extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private TextView tvemail, tvdept, tvaddress, tvreg, tvname;

    private static final String TAG = "profile";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    int backButtonCount=0;

    private static final String url = "http://192.168.1.119:8080/EmpAdmin.profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        tvemail = findViewById(R.id.tvemail);
        tvname = findViewById(R.id.tvname);
        tvdept = findViewById(R.id.tvdept);
        tvreg = findViewById(R.id.tvreg);
        tvaddress = findViewById(R.id.tvaddress);


        tvemail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        tvname.setText(SharedPrefManager.getInstance(this).getKeyUsername());
        tvdept.setText(SharedPrefManager.getInstance(this).getKeyDepartment());
        tvaddress.setText(SharedPrefManager.getInstance(this).getKeyAddress());
        tvreg.setText(SharedPrefManager.getInstance(this).getKeyRegisteredDate());

        BottomNavigationView NavBot = (BottomNavigationView) findViewById(R.id.NavBot);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavBot);
        bottomNavigationView.setSelectedItemId(R.id.Profile);
        NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Home:
                        Intent a = new Intent(profile.this, Homepage.class);
                        startActivity(a);
                        break;
                    case R.id.Apply_Leave:
                        Intent b = new Intent(profile.this, leave.class);
                        startActivity(b);

                        break;
                    case R.id.Notification:
                        Intent c = new Intent(profile.this, notification.class);
                        startActivity(c);
                        break;
                    case R.id.Profile:
                        break;
                }
                return false;
            }
        });


        Button logout = (Button) findViewById(R.id.logout);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //loadProfile();
    }

    public void logout(View v) {
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        Intent i = new Intent(profile.this, MainActivity.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}



    /*private void loadProfile() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




            }
        })
    }
}




    /*public void getusername(){
        final String name = tvname.getText().toString();
        new Asyncprofile().execute(name);

    }
    public void getemail(){
        final String email = tvemail.getText().toString();
        new Asyncprofile().execute(email);


    }
    public void getdepartment(){
        final String department = tvdept.getText().toString();
        new Asyncprofile().execute(department);


    }
    public void getaddress(){
        final String address = tvaddress.getText().toString();
        new Asyncprofile().execute(address);


    }
    public void getregistereddate(){
        final String registereddate = tvreg.getText().toString();
        new Asyncprofile().execute(registereddate);


    }

    private class Asyncprofile<GetJSON> extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://192.168.1.98:8080/EmpAdmin/profile.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("department", params[1])
                        .appendQueryParameter("address", params[2])
                        .appendQueryParameter("registrationdate",params[3]);

                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            tvemail.setText(result);
            tvdept.setText(result);
            tvaddress.setText(result);
            tvreg.setText(result);

            Toast.makeText(getApplicationContext(), "result:" + result, Toast.LENGTH_LONG).show();
            Log.e("eas", "Result: " + result);
            Log.e("eas", "true found" + result);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread



        }



    }
}*/










