package com.example.readymealapp.ui.main;

public class Meals
{
    // name of meals
    public static String breakfast;
    public static String Lunch;
    public static String Dinner;

    // name of veggies and carbs for lunch
    public static String VeggiesLunch;
    public static String CarbsLunch;

    // name of veggies and carbs for dinner
    public static String VeggiesDinner;
    public static String CarbsDinner;

    // calories associated with breakfast, lunch, dinner, and veggies/carbs pertaining to those meals
    public static float breakCal;
    public static float mainCalLunch;
    public static float vegCalLunch;
    public static float carbCalLunch;
    public static float mainCalDinner;
    public static float vegCalDinner;
    public static float carbCalDinner;

    // constructor
    public Meals()
    {
        breakfast = "";
        Lunch = "";
        Dinner = "";

        VeggiesLunch = "";
        CarbsLunch  = "";

        VeggiesDinner = "";
        CarbsDinner = "";

        breakCal = 0;
        mainCalLunch = 0;
        vegCalLunch = 0;
        carbCalLunch = 0;
        mainCalDinner = 0;
        vegCalDinner = 0;
        carbCalDinner = 0;
    }
}