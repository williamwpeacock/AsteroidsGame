package com.example.will.test.ingame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.will.test.main.GameView;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class Stars {

    public GameView gameView;

    public double[] pos = {0, 0};
    private ArrayList<int[]> stars = new ArrayList<>();
    private int size;
    private int speed;

    private double starX;
    private double starY;

    public Stars(GameView gameView, int file, int size, int speed) {
        this.gameView = gameView;
        this.size = size;
        this.speed = speed;

        readCSV(file);
    }

    private void readCSV(int file) {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(gameView.getResources().openRawResource(file)));
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                int[] newStar = {Integer.parseInt(nextRecord[0])*size, Integer.parseInt(nextRecord[1])*size};
                stars.add(newStar);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void update(double delta) {
        pos[0]-=(gameView.ingame.player.vel[0]/speed)*delta;
        pos[1]-=(gameView.ingame.player.vel[1]/speed)*delta;

        if(pos[0]>gameView.WIDTH) {
            pos[0]-=gameView.WIDTH;
        } else if(pos[0]<-gameView.WIDTH) {
            pos[0]+=gameView.WIDTH;
        }
        if(pos[1]>gameView.HEIGHT) {
            pos[1]-=gameView.HEIGHT;
        } else if(pos[1]<-gameView.HEIGHT) {
            pos[1]+=gameView.HEIGHT;
        }
    }

    public void render(Canvas canvas, Paint paint, Paint shaderPaint, double offsetX, double offsetY) {
        for(int i=0; i<stars.size(); i++) {
            starX = pos[0]+stars.get(i)[0]+offsetX;
            if(starX>gameView.WIDTH) {
                starX-=gameView.WIDTH;
            } else if(starX<0) {
                starX+=gameView.WIDTH;
            }

            starY = pos[1]+stars.get(i)[1]+offsetY;
            if(starY>gameView.HEIGHT) {
                starY-=gameView.HEIGHT;
            }else if(starY<0) {
                starY+=gameView.HEIGHT;
            }

            //shaderPaint.setShader(new RadialGradient((float) (starX+(size/2)), (float) (starY+(size/2)), 20, 0xFFFFFFFF, 0x00000000, android.graphics.Shader.TileMode.CLAMP));
            //canvas.drawCircle((float) (starX+(size/2)), (float) (starY+(size/2)), 20, shaderPaint);
            canvas.drawRect((float) (starX), (float) (starY), (float) (starX+size), (float) (starY+size), paint);
        }
    }
}
