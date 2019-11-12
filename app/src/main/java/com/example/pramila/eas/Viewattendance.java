package com.example.pramila.eas;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Viewattendance extends AppCompatActivity {
    String urladdress="http://"+Server.address+"/admin/viewattendanceapp.php";
    String sessionid;
    String[] Enrolltime;
    String[] Enrolldate;
    Activity context;
    CustomAttendanceView customAttendanceView;
    ListView listView;
    BufferedInputStream is;
    String line=null;
    String result=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewattendance);

        Intent intent = getIntent();
        sessionid = intent.getStringExtra("sessionid");
        Log.e("eas", "sessionid:" + sessionid);
        listView=(ListView)findViewById(R.id.lview3);


        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        //new AsyncCollect().execute();
        CollectData();



    }
    public void CollectData() {
        new AsyncCollect().execute();
    }

    class AsyncCollect extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
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
                result = ex.getMessage();


            }
            return result;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Log.e("eas","result"+result);


            try {
                JSONArray ja = new JSONArray(result);
                JSONObject jo = null;
                ja = new JSONArray(result);
                Enrolldate = new String[ja.length()];
                Enrolltime = new String[ja.length()];
                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    Enrolldate[i] = jo.getString("EnrollDate");
                    Enrolltime[i] = jo.getString("EnrollTime");

                }

                customAttendanceView = new CustomAttendanceView(Viewattendance.this,Enrolldate,Enrolltime);
                listView.setAdapter(customAttendanceView);

            } catch (Exception ex) {
                Log.e("eas","exception"+ex.getMessage());

                ex.printStackTrace();

            }
        }
    }
}



