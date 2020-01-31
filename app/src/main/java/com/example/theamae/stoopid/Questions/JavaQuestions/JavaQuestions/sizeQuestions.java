package com.example.theamae.stoopid.Questions.JavaQuestions.JavaQuestions;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by debbiesimon11 on 12/03/2018.
 */

public class sizeQuestions {
    private String questions;
    private ArrayList<Integer> choices;
    private int ans;

    public sizeQuestions(String questions, int A, int B, int C, int D, int ans) {
        this.questions = questions;
        choices = new ArrayList<Integer>();
        choices.add(A);
        choices.add(B);
        choices.add(C);
        choices.add(D);

        this.ans = ans;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public ArrayList<Integer> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<Integer> choices) {
        this.choices = choices;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }
}
