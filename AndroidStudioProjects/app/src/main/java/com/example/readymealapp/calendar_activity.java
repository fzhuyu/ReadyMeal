package com.example.readymealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

public class calendar_activity extends AppCompatActivity {

    CalendarView calender;
    TextView date_view;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}