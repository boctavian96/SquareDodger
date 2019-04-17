package com.octavian.game.datamodel;

/**
 * Created by octavian on 4/14/19.
 */

public class HighScore {
    private long score;

    public HighScore(String score){
        this.score = Long.valueOf(score);
    }

    public long getScore(){
        return score;
    }
}
