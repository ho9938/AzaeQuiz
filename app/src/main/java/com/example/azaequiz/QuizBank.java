package com.example.azaequiz;

import java.util.ArrayList;

public class QuizBank {
    private ArrayList<Quiz> quizList;
    private int quizSize;

    public QuizBank() {
        quizList = new ArrayList<>();
        quizList.add(new Quiz( "What is it that lives if it is fed, and dies if you give it a drink?", "fire"));
        quizSize = quizList.size();
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