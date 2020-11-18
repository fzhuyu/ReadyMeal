package com.example.readymealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);


        //instantiating the database
        final AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();
        //

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            final String userFirstName = Local_db.userDao().findFirstName();
            final String userLastName = Local_db.userDao().findLastName();
            final int userAge = Local_db.userDao().findAge();
            TextView GreetUser;
            GreetUser = findViewById(R.id.userGreeting);
            GreetUser.setText("Hello\t" + userFirstName + ' ' + userLastName + "\nYour Age is: " + userAge);
        });




    }
}