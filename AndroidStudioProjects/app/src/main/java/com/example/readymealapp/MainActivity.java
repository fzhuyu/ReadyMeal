package com.example.readymealapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readymealapp.ui.main.SectionsPagerAdapter;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        //ViewPager viewPager = findViewById(R.id.view_pager);              // Had to comment out these 4 lines in order to
        //viewPager.setAdapter(sectionsPagerAdapter);                       // get the program running (ignoring the tabs)
        //TabLayout tabs = findViewById(R.id.tabs);
        //tabs.setupWithViewPager(viewPager);
        //FloatingActionButton fab = findViewById(R.id.fab);


        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        })*/
    }

    //------------------------------------------Buttons------------------------------


    //this comes from TheFileWhereTheButtonis.xml onClick="NameOfTheFunction";
    //goes to the user input activity when user input button is pressed
      public void goToUserInputData(View v){
        Intent UserInputActivity = new Intent (this, UserInput.class);
        startActivity(UserInputActivity);
    }

    /*
    //goes to Main Activity, will be changed to Home after home is implemented, this is the submit button function
    //on the User Input page
    public void goToMainActivity(View v){
        Intent MainActivity = new Intent (this, MainActivity.class);
        startActivity(MainActivity);
    }*/
    //------------------------------------------Buttons------------------------------
}