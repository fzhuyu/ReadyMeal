package com.example.readymealapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readymealapp.ui.main.DataDisplay;
import com.example.readymealapp.ui.main.DietPage;
import com.example.readymealapp.ui.main.Meals;
import com.example.readymealapp.ui.main.StatisticsPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        //instantiating the database
        final AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();
        //
        //GETRequestCarbs();
        Log.d("dori", "yaaaa!!");

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            final String userFirstName = Local_db.userDao().findFirstName();
            final String userLastName = Local_db.userDao().findLastName();
            TextView GreetUser;
            GreetUser = findViewById(R.id.userGreeting);
            GreetUser.setText("Hello,\t" + userFirstName + ' ' + userLastName);

        });

    }

    //Function that will take you from the homepage to the UserInput (go back/restart)
    public void goToUserInputData(View view) {
        Intent UserInputActivity = new Intent (this, UserInput.class);
        startActivity(UserInputActivity);
    }

    //Function that will take you from the homepage to the DailyDiet page from clicking the cardview
    public void goToDailyDiet(View view){
        Intent DailyDietActivity = new Intent (this, DietPage.class);
        startActivity(DailyDietActivity);
    }

    public void goToDataDisplay(View view){
        Intent DataDisplayActivity = new Intent (this, DataDisplay.class);
        startActivity(DataDisplayActivity);
    }

    public void goToCalendar(View view){
        Intent DataDisplayActivity = new Intent (this, calendar_activity.class);
        startActivity(DataDisplayActivity);
    }

    //Function that will take you from the homepage to the Statistics Page from clicking the cardview
    public void goToStatistics(View view){
        Intent StatisticsPageActivity = new Intent (this, StatisticsPage.class);
        startActivity(StatisticsPageActivity);
    }

    public void GETRequestCarbs ()
    {
        final float[] TotalCalories = {0};

        ArrayList<String> Carbs = new ArrayList<>();
        Carbs.add("pastas");
        Carbs.add("rices");
        Carbs.add("breads");

        Random random = new Random();
        int i = random.nextInt(2);

        //int i =  (int) Math.random() * 2;

        String APIurlC = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=mOYUdPGUOJOJQJxoKffVm7buXQNzz5oKj7oqEBnX&query=" + Carbs.get(i);
        final String UserCarbs = Carbs.get(i);
        final int min = 4;

        JsonObjectRequest ObjReqCarb = new JsonObjectRequest(
                Request.Method.GET,
                APIurlC,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // get an array of JSON objects that are Arrays of "foods"
                            DietPage diet = new DietPage();
                            JSONArray jsonArray = response.getJSONArray("foods");

                            int index = min + (int)(Math.random() * ((15 - min) + 1));

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
                                    Meals.CarbsLunch = foodFav.getString("lowercaseDescription");
                                    Meals.carbCalLunch = JSONCal.getInt("value");

                                    break;
                                        /*
                                        if (Meals.CarbsLunch == null)
                                        {
                                            Log.d("myTag", "HEY IS THIS NULL? LMAO!!");
                                            Meals.CarbsLunch = foodFav.getString("description");
                                            //temp = foodFav.getString("KCAL");
                                            Meals.carbCalLunch = JSONCal.getInt("value");
                                            TotalCalories[0] += Meals.carbCalLunch;
                                            break;
                                        }

                                        if(Meals.CarbsDinner == null)
                                        {
                                            Meals.CarbsDinner = foodFav.getString("description");
                                            Meals.carbCalDinner = JSONCal.getInt("value");
                                            TotalCalories[0] += Meals.carbCalDinner;
                                            //break;
                                            return;
                                        }
                                        */


                                }
                                // else, don't set the calories
                            }
                            // end of while loop token
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
        //ReqQ.add(ObjReqCarb);
        Volley.newRequestQueue(getApplicationContext()).add(ObjReqCarb);
    }


}




/*
    @Override
    public void onClick(View view) {
        Intent i;
    //Function that will take you from the homepage to the Statistics Page from clicking the cardview
    public void goToStatistics(View view){
        Intent StatisticsPageActivity = new Intent (this, StatisticsPage.class);
        startActivity(StatisticsPageActivity);
    }


}
*/