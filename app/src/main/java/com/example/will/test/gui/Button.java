package com.example.will.test.gui;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Button {
    void render(Canvas canvas, Paint paint, double offsetX, double offsetY);
    boolean checkPressed(double x, double y);
}
