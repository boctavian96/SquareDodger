package com.octavian.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.octavian.game.Score;
import com.octavian.game.config.Config;
import com.octavian.game.datamodel.Coins;
import com.octavian.game.datamodel.HighScore;

/**
 * Created by octavian on 5/15/19.
 */

public class SaveState {


    /**
     * Use only on the game's first run.
     */
    public static void create(boolean forcePurge){
        Json json = new Json();
        FileHandle fileHandler;

        fileHandler = Gdx.files.local(Config.COINS_FILE);

        if(fileHandler.exists() && forcePurge) {
            fileHandler.writeString(json.prettyPrint(json.toJson(new Coins())), false);
        }

        fileHandler = Gdx.files.local(Config.HIGHSCORE_FILE);

        if(fileHandler.exists() && forcePurge) {
            fileHandler.writeString(json.prettyPrint(json.toJson(new HighScore())), false);
        }
    }

    public static void saveCoins(Coins obj, boolean isFromGame){

        if(isFromGame) {
            Coins previousCoins = SaveState.readCoins();
            obj.addCoins(previousCoins.getCoins());
        }

        Json json = new Json();
        FileHandle fileHandler = Gdx.files.local(Config.COINS_FILE);
        fileHandler.writeString(json.prettyPrint(json.toJson(obj)), false);
    }

    public static Coins readCoins(){
        Json json = new Json();
        FileHandle fileHandler = Gdx.files.local(Config.COINS_FILE);
        String coinsValue = fileHandler.readString();

        return json.fromJson(Coins.class, coinsValue);
    }

    public static void saveHighscore(Score score){
        HighScore oldHighScore = SaveState.readHighscore();

        if(score.getScore() > oldHighScore.getScore()){
            oldHighScore.setScore(score.getScore());
        }

        Json json = new Json();
        FileHandle fileHandler = Gdx.files.local(Config.HIGHSCORE_FILE);
        fileHandler.writeString(json.prettyPrint(json.toJson(oldHighScore)), false);
    }

    public static HighScore readHighscore(){
        Json json = new Json();
        FileHandle fileHandler = Gdx.files.local(Config.HIGHSCORE_FILE);
        String highscoreValue = null;

        if(fileHandler.exists()) {
            highscoreValue = fileHandler.readString();
        }else{
            return new HighScore();
        }

        return json.fromJson(HighScore.class, highscoreValue);
    }

}
