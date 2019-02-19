package com.github.jlagoscarrera.nadir;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

public class PlayScene extends Scene {
    RoomFiller r;

    public PlayScene(Context context, int sceneId, int screenWidth, int screenHeight) {
        super(context, sceneId, screenWidth, screenHeight);

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
