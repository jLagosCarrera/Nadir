package com.github.jlagoscarrera.nadir.Core;

import android.content.Context;
import android.content.SharedPreferences;

public class OptionsSettings {
    private final String optionsPreferences = "options";
    private final String musicPreference = "music";
    private final String soundsPreference = "sounds";
    private final String vibrationPreference = "vibration";
    private Context context;
    private SharedPreferences options;
    private boolean musicPlaying;
    private boolean playSounds;
    private boolean vibrate;

    public OptionsSettings(Context context){
        this.context = context;
        options = context.getSharedPreferences(optionsPreferences, Context.MODE_PRIVATE);
    }

    public void loadOptions(){
        musicPlaying = options.getBoolean(musicPreference, true);
        playSounds = options.getBoolean(soundsPreference, true);
        vibrate = options.getBoolean(vibrationPreference, true);
    }

    public void saveOptions(){
        SharedPreferences.Editor editor = options.edit();
        editor.putBoolean(musicPreference, musicPlaying);
        editor.putBoolean(soundsPreference, playSounds);
        editor.putBoolean(musicPreference, vibrate);
        editor.commit();
    }

    public boolean isMusicPlaying() {
        return musicPlaying;
    }

    public void setMusicPlaying(boolean musicPlaying) {
        this.musicPlaying = musicPlaying;
    }

    public boolean isPlaySounds() {
        return playSounds;
    }

    public void setPlaySounds(boolean playSounds) {
        this.playSounds = playSounds;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }
}