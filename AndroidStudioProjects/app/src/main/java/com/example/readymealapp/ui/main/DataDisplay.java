package com.example.readymealapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.readymealapp.AppDatabase;
import com.example.readymealapp.HomePage;
import com.example.readymealapp.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        //instantiating the database
        final AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();
        final CalDatabase Cal = Room.databaseBuilder(getApplicationContext(), CalDatabase.class, "User_db").build();

        //

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            final String userFirstName = Local_db.userDao().findFirstName();
            final String userLastName = Local_db.userDao().findLastName();
            final int userAge = Local_db.userDao().findAge();
            double userBMI = Local_db.userDao().LoadBMI();
            final String favFood = Local_db.userDao().LoadFoodPref();
            final String sex = Local_db.userDao().LoadSex();
            final int prefCalories = Local_db.userDao().LoadDesiredCalories();
            final String userActivity = Local_db.userDao().LoadActivity();
            TextView GreetUser;
            GreetUser = findViewById(R.id.userGreeting);
            GreetUser.setText("Hello,\t" + userFirstName + ' ' + userLastName
                    + "\nYour Age is: " + userAge
                    + "\nYour BMI is: " + userBMI
                    + "\nYour Favorite Food is: " + favFood
                    + "\nYour sex is: " + sex
                    + "\nYour Caloric Goal is: " + prefCalories
                    + "\nYour Physical Activity is: " + userActivity);
        });
    }

    public void goToHomePageFromDataDisplay(View v) {

        Intent HomePage = new Intent (this, com.example.readymealapp.HomePage.class);
        startActivity(HomePage);

    }
}