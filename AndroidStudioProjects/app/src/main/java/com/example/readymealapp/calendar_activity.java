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

import com.example.readymealapp.ui.main.Meals;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class calendar_activity extends AppCompatActivity {

    CalendarView calender;
    TextView date_view;
    TextView date_view2;
    TextView date_view3;
    TextView date_view4;
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

        date_view2 = (TextView)
                findViewById(R.id.date_view2);

        date_view3 = (TextView)
                findViewById(R.id.date_view3);

        date_view4 = (TextView)
                findViewById(R.id.date_view4);



        calender
                .setOnDateChangeListener(
                        (view, year, month, dayOfMonth) -> {

                            int total = Meals.TotalCalories();
                            String breakfast = Meals.breakfast;
                            String lunch = Meals.Lunch;
                            String dinner = Meals.Dinner;

                            String Date
                                    = (month + 1) + "-"
                                    + dayOfMonth + "-" + year;

                            if(Date.equals("12-4-2020"))
                            {
                                date_view.setText(total + " calories consumed on " + Date);
                                date_view2.setText("Breakfast: " + breakfast);
                                date_view3.setText("Lunch: " + lunch);
                                date_view4.setText("Dinner: " + dinner);
                            }
                            else{
                                date_view.setText("No meals generated on " + Date);
                                date_view2.setText("");
                                date_view3.setText("");
                                date_view4.setText("");
                            }
                        });

    }}


