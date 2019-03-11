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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The Testers add scene.
 */
public class TestersAdd extends Scene {

    /**
     * First letter of the name
     */
    MenuButton firstChar;
    /**
     * Up and down controls for first letter
     */
    MenuButton firstUp, firstDown;
    /**
     * Seconds letter of the name
     */
    MenuButton secondChar;
    /**
     * Up and down controls for second letter
     */
    MenuButton secondUp, secondDown;
    /**
     * Third letter of the name
     */
    MenuButton thirdChar;
    /**
     * Up and down controls for third letter
     */
    MenuButton thirdUp, thirdDown;
    /**
     * Accept and submit button
     */
    MenuButton accept;

    /**
     * Instantiates a new Testers add scene.
     *
     * @param gameReference the game engine reference
     * @param sceneId       the asociated scene id
     * @param screenWidth   the screen width
     * @param screenHeight  the screen height
     */
    public TestersAdd(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);

        //First char
        firstUp = new MenuButton(widthDiv, heighDiv, widthDiv * 7, heighDiv * 2);
        firstUp.getpText().setTextSize((int) (heighDiv * 0.75));
        firstUp.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        firstUp.setText("\u25B2");
        firstChar = new MenuButton(widthDiv, heighDiv * 3, widthDiv * 7, heighDiv * 6);
        firstChar.getpButton().setColor(Color.GRAY);
        firstChar.getpText().setTextSize((int) (heighDiv * 3 * 0.75));
        firstChar.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        firstChar.setText(String.valueOf('A'));
        firstDown = new MenuButton(widthDiv, heighDiv * 7, widthDiv * 7, heighDiv * 8);
        firstDown.getpText().setTextSize((int) (heighDiv * 0.75));
        firstDown.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        firstDown.setText("\u25BC");

        //Second char
        secondUp = new MenuButton(widthDiv * 9, heighDiv, widthDiv * 15, heighDiv * 2);
        secondUp.getpText().setTextSize((int) (heighDiv * 0.75));
        secondUp.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        secondUp.setText("\u25B2");
        secondChar = new MenuButton(widthDiv * 9, heighDiv * 3, widthDiv * 15, heighDiv * 6);
        secondChar.getpButton().setColor(Color.GRAY);
        secondChar.getpText().setTextSize((int) (heighDiv * 3 * 0.75));
        secondChar.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        secondChar.setText(String.valueOf('A'));
        secondDown = new MenuButton(widthDiv * 9, heighDiv * 7, widthDiv * 15, heighDiv * 8);
        secondDown.getpText().setTextSize((int) (heighDiv * 0.75));
        secondDown.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        secondDown.setText("\u25BC");

        //Third char
        thirdUp = new MenuButton(widthDiv * 17, heighDiv, widthDiv * 23, heighDiv * 2);
        thirdUp.getpText().setTextSize((int) (heighDiv * 0.75));
        thirdUp.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        thirdUp.setText("\u25B2");
        thirdChar = new MenuButton(widthDiv * 17, heighDiv * 3, widthDiv * 23, heighDiv * 6);
        thirdChar.getpButton().setColor(Color.GRAY);
        thirdChar.getpText().setTextSize((int) (heighDiv * 3 * 0.75));
        thirdChar.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        thirdChar.setText(String.valueOf('A'));
        thirdDown = new MenuButton(widthDiv * 17, heighDiv * 7, widthDiv * 23, heighDiv * 8);
        thirdDown.getpText().setTextSize((int) (heighDiv * 0.75));
        thirdDown.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        thirdDown.setText("\u25BC");

        //Accept
        accept = new MenuButton(widthDiv, heighDiv * 9, widthDiv * 23, heighDiv * 11);
        accept.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        accept.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        accept.setText(gameReference.getContext().getString(R.string.accept));
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
                if (isTouched(firstUp.getButton(), event)) updateChar(true, firstChar);
                if (isTouched(firstDown.getButton(), event)) updateChar(false, firstChar);
                if (isTouched(secondUp.getButton(), event)) updateChar(true, secondChar);
                if (isTouched(secondDown.getButton(), event)) updateChar(false, secondChar);
                if (isTouched(thirdUp.getButton(), event)) updateChar(true, thirdChar);
                if (isTouched(thirdDown.getButton(), event)) updateChar(false, thirdChar);

                if (isTouched(accept.getButton(), event)) {
                    String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                    gameReference.testers.add(currentDate + " || " + firstChar.getText() + secondChar.getText() + thirdChar.getText() + " -> " + gameReference.endTime);
                    return 97;
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

            firstUp.draw(c);
            firstChar.draw(c);
            firstDown.draw(c);
            secondUp.draw(c);
            secondChar.draw(c);
            secondDown.draw(c);
            thirdUp.draw(c);
            thirdChar.draw(c);
            thirdDown.draw(c);

            accept.draw(c);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }

    /**
     * Updates character of a menubutton
     *
     * @param increase Indicates if it will increase or decrease value
     * @param button   Button where this will apply
     */
    private void updateChar(boolean increase, MenuButton button) {
        char currentChar = button.getText().charAt(0);

        if (increase && currentChar < 'Z') {
            currentChar++;
        } else if (!increase && currentChar > 'A') {
            currentChar--;
        }

        button.setText(String.valueOf(currentChar));
    }
}
