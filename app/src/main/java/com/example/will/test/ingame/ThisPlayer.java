package com.example.will.test.ingame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.will.test.gamestates.InGame;
import com.example.will.test.ingame.Player;

import java.util.Arrays;
import java.util.Collections;

public class ThisPlayer extends Player {

    public double maxLineLength = 200;

    public ThisPlayer(InGame ingame, double[][] relVert, int[][] faces, int[] faceColors, double size) {
        super(ingame, relVert, faces, faceColors, size);
    }

    public void control() {
        double accMag = Math.sqrt((ingame.controlPos[0] - ingame.movementControl.pos[0]) * (ingame.controlPos[0] - ingame.movementControl.pos[0]) + (ingame.controlPos[1] - ingame.movementControl.pos[1]) * (ingame.controlPos[1] - ingame.movementControl.pos[1])) * maxAcc / maxLineLength;
        acc[0] = accMag * Math.cos(ang[0] - Math.toRadians(90));
        acc[1] = accMag * Math.sin(ang[0] - Math.toRadians(90));
    }

    public void reset() {
        pos[0] = 0;
        pos[1] = 0;
        pos[2] = 0;
        ang[0] = 0;
        ang[1] = 0;
        ang[2] = 0;

        vel[0] = 0;
        vel[1] = 0;
        vel[2] = 0;
        angVel[0] = 0;
        angVel[1] = 0;
        angVel[2] = 0;

        acc[0] = 0;
        acc[1] = 0;
        acc[2] = 0;
        angAcc[0] = 0;
        angAcc[1] = 0;
        angAcc[2] = 0;

        targetAng = 0;
        update(1);
    }

}
