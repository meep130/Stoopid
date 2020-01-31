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

import org.w3c.dom.Text;

import java.util.Random;

import static android.view.View.GONE;

public class ButtonActivity extends AppCompatActivity {

    private TextView question, ctr_tv, best;
    private ImageButton button, home;
    private int ctr, initial, ctr2, visibility, gameScore, currTime;
    private Random r;
    private Intent i;
    private CountDownTimer countDownTimer;
    private TextView time, score;

    private BackgroundSoundService bss;
    private boolean isBound = false;
    private int ctr3, firstScore;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

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

        score = (TextView) findViewById(R.id.score_textView);
        time = (TextView) findViewById(R.id.time_textView);
        best = (TextView) findViewById(R.id.best_textView);

        setupScore();

        home = (ImageButton) findViewById(R.id.home_btn);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                i = new Intent(ButtonActivity.this, MainMenuActivity.class);

                setResult(Activity.RESULT_CANCELED, getIntent());

                startActivityForResult(i, 0);

                countDownTimer.cancel();
                finish();
            }
        });

        gameScore = getIntent().getIntExtra("GAME_SCORE", 0);
        score.setText(gameScore + "");

        initializeComponents();

        initial = getIntent().getIntExtra("FIRST", 0);
        if (initial == 1) {
            start(61);
        }

        else {
            start(getIntent().getIntExtra("GAME_TIME", 0));
        }

        play(ctr);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getApplicationContext().unbindService(serviceConnection);
    }

    public void start(int x) {
        //time.setText("1:00");


        System.out.println("X = " + x);
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
                i.putExtra("ACTIVITY", "BUTTON");

                setResult(Activity.RESULT_CANCELED, getIntent());

                startActivityForResult(i, 0);

                countDownTimer.cancel();
                finish();
            }
        };

        countDownTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (i == null) {
            System.out.println("INTENT IS NULL");
            ctr3 = 1;
            bss.editVolume(1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ctr3 == 1) {
            bss.editVolume(0);
            ctr3 = 2;
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

    private void initializeComponents(){
        question = (TextView) findViewById(R.id.color_tv);
        ctr_tv = (TextView) findViewById(R.id.ctr_tv);
        button = (ImageButton) findViewById(R.id.button);

        r = new Random();
        ctr = r.nextInt((30-15) + 1) + 15;
        ctr2 = 0;
        visibility = r.nextInt((15-10) + 1) + 15;
        System.out.println("Visibility: " + visibility);

    }

    private void play(final int ctr){
        ctr_tv.setText(Integer.toString(ctr) + " TIMES");

        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                ctr2++;
                ctr_tv.setText(Integer.toString(ctr - ctr2) + " TIMES");

                if(ctr2 == visibility){
                    ctr_tv.setVisibility(GONE);
                }

                if(ctr2 == ctr){
                    //startActivity(getIntent());

                    Random r = new Random();
                    int nextAct = r.nextInt(5);
                    //int nextAct = 1;

                    if(nextAct == 0) {
                        gameScore++;
                        i = new Intent(getBaseContext(), GesturesActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 1) {
                        gameScore++;
                        i = new Intent(getBaseContext(), MathActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else if(nextAct == 2){
                        gameScore++;
                        i = new Intent(getBaseContext(), ShapeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 3){
                        gameScore++;
                        i = new Intent(getBaseContext(), SizeActivity.class);
                        i.putExtra("GAME_SCORE", gameScore);
                        i.putExtra("GAME_TIME", currTime);
                        i.putExtra("FIRST", 0);
                    }

                    else  if(nextAct == 4){
                        gameScore++;
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
    }

    private void setupScore() {
        SharedPreferences mSharedPreference = getSharedPreferences("SCORE_STORAGE", Context.MODE_PRIVATE);
        firstScore = mSharedPreference.getInt("HIGHEST", 0);

        best.setText(firstScore + "");
    }
}
