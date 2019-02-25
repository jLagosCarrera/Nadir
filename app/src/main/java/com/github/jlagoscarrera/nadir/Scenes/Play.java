package com.github.jlagoscarrera.nadir.Scenes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.github.jlagoscarrera.nadir.Components.MenuButton;
import com.github.jlagoscarrera.nadir.Core.NadirEngine;
import com.github.jlagoscarrera.nadir.Scripts.RoomFiller;
import com.github.jlagoscarrera.nadirGame.R;

public class Play extends Scene {
    RoomFiller r;
    MenuButton btnBack;
    int blockHeigth, blockWidth;
    int blockX, blockY;

    public Play(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);

        r = new RoomFiller(gameReference);

        blockHeigth = screenHeight / 8;
        blockWidth = blockHeigth;

        blockX = 0;
        blockY = 0;

        //Btn Back
        btnBack = new MenuButton((screenWidth / 24) * 23, 0, screenWidth, (screenHeight / 12));
        btnBack.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.backarrow));
    }

    public int onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();        //Obtain action index.
        int pointerID = event.getPointerId(pointerIndex); //Obtain id of the pointer asociated to the action.
        int action = event.getActionMasked();             //Obtain type of pulsation.
        switch (action) {
            case MotionEvent.ACTION_DOWN:           //First finger touches.
            case MotionEvent.ACTION_POINTER_DOWN:   //Second and next touches.
                break;
            case MotionEvent.ACTION_UP:             //Last finger up.
            case MotionEvent.ACTION_POINTER_UP:     //Any finger that isnt the last up.
                if (isTouched(btnBack.getButton(), event) && sceneId != 0) return 0;
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

    }

    //Drawing routine, called from the game thread.
    public void draw(Canvas c) {
        try {
            c.drawColor(Color.RED);


            if (r.isGenerated) {
                Rect blockRect = null;
                for (int i = 0; i < r.level.getRooms().length; i++) {
                    for (int j = 0; j < r.level.getRooms()[i].length; j++) {
                        blockY = i * blockHeigth * 8;
                        for (int k = 0; k < r.level.getRooms()[i][j].getBlocks().length; k++) {
                            blockX = j * blockWidth * 10;
                            for (int l = 0; l < r.level.getRooms()[i][j].getBlocks()[k].length; l++) {
                                blockRect = new Rect(blockX, blockY, blockX + blockWidth, blockY + blockHeigth);
                                if (r.level.getRooms()[i][j].getBlocks()[k][l].isCollisionable()) {
                                    c.drawBitmap(r.level.getRooms()[i][j].getBlocks()[k][l].getTile(), null, blockRect, null);
                                }
                                blockX += blockWidth;
                            }
                            blockY += blockHeigth;
                        }
                    }
                }
            }

            btnBack.draw(c);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
            r.generateRooms();
        }
    }
}
