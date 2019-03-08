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
import com.github.jlagoscarrera.nadir.Core.NadirEngine;
import com.github.jlagoscarrera.nadirGame.R;

/**
 * The basic scene.
 */
public class Scene {
    /**
     * Images used on parallax.
     */
    static private int[] backgrounds = {R.mipmap.back, R.mipmap.mid, R.mipmap.front};
    /**
     * The Parallax.
     */
    static MovingBackground[] parallax;
    /**
     * Parallax created or not.
     */
    static boolean isParallaxCreated = false;
    /**
     * The Game engine reference.
     */
    NadirEngine gameReference;
    /**
     * The current Scene id.
     */
    public int sceneId;
    /**
     * The Screen width.
     */
    int screenWidth;
    /**
     * The Screen height.
     */
    int screenHeight;
    /**
     * The Width divisor for buttons.
     */
    int widthDiv;
    /**
     * The Heigh divisor for buttons.
     */
    int heighDiv;

    /**
     * Instantiates a new basic scene.
     *
     * @param gameReference the game engine reference
     * @param sceneId       the current scene id
     * @param screenWidth   the screen width
     * @param screenHeight  the screen height
     */
    public Scene(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        this.gameReference = gameReference;
        this.sceneId = sceneId;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        widthDiv = screenWidth / 24;
        heighDiv = screenHeight / 12;

        //Parallax
        if (!isParallaxCreated) {
            isParallaxCreated = true;
            parallax = new MovingBackground[backgrounds.length];
            for (int i = 0; i < parallax.length; i++) {
                Bitmap aux = BitmapFactory.decodeResource(gameReference.getResources(), backgrounds[i]);
                aux = Bitmap.createScaledBitmap(aux, screenWidth, screenHeight, true);
                parallax[i] = new MovingBackground(aux, screenWidth, screenHeight);
            }
        }
    }

    /**
     * Touch event handling.
     *
     * @param event the touch event
     * @return the new Scene ID
     */
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
                break;
            case MotionEvent.ACTION_MOVE: //Any finger is moved.
                break;
            default:
                Log.i("Any other action", "Action is not defined: " + action);
        }

        return sceneId;
    }

    /**
     * Refresh physics.
     */
//We refresh game physics on screen.
    public void refreshPhysics() {

    }

    /**
     * Draw on the canvas.
     *
     * @param c the canvas to draw at
     */
//Drawing routine, called from the game thread.
    public void draw(Canvas c) {
        try {

        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }

    /**
     * Checks if a button has been touched.
     *
     * @param button the button to check
     * @param event  the touch event
     * @return if is touched or not
     */
    public boolean isTouched(Rect button, MotionEvent event) {
        if (button.contains((int) event.getX(), (int) event.getY())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Refresh parallax.
     */
    public void refreshParallax() {
        for (int i = 0; i < parallax.length; i++) {
            parallax[i].move((i + 1) * 2);
            if (parallax[i].position.x > screenWidth) {
                parallax[i].position.x = screenWidth - parallax[i].image.getWidth();
            }
        }
    }

    /**
     * Draw parallax on the canvas.
     *
     * @param c the canvas to draw at
     */
    public void drawParallax(Canvas c) {
        for (int i = 0; i < parallax.length; i++) {
            c.drawBitmap(parallax[i].image, parallax[i].position.x, parallax[i].position.y, null);
            c.drawBitmap(parallax[i].image, parallax[i].position.x - parallax[i].image.getWidth(), parallax[i].position.y, null);
        }
    }

    /**
     * Sets music to activated or not.
     */
    public void setMusic() {
        gameReference.options.setMusicPlaying(!gameReference.options.isMusicPlaying());
        gameReference.updateAudioObjects();
        gameReference.updateMusicPlayer();
    }

    /**
     * Sets sound to activated or not.
     */
    public void setSound() {
        gameReference.options.setPlaySounds(!gameReference.options.isPlaySounds());
    }

    /**
     * Sets vibrate to activated or not.
     */
    public void setVibrate() {
        gameReference.options.setVibrate(!gameReference.options.isVibrate());
    }

    /**
     * Gets game engine reference.
     *
     * @return the game engine reference
     */
    public NadirEngine getGameReference() {
        return gameReference;
    }

    /**
     * Sets game engine reference.
     *
     * @param gameReference the game engine reference
     */
    public void setGameReference(NadirEngine gameReference) {
        this.gameReference = gameReference;
    }

    /**
     * Gets current scene id.
     *
     * @return the current scene id
     */
    public int getSceneId() {
        return sceneId;
    }

    /**
     * Sets current scene id.
     *
     * @param sceneId the current scene id
     */
    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    /**
     * Gets screen width.
     *
     * @return the screen width
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Sets screen width.
     *
     * @param screenWidth the screen width
     */
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    /**
     * Gets screen height.
     *
     * @return the screen height
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Sets screen height.
     *
     * @param screenHeight the screen height
     */
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
