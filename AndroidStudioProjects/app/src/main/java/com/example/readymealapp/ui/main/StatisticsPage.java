package com.example.readymealapp.ui.main;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        final AppDatabase Local_db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_db").build();
        //

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

        //pieChart = findViewById(R.id.pie_chart);

        //Example data using the AnyChart Pie Chart implementation
        //We need to link this to the database to base it on the user's info

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Mon", 2200));
        data.add(new ValueDataEntry("Tue", 2000));
        data.add(new ValueDataEntry("Wed", 1800));
        data.add(new ValueDataEntry("Thu", 1850));

        pie.data(data);

        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);



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