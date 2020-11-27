package com.example.readymealapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class StatisticsPage extends AppCompatActivity {

    //AnyChartView pieChart;

    //String[] daysOfWeek = {"Sun", "Mon", "Tue"};
    //int[] calories = {2200, 2000, 1850};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

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