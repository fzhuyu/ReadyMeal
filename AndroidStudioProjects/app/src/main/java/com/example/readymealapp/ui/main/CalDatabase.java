package com.example.readymealapp.ui.main;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CalRoom.class}, version = 1)
public abstract class CalDatabase extends RoomDatabase {
    public abstract CalorieDao calDao();
}
