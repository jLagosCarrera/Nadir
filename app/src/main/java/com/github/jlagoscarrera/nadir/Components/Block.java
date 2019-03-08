package com.github.jlagoscarrera.nadir.Components;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * The Block class.
 */
public class Block {
    /**
     * The Tile used for the block.
     */
    Bitmap tile;
    /**
     * The collisionable property of the block for checking collisions.
     */
    boolean collisionable;
    /**
     * The Block type.
     */
    char blockType;
    /**
     * The Block rect position.
     */
    Rect blockRect;

    /**
     * Instantiates a new Block.
     *
     * @param tile          the tile used for the block
     * @param collisionable the collisionable property for checking collisions
     * @param blockType     the block type
     */
    public Block(Bitmap tile, boolean collisionable, char blockType) {
        this.tile = tile;
        this.collisionable = collisionable;
        this.blockType = blockType;
    }

    /**
     * Gets tile used for the block.
     *
     * @return the tile used for the block
     */
    public Bitmap getTile() {
        return tile;
    }

    /**
     * Sets tile used for the block.
     *
     * @param tile the tile used for the block
     */
    public void setTile(Bitmap tile) {
        this.tile = tile;
    }

    /**
     * Checks if block is collisionable.
     *
     * @return the property of being collisionable
     */
    public boolean isCollisionable() {
        return collisionable;
    }

    /**
     * Gets block type.
     *
     * @return the block type
     */
    public char getBlockType() {
        return blockType;
    }

    /**
     * Sets block type.
     *
     * @param blockType the block type
     */
    public void setBlockType(char blockType) {
        this.blockType = blockType;
    }

    /**
     * Sets collisionable property.
     *
     * @param collisionable the new collisionable state
     */
    public void setCollisionable(boolean collisionable) {
        this.collisionable = collisionable;
    }

    /**
     * Gets block rect position.
     *
     * @return the block rect position
     */
    public Rect getBlockRect() {
        return blockRect;
    }

    /**
     * Sets block rect position.
     *
     * @param blockRect the block rect position
     */
    public void setBlockRect(Rect blockRect) {
        this.blockRect = blockRect;
    }
}
