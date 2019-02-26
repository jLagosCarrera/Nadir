package com.github.jlagoscarrera.nadir.Components;

import android.graphics.Bitmap;

public class Block {
    Bitmap tile;
    boolean collisionable;
    char blockType;

    public Block(Bitmap tile, boolean collisionable, char blockType) {
        this.tile = tile;
        this.collisionable = collisionable;
        this.blockType = blockType;
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

    public char getBlockType() {
        return blockType;
    }

    public void setBlockType(char blockType) {
        this.blockType = blockType;
    }

    public void setCollisionable(boolean collisionable) {
        this.collisionable = collisionable;
    }
}
