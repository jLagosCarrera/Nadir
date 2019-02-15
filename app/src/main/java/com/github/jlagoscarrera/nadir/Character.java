package com.github.jlagoscarrera.nadir;

import android.graphics.Bitmap;

public class Character {
    float velX, velY;
    int x, y;
    Bitmap sprite;

    public Character(float velX, float velY, int x, int y, Bitmap sprite) {
        this.velX = velX;
        this.velY = velY;
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getSprite() {
        return sprite;
    }

    public void setSprite(Bitmap sprite) {
        this.sprite = sprite;
    }
}
