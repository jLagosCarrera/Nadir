package com.github.jlagoscarrera.nadir.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.github.jlagoscarrera.nadir.Components.MenuButton;
import com.github.jlagoscarrera.nadir.Components.MovingBackground;
import com.github.jlagoscarrera.nadirGame.R;

public class Scene {
    static private int[] backgrounds = {R.mipmap.back, R.mipmap.mid, R.mipmap.front};
    static MovingBackground[] parallax;
    static boolean isParallaxCreated = false;
    Context context;
    public int sceneId;
    int screenWidth, screenHeight;
    MenuButton btnBack;

    public Scene(Context context, int sceneId, int screenWidth, int screenHeight) {
        this.context = context;
        this.sceneId = sceneId;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        //Parallax
        if (!isParallaxCreated) {
            isParallaxCreated = true;
            parallax = new MovingBackground[backgrounds.length];
            for (int i = 0; i < parallax.length; i++) {
                Bitmap aux = BitmapFactory.decodeResource(context.getResources(), backgrounds[i]);
                aux = Bitmap.createScaledBitmap(aux, screenWidth, screenHeight, true);
                parallax[i] = new MovingBackground(aux, screenWidth, screenHeight);
            }
        }

        btnBack = new MenuButton((screenWidth / 24) * 22, 0, screenWidth, (screenHeight / 12) * 2);
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
            if (sceneId != 0) btnBack.draw(c);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }

    public boolean isTouched(Rect button, MotionEvent event) {
        if (button.contains((int) event.getX(), (int) event.getY())) {
            return true;
        } else {
            return false;
        }
    }

    public void refreshParallax() {
        for (int i = 0; i < parallax.length; i++) {
            parallax[i].move((i + 1) * 2);
            if (parallax[i].position.x > screenWidth) {
                parallax[i].position.x = screenWidth - parallax[i].image.getWidth();
            }
        }
    }

    public void drawParallax(Canvas c) {
        for (int i = 0; i < parallax.length; i++) {
            c.drawBitmap(parallax[i].image, parallax[i].position.x, parallax[i].position.y, null);
            c.drawBitmap(parallax[i].image, parallax[i].position.x - parallax[i].image.getWidth(), parallax[i].position.y, null);
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
