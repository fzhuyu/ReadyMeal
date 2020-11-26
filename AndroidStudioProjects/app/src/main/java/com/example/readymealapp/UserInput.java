package com.example.readymealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.Object;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserInput extends AppCompatActivity implements AdapterView.OnItemSelectedListener {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.activity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void goToHomePage(View v){
        Intent HomePage = new Intent (this, HomePage.class);
        int userAge = 0;
        int userHeight = 0;
        int userWeight = 0;

        //Creating a variable for first name
        EditText fNameEditText = findViewById(R.id.fName);
        //save first name to a variable
        String userfName = fNameEditText.getText().toString();

        //Last Name
        EditText lNameEditText = findViewById(R.id.lName);
        String userlName = lNameEditText.getText().toString();

        //Age
        EditText TextAge = findViewById(R.id.editTextAge);
        String StringAge = TextAge.getText().toString();

        //Height
        EditText heightFeet = findViewById(R.id.heightFeet);
        String heightFt = heightFeet.getText().toString();
        EditText heightInches = findViewById(R.id.heightInches);
        String heightIn = heightInches.getText().toString();

        //Age
        EditText weight = findViewById(R.id.weight);
        String weightLb = weight.getText().toString();

        //if First Name is empty
        if (userfName.matches("")) {
            Toast.makeText(this, "First Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userlName.matches("")) {
            Toast.makeText(this, "Last Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringAge.isEmpty() || StringAge.equals("0"))
        {
            Toast.makeText(this, "Age is Invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (heightFt.isEmpty() || heightIn.isEmpty() || heightFt.equals("0"))
        {
            Toast.makeText(this, "Height is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (weightLb.isEmpty() || weightLb.equals("0"))
        {
            Toast.makeText(this, "Weight is Empty", Toast.LENGTH_SHORT).show();
            return;
        }


        //else save the user name and go to home page
        else
            {
            //instantiating the database
            final AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();
            //creating the user
            User me = new User();

            //------name------
            //add the user first name into the database
            me.FName = userfName;
            me.LName = userlName;

            //------age--------
            //convert age to int
            userAge = Integer.parseInt(StringAge);
            me.UserAge = userAge;

            //-----height------
            //convert feet to inches and added them and save them to height, if the inches = 0, only calculate feet
            if(heightIn.equals("0"))
                {
                    userHeight = (0 + Integer.parseInt(heightFt)*12);
                }
            else
                {
                    userHeight = (Integer.parseInt(heightIn) + Integer.parseInt(heightFt)*12);
                }

            //------weight------
            //convert weightLb to integer
            userWeight = Integer.parseInt(weightLb);

            //-------BMI--------
            //calculate BMI and save to database
            me.UserBMI = (((703)*(userWeight))/(userHeight*userHeight));


            //Save to database
            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                Local_db.userDao().insertUser(me);
                double testTwo = Local_db.userDao().LoadBMI(); ////// end of test
            });


            startActivity(HomePage);
        }
    }

}
