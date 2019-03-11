package com.github.jlagoscarrera.nadir.Core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

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
     * Game engine context
     */
    private Context context;
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
     * Instantiates new settings object.
     *
     * @param context the game engine context
     */
    public OptionsSettings(Context context) {
        this.context = context;
        options = context.getSharedPreferences(optionsPreferences, Context.MODE_PRIVATE);
        loadOptions();
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
        this.vibrate = vibrate;
    }
}