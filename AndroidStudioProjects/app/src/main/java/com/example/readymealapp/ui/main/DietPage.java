package com.example.readymealapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readymealapp.AppDatabase;
import com.example.readymealapp.HomePage;
import com.example.readymealapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.sql.DriverManager.println;

public class DietPage extends AppCompatActivity {
    //String userFood;
    //String breakfast = "";
    String LunchVeggies;
    float LunchCalVeggies = 0;

    String DinnerVeggies;
    float DinnerCalVeggies = 0;
    final float[] TotalCalories = {0};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet);

        GETRequestVegs();

        showBreakfast();
        showLunch();
        showDinner();
        showCarbsLunch();
        showCarbsDinner();


        //GETRequestCarbs(TotalCalories);
        //GETRequestVegs(TotalCalories);
    }

    private void showBreakfast()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userMealBreakfast);
        String breakfastString = "Main:\n" + Meals.breakfast.substring(0, 1).toUpperCase() + Meals.breakfast.substring(1) + "\n" + Meals.breakCal + "cal";
        userMealTextView1.setText(breakfastString);
    }

    private void showLunch()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userMealLunch);
        String lunchString = "Main:\n" +  Meals.Lunch.substring(0, 1).toUpperCase() + Meals.Lunch.substring(1) + "\n" + Meals.mainCalLunch + "cal";
        userMealTextView1.setText(lunchString);
    }

    private void showDinner()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userMealDinner);
        String dinnerString = "Main:\n" + Meals.Dinner.substring(0, 1).toUpperCase() + Meals.Dinner.substring(1) + "\n" + Meals.mainCalDinner + "cal";
        userMealTextView1.setText(dinnerString);
    }

    private void showCarbsLunch()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userCarbsLunch);
        String carbsLunchString = "Carbs:\n" + Meals.CarbsLunch + "\n" + Meals.carbCalLunch + " cal";
        userMealTextView1.setText(carbsLunchString);
    }

    private void showCarbsDinner()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userCarbsDinner);
        String carbsDinnerString = "Carbs:\n" + Meals.CarbsDinner + "\n" + Meals.carbCalDinner + "cal";
        userMealTextView1.setText(carbsDinnerString);
    }

    private void showVeggiesLunch()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userVeggiesLunch);
        String veggiesLunchString = "Veggies:\n" + LunchVeggies + "\n" + LunchCalVeggies + " cal";
        userMealTextView1.setText(veggiesLunchString);
    }
    private void showVeggiesDinner()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userVeggiesDinner);
        String veggiesLunchString = "Veggies:\n" + DinnerVeggies + "\n" + DinnerCalVeggies + " cal";
        userMealTextView1.setText(veggiesLunchString);
    }


    private void GETRequestVegs()
    {

        ArrayList<String> Second = new ArrayList<>();
        Second.add("beans");
        Second.add("broccolis");
        Second.add("spinaches");
        Second.add("peas");
        Second.add("pastas");
        Second.add("rices");
        Second.add("breads");

        Random random = new Random();
        int i = random.nextInt(7);
        int min = 3;

        if (Second.get(i).equals("peas") || Second.get(i).equals("beans"))
            min = 15;

        String APIurlV = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=" + Second.get(i);
        final String UserSecond = Second.get(i);
        Log.d("sory", UserSecond);

        /////// code for using a GET request for JSON object from API ///////
        ////////////////////////////////////////////////////////////////////

        int finalMin = min;
        JsonObjectRequest ObjReqVeg = new JsonObjectRequest(
                Request.Method.GET,
                APIurlV,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // get an array of JSON objects that are Arrays of "foods"

                            JSONArray jsonArray = response.getJSONArray("foods");

                            int index = finalMin + (int)(Math.random() * ((18 - finalMin) + 1));

                            JSONObject foodFav = jsonArray.getJSONObject(index);
                            //String foodName = foodFav.getString("lowercaseDescription"); // title of the food, also might be index 3 if using JsonObjectRequest

                            JSONArray TempJsonObj = foodFav.getJSONArray("foodNutrients");
                            JSONObject JSONCal = (JSONObject) TempJsonObj.get(3);
                            TotalCalories[0] += JSONCal.getInt("value");
                            LunchCalVeggies = JSONCal.getInt("value");

                            LunchVeggies = foodFav.getString("lowercaseDescription");
                            Meals.VeggiesLunch = LunchVeggies;
                            Meals.vegCalLunch = LunchCalVeggies;


                            index = finalMin + (int)(Math.random() * ((18 - finalMin) + 1));

                            foodFav = jsonArray.getJSONObject(index);
                            //foodName = foodFav.getString("lowercaseDescription"); // title of the food, also might be index 3 if using JsonObjectRequest
                            DinnerVeggies = foodFav.getString("lowercaseDescription");
                            DinnerCalVeggies = JSONCal.getInt("value");
                            Meals.GETCalTotal += TotalCalories[0];

                            /*
                            // loop through this jsonArray to look for the user's food
                            while (found == false)
                            {
                                 StringTokenizer tokFood = new StringTokenizer(foodName); // tokenizes string to find the keyword, ie food preference


                                // when string is parsed, look for the keyword for user
                                while (tokFood.hasMoreTokens())
                                {
                                    Log.d("Tag", "I love memes");
                                    // if the tokenized food name found in request equals the user's food preference, then store the calories
                                    if (tokFood.nextToken().toLowerCase().equals(UserSecond.toLowerCase()))
                                    {

                                        // if we have reached the max calories OR all the main meals have been added to class "Meals" then we'll display everything in the Meals class
                                        if (TotalCalories[0] >= Meals.UserCalories)
                                        {
                                            Log.d("myTag", "Gonna print Veggies!");
                                            found = true;
                                            break;
                                            // display to user the info about their meal plan
                                        }
                                        else
                                        {
                                            //Log.d("myTag", "Got into the else to set breakfast name!");
                                            // looks to see if breakfast, lunch, and dinner have been fulfilled yet
                                            // if not fulfilled it'll set the name of the food to the Meal's static string and set that meal's calories too
                                            if (Meals.VeggiesLunch == null)
                                            {
                                                LunchVeggies = foodFav.getString("lowercaseDescription");
                                                Meals.VeggiesLunch = LunchVeggies;

                                                //temp = foodFav.getString("KCAL");
                                                LunchCalVeggies = JSONCal.getInt("value");
                                                Meals.vegCalLunch = LunchCalVeggies;
                                                break;
                                            }
                                            else if(Meals.VeggiesDinner == null)
                                            {

                                                // this ends.....display info to user
                                                found = true;
                                                break;
                                            }
                                        }
                                        // end of if token matches user's food preference

                                    }
                                    // else, don't set the calories
                                }
                                // end of while loop token
                            }
                            // end of JSON while loop
                             */
                            //showCarbsLunch();
                            //showCarbsDinner();
                            showVeggiesLunch();
                            showVeggiesDinner();

                        }
                        // end of try in case JSON Request is invalid or something
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                // end of API listener description
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }

        );

        Volley.newRequestQueue(getApplicationContext()).add(ObjReqVeg);
        Meals.VeggiesLunch = LunchVeggies;
        Meals.VeggiesDinner = DinnerVeggies;
        Meals.vegCalLunch = LunchCalVeggies;
        Meals.vegCalDinner = DinnerCalVeggies;
    }
    public void goToHomePage(View view){
        Intent HomePageActivity = new Intent (this, HomePage.class);
        startActivity(HomePageActivity);
    }
}