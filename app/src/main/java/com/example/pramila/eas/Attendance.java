package com.example.pramila.eas;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;
import com.skyhope.eventcalenderlibrary.model.Event;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Attendance extends AppCompatActivity {
    String sessionid;


    private static final String TAG = "eas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Intent intent = getIntent();
        sessionid = intent.getStringExtra("sessionid");
        Log.e("eas", "sessionid:" + sessionid);



        CalenderEvent calenderEvent = findViewById(R.id.calender_event);
        //Event event = new Event(calendar.getTimeInMillis(), "Test");
// to set desire day time milli second in first parameter
//or you set color for each event
        Calendar calendar = new GregorianCalendar();
        Event event = new Event(calendar.getTimeInMillis(), "P", Color.RED);
        calenderEvent.addEvent(event);
        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                Log.d(TAG, dayContainerModel.getDate());
            }
        });



    }
}
