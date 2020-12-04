package com.example.readymealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class calendar_activity extends AppCompatActivity {

    CalendarView calender;
    TextView date_view;
    Button bt;
    Button bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        bt = (Button) findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast mytoast= Toast.makeText(getApplicationContext(), "Select a Date to View Calories Consumed", Toast.LENGTH_SHORT);
                mytoast.show();

            }});


        bt2 = (Button) findViewById(R.id.button);

        bt2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(calendar_activity.this, HomePage.class));
            }
        });
        calender = (CalendarView)
                findViewById(R.id.calender);
        date_view = (TextView)
                findViewById(R.id.date_view);

        calender
                .setOnDateChangeListener(
                        (view, year, month, dayOfMonth) -> {

                            String Date
                                    = dayOfMonth + "-"
                                    + (month + 1) + "-" + year;


                            date_view.setText(Date);
                        });




    }}


