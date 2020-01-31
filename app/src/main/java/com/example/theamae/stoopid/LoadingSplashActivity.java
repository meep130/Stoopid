package com.example.theamae.stoopid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class LoadingSplashActivity extends AppCompatActivity {

    private TextView woofer;
    private ImageView doggo;
    private Intent i;
    public int firstScore, secScore, thirdScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_splash);

        woofer = (TextView) findViewById(R.id.woofer);
        doggo = (ImageView) findViewById(R.id.doggo);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.uptodown);

        woofer.startAnimation(anim);
        doggo.startAnimation(anim);

        Random r = new Random();
        //int nextAct = r.nextInt(8);
        int nextAct = 1;

        if(nextAct == 0){
            i = new Intent(this, ButtonActivity.class);
            i.putExtra("FIRST", 1);
        }

        else if(nextAct == 1){
            i = new Intent(this, GesturesActivity.class);
            i.putExtra("FIRST", 1);
        }

        else if(nextAct == 2){
            i = new Intent(this, MathActivity.class);
            i.putExtra("FIRST", 1);
        }

        else  if(nextAct == 3){
            i = new Intent(this, ShapeActivity.class);
            i.putExtra("FIRST", 1);
        }

        else if(nextAct == 4){
            i = new Intent(this, SizeActivity.class);
            i.putExtra("FIRST", 1);
        }

        else if(nextAct == 5){
            i = new Intent(this, BallGameActivity.class);
            i.putExtra("FIRST", 1);
        }

        else if(nextAct == 6){
            i = new Intent(this, KitchenGameActivity.class);
            i.putExtra("FIRST", 1);
        }

        else if(nextAct == 7){
            i = new Intent(this, RiddleActivity.class);
            i.putExtra("FIRST", 1);
        }

        else if(nextAct == 8){
            i = new Intent(this, EndActivity.class);
            i.putExtra("FIRST", 1);
        }

        System.out.println("nextAct: " + nextAct);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finally{
                    Bundle bundle = new Bundle();

                    startActivity(i);
                    finish();
                }
            }
        };

        timer.start();

        System.out.println("I: " + i);
    }
}
