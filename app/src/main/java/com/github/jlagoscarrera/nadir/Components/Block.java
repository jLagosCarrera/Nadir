package com.github.jlagoscarrera.nadir.Components;

import android.graphics.Bitmap;

public class Block {
    Bitmap tile;
    boolean collisionable;

    public Block(){

    }

    public Block(Bitmap tile, boolean collisionable) {
        this.tile = tile;
        this.collisionable = collisionable;
    }

    public Bitmap getTile() {
        return tile;
    }

    public void setTile(Bitmap tile) {
        this.tile = tile;
    }

    public boolean isCollisionable() {
        return collisionable;
    }

    public void setCollisionable(boolean collisionable) {
        this.collisionable = collisionable;
    }
}
