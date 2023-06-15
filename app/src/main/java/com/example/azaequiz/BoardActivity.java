package com.example.azaequiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import android.speech.tts.TextToSpeech;
import static android.speech.tts.TextToSpeech.ERROR;

public class BoardActivity extends AppCompatActivity {
    private BoardView board;
    private TextScanner textScanner;
    private String question_answer;
    private CheckAnswerDialog checkAnswerDialog;
    private boolean is_challenge;
    private TextToSpeech tts;

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
        is_challenge = intent.getBooleanExtra("is_challenge", false);
        String question_name = intent.getStringExtra("question_name");
        String question_content = intent.getStringExtra("question_content");
        question_answer = intent.getStringExtra("question_answer");

        checkAnswerDialog = new CheckAnswerDialog(this, goback, retry, gonext);

        TextView board_number = findViewById(R.id.board_qname);
        board_number.setText(question_name);

        TextView board_content = findViewById(R.id.board_qcontent);
        board_content.setText(question_content);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                    speakText(question_content);
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });


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

            startSubmitActivity();

            if (is_challenge) {
                if (checkAnswerDialog.isCorrect(question_answer, result)) {
                    returnWithCode(2001);
                } else {
                    returnWithCode(2000);
                }
            } else {
                checkAnswerDialog.setContent(question_answer, result);
                new Handler().postDelayed(() -> checkAnswerDialog.show(), 3000);
            }
        }
    };

    private void speakText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    OnFailureListener recogFailure = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e("recogBitmap", "fail");
        }
    };

    private void startSubmitActivity() {
        Intent intent = new Intent(BoardActivity.this, SubmitActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        board.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("image", byteArray);
        startActivity(intent);
    }

    private void returnWithCode(int code) {
        Intent intent = new Intent(BoardActivity.this, ManagerActivity.class);
        setResult(code, intent);
        finish();
    }

    View.OnClickListener goback = v -> {
        returnWithCode(-1);
    };

    View.OnClickListener retry = v -> {
        returnWithCode(1000);
    };

    View.OnClickListener gonext = v -> {
        returnWithCode(1001);
    };
}