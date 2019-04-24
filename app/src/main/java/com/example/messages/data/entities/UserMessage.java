package com.example.messages.data.entities;

import androidx.room.Embedded;

public class UserMessage {

    @Embedded
    User user;

    @Embedded
    Message message;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
