package com.github.jlagoscarrera.nadir.Core;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.github.jlagoscarrera.nadir.Scenes.Credits;
import com.github.jlagoscarrera.nadir.Scenes.Information;
import com.github.jlagoscarrera.nadir.Scenes.Testers;
import com.github.jlagoscarrera.nadir.Scenes.Menu;
import com.github.jlagoscarrera.nadir.Scenes.Options;
import com.github.jlagoscarrera.nadir.Scenes.Play;
import com.github.jlagoscarrera.nadir.Scenes.Scene;
import com.github.jlagoscarrera.nadir.Scenes.TestersAdd;
import com.github.jlagoscarrera.nadir.Scenes.Tutorial;
import com.github.jlagoscarrera.nadirGame.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The game engine.
 */
public class NadirEngine extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * Abstract interface for handling drawing surface.
     */
    private SurfaceHolder surfaceHolder;    //Abstract interface for handling drawing surface.
    /**
     * Application context.
     */
    private Context context;                //Application context.

    /**
     * Screen Width, its value its refreshed onSurfaceChanged.
     */
    private int screenWidth = 1;            //Screen Width, its value its refreshed onSurfaceChanged.
    /**
     * Screen Height, its value its refreshed onSurfaceChanged.
     */
    private int screenHeight = 1;           //Screen Height, its value its refreshed onSurfaceChanged.
    /**
     * Thread that handles drawing and physics.
     */
    private GameThread gameThread;          //Thread that handles drawing and physics.
    /**
     * Thread running control flag.
     */
    private boolean running = false;        //Thread control.
    /**
     * Actual game scene
     */
    private Scene actualScene;
    /**
     * Audio manager for sounds
     */
    private AudioManager audioManager;
    /**
     * The SoundPool of sounds that can be played.
     */
    public SoundPool effects;
    /**
     * The Jump sound.
     */
    public int jumpSound;
    /**
     * The Move sound.
     */
    public int moveSound;
    /**
     * Maximum simultaneous sounds
     */
    final private int maxSimultaneousSounds = 10;
    /**
     * The game music.
     */
    public MediaPlayer gameMusic;
    /**
     * The current volume.
     */
    public int volume;
    /**
     * The options class.
     */
    public OptionsSettings options;
    /**
     * The game activity.
     */
    public static NadirActivity activity;
    /**
     * List of testers that played the game.
     */
    public ArrayList<String> testers = new ArrayList<>();
    /**
     * End time of a run.
     */
    public String endTime;


    /**
     * Instantiates a new game engine.
     *
     * @param activity the game activity
     */
    public NadirEngine(NadirActivity activity) {
        super(activity.getApplicationContext());
        this.surfaceHolder = getHolder();       //We obtain the holder.
        this.surfaceHolder.addCallback(this);   //We indicate where callback methods are.
        this.context = activity.getApplicationContext();                 //We obtain context.
        this.activity = activity;
        gameThread = new GameThread();          //Initialize the thread.
        options = new OptionsSettings(context);

        if ((android.os.Build.VERSION.SDK_INT) >= 21) {
            SoundPool.Builder spb = new SoundPool.Builder();
            spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
            spb.setMaxStreams(maxSimultaneousSounds);
            this.effects = spb.build();
        } else {
            this.effects = new SoundPool(maxSimultaneousSounds, AudioManager.STREAM_MUSIC, 0);
        }
        jumpSound = effects.load(context, R.raw.jump, 1);
        moveSound = effects.load(context, R.raw.walk, 1);

        setFocusable(true);                     //We assure that it receives isTouched events./
    }

    /**
     * Instantiates new scenes depending on received sceneID on screen touch.
     *
     * @param event event asociated to the touch
     * @return indicates if it has to call super(?)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (surfaceHolder) {
            int newScene = actualScene.onTouchEvent(event);
            if (newScene != actualScene.sceneId) {
                switch (newScene) {
                    case 0:
                        actualScene = new Menu(this, 0, screenWidth, screenHeight);
                        break;
                    case 1:
                        actualScene = new Play(this, 1, screenWidth, screenHeight);
                        break;
                    case 93:
                        actualScene = new TestersAdd(this, 93, screenWidth, screenHeight);
                        break;
                    case 94:
                        actualScene = new Tutorial(this, 94, screenWidth, screenHeight);
                        break;
                    case 95:
                        actualScene = new Credits(this, 95, screenWidth, screenHeight);
                        break;
                    case 96:
                        actualScene = new Information(this, 96, screenWidth, screenHeight);
                        break;
                    case 97:
                        actualScene = new Testers(this, 97, screenWidth, screenHeight);
                        break;
                    case 98:
                        actualScene = new Options(this, 98, screenWidth, screenHeight);
                        break;
                    case 99:
                        activity.finishAndRemoveTask();
                        break;
                }
            }
        }

        return true;
    }

    /**
     * New surface created actions.
     *
     * @param holder surface holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        options.loadOptions();
        //Audio managing
        updateAudioObjects();
        updateVolume();
        updateMusicPlayer();
    }

    /**
     * Surface dimensions changed actions.
     *
     * @param holder surface holder
     * @param format the format
     * @param width  new screen width
     * @param height new screen height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;    //New screen width is set.
        screenHeight = height;  //New screen height is set.

        if (options == null) {
            options = new OptionsSettings(context);
            options.loadOptions();
        }
        updateAudioObjects();
        updateVolume();
        updateMusicPlayer();

        actualScene = new Menu(this, 0, screenWidth, screenHeight);

        gameThread.setWorking(true);                    //We start the game.
        if (gameThread.getState() == Thread.State.NEW)  //Creates the thread if it isnt created.
            gameThread.start();
        if (gameThread.getState() == Thread.State.TERMINATED) { //If the thread has finished, creates it again.
            gameThread = new GameThread();
            gameThread.start();
        }
        gameThread.setSurfaceSize(width, height);   //We stablish new screen size on the gameThread.
    }

    /**
     * Surface destroyed.
     *
     * @param holder surface holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setWorking(false);   //We stop game thread.
        try {
            gameThread.join();          //Wait till it finishes.
            stopSounds();
            options.saveOptions();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update audio objects.
     */
    public void updateAudioObjects() {
        if (audioManager == null) {
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        }
        if (gameMusic == null) {
            gameMusic = MediaPlayer.create(context, R.raw.antares_outsider);
            try {
                gameMusic.prepare();
                gameMusic.setLooping(true);
            } catch (IllegalStateException | IOException e) {
                Log.e("Error playing music", "" + e.getLocalizedMessage());
            }

        }
    }

    /**
     * Update volume.
     */
    public void updateVolume() {
        try {
            if (audioManager != null)
                volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (gameMusic != null)
                gameMusic.setVolume(volume / 2, volume / 2);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update music player.
     */
    public void updateMusicPlayer() {
        if (!gameMusic.isPlaying() && options.isMusicPlaying()) {
            try {
                gameMusic.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameMusic.start();
        } else if (gameMusic.isPlaying() && !options.isMusicPlaying()) {
            gameMusic.pause();
        }
    }

    /**
     * Stop sounds.
     */
    public void stopSounds() {
        if (gameMusic != null) {
            gameMusic.stop();
            gameMusic.release();
        }
        gameMusic = null;
        audioManager = null;
    }

    /**
     * The game thread in which we implement drawing and physics methods for running paralell with the user interface.
     */
    class GameThread extends Thread {
        /**
         * Instantiates a new game thread.
         */
        public GameThread() {

        }

        /**
         * Function executed on the thread.
         */
        @Override
        public void run() {
            long sleepTime;
            final int FPS = 60;
            final int TPS = 1000000000;
            final int TEMPORAL_FRAGMENT = TPS / FPS;
            long referenceTime = System.nanoTime();

            while (running) {
                updateVolume();
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

        /**
         * Sets game running or not.
         *
         * @param flag the flag
         */
        void setWorking(boolean flag) {
            running = flag;
        }

        /**
         * Function called if screen size or orientation changes.
         *
         * @param width  the new width
         * @param height the new height
         */
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {

            }
        }
    }
}
