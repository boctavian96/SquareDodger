package com.octavian.game.database;

import com.octavian.game.datamodel.Coins;
import com.octavian.game.datamodel.HighScore;
import com.octavian.game.datamodel.SkinStatus;
import com.octavian.game.util.FontFactory;

/**
 * Created by octavian on 4/14/19.
 */

public final class XMLDataSource implements IDataSource {

    private static XMLDataSource instance;

    private XMLDataSource(){
        super();
    }

    public static synchronized XMLDataSource getInstance(){
        if(instance == null){
            instance = new XMLDataSource();
        }
        return instance;
    }

    @Override
    public HighScore getScores() {
        return new HighScore();
    }

    @Override
    public SkinStatus[] getSkins() {
        return new SkinStatus[0];
    }

    @Override
    public Coins getCoins(){
        return new Coins();
    }
}
