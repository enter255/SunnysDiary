package com.example.main;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Bitmap bitmap ;
    private String titleStr ;
    private String descStr ;

    public void setPic(Bitmap bmp) {
        bitmap=bmp ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public Bitmap getPic() {
        return this.bitmap;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
}