package com.github.jlagoscarrera.nadir.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class Options extends Scene {
    public Options(Context context, int sceneId, int screenWidth, int screenHeight) {
        super(context, sceneId, screenWidth, screenHeight);
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
