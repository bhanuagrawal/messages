package com.example.messages.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "user_id",
        childColumns = "receiver_id"))
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "receiver_id")
    private int receiver;

    private String text;

    private Date date;

    public Message(int receiver, String text, Date date) {
        this.receiver = receiver;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
