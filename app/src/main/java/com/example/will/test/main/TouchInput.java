package com.example.will.test.main;

import android.view.MotionEvent;
import android.view.View;

import com.example.will.test.gamestates.GameStates;
import com.example.will.test.main.GameView;

public class TouchInput implements View.OnTouchListener {

    public GameView gameView;

    public TouchInput(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(gameView.gameState != GameStates.TRANSITION) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    pressed(event);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    pressed(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    dragged(event);
                    break;
                case MotionEvent.ACTION_UP:
                    released(event);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    released(event);
                    break;
            }
            return true;
        } else {
            return false;
        }
    }

    public void pressed(MotionEvent event) {
        switch (gameView.gameState) {
            case MENU:
                gameView.menu.pressed(event);
                break;
            case INGAME:
                gameView.ingame.pressed(event);
                break;
            case PAUSED:
                gameView.paused.pressed(event);
                break;
            case DEAD:
                gameView.dead.pressed(event);
                break;
        }
    }

    public void dragged(MotionEvent event) {
        switch (gameView.gameState) {
            case MENU:
                gameView.menu.dragged(event);
                break;
            case INGAME:
                gameView.ingame.dragged(event);
                break;
            case PAUSED:
                gameView.paused.dragged(event);
                break;
            case DEAD:
                gameView.dead.dragged(event);
                break;
        }
    }

    public void released(MotionEvent event) {
        switch (gameView.gameState) {
            case MENU:
                gameView.menu.released(event);
                break;
            case INGAME:
                gameView.ingame.released(event);
                break;
            case PAUSED:
                gameView.paused.released(event);
                break;
            case DEAD:
                gameView.dead.released(event);
                break;
        }
    }
}
