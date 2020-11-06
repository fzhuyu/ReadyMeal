package com.example.readymealapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readymealapp.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


        // name can be retreived from userinput page or something
        String fname = "";
        String lname = "";

        // retreiving data from Room for food preferences and calories based on name of user
        AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();

        // this gets user's food preference by setting the value to an instance of a User class
        final LiveData<User> userFood = Local_db.userDao().LoadFoodPref(fname, lname);

        /////// code for using a GET request for JSON object from API ///////
        ////////////////////////////////////////////////////////////////////
        try
        {
            // Defnition for JSON request
            RequestQueue ReqQ = Volley.newRequestQueue(this);
            JsonObjectRequest ObjReq = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.nal.usda.gov/fdc/v1/foods/list?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX",
                    null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                // get an array of JSON objects that are Branded Food Items
                                JSONArray jsonArray = response.getJSONArray("BrandedFoodItem");

                                // loop through this jsonArray to look for the user's food
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    // set JSON object equal to foodfav
                                    JSONObject foodfav = jsonArray.getJSONObject(i);

                                    String foodName = foodfav.getString("description");

                                    // if food name found in request equals the user's food preference, then store the calories to
                                    if (foodName.equals(userFood.toString())) {
                                        int Calories = foodfav.getInt("calories");
                                        break;
                                    }
                                    // else, don't set the calories
                                }
                                // will write store/show info to user
                            }
                            catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );

            /////////////////////// this actually does the GET Request /////////////////////////////
            //ReqQ.add(ObjReq);


        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        /////// end of code I added for GET Request ///////
        //////////////////////////////////////////

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