package com.example.readymealapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE UserID IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE First_Name LIKE :FName AND " +
            "Last_Name LIKE :LName LIMIT 1")
    User findByName(String FName, String LName);

    @Query("SELECT * FROM user WHERE Food_Preference IN (:FoodFav) AND First_Name IN (:FName) AND Last_Name IN (:LName)")
    User LoadFoodPref(String FName, String LName, String FoodFav);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(User... users);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    public void updateUsers(User... users);
}

