package com.example.azaequiz;

import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuizBank {
    private AssetManager assetManager;
    private ArrayList<Quiz> quizList;
    private int quizSize;

    public QuizBank(AssetManager assetManager) {
        this.assetManager = assetManager;
        constructBank();
    }

    public String getName(int index) {
        assert(0 <= index && index < quizSize);
        return "Q" + (index + 1);
    }

    public String getContent(int index) {
        assert(0 <= index && index < quizSize);
        return quizList.get(index).getQuestion();
    }

    public String getAnswer(int index) {
        assert(0 <= index && index < quizSize);
        return quizList.get(index).getAnswer();
    }

    public int getSize() {
        return this.quizSize;
    }

    private void constructBank() {
        Log.d("QuizBank", "json parsing start");

        StringBuilder json = new StringBuilder();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("quizzes.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line= reader.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        quizList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json.toString());
            JSONArray quizArray = jsonObject.getJSONArray("quizzes");

            for (int i = 0; i < quizArray.length(); i += 1) {
                JSONObject curObject = quizArray.getJSONObject(i);
                Quiz curQuiz = new Quiz(curObject.getString("content"), curObject.getString("answer"));

                quizList.add(curQuiz);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        quizSize = quizList.size();

        Log.d("QuizBank", "json parsing complete");
    }
}

class Quiz {
    final private String question;
    final private String answer;

    public Quiz(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}