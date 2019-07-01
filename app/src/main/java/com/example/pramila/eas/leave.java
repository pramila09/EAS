package com.example.pramila.eas;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class leave extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "leave";

    private TextView mDisplayDate, mDisplayDate2;
    private DatePickerDialog.OnDateSetListener mDataSetListener;
    private  DatePickerDialog.OnDateSetListener mDataSetListener1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        BottomNavigationView NavBot = (BottomNavigationView) findViewById(R.id.NavBot);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavBot);
        bottomNavigationView.setSelectedItemId(R.id.Apply_Leave);
        NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
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
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                Log.d(TAG, "onDataSet: mm/dd/yy:"+ month +"/" + day+"/"+year);
                String date = month +"/" + day+"/"+year;
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
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDataSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                Log.d(TAG, "onDataSet: mm/dd/yy:"+ month +"/" + day+"/"+year);
                String date = month +"/" + day+"/"+year;
                mDisplayDate2   .setText(date);
            }
        };


        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.leave, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

       String text = adapterView.getItemAtPosition(i).toString();
       // Toast.makeText(adapterView.getContext(),text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
