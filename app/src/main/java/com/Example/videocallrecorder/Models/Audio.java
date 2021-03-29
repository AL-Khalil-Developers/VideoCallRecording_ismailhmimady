package com.Example.videocallrecorder.Models;

public class Audio {
    String a;
    String b;
    String c;

    public Audio(){

    }

    public Audio(String str, String str2, String str3) {
        this.b = str;
        this.a = str2;
        this.c = str3;
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

    public void setDuration(String str) {
        this.a = str;
    }

    public void setName(String str) {
        this.b = str;
    }

    public void setSize(String str) {
        this.c = str;
    }
}
