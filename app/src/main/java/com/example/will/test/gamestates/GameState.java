package com.example.will.test.gamestates;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public interface GameState {
    void update(double delta);
    void render(Canvas canvas, Paint paint, Paint shaderPaint, double offsetX, double offsetY);

    void pressed(MotionEvent event);
    void dragged(MotionEvent event);
    void released(MotionEvent event);
}
