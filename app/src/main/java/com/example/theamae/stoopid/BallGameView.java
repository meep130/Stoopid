package com.example.theamae.stoopid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by theamae on 11/03/2018.
 */

public class BallGameView extends View {

    private int darkGrey = ContextCompat.getColor(this.getContext(), R.color.darkGrey);

    public BallGameView(Context context) {
        super(context);
    }

    public BallGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallGameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BallGameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(@Nullable AttributeSet set) {

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect();

        rect.left = 100;
        rect.top = 100;
        rect.right = rect.left + 10;
        rect.bottom = rect.top + 10;

        rect.set(130, 130, 650, 650);

        Paint paint = new Paint();
        paint.setColor(darkGrey);
        canvas.drawRect(rect, paint);
    }
}
