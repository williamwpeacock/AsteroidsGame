package com.example.will.test.ingame;

import com.example.will.test.gamestates.InGame;

public class Asteroid extends GameObject {

    public Asteroid(InGame ingame, double[][] relVert, int[][] faces, int[] faceColors) {
        super(ingame, relVert, faces, faceColors);
        int test = (int) randomWithRange(0, 3);
        if(test == 0){
            pos[0] = ingame.player.pos[0]-100-ingame.WIDTH/2;
            pos[1] = ingame.player.pos[1]+randomWithRange(0, ingame.HEIGHT);
            vel[0] = randomWithRange(-5, 5);
            vel[1] = randomWithRange(-5, 5);
        } else if(test == 1){
            pos[0] = ingame.player.pos[0]+100+ingame.WIDTH/2;
            pos[1] = ingame.player.pos[1]+randomWithRange(0, ingame.HEIGHT);
            vel[0] = -randomWithRange(-5, 5);
            vel[1] = randomWithRange(-5, 5);
        } else if(test == 2){
            pos[0] = ingame.player.pos[0]+randomWithRange(0, ingame.WIDTH);
            pos[1] = ingame.player.pos[1]-100-ingame.HEIGHT/2;
            vel[0] = randomWithRange(-5, 5);
            vel[1] = randomWithRange(-5, 5);
        } else if(test == 3){
            pos[0] = ingame.player.pos[0]+randomWithRange(0, ingame.WIDTH);
            pos[1] = ingame.player.pos[1]+100+ingame.HEIGHT/2;
            vel[0] = randomWithRange(-5, 5);
            vel[1] = -randomWithRange(-5, 5);
        }
        angVel[0] = randomWithRange(-0.1, 0.1);
        angVel[1] = randomWithRange(-0.1, 0.1);
        angVel[2] = randomWithRange(-0.1, 0.1);
    }

    private double randomWithRange(double min, double max) {
        double range = (max - min);
        return (Math.random() * range) + min;
    }
}
