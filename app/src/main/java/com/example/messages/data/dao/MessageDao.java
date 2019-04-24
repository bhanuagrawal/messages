package com.example.messages.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.messages.data.entities.Message;
import com.example.messages.data.entities.UserMessage;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * from Message")
    LiveData<List<Message>> getAll();

    @Query("SELECT * FROM Message INNER JOIN User ON Message.receiver_id = User.user_id order by date desc")
    LiveData<List<UserMessage>> getAllUserMessages();

    @Query("Delete from Message")
    void deleteAll();

    @Insert
    void add(List<Message> messages);

    @Insert
    void add(Message message);
}
