package com.example.will.test.ingame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.will.test.gamestates.InGame;

import java.util.Arrays;
import java.util.Collections;

public class GameObject {

    public InGame ingame;

    double[] xAxis = {1, 0, 0};
    double[] yAxis = {0, 1, 0};
    double[] zAxis = {0, 0, 1};

    public double[] pos = {0, 0, 0}; //x, y, z
    public double[] ang = {0, 0, 0}; //x, y, z

    public double[] vel = {0, 0, 0};
    public double[] angVel = {0, 0, 0};

    public double[] acc = {0, 0, 0};
    public double[] angAcc = {0, 0, 0};

    public double[][] relVert;
    public double[][] vert;
    public int[][] faces;
    public int[] faceColors;
    Double[] zValues;
    Double[] sortedzValues;
    public int faceIndex;

    public double[][] relBB = new double[2][2];
    public double[][] bb = new double[2][2];

    public GameObject(InGame ingame, double[][] relVert, int[][] faces, int[] faceColors) {
        this.ingame = ingame;

        this.relVert = new double[relVert.length][3];
        this.vert = new double[relVert.length][3];
        for(int i=0; i<relVert.length; i++) {
            this.relVert[i][0] = relVert[i][0];
            this.relVert[i][1] = relVert[i][1];
            this.relVert[i][2] = relVert[i][2];
            this.vert[i][0] = relVert[i][0];
            this.vert[i][1] = relVert[i][1];
            this.vert[i][2] = relVert[i][2];
        }

        this.faces = new int[faces.length][3];
        for(int i=0; i<faces.length; i++) {
            this.faces[i][0] = faces[i][0];
            this.faces[i][1] = faces[i][1];
            this.faces[i][2] = faces[i][2];
        }

        this.faceColors = new int[faceColors.length];
        for(int i=0; i<faceColors.length; i++) {
            this.faceColors[i] = faceColors[i];
        }

        this.zValues = new Double[faces.length];
        this.sortedzValues = new Double[faces.length];

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
        vel[0] += acc[0]*delta;
        vel[1] += acc[1]*delta;
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

    private void rotate() {
        vert = rotatePoints(relVert);

        relBB[0][0] = Double.MAX_VALUE;
        relBB[0][1] = Double.MAX_VALUE;
        relBB[1][0] = Double.MIN_VALUE;
        relBB[1][1] = Double.MIN_VALUE;
        for(int i=0; i<vert.length; i++) {
            relBB[0][0] = Math.min(relBB[0][0], vert[i][0]);
            relBB[0][1] = Math.min(relBB[0][1], vert[i][1]);
            relBB[1][0] = Math.max(relBB[1][0], vert[i][0]);
            relBB[1][1] = Math.max(relBB[1][1], vert[i][1]);
        }

        try {
            bb[0][0] = relBB[0][0] + pos[0] - ingame.player.pos[0];
            bb[0][1] = relBB[0][1] + pos[1] - ingame.player.pos[1];
            bb[1][0] = relBB[1][0] + pos[0] - ingame.player.pos[0];
            bb[1][1] = relBB[1][1] + pos[1] - ingame.player.pos[1];
        } catch (Exception e) {
            bb[0][0] = relBB[0][0];
            bb[0][1] = relBB[0][1];
            bb[1][0] = relBB[1][0];
            bb[1][1] = relBB[1][1];
        }
    }

    public double[][] rotatePoints(double[][] relPoints) {
        double[][] newPoints = new double[relPoints.length][3];
        for(int i=0; i<relPoints.length; i++) {
            newPoints[i][0] = relPoints[i][0];
            newPoints[i][1] = relPoints[i][1];
            newPoints[i][2] = relPoints[i][2];
        }

        for(int i=0; i<relPoints.length; i++) {
            newPoints[i] = rotateAroundAxis(newPoints[i], normalize(zAxis), ang[0]);
            newPoints[i] = rotateAroundAxis(newPoints[i], normalize(yAxis), ang[1]);
            newPoints[i] = rotateAroundAxis(newPoints[i], normalize(xAxis), ang[2]);
        }

        return newPoints;
    }

    public double[] rotateAroundAxis(double[] point, double[] axis, double angle) {
        double[] newPoint = new double[3];
        newPoint[0] = (point[0]*(Math.cos(angle)+(axis[0]*axis[0]*(1-Math.cos(angle)))))+(point[1]*((axis[0]*axis[1]*(1-Math.cos(angle)))-(axis[2]*Math.sin(angle))))+(point[2]*((axis[0]*axis[2]*(1-Math.cos(angle)))+(axis[1]*Math.sin(angle))));
        newPoint[1] = (point[0]*((axis[0]*axis[1]*(1-Math.cos(angle)))+(axis[2]*Math.sin(angle))))+(point[1]*(Math.cos(angle)+(axis[1]*axis[1]*(1-Math.cos(angle)))))+(point[2]*((axis[1]*axis[2]*(1-Math.cos(angle)))-(axis[0]*Math.sin(angle))));
        newPoint[2] = (point[0]*((axis[0]*axis[2]*(1-Math.cos(angle)))-(axis[1]*Math.sin(angle))))+(point[1]*((axis[1]*axis[2]*(1-Math.cos(angle)))+(axis[0]*Math.sin(angle))))+(point[2]*(Math.cos(angle)+(axis[2]*axis[2]*(1-Math.cos(angle)))));
        return newPoint;
    }

    public double[] normalize(double[] point) {
        double[] newPoint = new double[3];
        double mag = Math.sqrt((point[0]*point[0])+(point[1]*point[1])+(point[2]*point[2]));
        newPoint[0] = point[0]/mag;
        newPoint[1] = point[1]/mag;
        newPoint[2] = point[2]/mag;
        return newPoint;
    }

    public boolean collide(GameObject object) {
        if(collideBB(object)) {
            return true;
        }
        return false;
    }

    public boolean collideBB(GameObject object) {
        if(object.bb[0][0] < bb[1][0] && object.bb[1][0] > bb[0][0]) {
            if(object.bb[0][1] < bb[1][1] && object.bb[1][1] > bb[0][1]) {
                return true;
            }
        }
        return false;
    }

}
