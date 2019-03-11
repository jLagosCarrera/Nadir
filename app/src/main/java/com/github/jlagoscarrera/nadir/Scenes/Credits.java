package com.github.jlagoscarrera.nadir.Scenes;

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

import java.util.ArrayList;

/**
 * The credits scene.
 */
public class Credits extends Scene {
    /**
     * The button for going back to Information.
     */
    MenuButton btnBack;
    /**
     * Previous step on tutorial
     */
    MenuButton prev;
    /**
     * Next step on tutorial
     */
    MenuButton next;
    /**
     * Credits info
     */
    MenuButton credits;
    /**
     * Current credits progress
     */
    int currentCreditsProgress;
    /**
     * Maximum credit scenes
     */
    int maxCreditScenes;
    /**
     * Paint for text in the credits
     */
    Paint pText;

    /**
     * Instantiates a new credits scene.
     *
     * @param gameReference the game engine reference
     * @param sceneId       the asociated scene id
     * @param screenWidth   the screen width
     * @param screenHeight  the screen height
     */
    public Credits(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);

        //Btn Back
        btnBack = new MenuButton((screenWidth / 24) * 23, 0, screenWidth, (screenHeight / 12));
        btnBack.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.backarrow));

        //Btn prev
        prev = new MenuButton(0, heighDiv * 4, widthDiv * 4, heighDiv * 8);
        prev.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.leftarrow));

        //Btn Next
        next = new MenuButton(widthDiv * 20, heighDiv * 4, screenWidth, heighDiv * 8);
        next.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.rightarrow));

        //Tutorial drawing surface
        credits = new MenuButton(widthDiv * 4, 0, widthDiv * 20, screenHeight);
        credits.getpButton().setColor(Color.rgb(173, 179, 188));
        credits.getpButton().setAlpha(220);

        //Text paint
        pText = new Paint();
        pText.setTextSize((int) (credits.getButton().height() / 15));
        pText.setTypeface(Typeface.create(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"), Typeface.BOLD));

        currentCreditsProgress = 0;
        maxCreditScenes = 3;
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
                if (isTouched(btnBack.getButton(), event) && sceneId != 0) return 96;
                else if (isTouched(prev.getButton(), event) && currentCreditsProgress > 0)
                    currentCreditsProgress--;
                else if (isTouched(next.getButton(), event) && currentCreditsProgress < maxCreditScenes)
                    currentCreditsProgress++;
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
            credits.draw(c);
            prev.draw(c);
            next.draw(c);

            int offset;
            switch (currentCreditsProgress) {
                case 0:
                    offset = 0;
                    for (String s : splitString(gameReference.getContext().getString(R.string.sprites))) {
                        c.drawText(s,
                                credits.getLeft(), credits.getTop() + pText.getTextSize() + offset, pText);
                        offset += pText.getTextSize() + 5;
                    }
                    break;
                case 1:
                    offset = 0;
                    for (String s : splitString(gameReference.getContext().getString(R.string.audio))) {
                        c.drawText(s,
                                credits.getLeft(), credits.getTop() + pText.getTextSize() + offset, pText);
                        offset += pText.getTextSize() + 5;
                    }
                    break;
                case 2:
                    offset = 0;
                    for (String s : splitString(gameReference.getContext().getString(R.string.spThanks))) {
                        c.drawText(s,
                                credits.getLeft(), credits.getTop() + pText.getTextSize() + offset, pText);
                        offset += pText.getTextSize() + 5;
                    }
                    break;
                case 3:
                    offset = 0;
                    for (String s : splitString(gameReference.getContext().getString(R.string.thanks))) {
                        c.drawText(s,
                                credits.getLeft(), credits.getTop() + pText.getTextSize() + offset, pText);
                        offset += pText.getTextSize() + 5;
                    }
                    break;
            }

            btnBack.draw(c);

        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }

    /**
     * Splits a string
     *
     * @param splittedString String being splitted.
     * @return arraylist with all splitted strings
     */
    private ArrayList<String> splitString(String splittedString) {
        ArrayList<String> parts = new ArrayList<>();
        int length = splittedString.length();
        for (int i = 0; i < length; i += 30) {
            parts.add(splittedString.substring(i, Math.min(length, i + 30)) + "...");
        }
        return parts;
    }
}
