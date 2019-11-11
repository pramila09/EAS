package com.example.pramila.eas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Learn extends AppCompatActivity {

    TextView tvlearn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn2);

        tvlearn = (TextView) findViewById(R.id.tvlearn);
    }
}
