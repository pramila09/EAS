package com.example.pramila.eas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        BottomNavigationView NavBot = (BottomNavigationView) findViewById(R.id.NavBot);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.NavBot);
        bottomNavigationView.setSelectedItemId(R.id.Notification);
        NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.Home:
                        Intent d = new Intent(notification.this, homepage.class);
                        startActivity(d);
                        break;

                    case R.id.Apply_Leave:
                        Intent a = new Intent(notification.this, leave.class);
                        startActivity(a);
                        break;
                    case R.id.Notification:

                        break;
                    case R.id.Profile:
                        Intent c = new Intent(notification.this, homepage.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
    }
}
