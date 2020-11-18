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

    /*@Query("SELECT * FROM user")
    List<User> getAll();

     */

    /*@Query("SELECT * FROM user WHERE PriKey IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

     */

    @Query("SELECT First_Name FROM user WHERE ID = 0")
    LiveData<User> findFirstName();

    @Query("SELECT Last_Name FROM user WHERE ID = 0")
    LiveData<User> findLastName();

    @Query("SELECT Food_Preference FROM user WHERE ID = 0")
    LiveData<User> LoadFoodPref();

    @Query("SELECT BMI FROM user WHERE ID = 0")
    int LoadBMI();

    @Query("SELECT Desired_Calories_Under FROM user WHERE ID = 0")
    int LoadCalories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User person);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    public void updateUsers(User... users);
}

