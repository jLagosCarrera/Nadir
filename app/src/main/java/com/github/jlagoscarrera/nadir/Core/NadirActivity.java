package com.github.jlagoscarrera.nadir.Core;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.github.jlagoscarrera.nadir.Core.NadirEngine;

/**
 * The game activity.
 */
public class NadirActivity extends AppCompatActivity {

    /**
     * The game engine.
     */
    NadirEngine nadirEngine;


    /**
     * Creates game engine and shows the view of it
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int options = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(options);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        nadirEngine = new NadirEngine(this);
        nadirEngine.setKeepScreenOn(true);

        setContentView(nadirEngine);
    }

    /**
     * Sets view of the engine or creates it on resume
     */
    @Override
    protected void onResume() {
        super.onResume();

        View decorView = getWindow().getDecorView();
        int opciones = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(opciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (nadirEngine == null) {
            nadirEngine = new NadirEngine(this);
        }
        nadirEngine.setKeepScreenOn(true);
        setContentView(nadirEngine);
    }

    /**
     * Do nothing on back button pressed
     */
    @Override
    public void onBackPressed() {

    }
}
