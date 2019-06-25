package com.example.pramila.eas;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class homepage extends AppCompatActivity {

    private

    //CalendarView compactCalendar;
    //private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);



        //final ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(false);
        //actionbar.setTitle(null);

        //compactCalendar  = (CalendarView)findViewById(R.id.calendar);
        //compactCalendar.setUseThreeLetterAbbreviation(true);


        //UsageEvents.Event ev1 = new Event(Color.RED,1563214500000L,"Teachers' Day");
        //compactCalendar.addEvent(ev1);

        /*compactCalendar.setListener(new CalendarView.CalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();

                if(dateClicked.toString().compareTo("Tue July 16 00:00:00 AST 2019") == 0) {
                    Toast.makeText(context, "Teachers' Day", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "No Events planned for that day", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionbar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));

            }
        });
    }*/
}}


