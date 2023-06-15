package com.example.azaequiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
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


public class BoardActivity extends AppCompatActivity {
    private BoardView board;
    private TextScanner textScanner;
    private boolean is_challenge;
    private String question_answer;
    private CheckAnswerDialog checkAnswerDialog;
    private SoundManager soundManager;
    int remain_question;
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
        remain_question = intent.getIntExtra("remain_question", -1);

        checkAnswerDialog = new CheckAnswerDialog(this, goback, retry, gonext, is_challenge);

        TextView board_number = findViewById(R.id.board_qname);
        board_number.setText(question_name);

        TextView board_content = findViewById(R.id.board_qcontent);
        board_content.setText(question_content);

        soundManager = new SoundManager(this, new SoundPool.Builder().build());
        soundManager.addSound(0, R.raw.submit);
        soundManager.addSound(1, R.raw.question);
        soundManager.addSound(2, R.raw.success);
        soundManager.addSound(3, R.raw.failure);
        new Handler().postDelayed(() -> soundManager.playSound(1), 1000);

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.ENGLISH);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported");
                }
                new Handler().postDelayed(() -> {
                    speakText(question_content);
                }, 3000);
            } else {
                Log.e("TTS", "Initialization failed");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        soundManager.release();
    }

    public void notifyText(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    private void speakText(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
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
            String result = text.getText().trim().toUpperCase();
            boolean is_correct = checkAnswerDialog.isCorrect(question_answer, result);
//            notifyText(result);

            startSubmitActivity();

            checkAnswerDialog.setContent(question_answer, result, remain_question);
            new Handler().postDelayed(() -> {
                checkAnswerDialog.show();
                if (is_correct) {
                    soundManager.playSound(2);
                } else {
                    soundManager.playSound(3);
                }}, 3000);

            if (is_challenge) {
                new Handler().postDelayed(() -> {
                    checkAnswerDialog.show();
                    if (is_correct) {
                        returnWithCode(2001);
                    } else {
                        returnWithCode(2000);
                    }}, 6000);
            }
        }
    };

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
        soundManager.playSound(0);
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