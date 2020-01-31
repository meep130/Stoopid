package com.example.theamae.stoopid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.theamae.stoopid.Questions.JavaQuestions.JavaQuestions.mathQuestions;

import java.util.ArrayList;
import java.util.Random;

public class MathActivity extends AppCompatActivity {

    private ArrayList<mathQuestions> questions;
    private TextView question, score, time, best;
    private Button choiceA, choiceB, choiceC, choiceD;
    private ImageButton home;
    private CountDownTimer countDownTimer;
    private int gameScore, initial, currTime;

    private BackgroundSoundService bss;
    private boolean isBound = false;
    private Intent i;
    private int ctr, firstScore;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        context = this;

        Intent intent = new Intent(this, BackgroundSoundService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        Intent intent1 = new Intent();
        ctr = intent1.getIntExtra("ctr", 0);
        if (ctr == 1) {
            System.out.println("ctr is 1");
            bss.editVolume(0);
            ctr = 2;
        }

        time = (TextView) findViewById(R.id.time_textView);
        score = (TextView) findViewById(R.id.score_textView);
        best = (TextView) findViewById(R.id.best_textView);

        setupScore();

        home = (ImageButton) findViewById(R.id.home_btn);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                i = new Intent(MathActivity.this, MainMenuActivity.class);

                setResult(Activity.RESULT_CANCELED, getIntent());

                startActivityForResult(i, 0);

                countDownTimer.cancel();
                finish();
                onDestroy();
            }
        });

        gameScore = getIntent().getIntExtra("GAME_SCORE", 0);
        score.setText(gameScore + "");
        System.out.println("Math: " + gameScore);

        initial = getIntent().getIntExtra("FIRST", 0);
        if (initial == 1) {
            start(61);
        }

        else {
            start(getIntent().getIntExtra("GAME_TIME", 0));
        }


        initializeComponents();
        createQuestions();

        Random r = new Random();
        int index = r.nextInt(questions.size());
        String q = questions.get(index).getQuestions();
        ArrayList<String> choices = questions.get(index).getChoices();
        final int ans = questions.get(index).getAnswer();

        question.setText(q);
        choiceA.setText(choices.get(0));
        choiceB.setText(choices.get(1));
        choiceC.setText(choices.get(2));
        choiceD.setText(choices.get(3));

        choiceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ans == 0){
                    Random r = new Random();
                    int nextAct = r.nextInt(4);
                    gameScore++;

                    if(nextAct == 0) {
                        i = new Intent(getBaseContext(), GesturesActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 1) {
                        i = new Intent(getBaseContext(), ButtonActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 2){
                        i = new Intent(getBaseContext(), ShapeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 3){
                        i = new Intent(getBaseContext(), SizeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    Thread timer = new Thread(){
                        public void run(){
                            try{
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            finally{
                                setResult(Activity.RESULT_CANCELED, getIntent());
                                finish();

                                startActivityForResult(i, 0);
                            }
                        }
                    };

                    timer.start();
                }

                else {
                    gameScore--;

                    Random r = new Random();
                    int nextAct = r.nextInt(4);

                    if(nextAct == 0) {
                        i = new Intent(getBaseContext(), GesturesActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 1) {
                        i = new Intent(getBaseContext(), ButtonActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 2){
                        i = new Intent(getBaseContext(), ShapeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 3){
                        i = new Intent(getBaseContext(), SizeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    Thread timer = new Thread(){
                        public void run(){
                            try{
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            finally{

                                setResult(Activity.RESULT_CANCELED, getIntent());

                                startActivityForResult(i, 0);

                                countDownTimer.cancel();
                                finish();
                            }
                        }
                    };

                    timer.start();
                }
            }
        });

        choiceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ans == 1){
                    Random r = new Random();
                    int nextAct = r.nextInt(4);
                    gameScore++;

                    if(nextAct == 0) {
                        i = new Intent(getBaseContext(), GesturesActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 1) {
                        i = new Intent(getBaseContext(), ButtonActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 2){
                        i = new Intent(getBaseContext(), ShapeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 3){
                        i = new Intent(getBaseContext(), SizeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    Thread timer = new Thread(){
                        public void run(){
                            try{
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            finally{

                                setResult(Activity.RESULT_CANCELED, getIntent());

                                startActivityForResult(i, 0);

                                countDownTimer.cancel();
                                finish();
                            }
                        }
                    };

                    timer.start();
                }

                else {
                    gameScore--;

                    Random r = new Random();
                    int nextAct = r.nextInt(4);

                    if(nextAct == 0) {
                        i = new Intent(getBaseContext(), GesturesActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 1) {
                        i = new Intent(getBaseContext(), ButtonActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 2){
                        i = new Intent(getBaseContext(), ShapeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 3){
                        i = new Intent(getBaseContext(), SizeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    Thread timer = new Thread(){
                        public void run(){
                            try{
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            finally{

                                setResult(Activity.RESULT_CANCELED, getIntent());

                                startActivityForResult(i, 0);

                                countDownTimer.cancel();
                                finish();
                            }
                        }
                    };

                    timer.start();
                }
            }
        });

        choiceC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ans == 2){
                    /*
                    finish();
                    startActivity(getIntent());*/

                    gameScore++;
                    Random r = new Random();
                    int nextAct = r.nextInt(4);

                    if(nextAct == 0) {
                        i = new Intent(getBaseContext(), GesturesActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 1) {
                        i = new Intent(getBaseContext(), ButtonActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 2){
                        i = new Intent(getBaseContext(), ShapeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 3){
                        i = new Intent(getBaseContext(), SizeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    Thread timer = new Thread(){
                        public void run(){
                            try{
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            finally{
                                countDownTimer.cancel();

                                setResult(Activity.RESULT_CANCELED, getIntent());
                                finish();

                                startActivityForResult(i, 0);
                            }
                        }
                    };

                    timer.start();
                }

                else {
                    gameScore--;

                    Random r = new Random();
                    int nextAct = r.nextInt(5);

                    if(nextAct == 0) {
                        i = new Intent(getBaseContext(), GesturesActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 1) {
                        i = new Intent(getBaseContext(), ButtonActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 2){
                        i = new Intent(getBaseContext(), ShapeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 3){
                        i = new Intent(getBaseContext(), SizeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 4){
                        i = new Intent(getBaseContext(), RiddleActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    Thread timer = new Thread(){
                        public void run(){
                            try{
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            finally{

                                setResult(Activity.RESULT_CANCELED, getIntent());

                                startActivityForResult(i, 0);

                                countDownTimer.cancel();
                                finish();
                            }
                        }
                    };

                    timer.start();
                }
            }
        });

        choiceD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ans == 3){
                    //startActivity(getIntent());
                    Random r = new Random();
                    int nextAct = r.nextInt(4);
                    gameScore++;

                    if(nextAct == 0) {
                        i = new Intent(getBaseContext(), GesturesActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 1) {
                        i = new Intent(getBaseContext(), ButtonActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 2){
                        i = new Intent(getBaseContext(), ShapeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 3){
                        i = new Intent(getBaseContext(), SizeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    Thread timer = new Thread(){
                        public void run(){
                            try{
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            finally{

                                setResult(Activity.RESULT_CANCELED, getIntent());

                                startActivityForResult(i, 0);

                                countDownTimer.cancel();
                                finish();
                            }
                        }
                    };

                    timer.start();
                }

                else {
                    gameScore--;

                    Random r = new Random();
                    int nextAct = r.nextInt(4);

                    if(nextAct == 0) {
                        i = new Intent(getBaseContext(), GesturesActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 1) {
                        i = new Intent(getBaseContext(), ButtonActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 2){
                        i = new Intent(getBaseContext(), ShapeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 3){
                        i = new Intent(getBaseContext(), SizeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    Thread timer = new Thread(){
                        public void run(){
                            try{
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            finally{

                                setResult(Activity.RESULT_CANCELED, getIntent());

                                startActivityForResult(i, 0);

                                countDownTimer.cancel();
                                finish();
                            }
                        }
                    };

                    timer.start();
                }
            }
        });

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getApplicationContext().unbindService(serviceConnection);
    }

    public void start(int x) {
        //time.setText("1:00");

        countDownTimer = new CountDownTimer(x * 1000, 1000) {
            @Override
            public void onTick(long milliUntilFinished) {
                currTime = (int) milliUntilFinished / 1000;
                if (currTime == 60) {
                    currTime = 0;
                }

                time.setText("" + currTime);
            }

            @Override
            public void onFinish() {
                i = new Intent(context, EndActivity.class);
                i.putExtra("SCORE", gameScore);
                i.putExtra("ACTIVITY", "MATH");

                setResult(Activity.RESULT_CANCELED, getIntent());
                startActivityForResult(i, 0);

                countDownTimer.cancel();
                finish();
            }
        };

        countDownTimer.start();
    }

    private void initializeComponents(){
        question = (TextView) findViewById(R.id.color_tv);
        choiceA = (Button) findViewById(R.id.choiceA);
        choiceB = (Button) findViewById(R.id.choiceB);
        choiceC = (Button) findViewById(R.id.choiceC);
        choiceD = (Button) findViewById(R.id.choiceD);

        questions = new ArrayList<mathQuestions>();
    }

    private void createQuestions(){
        mathQuestions q1 = new mathQuestions("1/2 =", "0.005", "0.10", "0.05", "0.5", 3);
        mathQuestions q2 = new mathQuestions("20 - ___ = 13", "7", "11", "9", "4", 0);
        mathQuestions q3 = new mathQuestions("1 1/4 =", "0.75", "1.25", "0.05", "0.25", 1);
        mathQuestions q4 = new mathQuestions("7 - ___ + 11 = 15", "3", "5", "4", "7", 0);
        mathQuestions q5 = new mathQuestions("1/8 =", "0.25", "0.175", "0.125", "0.85", 2);
        mathQuestions q6 = new mathQuestions("20 - 8 =", "7", "12", "13", "9", 1);
        mathQuestions q7 = new mathQuestions("9 x ___ = 99", "10", "8", "13", "11", 3);
        mathQuestions q8 = new mathQuestions("57 - 8 =", "53", "49", "47", "45", 1);
        mathQuestions q9 = new mathQuestions("13 + ___ + 2 = 23", "7", "12", "5", "8", 3);
        mathQuestions q10 = new mathQuestions("6 + ___ + 7 = 17", "3", "2", "7", "4", 3);
        mathQuestions q11 = new mathQuestions("7 - 5 - ___ = -1", "5", "6", "3", "-1", 2);
        mathQuestions q12 = new mathQuestions("8 - ___ + 2 = 7", "8", "3", "16", "4", 1);
        mathQuestions q13 = new mathQuestions("43 - 87 = ___", "-37", "-40", "-23", "-44", 3);
        mathQuestions q14 = new mathQuestions("32 + ___ = 41", "3", "9", "7", "15", 1);
        mathQuestions q15 = new mathQuestions("13 * 4 - 5 =", "39", "41", "37", "47", 3);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);
        questions.add(q8);
        questions.add(q9);
        questions.add(q10);
        questions.add(q11);
        questions.add(q12);
        questions.add(q13);
        questions.add(q14);
        questions.add(q15);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (i == null) {
            System.out.println("INTENT IS NULL");
            ctr = 1;
            bss.editVolume(1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ctr == 1) {
            bss.editVolume(0);
            ctr = 2;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            BackgroundSoundService.LocalBinder binder = (BackgroundSoundService.LocalBinder) service;
            bss = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    private void setupScore() {
        SharedPreferences mSharedPreference = getSharedPreferences("SCORE_STORAGE", Context.MODE_PRIVATE);
        firstScore = mSharedPreference.getInt("HIGHEST", 0);

        best.setText(firstScore + "");
    }
}
