package com.example.pramila.eas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class profile extends AppCompatActivity {
    private static final String TAG = "profile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView NavBot = (BottomNavigationView) findViewById(R.id.NavBot);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.NavBot);
        bottomNavigationView.setSelectedItemId(R.id.Profile);
        NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Home:
                        Intent a = new Intent(profile.this, Homepage.class);
                        startActivity(a);
                        break;
                    case R.id.Apply_Leave:
                        Intent b = new Intent(profile.this, leave.class);
                        startActivity(b);

                        break;
                    case R.id.Notification:
                        Intent c = new Intent(profile.this, notification.class);
                        startActivity(c);
                        break;
                    case R.id.Profile:
                        break;
                }
                return false;
            }
        });


        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SharedPreferences myPrefs = getSharedPreferences("MY",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.commit();
                AppState.getSingleInstance().setLoggingOut(true);
                Log.d(TAG, "Now log out and start the activity login");
                Intent intent = new Intent(profile.this,
                       MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(profile.this, "You are logged out",Toast.LENGTH_SHORT).show();


            }
        });

        /*case R.id.logout:

        AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
        builder.setMessage("Do you want to exist?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                Intent logoutintent = new Intent();
                logoutintent.putExtra("finsih",true);
                logoutintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();*/



    }
}
