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

        if (Meals.UserCalories > (Meals.TotalCalories() + 100) || Meals.VeggiesLunch == null)
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

        Random random = new Random();
        int i = random.nextInt(4);
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
                response -> {
                    try {
                        // get an array of JSON objects that are Arrays of "foods"

                        JSONArray jsonArray = response.getJSONArray("foods");

                        int index = finalMin + (int)(Math.random() * ((18 - finalMin) + 1));

                        JSONObject foodFav = jsonArray.getJSONObject(index);
                        //String foodName = foodFav.getString("lowercaseDescription"); // title of the food, also might be index 3 if using JsonObjectRequest

                        JSONArray TempJsonObj = foodFav.getJSONArray("foodNutrients");
                        JSONObject JSONCal = (JSONObject) TempJsonObj.get(3);
                        while (JSONCal.getInt("value") == 0.0)
                        {
                            if (index++ == jsonArray.length())
                                index = 0;
                            else
                                index++;
                        }
                        LunchCalVeggies = JSONCal.getInt("value");
                        LunchVeggies = foodFav.getString("lowercaseDescription");
                        Meals.VeggiesLunch = LunchVeggies;
                        Meals.vegCalLunch = LunchCalVeggies;


                        index = finalMin + (int)(Math.random() * ((18 - finalMin) + 1));
                        foodFav = jsonArray.getJSONObject(index);
                        TempJsonObj = foodFav.getJSONArray("foodNutrients");
                        JSONCal = (JSONObject) TempJsonObj.get(3);
                        while (JSONCal.getInt("value") == 0.0)
                        {
                            if (index++ == jsonArray.length())
                                index = 0;
                            else
                                index++;
                        }
                        DinnerCalVeggies = JSONCal.getInt("value");
                        DinnerVeggies = foodFav.getString("lowercaseDescription");
                        Meals.VeggiesDinner = DinnerVeggies;
                        Meals.vegCalDinner = DinnerCalVeggies;

                        //showCarbsLunch();
                        //showCarbsDinner();
                        showVeggiesLunch();
                        showVeggiesDinner();

                    }
                    // end of try in case JSON Request is invalid or something
                    catch(JSONException e) {
                        e.printStackTrace();
                    }
                },
                // end of API listener description
                error -> error.printStackTrace()

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