package com.example.theamae.stoopid.Questions.JavaQuestions.JavaQuestions;

import java.util.ArrayList;

/**
 * Created by debbiesimon11 on 12/03/2018.
 */

public class mathQuestions {

    private String questions;
    private ArrayList<String> choices;
    private int answer;

    public mathQuestions(String questions, String A, String B, String C, String D, int answer) {
        this.questions = questions;
        choices = new ArrayList<String>();
        choices.add(A);
        choices.add(B);
        choices.add(C);
        choices.add(D);
        this.answer = answer;
    }

    public String getQuestions() {
        return questions;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public int getAnswer() {
        return answer;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}

