package com.example.messages.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.messages.data.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * from User")
    LiveData<List<User>> getAll();

    @Query("Delete from User")
    void deleteAll();

    @Insert
    void add(List<User> items);


    @Query("SELECT * from User where user_id = :userId")
    LiveData<User> getUser(int userId);
}
