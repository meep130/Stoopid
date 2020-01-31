package com.example.theamae.stoopid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainMenuActivity extends AppCompatActivity {

    private ImageButton play, mechanics, on, off;
    private MediaPlayer song;
    private BackgroundSoundService bss;
    private Intent svc, i;
    private TextView score;
    private int ctr, firstScore;
    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = new Intent(this, BackgroundSoundService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        Intent intent1 = new Intent();
        ctr = intent1.getIntExtra("ctr", 0);
        if (ctr == 1) {
            System.out.println("ctr is 1");
            bss.editVolume(0);
            ctr = 2;
        }

        song = new MediaPlayer();
        //song = MediaPlayer.create(this, R.raw.bgsong);
        bss = new BackgroundSoundService();
        svc = new Intent(this, BackgroundSoundService.class);
        startService(svc);

        score = (TextView) findViewById(R.id.score_textView);

        play = (ImageButton) findViewById(R.id.play_btn);

        setupScore();

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                i = new Intent(MainMenuActivity.this, LoadingSplashActivity.class);
                i.putExtra("ACTIVITY", "MAIN");

               // setResult(Activity.RESULT_CANCELED, getIntent());

                startActivityForResult(i, 0);

                //finish();
            }
        });

        mechanics = (ImageButton) findViewById(R.id.question_btn);

        mechanics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                i = new Intent(MainMenuActivity.this, MechanicsAcitivity.class);
                startActivityForResult(i, 0);
            }
        });

        on = (ImageButton) findViewById(R.id.musicOnBtn);

        on.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                on.setVisibility(View.INVISIBLE);
                off.setVisibility(View.VISIBLE);
                bss.editVolume(1);
            }
        });

        off = (ImageButton) findViewById(R.id.musicOffBtn);
        off.setVisibility(View.INVISIBLE);

        off.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                on.setVisibility(View.VISIBLE);
                off.setVisibility(View.INVISIBLE);
                bss.editVolume(0);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        bss.editVolume(0);
        return true;
    }

    @Override
    public void onPause()
    {
        super.onPause();

        if (i == null) {
            ctr = 1;
            bss.editVolume(1);
        }
        //song.pause();
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
    public void onDestroy()
    {
        super.onDestroy();
        stopService(svc);
        bss = null;
        getApplicationContext().unbindService(serviceConnection);
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

        score.setText(firstScore + "");
    }
}
