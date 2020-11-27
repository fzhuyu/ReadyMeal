package com.example.readymealapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = false)
   // public int PriKey;

    @ColumnInfo(name = "ID")
    public int UserID;

    @ColumnInfo(name = "First_Name")
    public String FName;

    @ColumnInfo(name = "Last_Name")
    public String LName;

    @ColumnInfo(name = "Food_Preference")
    public String FoodFav;

    @ColumnInfo(name = "Sex")
    public String UserSex;

    @ColumnInfo(name = "Age")
    public int UserAge;

    @ColumnInfo(name = "BMI")
    public double UserBMI;

    @ColumnInfo(name = "Desired_Calories_Under")
    public int DesiredCalories;

    @ColumnInfo(name = "Current_Calories")
    public int CurrentCalories;

    @ColumnInfo(name = "Activity")
    public String UserActivity;


    /*public User(int UserID, String FName, String LName, String FoodFav, String UserSex, int UserAge, int UserBMI, int Calories)
    {
        this.UserID = UserID;
        this.FName = FName;
        this.LName = LName;
        this.FoodFav = FoodFav;
        this.UserSex = UserSex;
        this.UserAge = UserAge;
        this.UserBMI = UserBMI;
        this.Calories = Calories;
    }*/
}
