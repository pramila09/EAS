package com.example.pramila.eas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.example.pramila.eas.JSONParser.json;


public class profile extends AppCompatActivity {
    TextView tvname, tvemail, tvdept, tvaddress, tvreg;
    String sessionid;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private static final String TAG_PROFILE = "user";
    // private static final String TAG_ID = "id";
    private static final String TAG_USERNAME = "Fname";
    private static final String TAG_EMAIL = "Emailid";
    private static final String TAG_ADDRESS = "District";
    private static final String TAG_DEPARTMENT = "Department";
    private static final String TAG_REGDATE = "regdate";
    String result = null;
    private static final String PROFILE_URL = "http://"+Server.address+"/admin/profile.php";
    HttpResponse httpResponse;
    JSONObject display;
    JSONArray user;
    //JSONParser jsonParser = new JSONParser();
    String StringHolder = "";
    static JSONArray jarr = null;
    InputStream is = null;
    String line = null;
    private ProgressDialog pDialog;
    String ngalan;
    //UserSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //session = new UserSessionManager(getApplicationContext());

        Intent intent = getIntent();
        sessionid = intent.getStringExtra("sessionid");
        Log.e("eas", "sessionid:" + sessionid);

        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);

        tvname = (TextView) findViewById(R.id.tvname);
        tvemail = (TextView) findViewById(R.id.tvemail);
        tvdept = (TextView) findViewById(R.id.tvdept);
        tvaddress = (TextView) findViewById(R.id.tvaddress);
        tvreg = (TextView) findViewById(R.id.tvreg);


        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncLogout().execute();


            }
        });

        /*Button changepassword = (Button) findViewById(R.id.changepassword);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, Changepassword.class);
                intent.putExtra("sessionid", sessionid);
                startActivity(intent);
            }
        });*/

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("sessionid")) {
            ngalan = extras.getString("sessionid");
            // Loading Profile in Background Thread



            new LoadProfile().execute();
        }
       // getJSON("http://192.168.1.98:8080/final/final/admin/profile.php");

    }
    class LoadProfile extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(profile.this);
            pDialog.setMessage("Loading profile ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Profile JSON
         */
        protected String doInBackground(String... args) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://" + Server.address + "/admin/profile.php");


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
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.setRequestProperty("Cookie", sessionid);

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
        protected void onPostExecute (String json){
            super.onPostExecute(json);
            Log.e("eas","json" + json);
                    // dismiss the dialog after getting all products
            pDialog.dismiss();
            try {
                display = new JSONObject(json);
                JSONArray Empid = display.getJSONArray("Empid");
                JSONObject jb = Empid.getJSONObject(0);
                String name = jb.getString("Fname");
                String email = jb.getString("emailid");
                String address = jb.getString("District");
                String department = jb.getString("department");
                String regdate = jb.getString("regdate");

                        // displaying all data in textview

               tvname.setText(name);
               tvemail.setText(email);
               tvdept.setText(department);
               tvaddress.setText(address);
               tvreg.setText(regdate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private class AsyncLogout extends AsyncTask<String, String,String> {
        ProgressDialog pdLoading = new ProgressDialog(profile.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://"+Server.address+"/admin/logout.php");


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }

            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoOutput(true);
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
            try{
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
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        // this method will interact with UI, display result sent from doInBackground method
        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();
            // if(result.equals("Success! This message is from PHP")) {
            //   textPHP.setText(result.toString());
            //}else{
            // you to understand error returned from doInBackground method
            super.onPostExecute(result);

            Log.e("eas", result);

            Toast.makeText(profile.this, result.toString(), Toast.LENGTH_LONG).show();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent i = new Intent(profile.this, MainActivity.class);
            startActivity(i);
        }
    }


}













