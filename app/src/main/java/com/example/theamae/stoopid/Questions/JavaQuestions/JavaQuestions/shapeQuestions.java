package com.example.theamae.stoopid.Questions.JavaQuestions.JavaQuestions;

import java.util.ArrayList;

/**
 * Created by debbiesimon11 on 12/03/2018.
 */

public class shapeQuestions {

    private String question;
    private Integer image;
    private ArrayList<String> choices;
    private int ans;

    public shapeQuestions(String question, Integer image, String A, String B, String C, String D, int ans) {
        this.question = question;
        this.image = image;

        this.choices = new ArrayList<String>();
        choices.add(A);
        choices.add(B);
        choices.add(C);
        choices.add(D);

        this.ans = ans;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setImage(ArrayList<String> choices) {
        this.choices = choices;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }
}
