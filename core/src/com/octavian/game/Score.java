package com.octavian.game;

import com.octavian.game.config.Config;
import com.octavian.game.util.Utils;

/**
 * Created by octavian on 2/21/18.
 */

public class Score {

    private long score;

    public Score(){
        score = 0;
    }

    public void clear(){
        score = 0;
    }

    public long getScore(){
        return score;
    }

    public void addScore(){
        this.score += Config.POINTS_PER_RECT;
    }

    public boolean isScoreBetter(long score){
        if (Utils.getGameFile('h') < score)
            return true;
        else
            return false;
    }

}
