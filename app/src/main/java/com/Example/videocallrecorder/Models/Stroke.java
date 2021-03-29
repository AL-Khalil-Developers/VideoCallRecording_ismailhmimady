package com.Example.videocallrecorder.Models;

import android.graphics.Path;

public class Stroke {
    private int paintColor;
    private Path path;

    public Stroke(Path path, int i) {
        this.path = path;
        this.paintColor = i;
    }

    public int getPaintColor() {
        return this.paintColor;
    }

    public Path getPath() {
        return this.path;
    }

    public void setPaintColor(int i) {
        this.paintColor = i;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
