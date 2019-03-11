package com.github.jlagoscarrera.nadir.Scenes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.github.jlagoscarrera.nadir.Components.MenuButton;
import com.github.jlagoscarrera.nadir.Core.NadirEngine;
import com.github.jlagoscarrera.nadirGame.R;

/**
 * The information scene.
 */
public class Information extends Scene {
    /**
     * The Title of the scene.
     */
    MenuButton title;
    /**
     * The button for going back to menu.
     */
    MenuButton btnBack;
    /**
     * The button for going into tutorial
     */
    MenuButton btnTutorial;
    /**
     * The button for going into credits
     */
    MenuButton btnCredits;

    /**
     * Instantiates a new tutorial scene.
     *
     * @param gameReference the game engine reference
     * @param sceneId       the asociated scene id
     * @param screenWidth   the screen width
     * @param screenHeight  the screen height
     */
    public Information(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);

        //Title
        title = new MenuButton(widthDiv * 6, 0, widthDiv * 18, heighDiv * 3);
        title.getpButton().setColor(Color.TRANSPARENT);
        title.getpButtonBorder().setColor(Color.TRANSPARENT);
        title.getpText().setTextSize((int) (heighDiv * 3 * 0.75));
        title.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        title.setText(gameReference.getContext().getString(R.string.info).toUpperCase());

        //Information, skins... button
        btnTutorial = new MenuButton(widthDiv, heighDiv * 4, widthDiv * 11, heighDiv * 8);
        btnTutorial.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnTutorial.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnTutorial.setText(gameReference.getContext().getString(R.string.tutorial));

        //Testers button
        btnCredits = new MenuButton(widthDiv * 13, heighDiv * 4, widthDiv * 23, heighDiv * 8);
        btnCredits.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnCredits.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnCredits.setText(gameReference.getContext().getString(R.string.credits));

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
                else if (isTouched(btnTutorial.getButton(), event)) return 94;
                else if (isTouched(btnCredits.getButton(), event)) return 95;
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

            title.draw(c);
            btnTutorial.draw(c);
            btnCredits.draw(c);
            btnBack.draw(c);

        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }
}
