package com.example.will.test.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.example.will.test.main.GameView;

import java.util.ArrayList;
import java.util.List;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;

    private boolean running;
    public Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int fps = 0;

        while(running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    long now = System.nanoTime();
                    delta += (now - lastTime) / ns;
                    lastTime = now;

                    while(delta >= 1){
                        gameView.update(delta);
                        delta--;
                    }

                    frames++;
                    if(System.currentTimeMillis() - timer > 1000){
                        timer += 1000;
                        fps = frames;
                        frames = 0;
                    }

                    gameView.render(canvas, fps);

                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
