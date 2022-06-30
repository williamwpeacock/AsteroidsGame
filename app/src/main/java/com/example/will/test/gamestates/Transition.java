package com.example.will.test.gamestates;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.will.test.main.GameView;

public class Transition implements GameState {

    GameView gameView;

    public int WIDTH;
    public int HEIGHT;

    GameStates startState;
    GameStates endState;
    public TransitionTypes transitionType;

    double progress = 0;

    public Transition(GameView gameView) {
        this.gameView = gameView;

        WIDTH = gameView.WIDTH;
        HEIGHT = gameView.HEIGHT;
    }

    @Override
    public void update(double delta) {
        switch(transitionType) {
            case NONE:
                progress = 1;
                break;
            default:
                progress+=0.05*delta;
                break;
        }

        if(progress>=1) {
            gameView.gameState = endState;
            progress = 0;
        }
    }

    @Override
    public void render(Canvas canvas, Paint paint, Paint shaderPaint, double offsetX, double offsetY) {
        double x, y;

        switch(transitionType) {
            case FADE:
                double alpha = progress*510;
                if(alpha>255) {
                    alpha = 510-alpha;
                    gameView.renderState(canvas, paint, shaderPaint, endState, offsetX, offsetY);
                } else {
                    gameView.renderState(canvas, paint, shaderPaint, startState, offsetX, offsetY);
                }

                canvas.drawARGB((int) alpha, 0, 0, 0);
                break;
            case FROM_LEFT:
                x = (progress*WIDTH);
                gameView.renderState(canvas, paint, shaderPaint, startState, offsetX, offsetY);
                gameView.renderState(canvas, paint, shaderPaint, endState, x, offsetY);
                break;
            case FROM_RIGHT:
                x = WIDTH-(progress*WIDTH);
                gameView.renderState(canvas, paint, shaderPaint, startState, offsetX, offsetY);
                gameView.renderState(canvas, paint, shaderPaint, endState, x, offsetY);
                break;
            case FROM_TOP:
                y = (progress*HEIGHT);
                gameView.renderState(canvas, paint, shaderPaint, startState, offsetX, offsetY);
                gameView.renderState(canvas, paint, shaderPaint, endState, offsetX, y);
                break;
            case FROM_BOTTOM:
                y = HEIGHT-(progress*HEIGHT);
                gameView.renderState(canvas, paint, shaderPaint, startState, offsetX, offsetY);
                gameView.renderState(canvas, paint, shaderPaint, endState, offsetX, y);
                break;
            case TO_LEFT:
                x = WIDTH-(progress*WIDTH);
                gameView.renderState(canvas, paint, shaderPaint, startState, x, offsetY);
                gameView.renderState(canvas, paint, shaderPaint, endState, offsetX, offsetY);
                break;
            case TO_RIGHT:
                x = (progress*WIDTH);
                gameView.renderState(canvas, paint, shaderPaint, startState, x, offsetY);
                gameView.renderState(canvas, paint, shaderPaint, endState, offsetX, offsetY);
                break;
            case TO_TOP:
                y = HEIGHT-(progress*HEIGHT);
                gameView.renderState(canvas, paint, shaderPaint, startState, offsetX, y);
                gameView.renderState(canvas, paint, shaderPaint, endState, offsetX, offsetY);
                break;
            case TO_BOTTOM:
                y = (progress*HEIGHT);
                gameView.renderState(canvas, paint, shaderPaint, startState, offsetX, y);
                gameView.renderState(canvas, paint, shaderPaint, endState, offsetX, offsetY);
                break;
        }
    }

    @Override
    public void pressed(MotionEvent event) {

    }

    @Override
    public void dragged(MotionEvent event) {

    }

    @Override
    public void released(MotionEvent event) {

    }

    public void transition(GameStates startState, GameStates endState, TransitionTypes transitionType) {
        this.startState = startState;
        this.endState = endState;
        this.transitionType = transitionType;
    }

}
