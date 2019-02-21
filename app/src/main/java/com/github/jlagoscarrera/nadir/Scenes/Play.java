package com.github.jlagoscarrera.nadir.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.github.jlagoscarrera.nadir.Core.NadirEngine;
import com.github.jlagoscarrera.nadir.Scripts.RoomFiller;

public class Play extends Scene {
    RoomFiller r;

    public Play(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);

        r = new RoomFiller();

    }

    //We refresh game physics on screen.
    public void refreshPhysics() {

    }

    //Drawing routine, called from the game thread.
    public void draw(Canvas c) {
        try {
            r.processGraphics(c);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }
}
