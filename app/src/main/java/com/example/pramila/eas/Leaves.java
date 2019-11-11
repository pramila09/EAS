package com.example.pramila.eas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class Leaves extends AppCompatActivity {

    String urladdress="http://"+Server.address+"/admin/leaves.php";
    String[] Fromdate;
    String[] Todate;
    String[] Leavetype;
    String[] Description;
    String[] Status;
    ListView listView;
    BufferedInputStream is;
    String line=null;
    String result=null;
    String sessionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves);

        Intent intent = getIntent();
        sessionid = intent.getStringExtra("sessionid");
        Log.e("eas", "sessionid:" + sessionid);
        listView=(ListView)findViewById(R.id.lview2);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        //new AsyncCollect().execute();
        CollectData();
        CustomLeaveView customLeaveView = new CustomLeaveView(this,Fromdate,Todate,Leavetype,Description,Status);
        listView.setAdapter(customLeaveView);


    }
    public void CollectData(){

        try {

            URL url = new URL(urladdress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Cookie",sessionid);
            is = new BufferedInputStream(con.getInputStream());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //content
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();

        } catch (Exception ex) {
            ex.printStackTrace();


    }
//JSON
        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
            Fromdate = new String[ja.length()];
            Todate = new String[ja.length()];
            Leavetype = new String[ja.length()];
            Description = new String[ja.length()];
            Status = new String[ja.length()];

            for (int i = 0; i <= ja.length(); i++) {
                jo = ja.getJSONObject(i);
                Fromdate[i] = jo.getString("fromdate");
                Todate[i] = jo.getString("todate");
                Leavetype[i] = jo.getString("leavetype");
                Description[i] = jo.getString("description");
                Status[i] = jo.getString("status");
            }
        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }
}




