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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.theamae.stoopid.Questions.JavaQuestions.JavaQuestions.sizeQuestions;

import java.util.ArrayList;
import java.util.Random;

public class SizeActivity extends AppCompatActivity {

    private TextView question, score, time, best;
    private ImageButton choiceA, choiceB, choiceC, choiceD, home;
    private ArrayList<sizeQuestions> questions;
    private Intent i;
    private CountDownTimer countDownTimer;
    private int gameScore, currTime, initial;

    private BackgroundSoundService bss;
    private boolean isBound = false;
    private int ctr, firstScore;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);

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

        home = (ImageButton) findViewById(R.id.home_btn3);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                i = new Intent(SizeActivity.this, MainMenuActivity.class);

                setResult(Activity.RESULT_CANCELED, getIntent());

                startActivityForResult(i, 0);

                countDownTimer.cancel();
                finish();
            }
        });

        gameScore = getIntent().getIntExtra("GAME_SCORE", 0);
        score.setText(gameScore + "");

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
        System.out.println("Index: " + index);

        String q = questions.get(index).getQuestions();
        ArrayList<Integer> choices = questions.get(index).getChoices();

        int ans = questions.get(index).getAns();
        System.out.println("Ans: " + ans);

        question.setText(q);
        choiceA.setBackgroundResource(choices.get(0));
        choiceB.setBackgroundResource(choices.get(1));
        choiceC.setBackgroundResource(choices.get(2));
        choiceD.setBackgroundResource(choices.get(3));


        if(ans == 0) {
            choiceA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        gameScore++;
                        success();
                }
            });
            choiceB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
            choiceC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
            choiceD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
        }

        else if(ans == 1) {
            choiceB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        gameScore++;
                        success();
                        System.out.println(choiceB.getDrawable());
                }
            });
            choiceA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
            choiceC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
            choiceD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
        }

        else if(ans == 2) {
            choiceC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gameScore++;
                    success();
                    System.out.println(choiceC.getDrawable());
                }
            });
            choiceB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
            choiceA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
            choiceD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
        }

        else if(ans == 3) {
            choiceD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        gameScore++;
                        success();
                        System.out.println(choiceD.getDrawable());

                }
            });
            choiceB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
            choiceC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
            choiceA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // decrement score
                    gameScore--;
                    success();
                }
            });
        }
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
    public void onDestroy()
    {
        super.onDestroy();
        getApplicationContext().unbindService(serviceConnection);
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
                i.putExtra("ACTIVITY", "SIZE");

                setResult(Activity.RESULT_CANCELED, getIntent());

                startActivityForResult(i, 0);

                countDownTimer.cancel();
                finish();
                onDestroy();
            }
        };

        countDownTimer.start();
    }

    private void initializeComponents(){
        question = (TextView) findViewById(R.id.color_tv);
        choiceA = (ImageButton) findViewById(R.id.choiceA);
        choiceB = (ImageButton) findViewById(R.id.choiceB);
        choiceC = (ImageButton) findViewById(R.id.choiceC);
        choiceD = (ImageButton) findViewById(R.id.choiceD);

        questions = new ArrayList<sizeQuestions>();
    }

    private void createQuestions(){
        sizeQuestions q1 = new sizeQuestions("Which is the tallest?", R.drawable.lib, R.drawable.tree, R.drawable.rocket, R.drawable.birdhouse, 2);
        sizeQuestions q2 = new sizeQuestions("Which is the smallest?", R.drawable.mountain, R.drawable.bigben, R.drawable.planet, R.drawable.whale, 3);
        sizeQuestions q3 = new sizeQuestions("Which is the fastest?", R.drawable.shark, R.drawable.car, R.drawable.rocket, R.drawable.ship, 2);
        sizeQuestions q4 = new sizeQuestions("Which is the slowest?", R.drawable.sloth, R.drawable.car, R.drawable.worm, R.drawable.snail, 3);
        sizeQuestions q5 = new sizeQuestions("Which is the hardest?", R.drawable.rock, R.drawable.shell, R.drawable.basketball, R.drawable.window, 0);
        sizeQuestions q6 = new sizeQuestions("Which is the fattest?", R.drawable.stick, R.drawable.basketball, R.drawable.panda, R.drawable.sumo, 3);
        sizeQuestions q7 = new sizeQuestions("Which is the softest?", R.drawable.soccer, R.drawable.pillow, R.drawable.shoe, R.drawable.basketball, 1);
        sizeQuestions q8 = new sizeQuestions("Which is the hottest?", R.drawable.sun, R.drawable.moon, R.drawable.lava, R.drawable.snail, 0);
        sizeQuestions q9 = new sizeQuestions("Which is the coldest?", R.drawable.shark, R.drawable.sun, R.drawable.icecream, R.drawable.penguin, 2);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);
        questions.add(q8);
        questions.add(q9);
    }

    private void success(){
        //startActivity(getIntent());

        Random r = new Random();
        //i = new Intent(getBaseContext(), EndActivity.class);

        //gameScore++;
        //int nextAct = r.nextInt(5);
        int nextAct = 1;

        if(nextAct == 0) {
            i = new Intent(getBaseContext(), GesturesActivity.class);
            i.putExtra("GAME_SCORE", gameScore);
            i.putExtra("GAME_TIME", currTime);
            i.putExtra("FIRST", 0);
        }

        else if(nextAct == 1) {
            i = new Intent(getBaseContext(), MathActivity.class);
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
            i = new Intent(getBaseContext(), ButtonActivity.class);
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

    private void setupScore() {
        SharedPreferences mSharedPreference = getSharedPreferences("SCORE_STORAGE", Context.MODE_PRIVATE);
        firstScore = mSharedPreference.getInt("HIGHEST", 0);

        best.setText(firstScore + "");
    }
}
