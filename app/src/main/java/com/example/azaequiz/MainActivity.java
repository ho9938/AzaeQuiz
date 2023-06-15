package com.example.azaequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView vv = findViewById(R.id.main_background);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/opening");
        vv.setVideoURI(uri);
        vv.setOnPreparedListener(mp -> {
            mp.start();
            mp.setLooping(true);
        });

        Button normalMode = findViewById(R.id.main_normal);
        normalMode.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizListActivity.class);
            startActivity(intent);
        });

        Button challengeMode = findViewById(R.id.main_challenge);
        challengeMode.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
            intent.putExtra("isChallenge", true);
            startActivity(intent);
        });
    }
}