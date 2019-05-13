package com.octavian.game.datamodel;

import java.io.Serializable;

/**
 * Created by octavian on 4/14/19.
 */

public class HighScore implements Serializable {
    private long score;

    public HighScore(String score){
        this.score = Long.valueOf(score);
    }

    public long getScore(){
        return score;
    }
}
