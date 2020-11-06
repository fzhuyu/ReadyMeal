package com.example.readymealapp;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM user WHERE First_Name LIKE (:FName)")
    LiveData<User> findFirstName(String FName);

    @Query("SELECT * FROM user WHERE Last_Name LIKE (:LName)")
    LiveData<User> findLastName(String LName);

    @Query("SELECT * FROM user WHERE Food_Preference IN (:FName) AND (:LName)")
    LiveData<User> LoadFoodPref(String FName, String LName);

    @Query("SELECT * FROM user WHERE BMI IN (:FName) AND (:LName)")
    LiveData<User> LoadBMI(String FName, String LName);

    @Query("SELECT * FROM user WHERE Desired_Calories_Under IN (:FName) AND (:LName)")
    LiveData<User> LoadCalories(String FName, String LName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(User... users);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    public void updateUsers(User... users);
}

