package com.github.jlagoscarrera.nadir;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

public class Menu extends Scene {
    MenuButton btnPlay, btnChest, btnHiScores, btnOptions, btnExit, btnNoMusic, btnNoSound, btnNoVibrate;
    MenuButton title;
    int widthDiv, heighDiv;

    public Menu(Context context, int sceneId, int screenWidth, int screenHeight) {
        super(context, sceneId, screenWidth, screenHeight);

        widthDiv = screenWidth / 24;
        heighDiv = screenHeight / 12;

        //TODO Internationalization
        //Title
        title = new MenuButton(widthDiv * 6, 0, widthDiv * 18, heighDiv * 3);
        title.getpButton().setColor(Color.TRANSPARENT);
        title.getpText().setTextSize((int) (heighDiv * 3 * 0.75));
        title.getpText().setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Seaside.ttf"));
        title.setText("NADIR");

        //Play button
        btnPlay = new MenuButton(widthDiv * 6, heighDiv * 3, widthDiv * 18, heighDiv * 5);
        btnPlay.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnPlay.getpText().setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Poiretone.ttf"));
        btnPlay.setText("Play");

        //Chest, skins... button
        btnChest = new MenuButton(widthDiv, heighDiv * 6, widthDiv * 11, heighDiv * 8);
        btnChest.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnChest.getpText().setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Poiretone.ttf"));
        btnChest.setText("Chest");

        //High Scores button
        btnHiScores = new MenuButton(widthDiv * 13, heighDiv * 6, widthDiv * 23, heighDiv * 8);
        btnHiScores.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnHiScores.getpText().setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Poiretone.ttf"));
        btnHiScores.setText("High Scores");

        //Options button
        btnOptions = new MenuButton(widthDiv, heighDiv * 9, widthDiv * 11, heighDiv * 11);
        btnOptions.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnOptions.getpText().setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Poiretone.ttf"));
        btnOptions.setText("Options");

        //Exit button
        btnExit = new MenuButton(widthDiv * 13, heighDiv * 9, widthDiv * 23, heighDiv * 11);
        btnExit.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnExit.getpText().setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Poiretone.ttf"));
        btnExit.setText("Exit");

        //No music button
        btnNoMusic = new MenuButton(widthDiv * 23, 0, screenWidth, heighDiv);

        //No sound button
        btnNoSound = new MenuButton(widthDiv * 21, 0, widthDiv * 22, heighDiv);

        //No vibrate button
        btnNoVibrate = new MenuButton(widthDiv * 19, 0, widthDiv * 20, heighDiv);
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
                //TODO touches en el menu
//                if (isTouched(juego, event)) return 1;
//                else if (isTouched(opciones, event)) return 99;
//                else if (isTouched(ayuda, event)) return 98;
//                else if (isTouched(records, event)) return 97;
//                break;

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
            //TODO parallax
//            c.drawBitmap(fondo, 0, 0, null);

            //Draw all buttons
            title.draw(c);

            btnPlay.draw(c);
            btnChest.draw(c);
            btnHiScores.draw(c);
            btnOptions.draw(c);
            btnExit.draw(c);

            btnNoMusic.draw(c);
            btnNoSound.draw(c);
            btnNoVibrate.draw(c);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }
}
