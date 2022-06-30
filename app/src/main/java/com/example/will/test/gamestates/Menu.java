package com.example.will.test.gamestates;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.will.test.gui.TextButton;
import com.example.will.test.main.GameView;
import com.example.will.test.gui.Text;

public class Menu implements GameState {

    public GameView gameView;

    public int WIDTH;
    public int HEIGHT;

    TextButton playButton;
    TextButton settingsButton;
    Text title;

    public Menu(GameView gameView) {
        this.gameView = gameView;

        WIDTH = gameView.WIDTH;
        HEIGHT = gameView.HEIGHT;

        title = new Text(gameView, WIDTH/4, 50, WIDTH*3/4, 300, "Menu");

        playButton = new TextButton(gameView, WIDTH/4, 350, WIDTH*3/4, 550, "PLAY");
        settingsButton = new TextButton(gameView, WIDTH/4, 600, WIDTH*3/4, 800, "SETTINGS");
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas, Paint paint, Paint shaderPaint, double offsetX, double offsetY) {
        title.render(canvas, paint, offsetX, offsetY);
        playButton.render(canvas, paint, offsetX, offsetY);
        settingsButton.render(canvas, paint, offsetX, offsetY);
    }

    @Override
    public void pressed(MotionEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        if(playButton.checkPressed(mouseX, mouseY)) {
            gameView.transition.transition(GameStates.MENU, GameStates.INGAME, TransitionTypes.FADE);
            gameView.gameState = GameStates.TRANSITION;
        } else if(settingsButton.checkPressed(mouseX, mouseY)) {
            gameView.transition.transition(GameStates.MENU, GameStates.INGAME, TransitionTypes.FADE);
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
