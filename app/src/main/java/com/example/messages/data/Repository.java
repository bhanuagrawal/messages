package com.example.messages.data;

import android.app.Application;

import com.example.messages.Utils;
import com.example.messages.data.entities.Message;
import com.example.messages.data.entities.User;
import com.example.messages.data.entities.UserMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {

    Application application;
    AppDatabase appDatabase;

    public Repository(Application application) {
        this.application = application;
        appDatabase = AppDatabase.getAppDatabase(application);

    }

    public LiveData<List<User>> getUsers() {
           return appDatabase.userDao().getAll();
    }

    public void addUsers() {
        String userJson = Utils.loadJSONFromAsset(application.getApplicationContext(), "users.json");
        List<User> users = new Gson().fromJson(userJson, new TypeToken<List<User>>(){}.getType());
        appDatabase.userDao().add(users);
    }


    public LiveData<User> getUser(int userId) {
        return appDatabase.userDao().getUser(userId);
    }

    public void saveMessage(User user, String otp) {
        appDatabase.messageDao().add(new Message(user.getId(), otp, new Date()));
    }

    public LiveData<List<UserMessage>> getMessages() {
        return appDatabase.messageDao().getAllUserMessages();
    }
}
