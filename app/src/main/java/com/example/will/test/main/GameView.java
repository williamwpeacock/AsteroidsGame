package com.example.will.test.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.will.test.gamestates.Dead;
import com.example.will.test.gamestates.GameStates;
import com.example.will.test.gamestates.InGame;
import com.example.will.test.gamestates.Menu;
import com.example.will.test.gamestates.Paused;
import com.example.will.test.gamestates.Transition;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    public int WIDTH;
    public int HEIGHT;

    private TouchInput touchInput;

    public GameStates gameState = GameStates.MENU;
    public Transition transition;
    public Menu menu;
    public InGame ingame;
    public Paused paused;
    public Dead dead;

    public String test = "no";

    public GameView(Context context, Point size) {
        super(context);
        setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY | SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        WIDTH = size.x;
        HEIGHT = size.y;

        touchInput = new TouchInput(this);
        setOnTouchListener(touchInput);

        transition = new Transition(this);
        menu = new Menu(this);
        ingame = new InGame(this);
        paused = new Paused(this);
        dead = new Dead(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(double delta) {
        switch (gameState) {
            case TRANSITION:
                transition.update(delta);
                break;
            case MENU:
                menu.update(delta);
                break;
            case INGAME:
                ingame.update(delta);
                break;
            case PAUSED:
                paused.update(delta);
                break;
            case DEAD:
                dead.update(delta);
                break;
        }
    }

    public void render(Canvas canvas, int fps) {
        this.draw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.WHITE);
        canvas.drawText("FPS: "+fps, 1620, 100, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);

        Paint shaderPaint = new Paint();
        shaderPaint.setColor(0xFF000000);

        renderState(canvas, paint, shaderPaint, gameState, 0, 0);
    }

    public void renderState(Canvas canvas, Paint paint, Paint shaderPaint, GameStates state, double offsetX, double offsetY) {
        switch (state) {
            case TRANSITION:
                transition.render(canvas, paint, shaderPaint, offsetX, offsetY);
                break;
            case MENU:
                menu.render(canvas, paint, shaderPaint, offsetX, offsetY);
                break;
            case INGAME:
                ingame.render(canvas, paint, shaderPaint, offsetX, offsetY);
                break;
            case PAUSED:
                paused.render(canvas, paint, shaderPaint, offsetX, offsetY);
                break;
            case DEAD:
                dead.render(canvas, paint, shaderPaint, offsetX, offsetY);
                break;
        }
    }
}
