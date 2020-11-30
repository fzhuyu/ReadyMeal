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
        int userAge = 0;
        int userHeight = 0;
        int userWeight = 0;

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------User input to Database----------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------

        //----------------------------------------------------------------retrieving data from textview/spinner to convert to string---------------------------------------
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

        //Favorite Food
        EditText faveFood = findViewById(R.id.favoriteFood);
        String favFood = faveFood.getText().toString();

        //Sex
        Spinner userSex = (Spinner) findViewById(R.id.spinner);
        String sex = userSex.getSelectedItem().toString();

        //User Desired Calories
        EditText calories = findViewById(R.id.caloricGoal);
        String caloricGoal = calories.getText().toString();

        //Physical Activity
        Spinner userPhysicalActivity = (Spinner) findViewById(R.id.spinner2);
        String userActivity = userPhysicalActivity.getSelectedItem().toString();

        //-----------------------------------------------------------------Check if the input is empty or null--------------------------------------------
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

        if (favFood.matches("")) {
            Toast.makeText(this, "Favorite Food is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (caloricGoal.isEmpty() || caloricGoal.equals("0"))
        {
            Toast.makeText(this, "Calories is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //-------------------------------------------------save the user data to the database--------------------------------------------------------

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


//---------------------------BMI---------------------------
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
//---------------------------BMI---------------------------

            //------Favorite Food------
            //add the user's favorite food into the database
            me.FoodFav = favFood;

            //--------Sex--------
            //add the user's favorite food into the database
            me.UserSex = sex;

            //--------Caloric Goal--------
            //add the caloric goal into the database
            me.DesiredCalories = Integer.parseInt(caloricGoal);
            //initialize current calories to 0
            me.CurrentCalories = 0;

            //-----Physical Activity-----
            //add the user's favorite food into the database
            me.UserActivity = userActivity;


            //-----------------------------Save the data to the user Database------------------------------
            //Save to database
            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                Local_db.userDao().insertUser(me);
            });
        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------User input to Database----------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------


        //Go to home from user input, code for the button
        Intent HomePage = new Intent (this, HomePage.class);
            startActivity(HomePage);



    }

}
