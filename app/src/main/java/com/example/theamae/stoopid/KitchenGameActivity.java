 package com.example.theamae.stoopid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

 public class KitchenGameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

     private int screenWidth, screenHeight;
     private ImageButton home;
     private TextView score, time, best;
     private float ballrighty, ballrightx;
     private Handler handler=new Handler();
     private Timer timer;
     private ImageButton ball;
     private ImageButton ballr,bally,ballo,ballb, down, up, left, right;
     private ImageButton imageButton5,imageButton6,imageButton7,imageButton4;
     private ImageButton ballcy,ballco,ballcb,ballcr;
     private int ballint, gameScore, initial, currTime;
     private CountDownTimer countDownTimer;
     private GestureDetectorCompat gestureDetectorCompat;
     private static final int SWIPE_THRESHOLD = 100;
     private static final int SWIPE_VELOCITY_THRESHOLD = 100;
     private ArrayList<Integer> speedList;
     private Random ran = new Random();
     private Vibrator vibe;

     private BackgroundSoundService bss;
     private boolean isBound = false;
     private Intent i;
     private int ctr, firstScore;
     private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_game);

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

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        gestureDetectorCompat = new GestureDetectorCompat(this, this);
        gestureDetectorCompat.setOnDoubleTapListener(this);

        setUp();

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        int r = (int) (0 + Math.random() * 3 + 0);
        setupball();
        Point size = new Point();

        ballr = (ImageButton) findViewById(R.id.ballr);
        bally = (ImageButton) findViewById(R.id.bally);
        ballo = (ImageButton) findViewById(R.id.ballo);
        ballb = (ImageButton) findViewById(R.id.ballb);

        down = (ImageButton) findViewById(R.id.down);
        up = (ImageButton) findViewById(R.id.up);
        left = (ImageButton) findViewById(R.id.left);
        right = (ImageButton) findViewById(R.id.right);

        ballcy = (ImageButton) findViewById(R.id.imageButton5);
        ballco = (ImageButton) findViewById(R.id.imageButton6);
        ballcr = (ImageButton) findViewById(R.id.imageButton7);
        ballcb = (ImageButton) findViewById(R.id.imageButton4);

        //EditText mEdit = (EditText) findViewById(R.id.edittext); mEdit.setEnabled(false);
        ballr.setVisibility(View.INVISIBLE);
        ballo.setVisibility(View.INVISIBLE);
        ballb.setVisibility(View.INVISIBLE);
        bally.setVisibility(View.INVISIBLE);

        down.setVisibility(View.INVISIBLE);
        up.setVisibility(View.INVISIBLE);
        left.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);

        ballcy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ball.getX() >= 400 && ball.getX() <= 700 && ballint == 0) {
                    gameScore++;
                    score.setText(gameScore + "");
                    //Toast.makeText(KitchenGameActivity.this, "YELLOW ", Toast.LENGTH_LONG).show();
                } else {

                    //Toast.makeText(KitchenGameActivity.this, "WRONG ", Toast.LENGTH_LONG).show();
                    gameScore--;
                    score.setText(gameScore + "");
                    vibe.vibrate(400);
                }
            }
        });

        ballco.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ball.getX() >= 400 && ball.getX() <= 700 && ballint == 2) {
                    gameScore++;
                    score.setText(gameScore + "");
                    //Toast.makeText(KitchenGameActivity.this, "ORANGE ", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(KitchenGameActivity.this, "WRONG ", Toast.LENGTH_LONG).show();
                    gameScore--;
                    score.setText(gameScore + "");
                    vibe.vibrate(400);
                }
            }
        });

        ballcr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ball.getX() >= 400 && ball.getX() <= 700 && ballint == 1) {
                    gameScore++;
                    score.setText(gameScore + "");
                    //Toast.makeText(KitchenGameActivity.this, "RED ", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(KitchenGameActivity.this, "WRONG ", Toast.LENGTH_LONG).show();
                    gameScore--;
                    score.setText(gameScore + "");
                    vibe.vibrate(400);
                }
            }
        });

        ballcb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ball.getX() >= 400 && ball.getX() <= 700 && ballint == 3) {
                    gameScore++;
                    score.setText(gameScore + "");
                    //Toast.makeText(KitchenGameActivity.this, "BLUE ", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(KitchenGameActivity.this, "WRONG ", Toast.LENGTH_LONG).show();
                    gameScore--;
                    score.setText(gameScore + "");
                    vibe.vibrate(400);
                }
            }
        });

        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changepos();
                    }
                });
            }
        }, 0, 20);

        start();
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

     @Override
     public boolean onDown(MotionEvent motionEvent) {
         return true;
     }

     @Override
     public void onShowPress(MotionEvent motionEvent) {

     }

     @Override
     public boolean onSingleTapUp(MotionEvent motionEvent) {
         return true;
     }

     @Override
     public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
         return true;
     }

     @Override
     public void onLongPress(MotionEvent motionEvent){

     }

     @Override
     public boolean onFling(MotionEvent e2, MotionEvent e1, float velocityX, float velocityY) {
         boolean result = false;
         try {
             float diffY = e2.getY() - e1.getY();
             float diffX = e2.getX() - e1.getX();
             if (Math.abs(diffX) > Math.abs(diffY)) {
                 if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                     if (diffX > 0) {
                         onSwipeRight();
                     } else {
                         onSwipeLeft();
                     }
                     result = true;
                 }
             }
             else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                 if (diffY > 0) {
                     onSwipeBottom();
                 } else {
                     onSwipeTop();
                 }
                 result = true;
             }
         } catch (Exception exception) {
             exception.printStackTrace();
         }
         return result;
     }

     @Override
     public boolean onTouchEvent(MotionEvent event) {
         this.gestureDetectorCompat.onTouchEvent(event);
         return super.onTouchEvent(event);
     }

     public void onSwipeRight() {
         // LEFT

         if (ball.getX() >= 400 && ball.getX() <= 700 && ballint == 6) {

             gameScore++;
             score.setText(gameScore + "");
             //Toast.makeText(KitchenGameActivity.this, "LEFT ", Toast.LENGTH_LONG).show();
         } else {
             //Toast.makeText(KitchenGameActivity.this, "WRONG ", Toast.LENGTH_LONG).show();
             gameScore--;
             score.setText(gameScore + "");
             vibe.vibrate(400);
         }
     }

     public void onSwipeLeft() {
         // RIGHT

         if (ball.getX() >= 400 && ball.getX() <= 700 && ballint == 7) {
             gameScore++;
             score.setText(gameScore + "");
             //Toast.makeText(KitchenGameActivity.this, "RIGHT ", Toast.LENGTH_LONG).show();
         } else {
             //Toast.makeText(KitchenGameActivity.this, "WRONG ", Toast.LENGTH_LONG).show();
             gameScore--;
             score.setText(gameScore + "");
             vibe.vibrate(400);
         }
     }

     public void onSwipeTop() {
         // DOWN

         if (ball.getX() >= 400 && ball.getX() <= 700 && ballint == 5) {
             gameScore++;
             score.setText(gameScore + "");
             //Toast.makeText(KitchenGameActivity.this, "DOWN ", Toast.LENGTH_LONG).show();
         } else {
             //Toast.makeText(KitchenGameActivity.this, "WRONG ", Toast.LENGTH_LONG).show();
             gameScore--;
             score.setText(gameScore + "");
             vibe.vibrate(400);
         }
     }

     public void onSwipeBottom() {
         // UP

         if (ball.getX() >= 400 && ball.getX() <= 700 && ballint == 4) {
             gameScore++;
             score.setText(gameScore + "");
             //Toast.makeText(KitchenGameActivity.this, "UP ", Toast.LENGTH_LONG).show();
         } else {
             //Toast.makeText(KitchenGameActivity.this, "WRONG ", Toast.LENGTH_LONG).show();
             gameScore--;
             score.setText(gameScore + "");
             vibe.vibrate(400);
         }
     }

     public void start() {
         //time.setText("1:00");

         countDownTimer = new CountDownTimer(61 * 1000, 1000) {
             @Override
             public void onTick(long milliUntilFinished) {
                 currTime = (int) milliUntilFinished / 1000;
                 time.setText("" + milliUntilFinished / 1000);
             }

             @Override
             public void onFinish() {
                 Intent i = new Intent(context, EndActivity.class);
                 i.putExtra("SCORE", gameScore);

                 setResult(Activity.RESULT_CANCELED, getIntent());
                 finish();

                 startActivityForResult(i, 0);
             }
         };

         countDownTimer.start();
     }

     @Override
     public void onDestroy()
     {
         super.onDestroy();
         getApplicationContext().unbindService(serviceConnection);
     }

    private void setUp() {
        time = (TextView) findViewById(R.id.time_textView);
        score = (TextView) findViewById(R.id.score_textView);
        home = (ImageButton) findViewById(R.id.home_btn);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setResult(Activity.RESULT_CANCELED, getIntent());
                finish();

                i = new Intent(KitchenGameActivity.this, MainMenuActivity.class);
                startActivityForResult(i, 0);
            }
        });

        speedList = new ArrayList<>();
        speedList.add(10);
        speedList.add(20);
        speedList.add(30);
    }

     public int generateRandom(int bound){
         int number;

         do{
             number = ran.nextInt(bound);
         }while(number < 0 && number >= bound);

         return number;
     }

    public void changepos()
    {
        System.out.println("width "+screenWidth);
        System.out.println("x "+ball.getX());

        int x = generateRandom(2);
        int s = speedList.get(x);
        System.out.println("CHANGE SPEED: " + s);
        ballrightx+=s;

        if(ball.getX()>screenWidth)
        {
            ballrightx=-100.0f;
        }
        else if(ball.getX()==screenWidth)
        {
            setupball();
            System.out.println("SETUP BALL");
        }

        System.out.println("new x: " + ballrightx);

        ball.setX(ballrightx);

      //  ball.setY(ballrighty);
    }
    public void setupball()
    {

        int r = generateRandom(7);
        //int r=(int)(0+Math.random()*7+0);
        System.out.println("RANDOM: " + r);
        //int r = 7;
        if(r==0)
        {
            ball=(ImageButton)findViewById(R.id.bally);

        }

        else if(r==1)
        {
            ball=(ImageButton)findViewById(R.id.ballr);

        }

        else if(r==2)
        {
            ball=(ImageButton)findViewById(R.id.ballo);
        }

        else if(r==3)
        {
            ball=(ImageButton)findViewById(R.id.ballb);
        }

        else if(r==4)
        {
            ball=(ImageButton)findViewById(R.id.up);
        }

        else if(r==5)
        {
            ball=(ImageButton)findViewById(R.id.down);
        }

        else if(r==6)
        {
            ball=(ImageButton)findViewById(R.id.left);
        }

        else if(r==7)
        {
            ball=(ImageButton)findViewById(R.id.right);
        }

        ball.setVisibility(View.VISIBLE);
        ball.bringToFront();
        ballint=r;
    }

     @Override
     public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
         return false;
     }

     @Override
     public boolean onDoubleTap(MotionEvent motionEvent) {
         return false;
     }

     @Override
     public boolean onDoubleTapEvent(MotionEvent motionEvent) {
         return false;
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
