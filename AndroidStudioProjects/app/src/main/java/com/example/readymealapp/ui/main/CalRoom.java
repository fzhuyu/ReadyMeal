package com.example.readymealapp.ui.main;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class CalRoom {
    @PrimaryKey(autoGenerate = false)
    // public int PriKey;

    @ColumnInfo(name = "Date")
    public Date CurDate;

    @ColumnInfo(name = "Calorie_Goal")
    public int CalGoal;

    @ColumnInfo(name = "Calorie_Achieved")
    public int CalAch;
}
