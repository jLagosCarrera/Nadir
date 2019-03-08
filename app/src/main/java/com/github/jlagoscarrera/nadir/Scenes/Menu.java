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

/**
 * The menu scene.
 */
public class Menu extends Scene {
    /**
     * The play button.
     */
    MenuButton btnPlay,
    /**
     * The tutorial button.
     */
    btnTutorial,
    /**
     * The testers button.
     */
    btnTesters,
    /**
     * The options button.
     */
    btnOptions,
    /**
     * The exit button.
     */
    btnExit,
    /**
     * The toggle music button.
     */
    btnMusic,
    /**
     * The toggle sound button.
     */
    btnSound,
    /**
     * The toggle vibrate button.
     */
    btnVibrate;
    /**
     * The Title of the scene.
     */
    MenuButton title;
    /**
     * The music button icons.
     */
    Bitmap[] musicIcons;
    /**
     * The sound button icons.
     */
    Bitmap[] soundIcons;
    /**
     * The vibrate button icons.
     */
    Bitmap[] vibrateIcons;

    /**
     * Instantiates a new menu scene.
     *
     * @param gameReference the game engine reference
     * @param sceneId       the current scene id
     * @param screenWidth   the screen width
     * @param screenHeight  the screen height
     */
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

        //Tutorial, skins... button
        btnTutorial = new MenuButton(widthDiv, heighDiv * 6, widthDiv * 11, heighDiv * 8);
        btnTutorial.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnTutorial.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnTutorial.setText("Tutorial");

        //High Scores button
        btnTesters = new MenuButton(widthDiv * 13, heighDiv * 6, widthDiv * 23, heighDiv * 8);
        btnTesters.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        btnTesters.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        btnTesters.setText("Testers");

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

    /**
     * Handles touches on the screen, scene changes.
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
                //Touches en el menu
                if (isTouched(btnPlay.getButton(), event)) return 1;
                else if (isTouched(btnTutorial.getButton(), event)) return 96;
                else if (isTouched(btnTesters.getButton(), event)) return 97;
                else if (isTouched(btnOptions.getButton(), event)) return 98;
                else if (isTouched(btnExit.getButton(), event)) return 99;


                if (isTouched(btnMusic.getButton(), event)) {
                    setMusic();
                    drawMusicIcon();
                }

                if (isTouched(btnSound.getButton(), event)) {
                    setSound();
                    drawSoundIcon();
                }

                if (isTouched(btnVibrate.getButton(), event)) {
                    setVibrate();
                    drawVibrateIcon();
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

            //Draw all buttons
            title.draw(c);

            btnPlay.draw(c);
            btnTutorial.draw(c);
            btnTesters.draw(c);
            btnOptions.draw(c);
            btnExit.draw(c);

            btnMusic.draw(c);
            btnSound.draw(c);
            btnVibrate.draw(c);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }

    /**
     * Draw music icon.
     */
    public void drawMusicIcon() {
        if (gameReference.options.isMusicPlaying()) {
            btnMusic.setIcon(musicIcons[0]);
        } else {
            btnMusic.setIcon(musicIcons[1]);
        }
    }

    /**
     * Draw sound icon.
     */
    public void drawSoundIcon() {
        if (gameReference.options.isPlaySounds()) {
            btnSound.setIcon(soundIcons[0]);
        } else {
            btnSound.setIcon(soundIcons[1]);
        }
    }

    /**
     * Draw vibrate icon.
     */
    public void drawVibrateIcon() {
        if (gameReference.options.isVibrate()) {
            btnVibrate.setIcon(vibrateIcons[0]);
        } else {
            btnVibrate.setIcon(vibrateIcons[1]);
        }
    }
}
