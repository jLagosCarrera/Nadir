package com.github.jlagoscarrera.nadir.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.github.jlagoscarrera.nadir.Components.Block;
import com.github.jlagoscarrera.nadir.Components.MenuButton;
import com.github.jlagoscarrera.nadir.Components.Player;
import com.github.jlagoscarrera.nadir.Core.NadirEngine;
import com.github.jlagoscarrera.nadir.Scripts.RoomFiller;
import com.github.jlagoscarrera.nadirGame.R;

public class Play extends Scene {
    public RoomFiller r;
    MenuButton btnBack;
    MenuButton btnLeft, btnRight;
    MenuButton btnJump;
    public int blockHeigth, blockWidth;
    int blockX, blockY;
    Player player;
    boolean movingLeft, movingRight;
    public boolean jumping, falling;

    public Play(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);

        blockHeigth = screenHeight / 8;
        blockWidth = blockHeigth;
        blockX = 0;
        blockY = 0;

        r = new RoomFiller(gameReference);
        if (r.isGenerated) {
            Rect blockRect;
            for (int i = 0; i < r.level.getRooms().length; i++) {
                for (int j = 0; j < r.level.getRooms()[i].length; j++) {
                    blockY = i * blockHeigth * 8;
                    for (int k = 0; k < r.level.getRooms()[i][j].blocks.length; k++) {
                        blockX = j * blockWidth * 10;
                        for (int l = 0; l < r.level.getRooms()[i][j].blocks[k].length; l++) {
                            blockRect = new Rect(blockX, blockY, blockX + blockWidth, blockY + blockHeigth);

                            if (r.level.getRooms()[i][j].blocks[k][l] == null) {
                                r.level.getRooms()[i][j].blocks[k][l] = new Block(null, false, '0');
                            }

                            r.level.getRooms()[i][j].blocks[k][l].setBlockRect(blockRect);
                            blockX += blockWidth;
                        }
                        blockY += blockHeigth;
                    }
                }
            }
        }

        //Btn Back
        btnBack = new MenuButton((screenWidth / 24) * 23, 0, screenWidth, (screenHeight / 12));
        btnBack.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.backarrow));

        //Control Buttons
        btnLeft = new MenuButton((screenWidth / 24), (screenHeight / 12) * 9,
                (screenWidth / 24) * 3, (screenHeight / 12) * 11);
        btnLeft.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.leftarrow));
        btnRight = new MenuButton((screenWidth / 24) * 4, (screenHeight / 12) * 9,
                (screenWidth / 24) * 6, (screenHeight / 12) * 11);
        btnRight.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.rightarrow));
        btnJump = new MenuButton((screenWidth / 24) * 21, (screenHeight / 12) * 9,
                (screenWidth / 24) * 23, (screenHeight / 12) * 11);
        btnJump.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.jumparrow));

        Rect cameraHandler = new Rect((screenWidth / 12) * 4, (screenHeight / 6) * 2,
                (screenWidth / 12) * 8, (screenHeight / 6) * 4);
        player = new Player(6, blockHeigth / 8,
                (r.level.getStartRoom().getX() * blockWidth * 8) + ((screenWidth / 12) * 4) + ((cameraHandler.width() / 2) - (blockWidth / 2)),
                ((screenHeight / 6) * 2) + (cameraHandler.height() - blockHeigth),
                BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.player_run_1),
                new Rect(cameraHandler.centerX() - (blockWidth / 2),
                        cameraHandler.bottom - blockHeigth,
                        cameraHandler.centerX() + (blockWidth / 2),
                        cameraHandler.bottom),
                cameraHandler, this);

        jumping = false;
        falling = true;
    }

    public int onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();        //Obtain action index.
        int pointerID = event.getPointerId(pointerIndex); //Obtain id of the pointer asociated to the action.
        int action = event.getActionMasked();             //Obtain type of pulsation.
        switch (action) {
            case MotionEvent.ACTION_DOWN:           //First finger touches.
            case MotionEvent.ACTION_POINTER_DOWN:   //Second and next touches.
                if (isTouched(btnLeft.getButton(), event)) {
                    movingLeft = true;
                    movingRight = false;
                }
                if (isTouched(btnRight.getButton(), event)) {
                    movingRight = true;
                    movingLeft = false;
                }
                if (isTouched(btnJump.getButton(), event) && !jumping && !falling) {
                    jumping = true;
                    falling = false;
                }
                break;
            case MotionEvent.ACTION_UP:             //Last finger up.
            case MotionEvent.ACTION_POINTER_UP:     //Any finger that isnt the last up.
                if (isTouched(btnBack.getButton(), event) && sceneId != 0) return 0;
                if (isTouched(btnLeft.getButton(), event)) {
                    movingLeft = false;
                }
                if (isTouched(btnRight.getButton(), event)) {
                    movingRight = false;
                }
                Log.i("Player Y -> ", String.valueOf(player.getY()));
                break;
            case MotionEvent.ACTION_MOVE: //Any finger is moved.
                break;
            default:
                Log.i("Any other action", "Action is not defined: " + action);
        }

        return sceneId;
    }

    //We refresh game physics on screen.
    public void refreshPhysics() {
        if (movingLeft) {
            player.moveLeft();
        }
        if (movingRight) {
            player.moveRight();
        }
        if (jumping) {
            player.jump();
        }

        player.aplicarGravedad();
    }

    //Drawing routine, called from the game thread.
    public void draw(Canvas c) {
        try {
            c.drawColor(Color.BLUE);

            c.save();
            c.translate((-r.level.getStartRoom().getX() * blockWidth * 8) + player.offSetX, (-r.level.getStartRoom().getY() * blockHeigth * 8) + player.offSetY);
            if (r.isGenerated) {
                Rect blockRect;
                for (int i = 0; i < r.level.getRooms().length; i++) {
                    for (int j = 0; j < r.level.getRooms()[i].length; j++) {
                        blockY = i * blockHeigth * 8;
                        for (int k = 0; k < r.level.getRooms()[i][j].blocks.length; k++) {
                            blockX = j * blockWidth * 10;
                            for (int l = 0; l < r.level.getRooms()[i][j].blocks[k].length; l++) {
                                blockRect = new Rect(blockX, blockY, blockX + blockWidth, blockY + blockHeigth);

                                if (r.level.getRooms()[i][j].blocks[k][l] == null) {
                                    r.level.getRooms()[i][j].blocks[k][l] = new Block(null, false, '0');
                                } else {
                                    if (r.level.getRooms()[i][j].blocks[k][l].isCollisionable()) {
                                        c.drawBitmap(r.level.getRooms()[i][j].blocks[k][l].getTile(), null, blockRect, null);
                                    }
                                }

                                r.level.getRooms()[i][j].blocks[k][l].setBlockRect(blockRect);
                                blockX += blockWidth;
                            }
                            blockY += blockHeigth;
                        }
                    }
                }
            }
            c.restore();

            btnLeft.draw(c);
            btnRight.draw(c);
            btnJump.draw(c);
            btnBack.draw(c);
            player.draw(c);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
