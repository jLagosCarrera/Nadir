package com.github.jlagoscarrera.nadir.Core;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.github.jlagoscarrera.nadir.Scenes.Menu;
import com.github.jlagoscarrera.nadir.Scenes.Options;
import com.github.jlagoscarrera.nadir.Scenes.Play;
import com.github.jlagoscarrera.nadir.Scenes.Scene;

public class NadirEngine extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;    //Abstract interface for handling drawing surface.
    private Context context;                //Application context.

    private int screenWidth = 1;            //Screen Width, its value its refreshed onSurfaceChanged.
    private int screenHeight = 1;           //Screen Height, its value its refreshed onSurfaceChanged.
    private GameThread gameThread;          //Thread that handles drawing and physics.
    private boolean running = false;        //Thread control.
    private Scene actualScene;

    public NadirEngine(Context context) {
        super(context);
        this.surfaceHolder = getHolder();       //We obtain the holder.
        this.surfaceHolder.addCallback(this);   //We indicate where callback methods are.
        this.context = context;                 //We obtain context.
        gameThread = new GameThread();          //Initialize the thread.
        setFocusable(true);                     //We assure that it receives isTouched events.
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (surfaceHolder) {
            int newScene = actualScene.onTouchEvent(event);
            if (newScene != actualScene.sceneId) {
                switch (newScene) {
                    case 0:
                        actualScene = new Menu(context, 0, screenWidth, screenHeight);
                        break;
                    case 1:
                        actualScene = new Play(context, 1, screenWidth, screenHeight);
                        break;
                    case 96:
                        break;
                    case 97:
                        break;
                    case 98:
                        actualScene = new Options(context, 98, screenWidth, screenHeight);
                        break;
                    case 99:
                        break;
                }
            }
        }

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;    //New screen width is set.
        screenHeight = height;  //New screen height is set.
        actualScene = new Menu(context, 0, screenWidth, screenHeight);
        gameThread.setWorking(true);                    //We start the game.
        if (gameThread.getState() == Thread.State.NEW)  //Creates the thread if it isnt created.
            gameThread.start();
        if (gameThread.getState() == Thread.State.TERMINATED) { //If the thread has finished, creates it again.
            gameThread = new GameThread();
            gameThread.start();
        }
        gameThread.setSurfaceSize(width, height);   //We stablish new screen size on the gameThread.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setWorking(false);   //We stop game thread.
        try {
            gameThread.join();          //Wait till it finishes.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //GameThread class in which we implement drawing and physics methods
    //for running paralell with the user interface.
    class GameThread extends Thread {
        public GameThread() {

        }

        @Override
        public void run() {
            long sleepTime = 0;
            final int FPS = 60;
            final int TPS = 1000000000;
            final int TEMPORAL_FRAGMENT = TPS / FPS;
            long referenceTime = System.nanoTime();

            while (running) {
                Canvas c = null;    //Required repaint all the canvas.
                try {
                    if (!surfaceHolder.getSurface().isValid())
                        continue;                           //If the canvas isnt ready we repeat repaint
                    c = surfaceHolder.lockCanvas();         //We obtain the canvas. Sync is necessary because its a common resource.
                    synchronized (surfaceHolder) {
                        actualScene.refreshPhysics();
                        actualScene.draw(c);
                    }
                } finally {  //Even if there is exception or not we have to liberate the canvas.
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }

                referenceTime += TEMPORAL_FRAGMENT;
                sleepTime = referenceTime - System.nanoTime();

                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime / 1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //Activates or deactivates GameThread running or not.
        void setWorking(boolean flag) {
            running = flag;
        }

        //Function called if screen size or orientation changes.
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {  // Its recomendated to do it atomically.

            }
        }
    }
}
