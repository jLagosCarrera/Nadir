package com.github.jlagoscarrera.nadir.Components;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * The Character class.
 */
public class Character {
    /**
     * The velocity on X axis.
     */
    int velX;
    /**
     * The gravity.
     */
    int gravity;
    /**
     * The X position of the character.
     */
    int x;
    /**
     * The Y position of the character.
     */
    int y;
    /**
     * The Sprite of the character.
     */
    Bitmap sprite;
    /**
     * The Container rect of the character.
     */
    Rect container;

    /**
     * Instantiates a new Character.
     *
     * @param velX      the velocity on the X axis
     * @param gravity   the gravity
     * @param x         the x position of the character
     * @param y         the y position of the character
     * @param sprite    the sprite of the character
     * @param container the container rect of the character
     */
    public Character(int velX, int gravity, int x, int y, Bitmap sprite, Rect container) {
        this.velX = velX;
        this.gravity = gravity;
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.container = container;
    }

    /**
     * Gets velocity on the X axis.
     *
     * @return the velocity on the X axis
     */
    public int getVelX() {
        return velX;
    }

    /**
     * Sets velocity on the X axis.
     *
     * @param velX the velocity on the X axis
     */
    public void setVelX(int velX) {
        this.velX = velX;
    }

    /**
     * Gets gravity.
     *
     * @return the gravity
     */
    public int getGravity() {
        return gravity;
    }

    /**
     * Sets gravity.
     *
     * @param gravity the gravity
     */
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    /**
     * Gets x position of the character.
     *
     * @return the x position of the character
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x position of the character.
     *
     * @param x the x position of the character
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y position of the character.
     *
     * @return the y position of the character
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y position of the character.
     *
     * @param y the y position of the character
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets sprite of the character.
     *
     * @return the sprite of the character
     */
    public Bitmap getSprite() {
        return sprite;
    }

    /**
     * Sets sprite of the character.
     *
     * @param sprite the sprite of the character
     */
    public void setSprite(Bitmap sprite) {
        this.sprite = sprite;
    }

    /**
     * Gets container of the character.
     *
     * @return the container of the character
     */
    public Rect getContainer() {
        return container;
    }

    /**
     * Sets container of the character.
     *
     * @param container the container of the character
     */
    public void setContainer(Rect container) {
        this.container = container;
    }
}
