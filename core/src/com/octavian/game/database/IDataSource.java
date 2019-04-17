package com.octavian.game.database;

import com.octavian.game.datamodel.Coins;
import com.octavian.game.datamodel.HighScore;
import com.octavian.game.datamodel.SkinStatus;

/**
 * Created by octavian on 4/14/19.
 */

public interface IDataSource {

    HighScore getScores();
    SkinStatus[] getSkins();
    Coins getCoins();

}
