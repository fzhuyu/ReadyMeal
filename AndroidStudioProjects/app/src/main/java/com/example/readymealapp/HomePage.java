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
        if (Meals.UserCalories > (Meals.TotalCalories() + 150) && Meals.CarbsLunch == null)
            GETRequestCarbs();


        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            final String userFirstName = Local_db.userDao().findFirstName();
            final String userLastName = Local_db.userDao().findLastName();
            TextView GreetUser;
            GreetUser = findViewById(R.id.userGreeting);
            GreetUser.setText("Hello,\t" + userFirstName + ' ' + userLastName);

        });

        Local_db.close();

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
        final float CalUnder = ((Meals.UserCalories - Meals.TotalCalories()) / 2);

        JsonObjectRequest ObjReqCarb = new JsonObjectRequest(
                Request.Method.GET,
                APIurlC,
                null,
                response -> {
                    try {
                        // get an array of JSON objects that are Arrays of "foods"
                        JSONArray jsonArray = response.getJSONArray("foods");

                        int index = min + (int)(Math.random() * ((15 - min) + 1));

                        JSONObject foodFav = jsonArray.getJSONObject(index);

                        JSONArray TempJsonObj = foodFav.getJSONArray("foodNutrients");
                        JSONObject JSONCal = (JSONObject) TempJsonObj.get(3);
                        while (JSONCal.getInt("value") == 0.0)
                        {
                            if (index++ == jsonArray.length())
                                index = 0;
                            else
                                index++;
                        }
                        Meals.CarbsLunch = foodFav.getString("lowercaseDescription");
                        Meals.carbCalLunch = JSONCal.getInt("value");

                        index = min + (int)(Math.random() * ((15 - min) + 1));
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

                        Meals.CarbsDinner = foodFav.getString("lowercaseDescription");
                        Meals.carbCalDinner = JSONCal.getInt("value");

                    }
                    // end of try in case JSON Request is invalid or something
                    catch(JSONException e) {
                        e.printStackTrace();
                    }
                }, // end of API listener description
                error -> error.printStackTrace()
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