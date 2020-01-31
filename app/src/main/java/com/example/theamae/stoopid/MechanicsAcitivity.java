package com.example.theamae.stoopid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MechanicsAcitivity extends AppCompatActivity {

    private ImageButton back;
    private BackgroundSoundService bss;
    private Intent svc, i;
    private int ctr;
    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics_acitivity);

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

                i = new Intent(MechanicsAcitivity.this, MainMenuActivity.class);

                setResult(Activity.RESULT_CANCELED, getIntent());
                finish();

                startActivityForResult(i, 0);
            }
        });
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
