package com.example.azaequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        VideoView vv = findViewById(R.id.result_background);
        TextView tv = findViewById(R.id.result_content);

        Intent intent = getIntent();
        boolean success = intent.getBooleanExtra("success", false);

        if (success) {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/success_ch");
            vv.setVideoURI(uri);
            tv.setText("YOU WON THE GOLDEN BELL");
        } else {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/failure_ch");
            vv.setVideoURI(uri);
            tv.setText("YOU LOST THE GOLDEN BELL");
        }

        vv.setOnPreparedListener(mp -> {
            mp.start();
            mp.setLooping(true);
        });

        vv.setOnClickListener(v -> finish());
    }
}