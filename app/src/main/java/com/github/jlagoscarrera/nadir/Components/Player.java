package com.github.jlagoscarrera.nadir.Components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.github.jlagoscarrera.nadir.Scenes.Play;


public class Player extends Character {
    Rect cameraHandler;
    Paint cameraP;
    public int offSetX, offSetY;
    Play playRef;
    boolean lookingRight = true;
    Matrix flip;
    int playerGravity;

    public Player(int velX, int velY, int x, int y, Bitmap sprite, Rect container, Rect cameraHandler, Play playRef) {
        super(velX, velY, x, y, sprite, container);
        this.cameraHandler = cameraHandler;
        cameraP = new Paint();
        cameraP.setColor(Color.RED);
        cameraP.setStyle(Paint.Style.STROKE);

        playerGravity = velY;
        offSetX = 0;
        offSetY = 0;
        this.playRef = playRef;

        flip = new Matrix();
        flip.postScale(-1, 1, sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    public boolean checkIntersect(Rect intersectedRect) {
        for (Room[] roomsRow : playRef.r.level.getRooms()) {
            for (Room room : roomsRow) {
                for (Block[] blocksRow : room.blocks) {
                    for (Block block : blocksRow) {
                        if (block.blockRect.intersect(intersectedRect) && block.isCollisionable()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void draw(Canvas c) {
        try {
            c.drawRect(container, cameraP);
            c.drawRect(cameraHandler, cameraP);
            c.drawBitmap(sprite, null, container, null);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public void moveRight() {
        Rect auxRect = new Rect(x + velX, y, x + velX + playRef.blockWidth, y + playRef.blockHeigth);
        if (!checkIntersect(auxRect)) {
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

    public void moveLeft() {
        Rect auxRect = new Rect(x - velX, y, x - velX + playRef.blockWidth, y + playRef.blockHeigth);
        if (!checkIntersect(auxRect)) {
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

    public void jump() {
        velY = playerGravity * -2;
        playRef.jumping = false;
        playRef.falling = true;
    }

    public void aplicarGravedad() {
        if (velY < playerGravity) {
            velY++;
        } else {
            velY = playerGravity;
        }

        Rect auxRect = new Rect(x, y + velY, x + playRef.blockWidth, y + velY + playRef.blockHeigth);
        if (!checkIntersect(auxRect)) {
            int aux = container.height();

            if (container.top + velY < cameraHandler.top) {
                container.top = cameraHandler.top;
                container.bottom = cameraHandler.top + aux;
                offSetY -= cameraHandler.top - container.top + velY;
            }
            if (container.top + velY == cameraHandler.top) {
                container.top = cameraHandler.top;
                container.bottom = cameraHandler.top + aux;
            }
            if (container.top + velY > cameraHandler.top && container.bottom + velY < cameraHandler.bottom) {
                container.bottom += velY;
                container.top += velY;
            }
            if (container.bottom + velY == cameraHandler.bottom) {
                container.bottom = cameraHandler.bottom;
                container.top = cameraHandler.bottom - aux;
            }
            if (container.bottom + velY > cameraHandler.bottom) {
                container.bottom = cameraHandler.bottom;
                container.top = cameraHandler.bottom - aux;
                offSetY -= container.bottom + velY - cameraHandler.bottom;
            }

            y += velY;
        } else {
            playRef.falling = false;
        }
    }
}
