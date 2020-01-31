package com.example.theamae.stoopid;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class StartupSplashActivity extends AppCompatActivity {

    private TextView woofer;
    private ImageView doggo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_splash);

        woofer = (TextView) findViewById(R.id.woofer);
        doggo = (ImageView) findViewById(R.id.doggo);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.startuptransition);

        woofer.startAnimation(anim);
        doggo.startAnimation(anim);

        MediaPlayer bark= MediaPlayer.create(StartupSplashActivity.this,R.raw.firstbark);
        bark.start();

        final Intent i = new Intent(this, MainMenuActivity.class);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finally{
                    startActivity(i);
                    finish();
                }
            }
        };

        timer.start();
    }
}
