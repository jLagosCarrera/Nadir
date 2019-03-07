package com.github.jlagoscarrera.nadir.Components;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Character {
    int velX, velY;
    int x, y;
    Bitmap sprite;
    Rect container;

    public Character(int velX, int velY, int x, int y, Bitmap sprite, Rect container) {
        this.velX = velX;
        this.velY = velY;
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.container = container;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
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

    public Rect getContainer() {
        return container;
    }

    public void setContainer(Rect container) {
        this.container = container;
    }
}
