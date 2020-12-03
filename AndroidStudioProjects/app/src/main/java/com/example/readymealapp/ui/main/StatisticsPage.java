package com.example.readymealapp.ui.main;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.example.readymealapp.AppDatabase;
import com.example.readymealapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StatisticsPage extends AppCompatActivity {

    //AnyChartView pieChart;

    //String[] daysOfWeek = {"Sun", "Mon", "Tue"};
    //int[] calories = {2200, 2000, 1850};

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        final AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();
        // Instatiating the database to diplay the user's input in the profile section

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            //final String userFirstName = Local_db.userDao().findFirstName();
            final int userAge = Local_db.userDao().findAge();
            double userBMI = Local_db.userDao().LoadBMI();
            final int prefCalories = Local_db.userDao().LoadDesiredCalories();

            final String sex = Local_db.userDao().LoadSex();
            final String userActivity = Local_db.userDao().LoadActivity();

            TextView PrintUA;
            PrintUA = findViewById(R.id.UA_out);
            PrintUA.setText(userActivity);

            TextView PrintSex;
            PrintSex = findViewById(R.id.sex_out);
            PrintSex.setText(sex);

            TextView PrintBMI;
            PrintBMI = findViewById(R.id.bmi_out);
            PrintBMI.setText(Double.toString(userBMI));

            TextView PrintGC;
            PrintGC = findViewById(R.id.caloric_goal_out2);
            PrintGC.setText(Integer.toString(prefCalories) + " cals");

            TextView PrintAge;
            PrintAge = findViewById(R.id.age_out);
            PrintAge.setText(Integer.toString(userAge));

        });

        // View Flipper to switch between the card views and the graph view
        viewFlipper = findViewById(R.id.view_flip);


        //Example data using the AnyChart Cartesian Chart implementation
        Cartesian bar = AnyChart.column();

        List<DataEntry> data1 = new ArrayList<>();
        data1.add(new ValueDataEntry("Breakfast", 500));
        data1.add(new ValueDataEntry("Lunch", 850));
        data1.add(new ValueDataEntry("Dinner", 900));

        bar.data(data1);

        bar.title("Daily Calorie Intake");
        bar.xAxis(0).title("Meal Type");
        bar.yAxis(0).title("Calories");

        AnyChartView barChartView = (AnyChartView) findViewById(R.id.bar_chart_view);
        barChartView.setChart(bar);

        // Removing the Parent otherwise we receive an error

        if (barChartView.getParent() != null)
        {
            ((ViewGroup)barChartView.getParent()).removeView(barChartView);
        }
        viewFlipper.addView(barChartView);
    }

    //SwitchView function for the viewflipper for the button to switch between views
    public void switchView(View v)
    {
        viewFlipper.showNext();
    }


/* Another method to try the data (using a for loop) - does not currently work

    public void SetupPieChart(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for(int i = 0; i < daysOfWeek.length; i++)
        {
            dataEntries.add(new ValueDataEntry(daysOfWeek[i], calories[i]));
        }

        pie.data(dataEntries);
        pieChart.setChart(pie);
    }*/
}