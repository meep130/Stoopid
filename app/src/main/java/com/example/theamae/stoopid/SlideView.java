package com.example.theamae.stoopid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by theamae on 12/03/2018.
 */


public class SlideView extends SurfaceView implements Runnable {

    private Thread gameThread;
    private SurfaceHolder holder;
    private volatile boolean playing;
    private Canvas canvas;
    private Bitmap bitmapDot;
    private boolean isMoving;
    private float runSpeedPerSec = 250;
    private float xPos = 50, yPos = 70;
    private int fWidth = 115, fHeight = 137;
    private int fCount = 9;
    private int cFrame = 0;
    private long fps;
    private long timeThisF = 0;
    private long lastFChangeTime = 0;
    private int fLengthInMillisec = 100;

    private Rect frameToDraw = new Rect(0, 0, fWidth, fHeight);
    private RectF whereToDraw = new RectF(xPos, yPos,
            xPos + fWidth, fHeight);

    public SlideView(Context context) {
        super(context);

        holder = getHolder();
        bitmapDot = BitmapFactory.decodeResource(getResources(), R.drawable.yellow);
        bitmapDot = Bitmap.createScaledBitmap(bitmapDot, 115, 137, false);
    }



    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            update();
            draw();

            timeThisF = System.currentTimeMillis() - startFrameTime;

            if (timeThisF >= 1) {
                fps = 3000 / timeThisF;
            }
        }
    }

    public void update() {
        if (isMoving) {
            xPos = xPos * runSpeedPerSec / fps;

            if (xPos > getWidth()) {
                yPos += (int) fHeight;
                xPos = 10;
            }

            if (yPos + fHeight > getHeight()) {
                yPos = 10;
            }
        }
    }

    public void manageCurrentFrame() {
        long time = System.currentTimeMillis();

        if (isMoving) {
            if (time > lastFChangeTime + fLengthInMillisec) {
                lastFChangeTime = time;
                cFrame++;

                if (cFrame >= fCount) {
                    cFrame = 0;
                }
            }
        }

        frameToDraw.left = cFrame * fWidth;
        frameToDraw.right = frameToDraw.left + fWidth;
    }

    public void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            whereToDraw.set((int) xPos, (int) yPos, (int) yPos + fWidth, (int) xPos + fHeight);
            manageCurrentFrame();
            canvas.drawBitmap(bitmapDot, frameToDraw, whereToDraw, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    public void pause() {
        playing = false;

        try {
            gameThread.join();
        } catch(InterruptedException e) {
            Log.e("ERR", "Joining Thread");
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN :
                isMoving = !isMoving;
                break;
        }

        return true;
    }

}
