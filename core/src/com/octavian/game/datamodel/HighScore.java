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

    public HighScore(){
        score = 0L;
    }

    public long getScore(){
        return score;
    }
    public void setScore(long newScore) { score = newScore;}
}
