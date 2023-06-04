package com.example.azaequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private BoardView board;
    private ImageButton submitBtn;
    private ImageView testView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = findViewById(R.id.q_answer);

        submitBtn = findViewById(R.id.q_submit);
        submitBtn.setOnClickListener(submitAnswer);

        testView = findViewById(R.id.q_test);
    }

    View.OnClickListener submitAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("MainActivity", "submitAnswer()");
            Bitmap result = board.getBitmap();
            testView.setImageBitmap(result);
        }
    };
}