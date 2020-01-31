package com.example.theamae.stoopid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLOutput;

public class EndActivity extends AppCompatActivity {

    private ImageButton home, settings;
    private int score;
    private TextView scoreTextView, best;

    public SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int firstScore, ctr;

    private BackgroundSoundService bss;
    private Context context;
    private boolean isBound = false;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        this.context = context;

        Intent intent2 = new Intent(this, BackgroundSoundService.class);
        bindService(intent2, serviceConnection, Context.BIND_AUTO_CREATE);

        if (getIntent().getStringExtra("ACTIVITY") != "MAIN") {
            setup();

            score = getIntent().getIntExtra("SCORE", 0);
            System.out.println("ACTIVITY CALLING: " + getIntent().getStringExtra("ACTIVITY"));

            System.out.println("SCORE: " + score);
            scoreTextView.setText(score + "");
            scoreCheck(score);

            best = (TextView) findViewById(R.id.best_textView);

            setupScore();

            Intent intent = new Intent();
        }

        else {

        }
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
        scoreTextView = (TextView) findViewById(R.id.finalScore_textView);

        home = (ImageButton) findViewById(R.id.home_btn);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivityForResult(new Intent(EndActivity.this, MainMenuActivity.class), 0);
                finish();
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = getSharedPreferences("SCORE_STORAGE", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        firstScore = sharedPreferences.getInt("HIGHEST", 0);
        /*
        secScore = sharedPreferences.getInt("SECOND", 0);
        thirdScore = sharedPreferences.getInt("THIRD", 0); */
    }

    private void scoreCheck(int score) {

        if (score >= firstScore) {
            editor.putInt("HIGHEST", score);
            editor.commit();
        }
        /*
        System.out.println("first: " + firstScore);
        System.out.println("sec: " + secScore);
        System.out.println("third: " + thirdScore);

        if (score >= firstScore) {
            editor.putInt("FIRST", score);
            editor.commit();
        }

        else {

            if (score >= secScore ) {
                editor.putInt("SECOND", score);
                editor.commit();

            }

            else {
                if (score >= thirdScore) {
                    editor.putInt("THIRD", score);
                    editor.commit();
                }
            }
        }

        firstScore = sharedPreferences.getInt("FIRST", 0);
        secScore = sharedPreferences.getInt("SECOND", 0);
        thirdScore = sharedPreferences.getInt("THIRD", 0);

        System.out.println("first: " + firstScore);
        System.out.println("sec: " + secScore);
        System.out.println("third: " + thirdScore); */
    }

    private void setupScore() {
        SharedPreferences mSharedPreference = getSharedPreferences("SCORE_STORAGE", Context.MODE_PRIVATE);
        firstScore = mSharedPreference.getInt("HIGHEST", 0);

        best.setText(firstScore + "");
    }
}
