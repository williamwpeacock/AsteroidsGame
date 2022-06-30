package com.example.will.test.ingame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.will.test.gamestates.InGame;

import java.util.Arrays;
import java.util.Collections;

public class Player extends GameObject {
    public InGame ingame;

    public double newVelX;
    public double newVelY;

    public double targetAng = 0;

    public double maxVel = 10;
    public double maxAcc = 1;

    public double size;

    public Player(InGame ingame, double[][] relVert, int[][] faces, int[] faceColors, double size) {
        super(ingame, relVert, faces, faceColors);
        this.ingame = ingame;
        this.size = size;

        rotate();
    }

    public void render(Canvas canvas, Paint paint, Path path, double offsetX, double offsetY) {
        for(int i=0;i<faces.length;i++) {
            zValues[i] = (vert[faces[i][0]][2]+vert[faces[i][1]][2]+vert[faces[i][2]][2])/3;
            sortedzValues[i] = zValues[i];
        }

        Arrays.sort(sortedzValues, Collections.reverseOrder());

        for(int i=0; i<sortedzValues.length; i++) {
            faceIndex = Arrays.asList(zValues).indexOf(sortedzValues[i]);
            zValues[faceIndex] = null;
            path.reset();
            path.moveTo((float) (vert[faces[faceIndex][0]][0]+pos[0]-ingame.player.pos[0]+ingame.WIDTH/2+offsetX), (float) (vert[faces[faceIndex][0]][1]+pos[1]-ingame.player.pos[1]+ingame.HEIGHT/2+offsetY));
            path.lineTo((float) (vert[faces[faceIndex][1]][0]+pos[0]-ingame.player.pos[0]+ingame.WIDTH/2+offsetX), (float) (vert[faces[faceIndex][1]][1]+pos[1]-ingame.player.pos[1]+ingame.HEIGHT/2+offsetY));
            path.lineTo((float) (vert[faces[faceIndex][2]][0]+pos[0]-ingame.player.pos[0]+ingame.WIDTH/2+offsetX), (float) (vert[faces[faceIndex][2]][1]+pos[1]-ingame.player.pos[1]+ingame.HEIGHT/2+offsetY));
            paint.setColor(faceColors[faceIndex]);
            canvas.drawPath(path, paint);
        }
    }

    public void update(double delta) {
        double d = distanceToTargetAngle();
        if(d>Math.toRadians(90)) {
            d=Math.toRadians(90);
        } else if(d<-Math.toRadians(90)) {
            d=-Math.toRadians(90);
        }
        ang[0] += (d/10)*delta;
        ang[1] = d;

        vel[0] += acc[0]*delta;
        vel[1] += acc[1]*delta;

        double velMag = Math.sqrt((vel[0]*vel[0])+(vel[1]*vel[1]));
        double velAng = Math.atan(vel[0]/-vel[1]);
        if (Double.isNaN(velAng)) {
            velAng = 0;
        }

        if (vel[1] > 0) {
            velAng += Math.toRadians(180);
        } else {
            if (vel[0] < 0) {
                velAng += Math.toRadians(360);
            }
        }


        double decel = 0.2;

        if(velMag>maxVel) {
            vel[0] = maxVel*Math.cos(ang[0] - Math.toRadians(90));
            vel[1] = maxVel*Math.sin(ang[0] - Math.toRadians(90));
        }

        if(velMag>0) {
            newVelX = vel[0]-(decel * delta * Math.cos(velAng - Math.toRadians(90)));
            newVelY = vel[1]-(decel * delta * Math.sin(velAng - Math.toRadians(90)));

            if(vel[0] < 0) {
                if(newVelX > 0) {
                    vel[0] = 0;
                } else {
                    vel[0] = newVelX;
                }
            } else if(vel[0] > 0) {
                if(newVelX < 0) {
                    vel[0] = 0;
                } else {
                    vel[0] = newVelX;
                }
            }

            if(vel[1] < 0) {
                if(newVelY > 0) {
                    vel[1] = 0;
                } else {
                    vel[1] = newVelY;
                }
            } else if(vel[1] > 0) {
                if(newVelY < 0) {
                    vel[1] = 0;
                } else {
                    vel[1] = newVelY;
                }
            }

        }

        angVel[0] += angAcc[0]*delta;
        angVel[1] += angAcc[1]*delta;
        angVel[2] += angAcc[2]*delta;

        pos[0] += vel[0]*delta;
        pos[1] += vel[1]*delta;

        for(int i=0; i<ang.length; i++) {
            ang[i] += angVel[i]*delta;
            if(ang[i]>=Math.toRadians(360)) {
                ang[i]-=Math.toRadians(360);
            } else if(ang[i]<0) {
                ang[i]+=Math.toRadians(360);
            }
        }

        rotate();
    }

    public void shoot() {
        ingame.projectiles.add(new Projectile(ingame, vert[0][0]+pos[0], vert[0][1]+pos[1], 20*Math.cos(ang[0]-Math.toRadians(90)), 20*Math.sin(ang[0]-Math.toRadians(90))));
    }

    private double distanceToTargetAngle() {
        double d = targetAng-ang[0];
        if(d>Math.toRadians(180)) {
            d = d-(2*Math.toRadians(180));
        } else if(d<-Math.toRadians(180)) {
            d = d+(2*Math.toRadians(180));
        }

        return d;
    }

    private void rotate() {
        vert = rotatePoints(relVert, 1);
    }

    private double[][] rotatePoints(double[][] relPoints, int lim) {
        double[][] newPoints = new double[relPoints.length][3];
        for(int i=0; i<relPoints.length; i++) {
            newPoints[i][0] = relPoints[i][0];
            newPoints[i][1] = relPoints[i][1];
            newPoints[i][2] = relPoints[i][2];
        }

        // turn
        for(int i=0; i<relPoints.length; i++) {
            newPoints[i] = rotateAroundAxis(newPoints[i], normalize(zAxis), ang[0]);
        }

        // barrel roll
        for(int i=lim; i<relPoints.length; i++) {
            newPoints[i] = rotateAroundAxis(newPoints[i], normalize(vert[0]), ang[1]);
        }

        return newPoints;
    }
}
