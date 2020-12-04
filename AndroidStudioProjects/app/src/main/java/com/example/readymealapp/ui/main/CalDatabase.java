package com.example.readymealapp.ui.main;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {CalRoom.class}, version = 1)
@TypeConverters({CalRoom.Converters.class})
public abstract class CalDatabase extends RoomDatabase {
    public abstract CalorieDao calDao();
}
