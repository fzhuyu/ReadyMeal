package com.example.readymealapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.readymealapp.ui.main.Meals;

import com.example.readymealapp.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringTokenizer;

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



        // fname and lname can be retreived from userinput page or something
        final String fname = "";
        final String lname = "";
        final float[] Calories = {0};
        final float[] TotalCalories = {0};
        final Meals UserMeal = new Meals();


        // retreiving data from Room for food preferences and calories based on name of user
        final AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();

        // this gets user's food preference by setting the value to an instance of a User class
        final LiveData<User> userFood = Local_db.userDao().LoadFoodPref(fname, lname);
        //User me = new User();
        //Local_db.userDao().insertUser(me);

        /////// code for using a GET request for JSON object from API ///////
        ////////////////////////////////////////////////////////////////////
        try
        {
            // Defnition for JSON GET request
            //////// if for some reason the current URL doesn't work, then try this: "https://nal.altarama.com/reft100.aspx?key=FoodData"
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
                                    // set JSON object equal to foodfavJSON
                                    JSONObject foodfavJSON = jsonArray.getJSONObject(i);
                                    String foodName = foodfavJSON.getString("description"); // title of the food basically
                                    StringTokenizer tokFood = new StringTokenizer(foodName); // tokenizes string to find the keyword, ie food preference

                                    // when string is parsed, look for the keyword for user
                                    while (tokFood.hasMoreTokens())
                                    {
                                        // if food name found in request equals the user's food preference, then store the calories
                                        if (tokFood.nextToken().equals(userFood.toString()))
                                        {
                                            TotalCalories[0] += foodfavJSON.getInt("calories");

                                            // if we have reached the max calories or all the main meals have been added to class "Meals" then we'll display everything in the Meals class
                                            if (Local_db.userDao().LoadCalories(fname, lname) < TotalCalories[0] || (UserMeal.breakfast != "" && UserMeal.Lunch != "" && UserMeal.Dinner != ""))
                                            {
                                                // display to user the info about their meal plan
                                            }
                                            else
                                            {
                                                // looks to see if breakfast, lunch, and dinner have been fulfilled yet
                                                if (UserMeal.breakfast == "")
                                                {
                                                    UserMeal.breakfast = foodfavJSON.getString("description");
                                                    UserMeal.breakCal = foodfavJSON.getInt("calories");;
                                                    break;
                                                }
                                                else if(UserMeal.Lunch == "")
                                                {
                                                    UserMeal.Lunch = foodfavJSON.getString("description");
                                                    UserMeal.mainCalLunch = foodfavJSON.getInt("calories");

                                                    // does search for veggies for meal, commented out cuz don't know if it can be implemented yet
                                                    /*
                                                    for (int j = 0; j < jsonArray.length(); j++)
                                                    {
                                                        JSONObject veggieJSON = jsonArray.getJSONObject(i);
                                                        String vegStr = veggieJSON.getString("description"); // title of the food

                                                        if (vegStr == "Broccoli" || vegStr == "Green Beans")
                                                        {
                                                            TotalCalories[0] += veggieJSON.getInt("calories");
                                                            UserMeal.VeggiesLunch = vegStr;
                                                            UserMeal.vegCalLunch = veggieJSON.getInt("calories");
                                                            break;
                                                        }
                                                    }
                                                    */
                                                    break;
                                                }
                                                else if(UserMeal.Dinner == "")
                                                {
                                                    UserMeal.Dinner = foodfavJSON.getString("description");
                                                    UserMeal.mainCalDinner = foodfavJSON.getInt("calories");
                                                    break;
                                                }
                                            }

                                        }
                                        // else, don't set the calories
                                    }
                                    // end of while loop token
                                }
                                // end of for loop
                            }
                            // end of try
                            catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, // end of API listener description
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            //  |
            //  V  this actually does the GET Request
            //ReqQ.add(ObjReq);

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        

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