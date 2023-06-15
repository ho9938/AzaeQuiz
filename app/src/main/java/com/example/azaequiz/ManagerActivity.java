package com.example.azaequiz;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {
    private QuizBank quizBank;
    private ActivityResultLauncher<Intent> boardLauncher;
    private boolean isChallenge;
    private int curIndex;
    private int challengeLevel=3;
    private ArrayList<Integer> shuffledList;
    private int curTurn;
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        quizBank = new QuizBank(getAssets());
        boardLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), boardResultHandler);

        Intent intent = getIntent();
        isChallenge = intent.getBooleanExtra("isChallenge", false);
        curIndex = intent.getIntExtra("index", 0);

        challengeLevel = Math.min(challengeLevel, quizBank.getSize());
        shuffledList = new ArrayList<>();

        if (!isChallenge) {
            launchBoard(curIndex);
        } else {
            shuffleQuiz();
            curTurn = 0;
            launchBoard(shuffledList.get(curTurn));
        }

        soundManager = new SoundManager(this, new SoundPool.Builder().build());
        soundManager.addSound(0, R.raw.submit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        soundManager.release();
    }

    private void launchBoard(int index) {
        Intent intent = new Intent(ManagerActivity.this, BoardActivity.class);
        intent.putExtra("is_challenge", isChallenge);
        intent.putExtra("question_name", quizBank.getName(index));
        intent.putExtra("question_content", quizBank.getContent(index));
        intent.putExtra("question_answer", quizBank.getAnswer(index));
        intent.putExtra("remain_question", challengeLevel - curTurn - 1);

        boardLauncher.launch(intent);
    }

    private void shuffleQuiz() {
        shuffledList.clear();

        int i = 0;
        while (i < challengeLevel) {
            int curLevel = (int) Math.round(Math.random() * quizBank.getSize());
            if (!shuffledList.contains(curLevel)) {
                shuffledList.add(curLevel);
                i += 1;
            }
        }
    }

    private void showResult() {
        Intent intent = new Intent(ManagerActivity.this, ResultActivity.class);
        boolean success = curTurn >= challengeLevel;
//        boolean success = true;
        intent.putExtra("success", success);

        startActivity(intent);
        new Handler().postDelayed(this::finish, 5000);
    }

    ActivityResultCallback<ActivityResult> boardResultHandler = result -> {
        int resultCode = result.getResultCode();
        if (1000 <= resultCode && resultCode < 2000) { // normal mode
            int decoded = resultCode - 1000;
            if (decoded == 1) {
                curIndex = (curIndex + 1) % quizBank.getSize();
            }
            launchBoard(curIndex);
        } else if (2000 <= resultCode && resultCode < 3000) { // challenge mode
            int decoded = resultCode - 2000;
            if (decoded == 0) { // incorrect answer
                showResult();
            } else {
                curTurn += 1;
                if (curTurn < challengeLevel) {
                    launchBoard(shuffledList.get(curTurn));
                } else {
                    showResult();
                }
            }
        } else {
            finish();
        }
    };
}