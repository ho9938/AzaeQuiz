package com.example.azaequiz;

import java.util.ArrayList;

public class QuizBank {
    private ArrayList<Quiz> quizList;
    private int quizSize;

    public QuizBank() {
        quizList = new ArrayList<>();
        quizList.add(new Quiz( "Answer is ONE", "ONE"));
        quizList.add(new Quiz( "Answer is TWO", "TWO"));
        quizList.add(new Quiz( "Answer is THREE", "THREE"));
        quizList.add(new Quiz( "Answer is FOUR", "FOUR"));
        quizList.add(new Quiz( "Answer is FIVE", "FIVE"));
        quizList.add(new Quiz( "Answer is SIX", "SIX"));
        quizList.add(new Quiz( "Answer is SEVEN", "SEVEN"));
        quizList.add(new Quiz( "Answer is EIGHT", "EIGHT"));
        quizList.add(new Quiz( "Answer is NINE", "NINE"));
        quizList.add(new Quiz( "Answer is TEN", "TEN"));
        quizList.add(new Quiz( "Answer is ELEVEN", "ELEVEN"));
        quizList.add(new Quiz( "Answer is TWELVE", "TWELVE"));

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