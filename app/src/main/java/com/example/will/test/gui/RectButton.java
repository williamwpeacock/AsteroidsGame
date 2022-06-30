package com.example.will.test.gui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.will.test.main.GameView;

public class RectButton implements Button {

    GameView gameView;

    int[][] verts = {{0, 0}, {0, 0}};

    int color0 = 0xFFFFFFFF;
    int color1 = 0xFF000000;

    public RectButton(GameView gameView, int left, int top, int right, int bottom) {
        this.gameView = gameView;

        this.verts[0][0] = left;
        this.verts[0][1] = top;
        this.verts[1][0] = right;
        this.verts[1][1] = bottom;
    }

    @Override
    public void render(Canvas canvas, Paint paint, double offsetX, double offsetY) {
        paint.setColor(color0);
        canvas.drawRect((float) (verts[0][0]+offsetX), (float) (verts[0][1]+offsetY), (float) (verts[1][0]+offsetX), (float) (verts[1][1]+offsetY), paint);
        paint.setColor(color1);
        canvas.drawRect((float) (verts[0][0]+2+offsetX), (float) (verts[0][1]+2+offsetY), (float) (verts[1][0]-2+offsetX), (float) (verts[1][1]-2+offsetY), paint);
        paint.setColor(color0);
    }

    @Override
    public boolean checkPressed(double x, double y) {
        if (x > verts[0][0] && x < verts[1][0]) {
            if (y > verts[0][1] && y < verts[1][1]) {
                return true;
            }
        }
        return false;
    }
}
