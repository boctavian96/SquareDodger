package com.octavian.game.database;

import com.octavian.game.datamodel.HighScore;
import com.octavian.game.datamodel.SkinStatus;

/**
 * Created by octavian on 4/14/19.
 */

public class XMLDataSource implements IDataSource {
    @Override
    public HighScore[] getScores() {
        return new HighScore[0];
    }

    @Override
    public SkinStatus[] getSkins() {
        return new SkinStatus[0];
    }
}
