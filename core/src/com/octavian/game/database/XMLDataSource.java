package com.octavian.game.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.octavian.game.config.Config;
import com.octavian.game.datamodel.Coins;
import com.octavian.game.datamodel.HighScore;
import com.octavian.game.datamodel.SkinStatus;
/**
 * Created by octavian on 4/14/19.
 */

public final class XMLDataSource implements IDataSource {

    private static XMLDataSource instance;

    private XmlReader reader;

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
        FileHandle fileHandle = Gdx.files.local(Config.LOADFILE);
        XmlReader.Element root = reader.parse(fileHandle);

        XmlReader.Element highScore = root.getChildByName("highscore");
        return new HighScore(highScore.getText());
    }

    @Override
    public SkinStatus[] getSkins() {
        return new SkinStatus[0];
    }

    @Override
    public Coins getCoins(){

        FileHandle fileHandle = Gdx.files.local(Config.LOADFILE);
        XmlReader.Element root = reader.parse(fileHandle);

        XmlReader.Element coins = root.getChildByName("coins");
        return new Coins(coins.getText());
    }
}
