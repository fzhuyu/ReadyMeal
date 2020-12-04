package com.example.readymealapp.ui.main;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

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

    public static class Converters {
        @TypeConverter
        public static Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }
        @TypeConverter
        public static Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
    }
}
