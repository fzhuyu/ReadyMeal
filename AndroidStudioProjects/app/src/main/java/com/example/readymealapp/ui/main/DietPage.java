package com.example.readymealapp.ui.main;

import android.os.Bundle;
import android.util.Log;
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
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.sql.DriverManager.println;

public class DietPage extends AppCompatActivity {
    String userFood;
    String breakfast = "";
    String lunch = "";
    String dinner = "";
    final float[] TotalCalories = {0};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet);

        if (Meals.VeggiesLunch == null)
            GETRequestVegs();

        //GETRequestCarbs(TotalCalories);
        //GETRequestVegs(TotalCalories);
    }

    private void showBreakfast()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userMealBreakfast);
        String breakfastString = breakfast.substring(0, 1).toUpperCase() + breakfast.substring(1) + "\n" + Meals.breakCal + "cal";
        userMealTextView1.setText(breakfastString);
    }

    private void showLunch()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userMealLunch);
        String lunchString = lunch.substring(0, 1).toUpperCase() + lunch.substring(1) + "\n" + Meals.mainCalLunch + "cal";
        userMealTextView1.setText(lunchString);
    }

    private void showDinner()
    {
        TextView userMealTextView1;
        userMealTextView1 = findViewById(R.id.userMealDinner);
        String dinnerString = dinner.substring(0, 1).toUpperCase() + dinner.substring(1) + "\n" + Meals.mainCalDinner + "cal";
        userMealTextView1.setText(dinnerString);
    }


    private void GETRequestVegs()
    {

        ArrayList<String> Veggies = new ArrayList<>();
        Veggies.add("beans");
        Veggies.add("broccolis");
        Veggies.add("spinaches");
        Veggies.add("peas");

        int i =  1 + (int)(Math.random() * ((3 - 1) + 1));
        int min = 1;

        if (Veggies.get(i).equals("peas") || Veggies.get(i).equals("beans"))
            min = 15;

        String APIurlV = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=" + Veggies.get(i);
        final String UserVeg = Veggies.get(i);
        //User me = new User();
        //User me = new User();
        //Local_db.userDao().insertUser(me);

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

                            // loop through this jsonArray to look for the user's food
                            for (int i = 0; i < jsonArray.length(); i++)
                            {

                                int index = finalMin + (int)(Math.random() * ((jsonArray.length() - finalMin) + 1));

                                JSONObject foodFav = jsonArray.getJSONObject(index);

                                String foodName = foodFav.getString("lowercaseDescription"); // title of the food, also might be index 3 if using JsonObjectRequest
                                StringTokenizer tokFood = new StringTokenizer(foodName); // tokenizes string to find the keyword, ie food preference

                                // when string is parsed, look for the keyword for user
                                while (tokFood.hasMoreTokens())
                                {
                                    // if the tokenized food name found in request equals the user's food preference, then store the calories
                                    if (tokFood.nextToken().toLowerCase().equals(UserVeg.toLowerCase()))
                                    {

                                        JSONArray TempJsonObj = foodFav.getJSONArray("foodNutrients");
                                        JSONObject JSONCal = (JSONObject) TempJsonObj.get(3);
                                        TotalCalories[0] += JSONCal.getInt("value");

                                        // if we have reached the max calories OR all the main meals have been added to class "Meals" then we'll display everything in the Meals class
                                        if (TotalCalories[0] >= Meals.UserCalories)
                                        {
                                            Log.d("myTag", "Gonna print Veggies!");
                                            return;
                                            // display to user the info about their meal plan

                                        }
                                        else
                                        {
                                            //Log.d("myTag", "Got into the else to set breakfast name!");
                                            // looks to see if breakfast, lunch, and dinner have been fulfilled yet
                                            // if not fulfilled it'll set the name of the food to the Meal's static string and set that meal's calories too
                                            if (Meals.VeggiesLunch == null)
                                            {
                                                Log.d("myTag", "YUM VEGGEIS....SAID NOBODY");
                                                Meals.CarbsLunch = foodFav.getString("lowercaseDescription");
                                                //temp = foodFav.getString("KCAL");
                                                Meals.carbCalLunch = JSONCal.getInt("value");
                                                break;
                                            }
                                            else if(Meals.VeggiesDinner == null)
                                            {
                                                Meals.CarbsDinner = foodFav.getString("lowercaseDescription");
                                                Meals.carbCalDinner = JSONCal.getInt("value");
                                                Meals.GETCalTotal += TotalCalories[0];
                                                // this ends.....display info to user
                                                return;
                                            }
                                        }
                                        // end of if token matches user's food preference

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
    }
}