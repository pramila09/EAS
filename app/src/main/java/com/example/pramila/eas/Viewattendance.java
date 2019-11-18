package com.example.pramila.eas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Handler;

public class Viewattendance extends AppCompatActivity {
    String urladdress="http://"+Server.address+"/admin/viewattendanceapp.php";
    String sessionid;
    String[] enrolltime;
    String[] enrolldate;
    Activity context;
    CustomAttendanceView customAttendanceView;
    ListView listView;
    BufferedInputStream is;
    String line=null;
    String result=null;
    EditText eText;
    DatePickerDialog picker;
    Button btnGet;
    private ArrayAdapter adapter;
    private static final String TAG = "Viewattendance";


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

        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         final Calendar cldr = Calendar.getInstance();
                                         int day = cldr.get(Calendar.DAY_OF_MONTH);
                                         int month = cldr.get(Calendar.MONTH);
                                         int year = cldr.get(Calendar.YEAR);
                                         // date picker dialog
                                         picker = new DatePickerDialog(Viewattendance.this,
                                                 new DatePickerDialog.OnDateSetListener() {
                                                     @Override
                                                     public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                         eText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                                     }
                                                 }, year, month, day);
                                         picker.show();
                                     }
                                 });

        eText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                Log.e("eas",cs.toString());
                // When user changed the Text
                //Viewattendance.this.customAttendanceView.getFilter().filter(cs);
                Viewattendance.this.customAttendanceView.getFilter().filter("2017");
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });



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
                con.setRequestProperty("Cookie", sessionid);
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("eas", "result:" + result);

            try {
                JSONArray ja = new JSONArray(result);
                JSONObject jo = null;
                enrolldate = new String[ja.length()];
                enrolltime = new String[ja.length()];

                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    enrolldate[i] = jo.getString("EnrollDate");
                    enrolltime[i] = jo.getString("EnrollTime");
                }
                customAttendanceView = new CustomAttendanceView(Viewattendance.this, enrolldate, enrolltime);
                listView.setAdapter(customAttendanceView);





            } catch (JSONException e) {
                Log.e("eas", "exception" + e.getMessage());
                e.printStackTrace();
            }



        }   }
}



