package com.example.magal.tennistracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button start_button;
    Button btnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_button = (Button) findViewById(R.id.start_button);
        btnStats = (Button) findViewById(R.id.btnStats);


        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewMatchActivity();
            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatsActivity();
            }
        });



    }

    private void openStatsActivity() {
        /*Intent i = new Intent(this, StatsActivity.class);
        startActivity(i);*/
                Intent intent = new Intent(this, StatsActivity.class);
                startActivity(intent);
    }

    public void openNewMatchActivity() {
        Intent i = new Intent(this, NewMatchActivity.class);
        startActivity(i);

    }
}
