package com.example.will.test.gui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.will.test.main.GameView;

public class MovementControl extends CircleButton {

    public MovementControl(GameView gameView, int x, int y, int r) {
        super(gameView, x, y, r);
    }

    public void render(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(0xFFFFFFFF);
        canvas.drawCircle((float) pos[0], (float) pos[1], (float) radius+2, paint);
        paint.setColor(0x64000000);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle((float) pos[0], (float) pos[1], (float) radius, paint);
    }

}
