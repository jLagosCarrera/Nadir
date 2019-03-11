package com.github.jlagoscarrera.nadir.Scenes;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.github.jlagoscarrera.nadir.Components.MenuButton;
import com.github.jlagoscarrera.nadir.Core.NadirEngine;
import com.github.jlagoscarrera.nadirGame.R;

/**
 * The Testers scene.
 */
public class Testers extends Scene {
    /**
     * The button for going back to menu.
     */
    MenuButton btnBack;
    /**
     * The detector for the fling action.
     */
    GestureDetectorCompat detectorScroll;
    /**
     * Paint for the records
     */
    Paint text;
    /**
     * Offset for the translate.
     */
    float offsetY;
    /**
     * Max offset for the translate.
     */
    float maxOffset;

    /**
     * Instantiates a new Testers scene.
     *
     * @param gameReference the game engine reference
     * @param sceneId       the asociated scene id
     * @param screenWidth   the screen width
     * @param screenHeight  the screen height
     */
    public Testers(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);
        offsetY = 0;

        text = new Paint();
        text.setTextSize((int) (heighDiv * 1.5 * 0.75));
        text.setTypeface(Typeface.create(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"), Typeface.BOLD));

        detectorScroll = new GestureDetectorCompat(gameReference.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (offsetY + distanceY < 0) {
                    offsetY = 0;
                } else if (offsetY + distanceY >= 0 && offsetY + distanceY <= maxOffset) {
                    offsetY += distanceY;
                } else {
                    offsetY = maxOffset;
                }
                return true;
            }
        });

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
        detectorScroll.onTouchEvent(event);

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

            c.save();
            c.translate(0, -offsetY);
            int startY = 0;
            for (int i = gameReference.testers.size() - 1; i >= 0; i--) {
                System.out.println(gameReference.endTime);
                c.drawText(gameReference.testers.get(i), 5, startY + text.getTextSize(), text);
                startY += text.getTextSize() + 5;
            }
            maxOffset = startY;
            if (maxOffset > screenHeight) {
                maxOffset = maxOffset - screenHeight;
            } else {
                maxOffset = 0;
            }
            c.restore();

            btnBack.draw(c);

        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }
}
