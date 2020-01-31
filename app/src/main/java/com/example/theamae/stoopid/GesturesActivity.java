package com.example.theamae.stoopid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class GesturesActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private int randomq, interacted, gameScore, currTime;
    private ImageButton sleep1, sleep2;
    private TextView tv_sleep1, tv_cry, time, score, best;
    private CountDownTimer countDownTimer;
    private ImageButton cry1, cry2, home;
    private int firsttime=0, initial;
    private Intent i;

    private BackgroundSoundService bss;
    private boolean isBound = false;
    private int ctr, firstScore;
    private Vibrator vibe;
    private Context context;

    private ScoreActivity scoreActivity = new ScoreActivity();

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];

            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if(z<0) {
                if(firsttime==0) {
                    MediaPlayer bark= MediaPlayer.create(GesturesActivity.this, R.raw.bark);
                    bark.start();
                   // interacted=1;
                    firsttime=1;

                    deviceinteracted(randomq);
                    CountDownTimer cd= new CountDownTimer(1500,1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            //startActivityForResult(new Intent(GesturesActivity.this, MainMenuActivity.class), 0);
                            Random r = new Random();
                            int nextAct = r.nextInt(4);
                            gameScore++;
                            vibe.vibrate(400);

                            if(nextAct == 0) {
                                i = new Intent(getBaseContext(), ButtonActivity.class);
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
                                i = new Intent(getBaseContext(), SizeActivity.class);
                                i.putExtra("GAME_SCORE", gameScore);
                                i.putExtra("GAME_TIME", currTime);
                                i.putExtra("FIRST", 0);
                            }

                            Thread timer = new Thread(){
                                public void run(){
                                    try{
                                        sleep(5000);
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
                    }.start();

                }
            }

            else if (mAccel > 12) {
                if(firsttime==0) {
                    MediaPlayer bark= MediaPlayer.create(GesturesActivity.this,R.raw.bark);
                    bark.start();
                   // interacted=0;
                    firsttime=1;
                    deviceinteracted(randomq);
                    CountDownTimer cd= new CountDownTimer(1500,1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            //startActivityForResult(new Intent(GesturesActivity.this, MainMenuActivity.class), 0);

                            gameScore++;
                            vibe.vibrate(400);
                            Random r = new Random();
                            //int nextAct = r.nextInt(5);
                            int nextAct=4;

                            if(nextAct == 0) {
                                i = new Intent(getBaseContext(), ButtonActivity.class);
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
                    }.start();
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

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

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        if (ctr == 1) {
            bss.editVolume(0);
            ctr = 2;
        }
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();

        if (i == null) {
            System.out.println("INTENT IS NULL");
            ctr = 1;
            bss.editVolume(1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures);

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

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        time = (TextView) findViewById(R.id.time_textView);
        score = (TextView) findViewById(R.id.score_textView);
        best = (TextView) findViewById(R.id.best_textView);

        setupScore();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;

        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        sleep1=(ImageButton) findViewById(R.id.sleep1gif);
        sleep2=(ImageButton) findViewById(R.id.sleep2gif);
        tv_sleep1=(TextView) findViewById(R.id.tv_sleep1);
        sleep1.setVisibility(View.INVISIBLE);
        sleep2.setVisibility(View.INVISIBLE);
        tv_sleep1.setVisibility(View.INVISIBLE);

        cry1=(ImageButton) findViewById(R.id.cry1);
        cry2=(ImageButton) findViewById(R.id.cry2);
        cry1.setVisibility(View.INVISIBLE);
        cry2.setVisibility(View.INVISIBLE);


        tv_cry=(TextView) findViewById(R.id.tv_cry);
        tv_cry.setVisibility(View.INVISIBLE);


        home = (ImageButton) findViewById(R.id.home_btn);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                i = new Intent(GesturesActivity.this, MainMenuActivity.class);
                startActivityForResult(i, 0);

                setResult(Activity.RESULT_CANCELED, getIntent());

                startActivityForResult(i, 0);

                countDownTimer.cancel();
                finish();
            }
        });

        Random r = new Random();
       randomq=r.nextInt(2);
       // randomq=0;
        System.out.println(randomq);

        setupgif(randomq);

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

                i = new Intent(context,  EndActivity.class);
                i.putExtra("SCORE", gameScore);
                i.putExtra("ACTIVITY", "GESTURES");

                setResult(Activity.RESULT_CANCELED, getIntent());

                startActivityForResult(i, 0);

                countDownTimer.cancel();
                finish();
                onDestroy();
            }
        };

        countDownTimer.start();
    }

    public void setupgif(int randomq)
    {
        if(randomq==0) {
            sleep1.setVisibility(View.VISIBLE);
            tv_sleep1.setVisibility(View.VISIBLE);
        }

        else if(randomq==1) {
            cry1.setVisibility(View.VISIBLE);
            tv_cry.setVisibility(View.VISIBLE);
        }
    }

    public void deviceinteracted(int randomq)
    {
        if(randomq==1)
        {
            cry1.setVisibility(View.INVISIBLE);
            cry2.setVisibility(View.VISIBLE);
            tv_cry.setVisibility(View.INVISIBLE);
        }

        else if(randomq==0){
            sleep1.setVisibility(View.INVISIBLE);
            sleep2.setVisibility(View.VISIBLE);
            tv_sleep1.setVisibility(View.INVISIBLE);
        }
    }

    private void setupScore() {
        SharedPreferences mSharedPreference = getSharedPreferences("SCORE_STORAGE", Context.MODE_PRIVATE);
        firstScore = mSharedPreference.getInt("HIGHEST", 0);

        best.setText(firstScore + "");
    }
}
