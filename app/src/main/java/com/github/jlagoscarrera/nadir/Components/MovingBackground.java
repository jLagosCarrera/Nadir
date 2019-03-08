package com.github.jlagoscarrera.nadir.Components;

import android.graphics.Bitmap;
import android.graphics.PointF;

/**
 * The moving background component.
 */
public class MovingBackground {
    /**
     * The current pointF position.
     */
    public PointF position;
    /**
     * The Image used on the background.
     */
    public Bitmap image;

    /**
     * Instantiates a new moving background component using a given point.
     *
     * @param image the image used on the background
     * @param x     the x position
     * @param y     the y position
     */
    public MovingBackground(Bitmap image, float x, float y) {
        this.image = image;
        this.position = new PointF(x, y);
    }

    /**
     * Instantiates a new moving background component using screen width.
     *
     * @param image       the image used on the background
     * @param screenWidth the screen width
     */
    public MovingBackground(Bitmap image, int screenWidth) {
        this(image, screenWidth - image.getWidth(), 0);
    }

    /**
     * Instantiates a new moving background component using all screen width and height.
     *
     * @param image        the image used on the background
     * @param screenWidth  the screen width
     * @param screenHeight the screen height
     */
    public MovingBackground(Bitmap image, int screenWidth, int screenHeight) {
        this.image = image;
        this.position = new PointF(screenWidth - image.getWidth(), screenHeight - image.getHeight());
    }

    /**
     * Move the background position.
     *
     * @param vel the velocity of the movement
     */
    public void move(int vel) {
        position.x += vel;
    }
}
