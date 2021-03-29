package com.Example.videocallrecorder.Models;

import android.net.Uri;

public class Video {
    String a;
    String b;
    String c;
    Uri d;

    public Video(){

    }

    public Video(String str, String str2, String str3, Uri uri) {
        this.b = str;
        this.a = str2;
        this.c = str3;
        this.d = uri;
    }

    public String getDuration() {
        return this.a;
    }

    public String getName() {
        return this.b;
    }

    public String getSize() {
        return this.c;
    }

    public Uri getThumbnail() {
        return this.d;
    }

    public void setDuration(String str) {
        this.a = str;
    }

    public void setName(String str) {
        this.b = str;
    }

    public void setSize(String str) {
        this.c = str;
    }

    public void setThumbnail(Uri uri) {
        this.d = uri;
    }
}
