package com.Example.videocallrecorder.Models;

import android.graphics.Bitmap;

public class Image {
    public Bitmap bitmap;
    public String name;
    public String size;

    public Image(){

    }

    public Image(String str, String str2, Bitmap bitmap) {
        this.name = str;
        this.size = str2;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public String getName() {
        return this.name;
    }

    public String getSize() {
        return this.size;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setSize(String str) {
        this.size = str;
    }
}
