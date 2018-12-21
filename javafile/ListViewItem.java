package com.example.main;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Bitmap picture;
    private String datetime;
    private String location;
    private String context;
    private String emotion;
    private String whether;

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public void setWhether(String whether) {
        this.whether = whether;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getLocation() {
        return location;
    }

    public String getContext() {
        return context;
    }

    public String getEmotion() {
        return emotion;
    }

    public String getWhether() {
        return whether;
    }

}