package com.example.readymealapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    /*
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE PriKey IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);
     */

//<<<<<<< Updated upstream
    @Query("SELECT First_Name FROM User WHERE ID = 0")
    String findFirstName();

    @Query("SELECT Last_Name FROM user WHERE ID = 0")
    String findLastName();

    @Query("SELECT Age FROM user WHERE ID = 0")
    int findAge();

    /*@Query("SELECT * Food_Preference FROM user WHERE ID = 0")
    LiveData<User> LoadFoodPref();

//=======
    @Query("SELECT First_Name FROM user WHERE ID = 0")
    LiveData<String> findFirstName();

    @Query("SELECT Last_Name FROM user WHERE ID = 0")
    LiveData<String> findLastName();

     */

    @Query("SELECT Food_Preference FROM user WHERE ID = 0")
    String LoadFoodPref();
//>>>>>>> Stashed changes

    @Query("SELECT BMI FROM user WHERE ID = 0")
    double LoadBMI();

    @Query("SELECT Sex FROM user WHERE ID = 0")
    String LoadSex();

    @Query("SELECT Activity FROM user WHERE ID = 0")
    String LoadActivity();

    @Query("SELECT Desired_Calories_Under FROM user WHERE ID = 0")
    int LoadDesiredCalories();

    @Query("SELECT Current_Calories FROM user WHERE ID = 0")
    int LoadCurrentCalories();

    @Query("DELETE FROM user")
    void NukeTable();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User person);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    public void updateUsers(User... users);
}

