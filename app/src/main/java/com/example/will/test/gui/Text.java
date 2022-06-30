package com.example.will.test.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.will.test.main.GameView;

public class Text {

    GameView gameView;

    int[][] verts = {{0, 0}, {0, 0}};

    int color = 0xFFFFFFFF;
    int textSize;
    int textPadding = 50;
    String text;

    public Text(GameView gameView, int left, int top, int right, int bottom, String text) {
        this.gameView = gameView;

        this.verts[0][0] = left;
        this.verts[0][1] = top;
        this.verts[1][0] = right;
        this.verts[1][1] = bottom;

        this.textSize = verts[1][1]-verts[0][1]-(2*textPadding);
        this.text = text;
    }

    public void render(Canvas canvas, Paint paint, double offsetX, double offsetY) {
        paint.setColor(color);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.create("roboto",Typeface.BOLD));
        canvas.drawText(text, (float) ((verts[1][0]+verts[0][0])/2+offsetX), (float) (verts[1][1]-(textSize/8)-textPadding+offsetY), paint);

    }

}
