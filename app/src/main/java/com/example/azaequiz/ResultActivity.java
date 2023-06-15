package com.example.azaequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ImageView iv = findViewById(R.id.result_background);
        TextView tv = findViewById(R.id.result_content);

        Intent intent = getIntent();
        boolean success = intent.getBooleanExtra("success", false);

        if (success) {
//            iv.setBackgroundResource(R.drawable.success);
            iv.setImageResource(R.drawable.success);
            tv.setText("YOU WON THE GOLDEN BELL!!!");
        } else {
//            iv.setBackgroundResource(R.drawable.failure);
            iv.setImageResource(R.drawable.failure);
            tv.setText("YOU LOST THE GOLDEN BELL...");
        }

        new Handler().postDelayed(this::finish, 5000);
    }
}