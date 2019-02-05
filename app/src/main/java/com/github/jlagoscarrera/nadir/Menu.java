package com.github.jlagoscarrera.nadir;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class Menu extends Scene {
    public Menu(Context context, int sceneId, int screenWidth, int screenHeight) {
        super(context, sceneId, screenWidth, screenHeight);
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

                //TODO gestionar rectangulos y pantallas aqui
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
            //TODO draw button rectangles
//            c.drawBitmap(fondo, 0, 0, null);
//
//            c.drawRect(juego, pBoton);
//            c.drawText("Jugar", juego.centerX(), juego.centerY() + alto / 2, pTexto);
//
//            c.drawRect(opciones, pBotonSec);
//            c.drawRect(ayuda, pBotonSec);
//            c.drawRect(records, pBotonSec);
//
//
//            c.drawText("Menú", anchoPantalla / 2, altoPantalla / 5, pTexto);
//            c.drawText("Menú", anchoPantalla / 2 + 5, altoPantalla / 5 + 10, pTextoSec);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }
}
