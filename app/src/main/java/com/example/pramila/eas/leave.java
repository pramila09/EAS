package com.example.pramila.eas;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class leave extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        TextView title = (TextView) findViewById(R.id.activityTitle1);
        title.setText("Activity One");

        BottomNavigationView NavBot = (BottomNavigationView) findViewById(R.id.NavBot);
        NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.Home:
                        Intent d = new Intent(leave.this, homepage.class);
                        startActivity(d);
                        break;
                    case R.id.Apply_Leave:

                        break;
                    case R.id.Notification:
                        Intent b = new Intent(leave.this, notification.class);
                        startActivity(b);
                        break;
                    case R.id.Profile:
                        Intent c = new Intent(leave.this, homepage.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
    }
}
