package com.example.pramila.eas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Homepage extends AppCompatActivity {





        //CalendarView compactCalendar;
    //private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        BottomNavigationView mButtonNavigationView;
        BottomNavigationView NavBot = (BottomNavigationView) findViewById(R.id.NavBot);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.NavBot);
        bottomNavigationView.setSelectedItemId(R.id.home);
       NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.Home:
                        break;
                    case R.id.Apply_Leave:
                        Intent a = new Intent(Homepage.this, leave.class);
                        startActivity(a);
                        break;
                    case R.id.Notification:
                        Intent b = new Intent(Homepage.this, notification.class);
                        startActivity(b);
                        break;
                    case R.id.Profile:
                        Intent c = new Intent(Homepage.this, profile.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });





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


