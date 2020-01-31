package com.example.theamae.stoopid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.theamae.stoopid.Questions.JavaQuestions.JavaQuestions.riddleQuestions;

import java.util.ArrayList;
import java.util.Random;

public class RiddleActivity extends AppCompatActivity {

    private ImageButton choiceA, choiceB, choiceC, choiceD, home;
    private TextView time, score, question, colorTextView, best;
    private int gameScore, currTime, initial, index;
    private CountDownTimer countDownTimer;
    private ArrayList<riddleQuestions> questions;
    private Intent i;
    private ArrayList<String> colorText;
    private int colorAnswer;
    private Random r = new Random();
    private riddleQuestions q1, q2;
    private int pickTextColor, firstScore;

    private BackgroundSoundService bss;
    private boolean isBound = false;
    private int ctr;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riddle);

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

        best = (TextView) findViewById(R.id.best_textView);

        setupScore();

        home = (ImageButton) findViewById(R.id.home_btn);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                i = new Intent(RiddleActivity.this, MainMenuActivity.class);

                setResult(Activity.RESULT_CANCELED, getIntent());

                startActivityForResult(i, 0);

                countDownTimer.cancel();
                finish();
            }
        });

        setup();
        setText();
        createQuestions();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getApplicationContext().unbindService(serviceConnection);
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

    private void setup() {

        choiceA = (ImageButton) findViewById(R.id.choiceA);
        choiceB = (ImageButton) findViewById(R.id.choiceB);
        choiceC = (ImageButton) findViewById(R.id.choiceC);
        choiceD = (ImageButton) findViewById(R.id.choiceD);

        choiceA.setVisibility(View.INVISIBLE);
        choiceB.setVisibility(View.INVISIBLE);
        choiceC.setVisibility(View.INVISIBLE);
        choiceD.setVisibility(View.INVISIBLE);

        questions = new ArrayList<riddleQuestions>();

        colorText = new ArrayList<>();
        colorText.add("YELLOW");
        colorText.add("BLUE");
        colorText.add("RED");
        colorText.add("ORANGE");

        time = (TextView) findViewById(R.id.time_textView);
        score = (TextView) findViewById(R.id.score_textView);
        colorTextView = (TextView) findViewById(R.id.color_tv);
        question = (TextView) findViewById(R.id.question_tv);

        question.setVisibility(View.INVISIBLE);

        gameScore = getIntent().getIntExtra("GAME_SCORE", 0);
        score.setText(gameScore + "");

        initial = getIntent().getIntExtra("FIRST", 0);
        if (initial == 1) {
            start(61);
        }

        else {
            start(getIntent().getIntExtra("GAME_TIME", 0));
        }
    }

    private void setText() {
        colorAnswer = generateRandom(3);
        colorTextView.setText(colorText.get(colorAnswer));

        pickTextColor = r.nextInt(3 - 0 + 1) + 0;

        if (pickTextColor == 0) {
            int c = ContextCompat.getColor(this, R.color.yellow);
            colorTextView.setTextColor(c);
        }

        else if (pickTextColor == 1) {
            int c = ContextCompat.getColor(this, R.color.red);
            colorTextView.setTextColor(c);
        }

        else if (pickTextColor == 2) {
            int c = ContextCompat.getColor(this, R.color.orange);
            colorTextView.setTextColor(c);
        }

        else if (pickTextColor == 3) {
            int c = ContextCompat.getColor(this, R.color.blue);
            colorTextView.setTextColor(c);
        }
    }

    public int generateRandom(int bound){
        int number;

        do{
            number = r.nextInt(bound);
        }while(number < 0 && number >= bound);

        return number;
    }

    public void setQuestionText() {
        colorTextView.setVisibility(View.INVISIBLE);
        question.setVisibility(View.VISIBLE);
        choiceA.setVisibility(View.VISIBLE);
        choiceB.setVisibility(View.VISIBLE);
        choiceC.setVisibility(View.VISIBLE);
        choiceD.setVisibility(View.VISIBLE);

        index = r.nextInt(questions.size());

        question.setText(questions.get(index).getQuestions());
        System.out.println("ANSWER: " + pickTextColor);
        // 0: YELLOW, 1: BLUE, 2: RED, 3: ORANGE

        // A: ORANGE, B: BLUE, C: RED, D: YELLOW
        if (index == 0) {
            questions.get(index).setAns(colorAnswer);

            if (colorAnswer == 0) {

                choiceC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore--;
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
                        gameScore++;
                        success();
                    }
                });
            }

            else if (colorAnswer == 1) {

                choiceC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                        gameScore--;
                        success();
                    }
                });

                choiceB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore++;
                        success();
                    }
                });
            }

            else if (colorAnswer == 2) {

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
                        gameScore--;
                        success();
                    }
                });

                choiceC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore++;
                        success();
                    }
                });
            }

            else if (colorAnswer == 3) {

                choiceC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore--;
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

                choiceD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore--;
                        success();
                    }
                });

                choiceA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore++;
                        success();
                    }
                });
            }
        }

        else if (index == 1) {
            questions.get(index).setAns(pickTextColor);

            if (pickTextColor == 0) {
                choiceC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore--;
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
                        gameScore++;
                        success();
                    }
                });
            }

            else if (pickTextColor == 1) {

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
                        gameScore--;
                        success();
                    }
                });

                choiceC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore++;
                        success();
                    }
                });
            }

            else if (pickTextColor == 2) {
                choiceC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore--;
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

                choiceD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore--;
                        success();
                    }
                });

                choiceA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore++;
                        success();
                    }
                });
            }

            else if (pickTextColor == 3) {
                choiceC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                        gameScore--;
                        success();
                    }
                });

                choiceB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore++;
                        success();
                    }
                });
            }
        }
    }

    public void start(final int x) {
        //time.setText("1:00");

        countDownTimer = new CountDownTimer(x * 1000, 1000) {
            @Override
            public void onTick(long milliUntilFinished) {
                currTime = (int) milliUntilFinished / 1000;
                if (currTime == 60) {
                    currTime = 0;
                }

                time.setText("" + currTime);

                if (currTime == x - 3) {
                    setQuestionText();
                }
            }

            @Override
            public void onFinish() {

                i = new Intent(context, EndActivity.class);
                i.putExtra("SCORE", gameScore);
                i.putExtra("ACTIVITY", "RIDDLE");

                setResult(Activity.RESULT_CANCELED, getIntent());

                startActivityForResult(i, 0);

                countDownTimer.cancel();
                finish();
                onDestroy();
            }
        };

        countDownTimer.start();
    }

    private void createQuestions() {
        q1 = new riddleQuestions("What color did the word say?");
        q2 = new riddleQuestions("What color was the word?");

        questions.add(q1);
        questions.add(q2);
    }

    private void success() {
        //startActivity(getIntent());

        Random r = new Random();
        //i = new Intent(getBaseContext(), EndActivity.class);

        //gameScore++;
        int nextAct = r.nextInt(5);

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
