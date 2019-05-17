package com.octavian.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.octavian.game.Score;
import com.octavian.game.config.Config;
import com.octavian.game.datamodel.Coins;
import com.octavian.game.datamodel.HighScore;
import com.octavian.game.datamodel.Skin;
import com.octavian.game.datamodel.SkinStatus;

/**
 * Created by octavian on 5/15/19.
 */

public class SaveState {

    private SaveState(){
        //Do not instantiate.
    }

    /**
     * Use only on the game's first run.
     */
    public static void create(boolean forcePurge){
        Json json = new Json();
        FileHandle fileHandler;

        fileHandler = Gdx.files.local(Config.COINS_FILE);

        if(!fileHandler.exists() || forcePurge) {
            Gdx.app.log("INFO", "Creating Coins.json");
            fileHandler.writeString(json.prettyPrint(json.toJson(new Coins())), false);
        }

        fileHandler = Gdx.files.local(Config.HIGHSCORE_FILE);

        if(!fileHandler.exists() || forcePurge) {
            Gdx.app.log("INFO", "Creating Highscore.json");
            fileHandler.writeString(json.prettyPrint(json.toJson(new HighScore())), false);
        }

        fileHandler = Gdx.files.local(Config.SKINS_FILE);

        if(!fileHandler.exists() || forcePurge) {
            Gdx.app.log("INFO", "Creating Skins.json");
            fileHandler.writeString(json.prettyPrint(json.toJson(new SkinStatus())), false);
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

    public static void saveSkins(Array<Skin> skins){
        Json json = new Json();
        FileHandle fileHandler = Gdx.files.local(Config.SKINS_FILE);
        SkinStatus skinStatus = new SkinStatus(skins);
        fileHandler.writeString(json.prettyPrint(json.toJson(skinStatus)), false);
    }

    public static Array<Skin> readSkins(){
        Json json = new Json();
        FileHandle fileHandler = Gdx.files.local(Config.SKINS_FILE);

        String skins = fileHandler.readString();
        SkinStatus skinStatus = json.fromJson(SkinStatus.class, skins);
        return skinStatus.getSkins();
    }

    public static void saveSelectedSkin(int selectedSkin){
        FileHandle fileHandler = Gdx.files.local(Config.SELECT);
        fileHandler.writeString(selectedSkin + "", false);
    }

    public static int readSelectedSkin(){
        FileHandle fileHandler = Gdx.files.local(Config.SELECT);

        if(fileHandler.exists()){
            return Integer.parseInt(fileHandler.readString());
        }else{
            fileHandler.writeString("0", false);
            return 0;
        }
    }

}
