package com.example.will.test.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.will.test.main.GameView;

public class ShootButton extends CircleButton {

    public ShootButton(GameView gameView, int x, int y, int r) {
        super(gameView, x, y, r);
    }

    public void render(Canvas canvas, Paint paint, double offsetX, double offsetY) {
        super.render(canvas, paint, offsetX, offsetY);

        paint.setColor(color0);
        canvas.drawCircle(pos[0], pos[1], radius/2, paint);
        paint.setColor(color1);
        canvas.drawCircle(pos[0], pos[1], (radius/2)-2, paint);
        paint.setColor(color0);
        canvas.drawCircle(pos[0], pos[1], 2, paint);
        canvas.drawLine(pos[0], pos[1]-(radius/2)-10, pos[0], pos[1]-(radius/2)+10, paint);
        canvas.drawLine(pos[0], pos[1]+(radius/2)-10, pos[0], pos[1]+(radius/2)+10, paint);
        canvas.drawLine(pos[0]-(radius/2)-10, pos[1], pos[0]-(radius/2)+10, pos[1], paint);
        canvas.drawLine(pos[0]+(radius/2)-10, pos[1], pos[0]+(radius/2)+10, pos[1], paint);
    }
}
