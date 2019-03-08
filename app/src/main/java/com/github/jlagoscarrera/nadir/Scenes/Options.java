package com.github.jlagoscarrera.nadir.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.github.jlagoscarrera.nadir.Components.MenuButton;
import com.github.jlagoscarrera.nadir.Core.NadirEngine;
import com.github.jlagoscarrera.nadirGame.R;

/**
 * The options scene.
 */
public class Options extends Scene {
    /**
     * The music toggle button.
     */
    MenuButton btnMusic, /**
     * The sound toggle button.
     */
    btnSound, /**
     * The vibrate toggle button.
     */
    btnVibrate;
    /**
     * The Title of the scene.
     */
    MenuButton title;

    /**
     * The back to menu button.
     */
    MenuButton btnBack;

    /**
     * Instantiates a new options scene.
     *
     * @param gameReference the game engine reference
     * @param sceneId       the current scene id
     * @param screenWidth   the screen width
     * @param screenHeight  the screen height
     */
    public Options(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);

        //Title
        title = new MenuButton(widthDiv * 6, 0, widthDiv * 18, heighDiv * 3);
        title.getpButton().setColor(Color.TRANSPARENT);
        title.getpButtonBorder().setColor(Color.TRANSPARENT);
        title.getpText().setTextSize((int) (heighDiv * 3 * 0.75));
        title.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        title.setText("OPTIONS");

        //Music button
        btnMusic = new MenuButton(widthDiv * 6, heighDiv * 3, widthDiv * 18, heighDiv * 5);
        btnMusic.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnMusic.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnMusic.setText("Music");

        //Sound button
        btnSound = new MenuButton(widthDiv * 6, heighDiv * 6, widthDiv * 18, heighDiv * 8);
        btnSound.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnSound.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnSound.setText("Sound");

        //Vibration button
        btnVibrate = new MenuButton(widthDiv * 6, heighDiv * 9, widthDiv * 18, heighDiv * 11);
        btnVibrate.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnVibrate.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnVibrate.setText("Vibration");

        //Btn Back
        btnBack = new MenuButton((screenWidth / 24) * 23, 0, screenWidth, (screenHeight / 12));
        btnBack.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.backarrow));
    }

    /**
     * Handles touches on the screen.
     *
     * @param event asociated event to the touch
     * @return the new scene ID
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
                if (isTouched(btnBack.getButton(), event) && sceneId != 0) return 0;

                if (isTouched(btnMusic.getButton(), event)) {
                    setMusic();
                }

                if (isTouched(btnSound.getButton(), event)) {
                    setSound();
                }

                if (isTouched(btnVibrate.getButton(), event)) {
                    setVibrate();
                }
                break;

            case MotionEvent.ACTION_MOVE: //Any finger is moved.
                break;
            default:
                Log.i("Any other action", "Action is not defined: " + action);
        }

        return sceneId;
    }

    /**
     * Refresh parallax on screen.
     */
    //We refresh game physics on screen.
    public void refreshPhysics() {
        refreshParallax();
    }

    /**
     * Draw on canvas.
     *
     * @param c the canvas to draw at
     */
    //Drawing routine, called from the game thread.
    public void draw(Canvas c) {
        try {
            //Draw parallax
            drawParallax(c);

            btnBack.draw(c);

            title.draw(c);

            if (gameReference.options.isMusicPlaying()) {
                btnMusic.getpButton().setColor(Color.GREEN);
            } else {
                btnMusic.getpButton().setColor(Color.RED);
            }
            btnMusic.draw(c);

            if (gameReference.options.isPlaySounds()) {
                btnSound.getpButton().setColor(Color.GREEN);
            } else {
                btnSound.getpButton().setColor(Color.RED);
            }
            btnSound.draw(c);

            if (gameReference.options.isVibrate()) {
                btnVibrate.getpButton().setColor(Color.GREEN);
            } else {
                btnVibrate.getpButton().setColor(Color.RED);
            }
            btnVibrate.draw(c);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }
}
