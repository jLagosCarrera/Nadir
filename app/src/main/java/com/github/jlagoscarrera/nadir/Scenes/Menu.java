package com.github.jlagoscarrera.nadir.Scenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.github.jlagoscarrera.nadir.Components.MenuButton;
import com.github.jlagoscarrera.nadir.Core.NadirEngine;
import com.github.jlagoscarrera.nadirGame.R;

public class Menu extends Scene {
    MenuButton btnPlay, btnChest, btnHiScores, btnOptions, btnExit, btnMusic, btnSound, btnVibrate;
    MenuButton title;
    Bitmap[] musicIcons;
    Bitmap[] soundIcons;
    Bitmap[] vibrateIcons;

    public Menu(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);

        //Title
        title = new MenuButton(widthDiv * 6, 0, widthDiv * 18, heighDiv * 3);
        title.getpButton().setColor(Color.TRANSPARENT);
        title.getpButtonBorder().setColor(Color.TRANSPARENT);
        title.getpText().setTextSize((int) (heighDiv * 3 * 0.75));
        title.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Seaside.ttf"));
        title.setText("NADIR");

        //Play button
        btnPlay = new MenuButton(widthDiv * 6, heighDiv * 3, widthDiv * 18, heighDiv * 5);
        btnPlay.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnPlay.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnPlay.setText("Play");

        //Chest, skins... button
        btnChest = new MenuButton(widthDiv, heighDiv * 6, widthDiv * 11, heighDiv * 8);
        btnChest.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnChest.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnChest.setText("Chest");

        //High Scores button
        btnHiScores = new MenuButton(widthDiv * 13, heighDiv * 6, widthDiv * 23, heighDiv * 8);
        btnHiScores.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnHiScores.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnHiScores.setText("High Scores");

        //Settings button
        btnOptions = new MenuButton(widthDiv, heighDiv * 9, widthDiv * 11, heighDiv * 11);
        btnOptions.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnOptions.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnOptions.setText("Options");

        //Exit button
        btnExit = new MenuButton(widthDiv * 13, heighDiv * 9, widthDiv * 23, heighDiv * 11);
        btnExit.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnExit.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnExit.setText("Exit");

        //Load icons
        musicIcons = new Bitmap[]{
                BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.musicon),
                BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.musicoff)};
        soundIcons = new Bitmap[]{
                BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.soundon),
                BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.soundoff)};
        vibrateIcons = new Bitmap[]{
                BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.vibrateon),
                BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.vibrateoff)};

        //No music button
        btnMusic = new MenuButton(widthDiv * 19, 0, widthDiv * 20, heighDiv);
        drawMusicIcon();

        //No sound button
        btnSound = new MenuButton(widthDiv * 21, 0, widthDiv * 22, heighDiv);
        drawSoundIcon();

        //No vibrate button
        btnVibrate = new MenuButton(widthDiv * 23, 0, screenWidth, heighDiv);
        drawVibrateIcon();
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
                //Touches en el menu
                if (isTouched(btnPlay.getButton(), event)) return 1;
                else if (isTouched(btnChest.getButton(), event)) return 96;
                else if (isTouched(btnHiScores.getButton(), event)) return 97;
                else if (isTouched(btnOptions.getButton(), event)) return 98;
                else if (isTouched(btnExit.getButton(), event)) return 99;


                if (isTouched(btnMusic.getButton(), event)) {
                    setMusic();
                    drawMusicIcon();
                    gameReference.options.saveOptions();
                }

                if (isTouched(btnSound.getButton(), event)) {
                    setSound();
                    drawSoundIcon();
                    gameReference.options.saveOptions();
                }

                if (isTouched(btnVibrate.getButton(), event)) {
                    setVibrate();
                    drawVibrateIcon();
                    gameReference.options.saveOptions();
                }
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
        refreshParallax();
    }

    //Drawing routine, called from the game thread.
    public void draw(Canvas c) {
        try {
            //Draw parallax
            drawParallax(c);

            //Draw all buttons
            title.draw(c);

            btnPlay.draw(c);
            btnChest.draw(c);
            btnHiScores.draw(c);
            btnOptions.draw(c);
            btnExit.draw(c);

            btnMusic.draw(c);
            btnSound.draw(c);
            btnVibrate.draw(c);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }

    public void drawMusicIcon() {
        if (gameReference.options.isMusicPlaying()) {
            btnMusic.setIcon(musicIcons[0]);
        } else {
            btnMusic.setIcon(musicIcons[1]);
        }
    }

    public void drawSoundIcon() {
        if (gameReference.options.isPlaySounds()) {
            btnSound.setIcon(soundIcons[0]);
        } else {
            btnSound.setIcon(soundIcons[1]);
        }
    }

    public void drawVibrateIcon() {
        if (gameReference.options.isVibrate()) {
            btnVibrate.setIcon(vibrateIcons[0]);
        } else {
            btnVibrate.setIcon(vibrateIcons[1]);
        }
    }
}
