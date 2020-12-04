package com.example.readymealapp.ui.main;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import androidx.room.Dao;

import com.example.readymealapp.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Dao
public interface CalorieDao {

    // returns a class of CalRoom
    @Query("SELECT * FROM CalRoom WHERE Date = (:date)")
    CalRoom FindDate(Date date);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CalRoom cal);
}
