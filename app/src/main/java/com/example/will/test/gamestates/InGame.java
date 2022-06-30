package com.example.will.test.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.example.will.test.gui.BoostButton;
import com.example.will.test.gui.MovementControl;
import com.example.will.test.gui.PauseButton;
import com.example.will.test.gui.ShootButton;
import com.example.will.test.main.GameView;
import com.example.will.test.R;
import com.example.will.test.ingame.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class InGame implements GameState {

    public GameView gameView;

    public int WIDTH;
    public int HEIGHT;

    private int movementPointerIndex = -1;
    private int shootPointerIndex = -1;
    private int boostPointerIndex = -1;

    public ThisPlayer player;

    double[][] asteroidRelVert = {{0, 1, 0}, {0, -1, 0}, {1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1}};
    int[][] asteroidFaces = {{0, 2, 4}, {0, 3, 4}, {0, 2, 5}, {0, 3, 5}, {1, 2, 4}, {1, 3, 4}, {1, 2, 5}, {1, 3, 5}};
    int[] asteroidFaceColors = {0xFFFFFFFF, 0xFFE6E6E6, 0xFFC8C8C8, 0xFFAAAAAA, 0xFFF5F5F5, 0xFFD7D7D7, 0xFFB9B9B9, 0xFF9B9B9B};
    public ArrayList<Asteroid> asteroids = new ArrayList<>();

    public ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Stars> stars = new ArrayList<>();

    public MovementControl movementControl;
    public double[] controlPos = new double[2];
    private PauseButton pauseButton;
    private BoostButton boostButton;
    private ShootButton shootButton;

    public InGame(GameView gameView) {
        this.gameView = gameView;

        WIDTH = gameView.WIDTH;
        HEIGHT = gameView.HEIGHT;

        double playerSize = 40;

        double[][] playerRelVert = {{0, -2*playerSize, 0}, {-playerSize, playerSize, 0}, {playerSize, playerSize, 0}, {0, playerSize/4, playerSize/2}, {0, playerSize/4, -playerSize/2}};
        int[][] playerFaces = {{0, 1, 3}, {0, 2, 3}, {1, 2, 3}, {0, 1, 4}, {0, 2, 4}, {1, 2, 4}};
        int[] playerFaceColors = {0xFFFFFFFF, 0xFFE6E6E6, 0xFFC8C8C8, 0xFFFFFFFF, 0xFFE6E6E6, 0xFFC8C8C8};

        player = new ThisPlayer(this, playerRelVert, playerFaces, playerFaceColors, playerSize);

        stars.add(new Stars(gameView, R.raw.stars0, 5, 20));
        stars.add(new Stars(gameView, R.raw.stars1, 5, 10));

        movementControl = new MovementControl(gameView, 300, HEIGHT-300, 200);
        controlPos[0] = movementControl.pos[0];
        controlPos[1] = movementControl.pos[1];
        pauseButton = new PauseButton(gameView, WIDTH-130, 30, WIDTH-30, 130);
        boostButton = new BoostButton(gameView, WIDTH-275, HEIGHT-200, 75);
        shootButton = new ShootButton(gameView, WIDTH-175, HEIGHT-400, 75);
    }

    @Override
    public void update(double delta) {
        player.control();
        player.update(delta);

        if((int)(Math.random() * 10) + 1 == 1) {
            double asteroidSize = (int)(Math.random() * 71) + 30;
            double[][] newAsteroidRelVert = new double[asteroidRelVert.length][3];
            for(int i=0; i<asteroidRelVert.length; i++) {
                newAsteroidRelVert[i][0]=asteroidRelVert[i][0]*asteroidSize;
                newAsteroidRelVert[i][1]=asteroidRelVert[i][1]*asteroidSize;
                newAsteroidRelVert[i][2]=asteroidRelVert[i][2]*asteroidSize;
            }
            asteroids.add(new Asteroid(this, newAsteroidRelVert, asteroidFaces, asteroidFaceColors));
        }

        Asteroid currentAsteroid;
        ArrayList<Integer> deleteAsteroids = new ArrayList<>();
        for(int i=0; i<asteroids.size(); i++) {
            currentAsteroid = asteroids.get(i);
            currentAsteroid.update(delta);
            if(currentAsteroid.pos[0]<player.pos[0]-200-WIDTH/2 || currentAsteroid.pos[0]>player.pos[0]+200+WIDTH/2 || currentAsteroid.pos[1]<player.pos[1]-200-HEIGHT/2 || currentAsteroid.pos[1]>player.pos[1]+200+HEIGHT/2) {
                deleteAsteroids.add(i);
            }
            if(player.collide(asteroids.get(i))) {
                resetGame();
                resetAllControls();

                gameView.transition.transition(GameStates.INGAME, GameStates.DEAD, TransitionTypes.FADE);
                gameView.gameState = GameStates.TRANSITION;
            }
        }

        for(int i=0; i<deleteAsteroids.size(); i++) {
            asteroids.remove(asteroids.get(deleteAsteroids.get(i)));
        }

        for(int i=0; i<projectiles.size(); i++) {
            projectiles.get(i).update(delta);
        }

        for(int i=0; i<stars.size(); i++) {
            stars.get(i).update(delta);
        }
    }

    @Override
    public void render(Canvas canvas, Paint paint, Paint shaderPaint, double offsetX, double offsetY) {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create("roboto", Typeface.NORMAL));

        for(int i=0; i<stars.size(); i++) {
            stars.get(i).render(canvas, paint, shaderPaint, offsetX, offsetY);
        }

        /*
        canvas.drawText("x: "+(int) Math.floor(player.pos[0]), 100, 100, paint);
        canvas.drawText("y: "+(int) Math.floor(player.pos[1]), 100, 130, paint);
        canvas.drawText("velX: "+(int) Math.floor(player.vel[0]), 100, 160, paint);
        canvas.drawText("velY: "+(int) Math.floor(player.vel[1]), 100, 190, paint);
        canvas.drawText("accX: "+(int) Math.floor(player.acc[0]), 100, 220, paint);
        canvas.drawText("accY: "+(int) Math.floor(player.acc[1]), 100, 250, paint);
        canvas.drawText("test: "+gameView.test, 100, 280, paint);
        */

        Path path = new Path();
        player.render(canvas, paint, path, offsetX, offsetY);

        for(int i=0; i<asteroids.size(); i++) {
            asteroids.get(i).render(canvas, paint, path, offsetX, offsetY);
        }

        for(int i=0; i<projectiles.size(); i++) {
            projectiles.get(i).render(canvas, paint, path, offsetX, offsetY);
        }

        movementControl.render(canvas, paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle((float) controlPos[0], (float) controlPos[1], 50, paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle((float) controlPos[0], (float) controlPos[1], 40, paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle((float) controlPos[0], (float) controlPos[1], 35, paint);

        pauseButton.render(canvas, paint, offsetX, offsetY);
        shootButton.render(canvas, paint, offsetX, offsetY);
        boostButton.render(canvas, paint, offsetX, offsetY);
    }

    @Override
    public void pressed(MotionEvent event) {
        double mouseX = event.getX(event.getActionIndex());
        double mouseY = event.getY(event.getActionIndex());

        if(pauseButton.checkPressed(mouseX, mouseY)) {
            resetAllControls();

            gameView.transition.transition(GameStates.INGAME, GameStates.PAUSED, TransitionTypes.FROM_BOTTOM);
            gameView.gameState = GameStates.TRANSITION;
        } else if (movementControl.checkPressed(mouseX, mouseY) && movementPointerIndex == -1) {
            movementPointerIndex = event.getActionIndex();
            updateMovementControls(mouseX, mouseY);
        } else if (shootButton.checkPressed(mouseX, mouseY)) {
            shootPointerIndex = event.getActionIndex();
            shootButton.color1 = 0xFF222222;
            player.shoot();
        } else if (boostButton.checkPressed(mouseX, mouseY)) {
            boostPointerIndex = event.getActionIndex();
            boostButton.color1 = 0xFF222222;
            player.maxVel = 20;
        }
    }

    @Override
    public void dragged(MotionEvent event) {
        int numPointers = event.getPointerCount();

        for(int i=0; i<numPointers; i++) {
            double mouseX = event.getX(i);
            double mouseY = event.getY(i);

            if (i == movementPointerIndex) {
                updateMovementControls(mouseX, mouseY);
            }
        }
    }

    @Override
    public void released(MotionEvent event) {
        if(event.getPointerCount() == 1) {
            resetAllControls();
        } else {
            if (event.getActionIndex() == movementPointerIndex) {
                movementPointerIndex = -1;
                controlPos[0] = movementControl.pos[0];
                controlPos[1] = movementControl.pos[1];
            } else if (event.getActionIndex() == shootPointerIndex) {
                shootPointerIndex = -1;
                shootButton.color1 = 0xFF000000;
            } else if (event.getActionIndex() == boostPointerIndex) {
                boostPointerIndex = -1;
                boostButton.color1 = 0xFF000000;
                player.maxVel = 10;
            }
        }
    }

    public void updateMovementControls(double mouseX, double mouseY) {
        double adjustedX = mouseX - movementControl.pos[0];
        double adjustedY = movementControl.pos[1] - mouseY;

        double ang = Math.atan(adjustedX / adjustedY);
        if (Double.isNaN(ang)) {
            ang = 0;
        }
        if (adjustedY < 0) {
            ang += Math.toRadians(180);
        } else {
            if (adjustedX < 0) {
                ang += Math.toRadians(360);
            }
        }

        player.targetAng = ang;

        if ((adjustedX * adjustedX) + (adjustedY * adjustedY) > player.maxLineLength * player.maxLineLength) {
            mouseX = (player.maxLineLength * Math.cos(ang - Math.toRadians(90))) + movementControl.pos[0];
            mouseY = (player.maxLineLength * Math.sin(ang - Math.toRadians(90))) + movementControl.pos[1];
        }
        controlPos[0] = mouseX;
        controlPos[1] = mouseY;
    }

    public void resetAllControls() {
        movementPointerIndex = -1;
        controlPos[0] = movementControl.pos[0];
        controlPos[1] = movementControl.pos[1];
        shootPointerIndex = -1;
        shootButton.color1 = 0xFF000000;
        boostPointerIndex = -1;
        boostButton.color1 = 0xFF000000;
        player.maxVel = 10;
    }

    public void resetGame() {
        asteroids.clear();
        player.reset();
    }
}
