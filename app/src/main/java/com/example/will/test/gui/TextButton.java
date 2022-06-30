package com.example.will.test.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.will.test.main.GameView;

public class TextButton extends RectButton {

    int textSize;
    int textPadding = 50;
    String text;

    public TextButton(GameView gameView, int left, int top, int right, int bottom, String text) {
        super(gameView, left, top, right, bottom);

        this.textSize = verts[1][1]-verts[0][1]-(2*textPadding);
        this.text = text;
    }

    public void render(Canvas canvas, Paint paint, double offsetX, double offsetY) {
        super.render(canvas, paint, offsetX, offsetY);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.create("roboto",Typeface.BOLD));
        canvas.drawText(text, (float) ((verts[1][0]+verts[0][0])/2+offsetX), (float) (verts[1][1]-(textSize/8)-textPadding+offsetY), paint);
    }
}
