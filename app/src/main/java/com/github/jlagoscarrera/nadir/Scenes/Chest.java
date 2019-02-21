package com.github.jlagoscarrera.nadir.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.github.jlagoscarrera.nadir.Core.NadirEngine;

public class Chest extends Scene {
    public Chest(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);
    }

    //We refresh game physics on screen.
    public void refreshPhysics() {
        refreshParallax();
    }

    //Drawing routine, called from the game thread.
    public void draw(Canvas c) {
        try {
            //Draw parallax
            drawParallax(c);

        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }
}
