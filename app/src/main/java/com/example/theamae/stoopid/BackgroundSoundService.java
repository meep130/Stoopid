package com.example.theamae.stoopid;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import java.sql.SQLOutput;

public class BackgroundSoundService extends Service {

    private static final String TAG = null;
    private final IBinder thisBinder = new LocalBinder();
    public MediaPlayer player;


    @Override
    public void onCreate() {
        //super.onCreate();

        player = MediaPlayer.create(this, R.raw.bgsong);
        player.setLooping(true); // Set looping
        player.setVolume(100, 100);
        Log.e("Bss", "Music Player Started");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        player.start();

        return START_NOT_STICKY;
    }

    public void onStart(Intent intent, int startId) {
        // TODO
    }

    public class LocalBinder extends Binder {
        BackgroundSoundService getService() {
            return BackgroundSoundService.this;
        }
    }

    public IBinder onBind(Intent i) {
        return thisBinder;
    }

    public IBinder onUnBind(Intent arg0) {
        // TODO Auto-generated method stub

        return null;
    }

    public void editVolume(int vol) {
        if(vol == 0) {
            System.out.println("VOLUME 100");
            player.reset();
            player = MediaPlayer.create(this, R.raw.bgsong);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            player.start();
        }

        else if(vol == 1) {
            System.out.println("VOLUME 0");
            player.setVolume(0,0);
        }
    }

    public void onStop() {

    }

    public void onPause() {

    }

    @Override
    public void onDestroy() {

        player.stop();
        player.release();


    }

    public void unbind() {

    }

    @Override
    public void onLowMemory() {

    }
}
