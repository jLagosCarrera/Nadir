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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The tutorial scene.
 */
public class Tutorial extends Scene {
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
     * Tutorial info
     */
    MenuButton tuto;
    /**
     * Current tutorial progress
     */
    int currentTutoProgress;
    /**
     * Maximum tutorial scenes
     */
    int maxTutoProgress;
    /**
     * Paint for text in the tutorial
     */
    Paint pText;
    /**
     * Images for tutorial
     */
    ArrayList<Bitmap> tutoImages = new ArrayList<>();

    /**
     * Instantiates a new tutorial scene.
     *
     * @param gameReference the game engine reference
     * @param sceneId       the asociated scene id
     * @param screenWidth   the screen width
     * @param screenHeight  the screen height
     */
    public Tutorial(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
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
        tuto = new MenuButton(widthDiv * 4, 0, widthDiv * 20, screenHeight);
        tuto.getpButton().setColor(Color.rgb(173, 179, 188));
        tuto.getpButton().setAlpha(220);

        //Text paint
        pText = new Paint();
        pText.setTextSize((int) (tuto.getButton().height() / 10));
        pText.setTypeface(Typeface.create(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"), Typeface.BOLD));

        //Tutorial images
        Bitmap aux;
        aux = BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.tuto1);
        aux = Bitmap.createScaledBitmap(aux,
                tuto.getButton().width(), aux.getHeight()-(tuto.getButton().height()-aux.getHeight())/2, true);
        tutoImages.add(aux);
        aux = BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.tuto2);
        aux = Bitmap.createScaledBitmap(aux,
                tuto.getButton().width(), aux.getHeight()-(tuto.getButton().height()-aux.getHeight())/2, true);
        tutoImages.add(aux);


        currentTutoProgress = 0;
        maxTutoProgress = 4;
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
                else if (isTouched(prev.getButton(), event) && currentTutoProgress > 0)
                    currentTutoProgress--;
                else if (isTouched(next.getButton(), event) && currentTutoProgress < maxTutoProgress)
                    currentTutoProgress++;
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
            tuto.draw(c);
            prev.draw(c);
            next.draw(c);

            int offset;
            switch (currentTutoProgress) {
                case 0:
                    offset = 0;
                    for (String s : splitString(gameReference.getContext().getString(R.string.gameInfo))) {
                        c.drawText(s,
                                tuto.getLeft(), tuto.getTop() + pText.getTextSize() + offset, pText);
                        offset += pText.getTextSize() + 5;
                    }
                    break;
                case 1:
                    offset = 0;
                    for (String s : splitString(gameReference.getContext().getString(R.string.controls))) {
                        c.drawText(s,
                                tuto.getLeft(), tuto.getTop() + pText.getTextSize() + offset, pText);
                        offset += pText.getTextSize() + 5;
                    }
                    break;
                case 2:
                    c.drawBitmap(tutoImages.get(0), tuto.getLeft(), (tuto.getButton().height()-tutoImages.get(1).getHeight())/2, null);
                    break;
                case 3:
                    offset = 0;
                    for (String s : splitString(gameReference.getContext().getString(R.string.shakeEnd))) {
                        c.drawText(s,
                                tuto.getLeft(), tuto.getTop() + pText.getTextSize() + offset, pText);
                        offset += pText.getTextSize() + 5;
                    }
                    break;
                case 4:
                    c.drawBitmap(tutoImages.get(1), tuto.getLeft(), (tuto.getButton().height()-tutoImages.get(1).getHeight())/2, null);
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
        for (int i = 0; i < length; i += 20) {
            parts.add(splittedString.substring(i, Math.min(length, i + 20)) + "...");
        }
        return parts;
    }
}
