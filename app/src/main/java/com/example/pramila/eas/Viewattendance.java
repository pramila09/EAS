package com.example.pramila.eas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;

public class Viewattendance extends AppCompatActivity {
    String urladdress="http://"+Server.address+"/project/admin/viewattendanceapp.php";
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
    Button buttonsearch;
    private ArrayAdapter adapter;
    String tempdate;
    int i;
    private static final String TAG = "Viewattendance";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    String address="http://"+Server.address+"/project/admin/viewyearattendanceapp.php";
    HttpURLConnection urlConnection = null;
    private DatePickerDialog.OnDateSetListener mDataSetListener;
    private TextView mDisplayDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewattendance);

        Intent intent = getIntent();
        sessionid = intent.getStringExtra("sessionid");
        Log.e("eas", "sessionid:" + sessionid);
        listView=(ListView)findViewById(R.id.lview3);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);

        for (i = 2010; i <= thisYear; i++)
        {
            years.add(Integer.toString(i));
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, years);


        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        //new AsyncCollect().execute();
        CollectData();
/*
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
*/

        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Viewattendance.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDataSetListener,
                        year, month, day);

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }

        });

        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDataSet: yy/mm/dd:" + year + "-" + month + "-" + day);
                String date = year + "-" + month + "-" + day;
                mDisplayDate.setText(date);

            }
        };

      buttonsearch=(Button)findViewById(R.id.buttonsearch);
      buttonsearch.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              getdata();
              InsertData(tempdate);

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



        }
    }
    public void getdata(){
        tempdate=mDisplayDate.getText().toString();
        Log.e("date:",tempdate);
    }
    public void InsertData(final String date){


        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            HttpURLConnection conn;
            URL url = null;

            @Override
            protected String doInBackground(String... params) {
                String dateholder = date;
                Log.e("dateholder",dateholder);
                try {

                    url = new URL(address);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e("eas",e.getMessage());
                    return "exception";
                }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("year", dateholder));
                //content
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Cookie", sessionid);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("year", dateholder);
                    String query = builder.build().getEncodedQuery();
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));

                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();
                    conn.connect();

                } catch (ClientProtocolException e) {

                } catch (IOException e) {

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
                    Log.e("eas", e.getMessage());
                    return "exception";
                } finally {
                    conn.disconnect();
                }

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.e("eas", "result:" + result);
                Log.e("eas", result);

                Toast.makeText(Viewattendance.this, "Search Successful", Toast.LENGTH_LONG).show();
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
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(date);






        }
    }




