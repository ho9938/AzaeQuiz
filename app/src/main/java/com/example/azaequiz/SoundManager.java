package com.example.azaequiz;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;

import java.util.HashMap;

public class SoundManager {
    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;
    private AudioManager audioManager;
    private Context context;

    public SoundManager(Context context, SoundPool soundPool) {
        this.context = context;
        this.soundPool = soundPool;

        soundPoolMap = new HashMap<>();
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void addSound(int index, int soundId) {
        soundPoolMap.put(index, soundPool.load(context, soundId, 1));
    }

    public int playSound(int index) {
        Log.d("sound", "play" + soundPoolMap.get(index));
        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        new Handler().postDelayed(() -> soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f), 1000);
        soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
        return 0;
    }

    public void stopSound(int streamId) {
        soundPool.stop(streamId);
    }

    public void pauseSound(int streamId) {
        soundPool.pause(streamId);
    }

    public void resumeSound(int streamId) {
        soundPool.resume(streamId);
    }

    public void release() {
        soundPool.release();
        soundPool = null;
    }
}
