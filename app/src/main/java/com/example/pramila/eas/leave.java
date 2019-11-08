package com.example.pramila.eas;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.List;

public class leave extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "leave";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private TextView mDisplayDate, mDisplayDate2;
    Spinner spinner1;
    EditText description;
    private DatePickerDialog.OnDateSetListener mDataSetListener;
    private DatePickerDialog.OnDateSetListener mDataSetListener1;
    String tempfromdate, temptodate, temptype, tempdescription, tempempid;
    Button btnapply;
    InputStream is = null;
    String result = null;
    String line = null;
    String[] leavetype;
    HttpURLConnection urlConnection = null;
    String sessionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        Intent intent = getIntent();
        sessionid = intent.getStringExtra("sessionid");
        Log.e("eas", "sessionid:" + sessionid);

        description = findViewById(R.id.description);

        btnapply = findViewById(R.id.btnapply);

        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        leave.this,
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
                Log.d(TAG, "onDataSet: mm/dd/yy:" + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        mDisplayDate2 = (TextView) findViewById(R.id.tvDate2);
        mDisplayDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        leave.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDataSetListener1,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDataSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDataSet: mm/dd/yy:" + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mDisplayDate2.setText(date);
            }
        };


        spinner1 = findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);

        new spinnertask().execute();


        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getdata();
                InsertData(tempfromdate, temptodate, temptype, tempdescription);

                //createleave();

            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getdata() {
        tempfromdate = mDisplayDate.getText().toString();
        temptodate = mDisplayDate2.getText().toString();
        temptype = spinner1.getSelectedItem().toString();
        temptype = String.valueOf(spinner1.getSelectedItem());
        tempdescription = description.getText().toString();
    }

    public void InsertData(final String fromdate, final String todate, final String leavetype, final String description) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            HttpURLConnection conn;
            URL url = null;

            @Override
            protected String doInBackground(String... params) {

                String fromdateholder = fromdate;
                String todateholder = todate;
                String typeholder = leavetype;
                String descriptionholder = description;

                try {
                    url = new URL("http://"+Server.address+"/final/final/admin/leave.php");
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e("eas",e.getMessage());

                    return "exception";
                }
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("fromdate", fromdateholder));
                nameValuePairs.add(new BasicNameValuePair("todate", todateholder));
                nameValuePairs.add(new BasicNameValuePair("leavetype", typeholder));
                nameValuePairs.add(new BasicNameValuePair("description", descriptionholder));
                try {
                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Cookie", sessionid);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("fromdate", fromdateholder)
                            .appendQueryParameter("todate", todateholder)
                            .appendQueryParameter("leavetype", typeholder)
                            .appendQueryParameter("description", descriptionholder);
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
                        return (sessionid + result.toString());

                    } else {

                        return ("unsuccessful");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("eas",e.getMessage());
                    return "exception";
                } finally {
                    conn.disconnect();
                }

            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Log.e("eas", result);

                Toast.makeText(leave.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(fromdate, todate, description);


    }

    public class spinnertask extends AsyncTask<Void, Void, Void> {
        final List<String> list1 = new ArrayList<String>();
        HttpURLConnection httpURLConnection;
        URL url = null;




        @Override
        protected Void doInBackground(Void...arg0 ) {

            try {
                URL url = new URL("http://"+Server.address+"/final/final/admin/spinner.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                is = urlConnection.getInputStream();

            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
               // Toast.makeText(getApplicationContext(), "Invalid IP address", Toast.LENGTH_LONG).show();
                finish();
            }


            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (Exception e) {
                Log.e("Fail 2", e.toString());
            }
            try {
                JSONArray JA = new JSONArray(result);
                JSONObject json = null;
                leavetype = new String[JA.length()];
                for (int i = 0; i < JA.length(); i++) {
                    json = JA.getJSONObject(i);
                    leavetype[i] = json.getString("leavetype");

                }

                Toast.makeText(getApplicationContext(), "Data Loaded", Toast.LENGTH_LONG).show();



            } catch (Exception e) {

                Log.e("Fail 3", e.toString());

            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            spinner_fn();
        }
    }
    private void spinner_fn() {
// TODO Auto-generated method stub

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, leavetype);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);

    }

}

