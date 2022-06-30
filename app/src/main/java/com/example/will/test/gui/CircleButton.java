package com.example.will.test.gui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.will.test.main.GameView;

public class CircleButton implements Button {

    GameView gameView;

    public int[] pos = {0, 0};
    int radius;

    int color0 = 0xFFFFFFFF;
    public int color1 = 0xFF000000;

    public CircleButton(GameView gameView, int x, int y, int r) {
        this.gameView = gameView;

        this.pos[0] = x;
        this.pos[1] = y;
        this.radius = r;
    }

    @Override
    public void render(Canvas canvas, Paint paint, double offsetX, double offsetY) {
        paint.setColor(color0);
        canvas.drawCircle((float) (pos[0]+offsetX), (float) (pos[1]+offsetY), radius+2, paint);
        paint.setColor(color1);
        canvas.drawCircle((float) (pos[0]+offsetX), (float) (pos[1]+offsetY), radius, paint);
    }

    @Override
    public boolean checkPressed(double x, double y) {
        if(((x-pos[0])*(x-pos[0]))+((y-pos[1])*(y-pos[1])) <= (radius*radius)) {
            return true;
        }
        return false;
    }
}
