package com.example.azaequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SubmitActivity extends AppCompatActivity {
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        ImageView board = findViewById(R.id.submit_board);

        Intent intent = getIntent();
        byte[] byteArray = intent.getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        board.setImageBitmap(image);

        soundManager = new SoundManager(this, new SoundPool.Builder().build());
        soundManager.addSound(1, R.raw.submit);
//        soundManager.playSound(1);

        new Handler().postDelayed(this::finish, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        soundManager.release();
    }
}