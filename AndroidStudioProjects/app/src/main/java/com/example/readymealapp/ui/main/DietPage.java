package com.example.readymealapp.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readymealapp.AppDatabase;
import com.example.readymealapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.sql.DriverManager.println;


public class DietPage extends AppCompatActivity {
    String userFood;
    String breakfast = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet);

        final float[] TotalCalories = {0};

        // an atomicinteger UserCalories AndroidStudio created so LoadCalories can set its return val to UserCalories
        AtomicInteger UserCalories = new AtomicInteger();

        // retreiving data from Room for food preferences and calories based on name of user
        final AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();

        final String[] userFood = new String[1];

        // this gets user's food preference by setting the value to an instance of a User class
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            userFood[0] = Local_db.userDao().LoadFoodPref();
        });
                //User me = new User();

        //User me = new User();
        //Local_db.userDao().insertUser(me);

        /////// code for using a GET request for JSON object from API ///////
        ////////////////////////////////////////////////////////////////////
        try
        {
            // Defnition for JSON GET request
            //////// if for some reason the current URL doesn't work, then try this: "https://nal.altarama.com/reft100.aspx?key=FoodData" or "https://api.nal.usda.gov/fdc/v1/foods/list?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX"
            RequestQueue ReqQ = Volley.newRequestQueue(this);
            JsonObjectRequest ObjReq = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.nal.usda.gov/fdc/v1/foods/list?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX",
                    null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("myTag", "HWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
                                // get an array of JSON objects that are Branded Food Items
                                JSONArray jsonArray = response.getJSONArray("BrandedFoodItem");

                                // loop through this jsonArray to look for the user's food
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    Log.d("myTag", "HXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                                    // set JSON object equal to foodfavJSON
                                    JSONObject foodfavJSON = jsonArray.getJSONObject(i);
                                    String foodName = foodfavJSON.getString("description"); // title of the food basically
                                    StringTokenizer tokFood = new StringTokenizer(foodName); // tokenizes string to find the keyword, ie food preference

                                    // when string is parsed, look for the keyword for user
                                    while (tokFood.hasMoreTokens())
                                    {
                                        Log.d("TAAAAAAAAAAAG", "HAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                                        // if the tokenized food name found in request equals the user's food preference, then store the calories
                                        if (tokFood.nextToken().toLowerCase().equals(userFood[0].toLowerCase()))
                                        {
                                            Log.d("myTag", "HBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                                            TotalCalories[0] += foodfavJSON.getInt("calories");

                                            // uses single thread executor to retrieve user's calorie preference and sets it to an AtomicInteger
                                            Executor myExecutor = Executors.newSingleThreadExecutor();
                                            myExecutor.execute(() -> {

                                                UserCalories.set(Local_db.userDao().LoadCurrentCalories());
                                            });

                                            // if we have reached the max calories OR all the main meals have been added to class "Meals" then we'll display everything in the Meals class
                                            if (UserCalories.floatValue() <= TotalCalories[0] || (Meals.breakfast != "" && Meals.Lunch != "" && Meals.Dinner != ""))
                                            {
                                                breakfast = Meals.breakfast;
                                                // display to user the info about their meal plan

                                            }
                                            else
                                            {
                                                // looks to see if breakfast, lunch, and dinner have been fulfilled yet
                                                // if not fulfilled it'll set the name of the food to the Meal's static string and set that meal's calories too
                                                if (Meals.breakfast == "")
                                                {
                                                    Meals.breakfast = foodfavJSON.getString("description");
                                                    Meals.breakCal = foodfavJSON.getInt("calories");
                                                    println("WE ARE HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEERE");
                                                    println(foodfavJSON.getString("description"));
                                                    println("WE ARE HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEERE");
                                                    break;
                                                }
                                                else if(Meals.Lunch == "")
                                                {
                                                    Meals.Lunch = foodfavJSON.getString("description");
                                                    Meals.mainCalLunch = foodfavJSON.getInt("calories");

                                                    // does search for veggies for meal, commented out cuz don't know if it can be implemented yet
                                                    /*
                                                    for (int j = 0; j < jsonArray.length(); j++)
                                                    {
                                                        JSONObject veggieJSON = jsonArray.getJSONObject(i);
                                                        String vegStr = veggieJSON.getString("description"); // title of the food

                                                        if (vegStr == "Broccoli" || vegStr == "Green Beans")
                                                        {
                                                            TotalCalories[0] += veggieJSON.getInt("calories");
                                                            Meals.VeggiesLunch = vegStr;
                                                            Meals.vegCalLunch = veggieJSON.getInt("calories");
                                                            break;
                                                        }
                                                    }
                                                    */
                                                    break;
                                                }
                                                else if(Meals.Dinner == "")
                                                {
                                                    Meals.Dinner = foodfavJSON.getString("description");
                                                    Meals.mainCalDinner = foodfavJSON.getInt("calories");
                                                    break;
                                                }
                                            }

                                        }
                                        // else, don't set the calories
                                    }
                                    // end of while loop token
                                }
                                // end of JSON for loop
                            }
                            // end of try in case JSON Request is invalid or something
                            catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, // end of API listener description
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            );
            //  |
            //  V  this actually does the GET Request
            ReqQ.add(ObjReq);

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        showText();
    }

    private void showText()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userMealTextView);
        userMealTextView1.setText("Hello, your breakfast is: \t" + breakfast);
    }
}
