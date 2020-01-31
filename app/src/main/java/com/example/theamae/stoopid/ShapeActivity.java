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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.theamae.stoopid.Questions.JavaQuestions.JavaQuestions.shapeQuestions;

import java.util.ArrayList;
import java.util.Random;

public class ShapeActivity extends AppCompatActivity {

    private TextView question, score, time, best;
    private ImageView image;
    private Button choiceA, choiceB, choiceC, choiceD;
    private ImageButton home;
    private ArrayList<shapeQuestions> questions;
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
        setContentView(R.layout.activity_shape);

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
        home = (ImageButton) findViewById(R.id.home3);
        best = (TextView) findViewById(R.id.best_textView);

        setupScore();

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                i = new Intent(ShapeActivity.this, MainMenuActivity.class);

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
        String q = questions.get(index).getQuestion();
        ArrayList<String> choices = questions.get(index).getChoices();
        final int ans = questions.get(index).getAns();

        question.setText(q);
        image.setImageResource(questions.get(index).getImage());
        choiceA.setText(choices.get(0));
        choiceB.setText(choices.get(1));
        choiceC.setText(choices.get(2));
        choiceD.setText(choices.get(3));


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
                i.putExtra("ACTIVITY", "SHAPE");

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
        image = (ImageView) findViewById(R.id.imageView);
        choiceA = (Button) findViewById(R.id.choiceA);
        choiceB = (Button) findViewById(R.id.choiceB);
        choiceC = (Button) findViewById(R.id.choiceC);
        choiceD = (Button) findViewById(R.id.choiceD);

        questions = new ArrayList<shapeQuestions>();
    }

    private void createQuestions(){
        shapeQuestions q1 = new shapeQuestions("HOW MANY TRIANGLES ARE THERE", R.drawable.triangle, "9", "13", "7", "11", 2);
        shapeQuestions q2 = new shapeQuestions("HOW MANY SQUARES ARE THERE", R.drawable.square  , "15", "8", "13", "10", 3);
        shapeQuestions q3 = new shapeQuestions("HOW MANY CIRCLES ARE THERE", R.drawable.circle, "9", "6", "7", "11", 2);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
    }

    private void success(){
        //startActivity(getIntent());

        Random r = new Random();
        int nextAct = r.nextInt(5);
        //int nextAct = 1;

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
            i = new Intent(getBaseContext(), ButtonActivity.class);
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
