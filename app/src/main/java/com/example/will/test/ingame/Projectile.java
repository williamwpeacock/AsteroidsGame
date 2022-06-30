package com.example.will.test.ingame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.will.test.gamestates.InGame;

public class Projectile {

    public InGame ingame;

    public double[] pos = {0, 0};
    public double[] vel = {0, 0};
    public double[] acc = {0, 0};

    public Projectile(InGame ingame, double x, double y, double velX, double velY) {
        this.ingame = ingame;

        this.pos[0] = x;
        this.pos[1] = y;
        this.vel[0] = velX;
        this.vel[1] = velY;
    }

    public void update(double delta) {
        vel[0] += acc[0]*delta;
        vel[1] += acc[1]*delta;

        pos[0] += vel[0]*delta;
        pos[1] += vel[1]*delta;
    }

    public void render(Canvas canvas, Paint paint, Path path, double offsetX, double offsetY) {
        paint.setColor(0xFFFFFFFF);
        canvas.drawCircle((float) (pos[0]-ingame.player.pos[0]+ingame.WIDTH/2+offsetX), (float) (pos[1]-ingame.player.pos[1]+ingame.HEIGHT/2+offsetY), 10, paint);
    }
}
