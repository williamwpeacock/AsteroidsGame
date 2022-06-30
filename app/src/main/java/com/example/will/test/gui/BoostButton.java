package com.example.will.test.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.will.test.main.GameView;

public class BoostButton extends CircleButton {

    public BoostButton(GameView gameView, int x, int y, int r) {
        super(gameView, x, y, r);
    }

    public void render(Canvas canvas, Paint paint, double offsetX, double offsetY) {
        super.render(canvas, paint, offsetX, offsetY);
        Path path = new Path();
        path.moveTo(pos[0], pos[1]-radius/2);
        path.lineTo(pos[0]+radius/2, pos[1]);
        path.lineTo(pos[0]-radius/2, pos[1]);
        paint.setColor(color0);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(pos[0], pos[1]+10-radius/2);
        path.lineTo(pos[0]+radius/2, pos[1]+10);
        path.lineTo(pos[0]-radius/2, pos[1]+10);
        paint.setColor(color1);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(pos[0], pos[1]+30-radius/2);
        path.lineTo(pos[0]+radius/2, pos[1]+30);
        path.lineTo(pos[0]-radius/2, pos[1]+30);
        paint.setColor(color0);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(pos[0], pos[1]+40-radius/2);
        path.lineTo(pos[0]+radius/2, pos[1]+40);
        path.lineTo(pos[0]-radius/2, pos[1]+40);
        paint.setColor(color1);
        canvas.drawPath(path, paint);
    }
}
