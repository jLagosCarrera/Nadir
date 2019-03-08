package com.github.jlagoscarrera.nadir.Components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.github.jlagoscarrera.nadir.Scenes.Play;


/**
 * The Player class.
 */
public class Player extends Character {
    /**
     * The camera rectangle for handling camera movement.
     */
    Rect cameraHandler;
    /**
     * The Camera paint for debugging.
     */
    Paint cameraP;
    /**
     * The offset for the canvas translate on X axis.
     */
    public int offSetX;
    /**
     * The offset for the canvas translate on Y axis.
     */
    public int offSetY;
    /**
     * The Play scene reference.
     */
    Play playRef;
    /**
     * Indicates if player is looking right.
     */
    boolean lookingRight = true;
    /**
     * The Flip for inverting player tile on X axis.
     */
    Matrix flip;
    /**
     * The Player gravity.
     */
    int playerGravity;

    /**
     * Instantiates a new Player.
     *
     * @param velX          the velocity on X axis
     * @param gravity       the player gravity
     * @param x             the x position of the player
     * @param y             the y position of the player
     * @param sprite        the player sprite
     * @param container     the player rect container
     * @param cameraHandler the camera handler rect container
     * @param playRef       the play scene reference
     */
    public Player(int velX, int gravity, int x, int y, Bitmap sprite, Rect container, Rect cameraHandler, Play playRef) {
        super(velX, gravity, x, y, sprite, container);
        this.cameraHandler = cameraHandler;
        cameraP = new Paint();
        cameraP.setColor(Color.RED);
        cameraP.setStyle(Paint.Style.STROKE);

        playerGravity = gravity;
        offSetX = 0;
        offSetY = 0;
        this.playRef = playRef;

        flip = new Matrix();
        flip.postScale(-1, 1, sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    /**
     * Check intersect with a block.
     *
     * @param intersectedRect the block to check intersection with other.
     * @return an object array with first position indicating if there was collision with true or false and second position indicating the collided block or null
     */
    public Object[] checkIntersect(Rect intersectedRect) {
        for (Room[] roomsRow : playRef.r.level.getRooms()) {
            for (Room room : roomsRow) {
                for (Block[] blocksRow : room.blocks) {
                    for (Block block : blocksRow) {
                        if (block.blockRect.intersect(intersectedRect) && block.isCollisionable()) {
                            return new Object[]{true, block};
                        }
                    }
                }
            }
        }
        return new Object[]{false, null};
    }

    /**
     * Draw on the canvas.
     *
     * @param c the canvas to draw at
     */
    public void draw(Canvas c) {
        try {
            //c.drawRect(container, cameraP);
            //c.drawRect(cameraHandler, cameraP);
            c.drawBitmap(sprite, null, container, null);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    /**
     * Move player to the right.
     */
    public void moveRight() {
        Rect auxRect = new Rect(x + velX, y, x + velX + playRef.blockWidth - 5, y + playRef.blockHeigth - 5);
        Object[] intersect = checkIntersect(auxRect);
        if (!(boolean) intersect[0]) {
            int aux = container.width();

            if (container.right + velX > cameraHandler.right) {
                container.right = cameraHandler.right;
                container.left = container.right - aux;
                offSetX -= velX;
            } else {
                container.right += velX;
                container.left += velX;
            }

            x += velX;
        }

        if (!lookingRight) {
            lookingRight = true;
            sprite = Bitmap.createBitmap(sprite, 0, 0, sprite.getWidth(), sprite.getHeight(), flip, true);
        }
    }

    /**
     * Move player to the left.
     */
    public void moveLeft() {
        Rect auxRect = new Rect(x - velX, y, x - velX + playRef.blockWidth - 5, y + playRef.blockHeigth - 5);
        Object[] intersect = checkIntersect(auxRect);
        if (!(boolean) intersect[0]) {
            int aux = container.width();

            if (container.left - velX < cameraHandler.left) {
                container.left = cameraHandler.left;
                container.right = cameraHandler.left + aux;
                offSetX += velX;
            } else {
                container.left -= velX;
                container.right -= velX;
            }

            x -= velX;
        }

        if (lookingRight) {
            lookingRight = false;
            sprite = Bitmap.createBitmap(sprite, 0, 0, sprite.getWidth(), sprite.getHeight(), flip, true);
        }
    }

    /**
     * Jump.
     */
    public void jump() {
        gravity = playerGravity * -2;
        playRef.jumping = false;
        playRef.falling = true;
    }

    /**
     * Applies gravity and handles Y position of the player.
     */
    public void aplicarGravedad() {
        int containerHeight = container.height();

        Rect auxRect = new Rect(x, y + gravity, x + playRef.blockWidth - 5, y + gravity + playRef.blockHeigth - 5);
        Object[] intersect = checkIntersect(auxRect);
        if (!(boolean) intersect[0]) {
            y += gravity;
            if (container.top + gravity < cameraHandler.top) {
                container.top = cameraHandler.top;
                container.bottom = cameraHandler.top + containerHeight;
                offSetY -= gravity;
                playRef.falling = true;
            } else if (container.top + gravity == cameraHandler.top) {
                container.top = cameraHandler.top;
                container.bottom = cameraHandler.top + containerHeight;
                playRef.falling = true;
            } else if (container.top + gravity > cameraHandler.top && container.bottom + gravity < cameraHandler.bottom) {
                container.bottom += gravity;
                container.top += gravity;
                playRef.falling = true;
            } else if (container.bottom + gravity == cameraHandler.bottom) {
                container.bottom = cameraHandler.bottom;
                container.top = cameraHandler.bottom - containerHeight;
                playRef.falling = false;
            } else if (container.bottom + gravity > cameraHandler.bottom) {
                container.bottom = cameraHandler.bottom;
                container.top = cameraHandler.bottom - containerHeight;
                offSetY -= gravity;
                playRef.falling = false;
            }
        } else {
            Block intersectedBlock = (Block) intersect[1];
//            Log.i("PRUEBAS -> Y+VelY+Heigh", String.valueOf(y + gravity + containerHeight));
//            Log.i("PRUEBAS -> Y+VelY", String.valueOf(y + gravity));
//            Log.i("PRUEBAS -> RECT TOP", String.valueOf(intersectedBlock.getBlockRect().top));
//            Log.i("PRUEBAS -> RECT BOTTOM", String.valueOf(intersectedBlock.getBlockRect().bottom));

            if (y + gravity + containerHeight <= intersectedBlock.getBlockRect().top) {
                if (y + gravity + containerHeight <= intersectedBlock.getBlockRect().top) {
                    int aumento = intersectedBlock.getBlockRect().top - (y + gravity + containerHeight);
                    y += aumento;
                    container.top += aumento;
                    container.bottom += aumento;
                    playRef.falling = false;
                } else {
                    int sorpasso = (y + gravity + containerHeight) - intersectedBlock.getBlockRect().top;
                    y = intersectedBlock.getBlockRect().top;
                    container.top -= sorpasso;
                    container.bottom -= sorpasso;
                    playRef.falling = false;
                }
            } else if (y + gravity >= intersectedBlock.getBlockRect().bottom) {
                if (y + gravity >= intersectedBlock.getBlockRect().bottom) {
                    int aumento = (y + gravity) - intersectedBlock.getBlockRect().bottom;
                    y += aumento;
                    container.top += aumento;
                    container.bottom += aumento;
                } else {
                    int sorpasso = intersectedBlock.getBlockRect().bottom - (y + gravity);
                    y = intersectedBlock.getBlockRect().bottom;
                    container.top -= sorpasso;
                    container.bottom -= sorpasso;
                }
            }
        }

        if (gravity < playerGravity) {
            gravity++;
        } else {
            gravity = playerGravity;
        }
    }
}
