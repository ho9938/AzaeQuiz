package com.example.azaequiz;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.util.HashMap;

public class SoundManager {
    private SoundPool soundPool;
    private final HashMap<Integer, Integer> soundPoolMap;
    private final AudioManager audioManager;
    private final Context context;

    public SoundManager(Context context, SoundPool soundPool) {
        this.context = context;
        this.soundPool = soundPool;

        soundPoolMap = new HashMap<>();
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void addSound(int index, int soundId) {
        soundPoolMap.put(index, soundPool.load(context, soundId, 1));
    }

    public void playSound(int index) {
        Log.d("sound", "play" + soundPoolMap.get(index));
        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        new Handler().postDelayed(() -> soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f), 1000);
        soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
    }

    public void release() {
        soundPool.release();
        soundPool = null;
    }
}
