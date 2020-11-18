package com.example.readymealapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int PriKey;

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
    public int UserBMI;

    @ColumnInfo(name = "Desired_Calories_Under")
    public int Calories;
}
