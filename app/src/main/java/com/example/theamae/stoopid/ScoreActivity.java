package com.example.theamae.stoopid;

import android.app.Activity;
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

import com.example.theamae.stoopid.R;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    private ImageButton back;
    private TextView scoreOne, scoreTwo, scoreThree;
    private int firstScore, secScore, thirdScore;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private EndActivity endActivity;

    private BackgroundSoundService bss;
    private Intent svc, i;
    private int ctr;
    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = new Intent(this, BackgroundSoundService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        Intent intent1 = new Intent();
        ctr = intent1.getIntExtra("ctr", 0);
        if (ctr == 1) {
            System.out.println("ctr is 1");
            bss.editVolume(0);
            ctr = 2;
        }

        back = (ImageButton) findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                i = new Intent(ScoreActivity.this, MainMenuActivity.class);
                setResult(Activity.RESULT_CANCELED, new Intent(i));
                finish();
            }
        });

        scoreOne = (TextView) findViewById(R.id.scoreOne);
        scoreTwo = (TextView) findViewById(R.id.scoreTwo);
        scoreThree = (TextView) findViewById(R.id.scoreThree);

        setupScore();
    }



    private void setupScore() {
        SharedPreferences mSharedPreference = getSharedPreferences("SCORE_STORAGE", Context.MODE_PRIVATE);
        firstScore = mSharedPreference.getInt("FIRST", 0);
        secScore = mSharedPreference.getInt("SECOND", 0);
        thirdScore = mSharedPreference.getInt("THIRD", 0);

        scoreOne.setText(firstScore + "");
        scoreTwo.setText(secScore + "");
        scoreThree.setText(thirdScore + "");
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
}
