package com.example.pramila.eas;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class leave extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "leave";

    private TextView mDisplayDate, mDisplayDate2;
    Spinner spinner1;
    EditText description;
    private DatePickerDialog.OnDateSetListener mDataSetListener;
    private  DatePickerDialog.OnDateSetListener mDataSetListener1;
    String tempfromdate, temptodate, temptype, tempdescription;
    String ServerURL = "http://192.168.1.98:8080/EmpAdmin/leave.php";
    Button btnapply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        description = findViewById(R.id.description);

        btnapply = findViewById(R.id.btnapply);

        BottomNavigationView NavBot = (BottomNavigationView) findViewById(R.id.NavBot);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavBot);
        bottomNavigationView.setSelectedItemId(R.id.Apply_Leave);
        NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Home:
                        Intent d = new Intent(leave.this, Homepage.class);
                        startActivity(d);
                        break;
                    case R.id.Apply_Leave:

                        break;
                    case R.id.Notification:
                        Intent b = new Intent(leave.this, notification.class);
                        startActivity(b);
                        break;
                    case R.id.Profile:
                        Intent c = new Intent(leave.this, Homepage.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });

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
        //String name = spinner.getSelectedItem().toString();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.leave, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        /*spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object   = adapterView.getItemIdAtPosition(i);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/





        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getdata();
                InsertData(tempfromdate, temptodate, temptype, tempdescription);
                //createleave();

            }

        });


    }
   /* private final void createleave(){
        String temptype=spinner1.getSelectedItem().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("type",temptype);


    }*/


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

       //temptype = parent.getItemAtPosition(i).toString();
       // Toast.makeText(adapterView.getContext(),text, Toast.LENGTH_SHORT).show();
       // btnapply.setTag(i+""); // Passing as string
      //  parent.getItemAtPosition(i;
      //  parent.setSelection(0);
      //  parent.getSelectedItemPosition();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

   public void getdata(){
        tempfromdate=mDisplayDate.getText().toString();
        temptodate=mDisplayDate2.getText().toString();
        temptype=spinner1.getSelectedItem().toString();
        temptype=String.valueOf(spinner1.getSelectedItem());

       tempdescription=description.getText().toString();


    }
    public void InsertData(final String fromdate, final String todate, final String leavetype, final String description) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String fromdateholder = fromdate;
                String todateholder = todate;
                String typeholder = leavetype;
                String descriptionholder = description;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("fromdate", fromdateholder));
                nameValuePairs.add(new BasicNameValuePair("todate", todateholder));
                nameValuePairs.add(new BasicNameValuePair("leavetype", typeholder));
                nameValuePairs.add(new BasicNameValuePair("description", descriptionholder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(leave.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(fromdate, todate,  description);


    }

}

