package com.example.will.test.gui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.will.test.main.GameView;

public class PauseButton extends RectButton {

    public PauseButton(GameView gameView, int left, int top, int right, int bottom) {
        super(gameView, left, top, right, bottom);
    }

    public void render(Canvas canvas, Paint paint, double offsetX, double offsetY) {
        super.render(canvas, paint, offsetX, offsetY);
        canvas.drawRect((float) (verts[0][0]+22+offsetX), (float) (verts[0][1]+15+offsetY), (float) (((verts[0][0]+verts[1][0])/2)-8+offsetX), (float) (verts[1][1]-15+offsetY), paint);
        canvas.drawRect((float) (((verts[0][0]+verts[1][0])/2)+8+offsetX), (float) (verts[0][1]+15+offsetY), (float) (verts[1][0]-22+offsetX), (float) (verts[1][1]-15+offsetY), paint);
    }
}
