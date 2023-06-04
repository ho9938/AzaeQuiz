package com.example.azaequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private BoardView board;
    private ImageButton submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = findViewById(R.id.q_answer);
        submitBtn = findViewById(R.id.q_submit);
//        submitBtn.setOnClickListener(submitAnswer);

    }

//    View.OnClickListener submitAnswer = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    }
}