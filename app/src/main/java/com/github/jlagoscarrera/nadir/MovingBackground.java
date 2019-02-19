package com.github.jlagoscarrera.nadir;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class MovingBackground {
    public PointF position;
    public Bitmap image;

    public MovingBackground(Bitmap image, float x, float y) {
        this.image = image;
        this.position = new PointF(x, y);
    }

    public MovingBackground(Bitmap image, int screenWidth) {
        this(image, screenWidth - image.getWidth(), 0);
    }

    public MovingBackground(Bitmap image, int screenWidth, int screenHeight) {
        this.image = image;
        this.position = new PointF(screenWidth - image.getWidth(), screenHeight - image.getHeight());
    }

    public void move(int vel) {
        position.x += vel;
    }
}
