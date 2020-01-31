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

import java.util.ArrayList;
import java.util.Random;

public class BallGameActivity extends AppCompatActivity {

    private ImageButton home, settings;
    private int setColor;
    private String resColorRan;

    private ArrayList<String> colorDotText;
    private ArrayList<String> textColors;
    private Random r = new Random();
    private int stage, colorAnswer, gameScore, firstScore;
    private int[] otherColors;
    private CountDownTimer countDownTimer;

    private TextView statement_textView, best;

    private static final int[] firstSet = {R.id.yellowdot12, R.id.yellowdot13, R.id.yellowdot14, R.id.yellowdot17, R.id.yellowdot18,
            R.id.yellowdot19}; // 6 - 11

    private ImageButton[] firstBtns = new ImageButton[firstSet.length];


    private static final int[] secSet = {R.id.yellowdot7, R.id.yellowdot8, R.id.yellowdot9, R.id.yellowdot12, R.id.yellowdot13,
            R.id.yellowdot14, R.id.yellowdot17, R.id.yellowdot18, R.id.yellowdot19, R.id.yellowdot22, R.id.yellowdot23, R.id.yellowdot24,};

    private ImageButton[] secBtns = new ImageButton[secSet.length];

    private static final int[] thirdSet = {R.id.yellowdot1, R.id.yellowdot2, R.id.yellowdot3, R.id.yellowdot4, R.id.yellowdot5, R.id.yellowdot6,
            R.id.yellowdot7, R.id.yellowdot8, R.id.yellowdot9, R.id.yellowdot10, R.id.yellowdot11, R.id.yellowdot12, R.id.yellowdot13,
            R.id.yellowdot14, R.id.yellowdot15, R.id.yellowdot16, R.id.yellowdot17, R.id.yellowdot18, R.id.yellowdot19, R.id.yellowdot20,
            R.id.yellowdot21, R.id.yellowdot22, R.id.yellowdot23, R.id.yellowdot24, R.id.yellowdot25, R.id.yellowdot26, R.id.yellowdot27,
            R.id.yellowdot28, R.id.yellowdot29, R.id.yellowdot30};

    private ImageButton[] thirdBtns = new ImageButton[thirdSet.length];
    private TextView score, time;

    private BackgroundSoundService bss;
    private boolean isBound = false;
    private Intent i;
    private int ctr;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_game);

        Intent intent = new Intent(this, BackgroundSoundService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        context = this;

        Intent intent1 = new Intent();
        ctr = intent1.getIntExtra("ctr", 0);
        if (ctr == 1) {
            System.out.println("ctr is 1");
            bss.editVolume(0);
            ctr = 2;
        }

        statement_textView = (TextView) findViewById(R.id.statement_textView);
        time = (TextView) findViewById(R.id.time_textView);
        score = (TextView) findViewById(R.id.score_textView);
        best = (TextView) findViewById(R.id.best_textView);

        setupScore();

        initializeComponents();

        setText();
        setupBtns();
        setupDots();

        System.out.println("STARTUP");

        setColor = r.nextInt(3);
        stage = 0;
        System.out.println("Stage: " + stage);
        setPos();

        start();
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

    private void setText() {
        colorAnswer = generateRandom(3);
        statement_textView.setText(colorDotText.get(colorAnswer));

        int pickTextColor = r.nextInt(3);

        if (pickTextColor == 0) {
            int c = ContextCompat.getColor(this, R.color.yellow);
            statement_textView.setTextColor(c);
        } else if (pickTextColor == 1) {
            int c = ContextCompat.getColor(this, R.color.red);
            statement_textView.setTextColor(c);
        } else if (pickTextColor == 2) {
            int c = ContextCompat.getColor(this, R.color.orange);
            statement_textView.setTextColor(c);
        }


    }

    private void setupBtns() {

        home = (ImageButton) findViewById(R.id.home_btn);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setResult(Activity.RESULT_CANCELED, getIntent());
                finish();

                i = new Intent(BallGameActivity.this, MainMenuActivity.class);
                startActivityForResult(i, 0);
            }
        });

        System.out.println("SETUP BUTTONS");
    }

    private void setupDots() {
        for (int x = 0; x < firstSet.length; x++) {
            firstBtns[x] = (ImageButton) findViewById(firstSet[x]);
            firstBtns[x].setVisibility(View.INVISIBLE);
        }

        for (int x = 0; x < secSet.length; x++) {
            secBtns[x] = (ImageButton) findViewById(secSet[x]);
            secBtns[x].setVisibility(View.INVISIBLE);
        }

        for (int x = 0; x < thirdSet.length; x++) {
            thirdBtns[x] = (ImageButton) findViewById(thirdSet[x]);
            thirdBtns[x].setVisibility(View.INVISIBLE);
        }

        System.out.println("SETUP DOTS");
    }

    private void setPos() {
        System.out.println("SET POS");
        int image = 0; //Inital value of image
        int answerImage = 0;
        int answer = 0; //Random position for yellow
        int red = 0, orange = 0;
        boolean dotAnswer = false; //To know if answerdot has been printed
        ImageButton[] stagebtns = new ImageButton[0];
        String checker = "Wala"; //For debugging purposes
        otherColors = new int[2];

        //stage++;

        System.out.println("Stage: " + stage);

        stage = r.nextInt(3);
        System.out.println("STAGE: " + stage);

        if(stage == 0){
            stagebtns = firstBtns;
        }

        else if (stage == 1){
            stagebtns = secBtns;
        }

        else if (stage == 2){
            stagebtns = thirdBtns;
        }

        else{
            stagebtns = firstBtns;
        }

        if (dotAnswer == false){
            System.out.println("PASOK IF YELLOW == FALSE");
            System.out.println("DOT ANSWER: " + dotAnswer);

            answer = generateRandom(stagebtns.length);

            if(colorAnswer == 0){
                System.out.println("Color Answer: " + colorDotText.get(colorAnswer));
                answerImage = R.drawable.yellowbtn;
                otherColors[0] = R.drawable.orangebtn;
                otherColors[1] = R.drawable.redbtn;
            }
            else if(colorAnswer == 1){
                System.out.println("Color Answer: " + colorDotText.get(colorAnswer));
                answerImage = R.drawable.orangebtn;
                otherColors[0] = R.drawable.yellowbtn;
                otherColors[1] = R.drawable.redbtn;
            }
            else if(colorAnswer == 2){
                System.out.println("Color Answer: " + colorDotText.get(colorAnswer));
                answerImage = R.drawable.redbtn;
                otherColors[0] = R.drawable.orangebtn;
                otherColors[1] = R.drawable.yellowbtn;
            }

            //checker = "YELLOW";

            stagebtns[answer].setBackgroundResource(answerImage);
            stagebtns[answer].setVisibility(View.VISIBLE);

            dotAnswer = true;
        }
        for(int i = 0; i < stagebtns.length; i++){

            int color = generateRandom(2);

            System.out.println("STAGEBTNS: " + stagebtns.length);
            System.out.println("BALL " + i);

            System.out.println("COLOR: " + color);

            if(dotAnswer == true && i != answer) { //Checks if yellow has been printed and position is not equals to yellow position

                System.out.println("PASOK SA IF DOTANSWER = TRUE && " + i + " != " + answer);
                if (color == 0) {
                    image = otherColors[0];
                    red++;
                    System.out.println("COLOR:" + image);
                } else if (color == 1) {
                    image = otherColors[1];
                    orange++;
                    System.out.println("COLOR: 1" + image);
                }

                else{
                    if(red >= orange){
                        image = otherColors[1];
                    }
                    else{
                        image = otherColors[0];
                    }
                }

                stagebtns[i].setBackgroundResource(image);
                stagebtns[i].setVisibility(View.VISIBLE);
            }

            if(i == answer){
                stagebtns[answer].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore++;
                        callMethods();
                    }
                });
            }

            else if (i != answer){
                stagebtns[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameScore--;
                        callMethods();
                    }
                });
            }
        }


        System.out.println("OTHERCOLORS SIZE: " + otherColors.length);

        for(int i = 0; i < otherColors.length; i++){
            System.out.println(otherColors[i]);
        }
    }

    public void initializeComponents(){
        colorDotText = new ArrayList<>();
        colorDotText.add("YELLOW DOT");
        colorDotText.add("ORANGE DOT");
        colorDotText.add("RED DOT");

        textColors = new ArrayList<>();
        textColors.add("yellow");
        textColors.add("red");
        textColors.add("orange");

        System.out.println("INITIALIZED!");

        System.out.println("RED: " + R.drawable.redbtn);
        System.out.println("ORANGE: " + R.drawable.orangebtn);
        System.out.println("YELLOW: " + R.drawable.yellowbtn);
    }

    public int generateRandom(int bound){
        int number;

        do{
            number = r.nextInt(bound);
        }while(number < 0 && number >= bound);

        return number;
    }

    public void callMethods(){
        score.setText(gameScore + "" );

        initializeComponents();

        setText();
        setupBtns();
        setupDots();
        setPos();
    }

    public void start() {
        //time.setText("1:00");

        countDownTimer = new CountDownTimer(61 * 1000, 1000) {
            @Override
            public void onTick(long milliUntilFinished) {
                time.setText("" + milliUntilFinished / 1000);

                if(milliUntilFinished / 1000 == 0) {

                    i = new Intent(getApplicationContext(), EndActivity.class);
                    i.putExtra("SCORE", gameScore);

                    setResult(Activity.RESULT_CANCELED, getIntent());
                    finish();

                    startActivityForResult(i, 0);
                }
            }

            @Override
            public void onFinish() {

                i = new Intent(context, EndActivity.class);
                i.putExtra("SCORE", gameScore);

                setResult(Activity.RESULT_CANCELED, getIntent());
                finish();

                startActivityForResult(i, 0);
            }
        };

        countDownTimer.start();
    }

    private void setupScore() {
        SharedPreferences mSharedPreference = getSharedPreferences("SCORE_STORAGE", Context.MODE_PRIVATE);
        firstScore = mSharedPreference.getInt("HIGHEST", 0);

        best.setText(firstScore + "");
    }
}



