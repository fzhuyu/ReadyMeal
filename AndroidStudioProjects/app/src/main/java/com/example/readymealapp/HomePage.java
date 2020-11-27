package com.example.readymealapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        CardView dietcard = (CardView) findViewById(R.id.diet_card);

        //dietcard.setOnClickListener(this);

        //instantiating the database
        final AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();
        //

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            final String userFirstName = Local_db.userDao().findFirstName();
            final String userLastName = Local_db.userDao().findLastName();
            final int userAge = Local_db.userDao().findAge();
            final double userBMI = Local_db.userDao().LoadBMI();
            TextView GreetUser;
            GreetUser = findViewById(R.id.userGreeting);
            GreetUser.setText("Hello,\t" + userFirstName + ' ' + userLastName
                                + "\nYour Age is: " + userAge
                                + "\nYour BMI is: " + userBMI);

        });


    }

    public void goToUserInputData(View view) {
        Intent UserInputActivity = new Intent (this, UserInput.class);
        startActivity(UserInputActivity);
    }

    public void goToDailyDiet(View view){
        Intent DailyDietActivity = new Intent (this, DailyDiet.class);
        startActivity(DailyDietActivity);
    }


/*
    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.diet_card:
                i = new Intent(this, DailyDiet.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }
    */

}
