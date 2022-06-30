package com.example.will.test.gamestates;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.will.test.gui.TextButton;
import com.example.will.test.main.GameView;
import com.example.will.test.gui.Text;

public class Paused implements GameState {

    public GameView gameView;

    public int WIDTH;
    public int HEIGHT;

    TextButton resumeButton;
    TextButton settingsButton;
    TextButton quitButton;
    Text title;

    public Paused(GameView gameView) {
        this.gameView = gameView;

        WIDTH = gameView.WIDTH;
        HEIGHT = gameView.HEIGHT;

        title = new Text(gameView, WIDTH/4, 20, WIDTH*3/4, 280, "Paused");

        resumeButton = new TextButton(gameView, WIDTH/4, 300, WIDTH*3/4, 500, "RESUME");
        settingsButton = new TextButton(gameView, WIDTH/4, 550, WIDTH*3/4, 750, "SETTINGS");
        quitButton = new TextButton(gameView, WIDTH/4, 800, WIDTH*3/4, 1000, "QUIT");
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas, Paint paint, Paint shaderPaint, double offsetX, double offsetY) {
        if(gameView.gameState != GameStates.TRANSITION) {
            gameView.renderState(canvas, paint, shaderPaint, GameStates.INGAME, offsetX, offsetY);
        }

        canvas.drawARGB(200, 0, 0, 0);

        title.render(canvas, paint, offsetX, offsetY);
        resumeButton.render(canvas, paint, offsetX, offsetY);
        settingsButton.render(canvas, paint, offsetX, offsetY);
        quitButton.render(canvas, paint, offsetX, offsetY);
    }

    @Override
    public void pressed(MotionEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        if(resumeButton.checkPressed(mouseX, mouseY)) {
            gameView.transition.transition(GameStates.PAUSED, GameStates.INGAME, TransitionTypes.TO_BOTTOM);
            gameView.gameState = GameStates.TRANSITION;
        } else if(settingsButton.checkPressed(mouseX, mouseY)) {
            gameView.transition.transition(GameStates.PAUSED, GameStates.INGAME, TransitionTypes.FADE);
            gameView.gameState = GameStates.TRANSITION;
        } else if(quitButton.checkPressed(mouseX, mouseY)) {
            gameView.transition.transition(GameStates.PAUSED, GameStates.MENU, TransitionTypes.FADE);
            gameView.gameState = GameStates.TRANSITION;
        }
    }

    @Override
    public void dragged(MotionEvent event) {

    }

    @Override
    public void released(MotionEvent event) {

    }
}
