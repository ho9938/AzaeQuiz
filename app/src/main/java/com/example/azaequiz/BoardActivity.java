package com.example.azaequiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.text.Text;

import java.util.ArrayList;


public class BoardActivity extends AppCompatActivity {
    private BoardView board;
    private TextScanner textScanner;
    private String question_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        board = findViewById(R.id.board_answer);
        board.initBoard(findViewById(R.id.board_pen), findViewById(R.id.board_eraser));

        ImageButton submitBtn = findViewById(R.id.board_submit);
        submitBtn.setOnClickListener(submitAnswer);
        
        textScanner = new TextScanner(recogSuccess, recogFailure);

        Intent intent = getIntent();
        String question_name = intent.getStringExtra("question_name");
        String question_content = intent.getStringExtra("question_content");
        this.question_answer = intent.getStringExtra("question_answer");

        TextView board_number = findViewById(R.id.board_qname);
        board_number.setText(question_name);

        TextView board_content = findViewById(R.id.board_qcontent);
        board_content.setText(question_content);
    }

    public void notifyText(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
  
    View.OnClickListener submitAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("MainActivity", "submitAnswer()");
            Bitmap result = board.getBitmap();
            textScanner.recogBitmap(result);
        }
    };

    OnSuccessListener<Text> recogSuccess = new OnSuccessListener<Text>() {
        @Override
        public void onSuccess(Text text) {
            Log.d("recogBitmap", "success");
            String result = text.getText();
            notifyText(result);
        }
    };

    OnFailureListener recogFailure = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e("recogBitmap", "fail");
        }
    };
}