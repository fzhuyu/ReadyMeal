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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet);

        final float[] TotalCalories = {0};

        GETRequest(TotalCalories);
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

    private void GETRequest(final float[] TotalCalories)
    {
        //final float[] TotalCalories = {0};

        Map<String, Integer> breakfastItems = new HashMap<String, Integer>();
        breakfastItems.put("Waffles", 82);
        breakfastItems.put("Pancakes", 64);
        breakfastItems.put("Sausages", 391);
        breakfastItems.put("Grits", 143);
        breakfastItems.put("Cereal", 307);

        int i =  1 + (int)(Math.random() * ((5 - 1) + 1));

        Iterator<Map.Entry<String, Integer>> itr = breakfastItems.entrySet().iterator();

        while(itr.hasNext() && i != 0)
        {
            Map.Entry<String, Integer> entry = itr.next();
            Meals.breakfast = entry.getKey();
            Meals.breakCal = entry.getValue();
            i--;
        }

        /*while (i != 0)
        {
            breakfastItems.entrySet().iterator().next();
            Map.Entry<String,Integer> entry = breakfastItems.entrySet().iterator().next();
            Meals.breakfast = entry.getKey();
            Meals.breakCal = entry.getValue();
            i--;
        }
        */


        // retreiving data from Room for food preferences and calories based on name of user
        //final AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();
        //final String[] userFood = new String[1];

        /*Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            userFood[0] = Local_db.userDao().LoadFoodPref();
        });
        */

        String APIurl = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=" + Meals.UserFoodPref + "s";

        //User me = new User();
        //User me = new User();
        //Local_db.userDao().insertUser(me);

        /////// code for using a GET request for JSON object from API ///////
        ////////////////////////////////////////////////////////////////////
        try
        {

            // Defnition for JSON GET request
            //////// if for some reason the current URL doesn't work, then try these:
            // or "https://api.nal.usda.gov/fdc/v1/foods/list?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX"
            // or https://developer.nrel.gov/api/alt-fuel-stations/v1.json?limit=1&api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX
            // or https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=Cheddar%20Cheese OR https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=Chicken

            RequestQueue ReqQ = Volley.newRequestQueue(this);
            JsonObjectRequest ObjReq = new JsonObjectRequest(
                    Request.Method.GET,
                    APIurl,
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

                                    int index = 1 + (int)(Math.random() * ((jsonArray.length() - 1) + 1));

                                    JSONObject foodFav = jsonArray.getJSONObject(index);

                                    String foodName = foodFav.getString("lowercaseDescription"); // title of the food, also might be index 3 if using JsonObjectRequest
                                    StringTokenizer tokFood = new StringTokenizer(foodName); // tokenizes string to find the keyword, ie food preference

                                    // when string is parsed, look for the keyword for user
                                    while (tokFood.hasMoreTokens())
                                    {
                                        // if the tokenized food name found in request equals the user's food preference, then store the calories
                                        if (tokFood.nextToken().toLowerCase().equals(Meals.UserFoodPref.toLowerCase()))
                                        {

                                            JSONArray TempJsonObj = foodFav.getJSONArray("foodNutrients");
                                            JSONObject JSONCal = (JSONObject) TempJsonObj.get(3);
                                            TotalCalories[0] += JSONCal.getInt("value");

                                            // if we have reached the max calories OR all the main meals have been added to class "Meals" then we'll display everything in the Meals class
                                            if (Meals.UserCalories <= TotalCalories[0] || (Meals.breakfast != null && Meals.Lunch != null && Meals.Dinner != null))
                                            {
                                                //Log.d("myTag", "Gonna print breakfast name now!");
                                                breakfast = Meals.breakfast;
                                                lunch = Meals.Lunch;
                                                dinner = Meals.Dinner;
                                                showBreakfast();
                                                showLunch();
                                                showDinner();
                                                // display to user the info about their meal plan

                                            }
                                            else
                                            {
                                                //Log.d("myTag", "Got into the else to set breakfast name!");
                                                // looks to see if breakfast, lunch, and dinner have been fulfilled yet
                                                // if not fulfilled it'll set the name of the food to the Meal's static string and set that meal's calories too
                                                if (Meals.breakfast == null)
                                                {
                                                    Meals.breakfast = foodFav.getString("lowercaseDescription");
                                                    //temp = foodFav.getString("KCAL");
                                                    Meals.breakCal = JSONCal.getInt("value");
                                                    break;
                                                }
                                                else if(Meals.Lunch == null)
                                                {
                                                    Meals.Lunch = foodFav.getString("lowercaseDescription");
                                                    Meals.mainCalLunch = JSONCal.getInt("value");
                                                    break;
                                                }
                                                else if(Meals.Dinner == null)
                                                {
                                                    Meals.Dinner = foodFav.getString("lowercaseDescription");
                                                    Meals.mainCalDinner = JSONCal.getInt("value");
                                                    break;
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
            //  |
            //  V  this actually does the GET Request
            ReqQ.add(ObjReq);

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    private void GETRequestCarbs(final float[] TotalCalories)
    {

        ArrayList<String> Carbs = new ArrayList<>();
        Carbs.add("pastas");
        Carbs.add("rices");
        Carbs.add("breads");

        int i =  1 + (int)(Math.random() * ((2 - 1) + 1));

        String APIurl = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=" + Carbs.get(i);
        final String UserCarbs = Carbs.get(i);

        //User me = new User();
        //User me = new User();
        //Local_db.userDao().insertUser(me);

        /////// code for using a GET request for JSON object from API ///////
        ////////////////////////////////////////////////////////////////////
        try
        {

            // Defnition for JSON GET request
            //////// if for some reason the current URL doesn't work, then try these:
            // or "https://api.nal.usda.gov/fdc/v1/foods/list?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX"
            // or https://developer.nrel.gov/api/alt-fuel-stations/v1.json?limit=1&api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX
            // or https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=Cheddar%20Cheese OR https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=Chicken

            RequestQueue ReqQ = Volley.newRequestQueue(this);
            JsonObjectRequest ObjReq = new JsonObjectRequest(
                    Request.Method.GET,
                    APIurl,
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

                                    int index = 1 + (int)(Math.random() * ((jsonArray.length() - 1) + 1));

                                    JSONObject foodFav = jsonArray.getJSONObject(index);

                                    String foodName = foodFav.getString("lowercaseDescription"); // title of the food, also might be index 3 if using JsonObjectRequest
                                    StringTokenizer tokFood = new StringTokenizer(foodName); // tokenizes string to find the keyword, ie food preference

                                    // when string is parsed, look for the keyword for user
                                    while (tokFood.hasMoreTokens())
                                    {
                                        // if the tokenized food name found in request equals the user's food preference, then store the calories
                                        if (tokFood.nextToken().toLowerCase().equals(UserCarbs.toLowerCase()))
                                        {

                                            JSONArray TempJsonObj = foodFav.getJSONArray("foodNutrients");
                                            JSONObject JSONCal = (JSONObject) TempJsonObj.get(3);
                                            TotalCalories[0] += JSONCal.getInt("value");

                                            // if we have reached the max calories OR all the main meals have been added to class "Meals" then we'll display everything in the Meals class
                                            if (Meals.UserCalories <= TotalCalories[0] || (Meals.CarbsLunch != null && Meals.CarbsLunch != null))
                                            {
                                                Log.d("myTag", "Gonna print Carbs!");
                                                // display to user the info about their meal plan

                                            }
                                            else
                                            {
                                                //Log.d("myTag", "Got into the else to set breakfast name!");
                                                // looks to see if breakfast, lunch, and dinner have been fulfilled yet
                                                // if not fulfilled it'll set the name of the food to the Meal's static string and set that meal's calories too
                                                if (Meals.CarbsLunch == null)
                                                {
                                                    Meals.CarbsLunch = foodFav.getString("lowercaseDescription");
                                                    //temp = foodFav.getString("KCAL");
                                                    Meals.carbCalLunch = JSONCal.getInt("value");
                                                    break;
                                                }
                                                else if(Meals.CarbsDinner == null)
                                                {
                                                    Meals.CarbsDinner = foodFav.getString("lowercaseDescription");
                                                    Meals.carbCalDinner = JSONCal.getInt("value");
                                                    break;
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
            //  |
            //  V  this actually does the GET Request
            ReqQ.add(ObjReq);

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    private void GETRequestVegs(final float[] TotalCalories)
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

        String APIurl = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=" + Veggies.get(i);
        final String UserVeg = Veggies.get(i);
        //User me = new User();
        //User me = new User();
        //Local_db.userDao().insertUser(me);

        /////// code for using a GET request for JSON object from API ///////
        ////////////////////////////////////////////////////////////////////
        try
        {

            // Defnition for JSON GET request
            //////// if for some reason the current URL doesn't work, then try these:
            // or "https://api.nal.usda.gov/fdc/v1/foods/list?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX"
            // or https://developer.nrel.gov/api/alt-fuel-stations/v1.json?limit=1&api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX
            // or https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=Cheddar%20Cheese OR https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=Chicken

            RequestQueue ReqQ = Volley.newRequestQueue(this);
            int finalMin = min;
            JsonObjectRequest ObjReq = new JsonObjectRequest(
                    Request.Method.GET,
                    APIurl,
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
                                        if (tokFood.nextToken().toLowerCase().equals(Meals.UserFoodPref.toLowerCase()))
                                        {

                                            JSONArray TempJsonObj = foodFav.getJSONArray("foodNutrients");
                                            JSONObject JSONCal = (JSONObject) TempJsonObj.get(3);
                                            TotalCalories[0] += JSONCal.getInt("value");

                                            // if we have reached the max calories OR all the main meals have been added to class "Meals" then we'll display everything in the Meals class
                                            if (Meals.UserCalories <= TotalCalories[0] || (Meals.VeggiesLunch != null && Meals.VeggiesDinner != null))
                                            {
                                                Log.d("myTag", "Gonna print Veggies!");
                                                // display to user the info about their meal plan

                                            }
                                            else
                                            {
                                                //Log.d("myTag", "Got into the else to set breakfast name!");
                                                // looks to see if breakfast, lunch, and dinner have been fulfilled yet
                                                // if not fulfilled it'll set the name of the food to the Meal's static string and set that meal's calories too
                                                if (Meals.VeggiesLunch == null)
                                                {
                                                    Meals.CarbsLunch = foodFav.getString("lowercaseDescription");
                                                    //temp = foodFav.getString("KCAL");
                                                    Meals.carbCalLunch = JSONCal.getInt("value");
                                                    break;
                                                }
                                                else if(Meals.VeggiesDinner == null)
                                                {
                                                    Meals.CarbsDinner = foodFav.getString("lowercaseDescription");
                                                    Meals.carbCalDinner = JSONCal.getInt("value");
                                                    break;
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
            //  |
            //  V  this actually does the GET Request
            ReqQ.add(ObjReq);


        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}