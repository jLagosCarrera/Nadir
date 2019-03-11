package com.github.jlagoscarrera.nadir.Core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The options shared preferences class.
 */
public class OptionsSettings {
    /**
     * Shared preferences name for this game
     */
    private final String optionsPreferences = "nadirOptions";
    /**
     * Music name on preferences
     */
    private final String musicPreference = "music";
    /**
     * Sounds name on preferences
     */
    private final String soundsPreference = "sounds";
    /**
     * Vibration name on preferences
     */
    private final String vibrationPreference = "vibration";
    /**
     * Game engine reference
     */
    private NadirEngine gameRef;
    /**
     * Shared preferences
     */
    private SharedPreferences options;
    /**
     * Indicates if music is playing or not
     */
    private boolean musicPlaying;
    /**
     * Indicates if sounds are playing or not
     */
    private boolean playSounds;
    /**
     * Indicates if vibration is activated or not
     */
    private boolean vibrate;

    /**
     * Vibrator service
     */
    public Vibrator vibrator;

    /**
     * Instantiates new settings object.
     *
     * @param gameRef the game engine context
     */
    public OptionsSettings(NadirEngine gameRef) {
        this.gameRef = gameRef;
        options = gameRef.getContext().getSharedPreferences(optionsPreferences, Context.MODE_PRIVATE);
        loadOptions();
        vibrator = (Vibrator) gameRef.getContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Load options from sharedpreferences.
     */
    public void loadOptions() {
        musicPlaying = options.getBoolean(musicPreference, true);
        playSounds = options.getBoolean(soundsPreference, true);
        vibrate = options.getBoolean(vibrationPreference, true);
    }

    /**
     * Save options on sharedpreferences.
     */
    public void saveOptions() {
        Editor editor = options.edit();
        editor.putBoolean(musicPreference, isMusicPlaying());
        editor.putBoolean(soundsPreference, isPlaySounds());
        editor.putBoolean(vibrationPreference, isVibrate());
        editor.commit();

        try (DataOutputStream dos = new DataOutputStream(gameRef.getContext().openFileOutput("testers.txt", Context.MODE_PRIVATE))) {
            for (String s : gameRef.testers) {
                dos.writeUTF(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if music is playing or not.
     *
     * @return if music is playing or not
     */
    public boolean isMusicPlaying() {
        return musicPlaying;
    }

    /**
     * Sets music playing or not.
     *
     * @param musicPlaying the new value of music playing or not
     */
    public void setMusicPlaying(boolean musicPlaying) {
        this.musicPlaying = musicPlaying;
    }

    /**
     * Checks if sounds are playing or not.
     *
     * @return if sounds are playing or not
     */
    public boolean isPlaySounds() {
        return playSounds;
    }

    /**
     * Sets sounds playing or not.
     *
     * @param playSounds the new value of sounds playing or not
     */
    public void setPlaySounds(boolean playSounds) {
        this.playSounds = playSounds;
    }

    /**
     * Checks if vibration is activated or not.
     *
     * @return if vibration is activated or not
     */
    public boolean isVibrate() {
        return vibrate;
    }

    /**
     * Sets vibration activated or not.
     *
     * @param vibrate the new value of vibration activated or not
     */
    public void setVibrate(boolean vibrate) {
        if (vibrate) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(300);
            }
        }

        this.vibrate = vibrate;
    }

    /**
     * Makes device vibrate
     */
    public void vibrate() {
        if (isVibrate()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(300);
            }
        }
    }

    public void leerTesters(){
        try (DataInputStream dis = new DataInputStream(gameRef.getContext().openFileInput("testers.txt"))) {
            String record;
            while ((record = dis.readUTF()) != null) {
                gameRef.testers.add(record);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}