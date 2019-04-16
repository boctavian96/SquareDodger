package com.octavian.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by octavian on 4/14/19.
 */

public final class Assets {
    //User interface
    public static Texture playTexture;
    public static Texture playPressTexture;
    public static Texture aboutTexture;
    public static Texture aboutPressTexture;
    public static Texture skinsTexture;
    public static Texture skinsPressTexture;
    public static Texture exitTexture;
    public static Texture exitPressTexture;
    public static Texture backTexture;
    public static Texture backPressTexture;
    public static Texture buyTexture;
    public static Texture buyPressTexture;

    public static Texture lockTexture;
    public static Texture lockTexturePress;
    public static Texture gameover;
    //public static Texture selection; //Selected texture

    public static ImageButton play;
    public static ImageButton about;
    public static ImageButton skins;
    public static ImageButton exit;
    public static ImageButton back;


    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static ImageButton loadButton(Texture buttonUp, Texture buttonDown){
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(buttonUp)), new TextureRegionDrawable(new TextureRegion(buttonDown)));

    }


    public static void load(){
        playTexture = loadTexture(Config.PLAY);
        playPressTexture = loadTexture(Config.PLAY_PRESS);
        aboutTexture = loadTexture(Config.ABOUT);
        aboutPressTexture = loadTexture(Config.ABOUT_PRESS);
        skinsTexture = loadTexture(Config.SKINS);
        skinsPressTexture = loadTexture(Config.SKINS_PRESS);
        exitTexture = loadTexture(Config.EXIT);
        exitPressTexture = loadTexture(Config.EXIT_PRESS);
        backTexture = loadTexture(Config.BACK);
        backPressTexture = loadTexture(Config.BACK_PRESS);
        buyTexture = loadTexture(Config.BUY);
        buyPressTexture = loadTexture(Config.BUY_PRESS);
        lockTexture = loadTexture(Config.LOCK);
        lockTexturePress = loadTexture(Config.LOCK_PRESS);
        gameover = loadTexture(Config.GAMEOVER);

        play = loadButton(playTexture, playPressTexture);
        about = loadButton(aboutTexture, aboutPressTexture);
        skins = loadButton(skinsTexture,skinsPressTexture);
        exit = loadButton(exitTexture, exitPressTexture);
        back = loadButton(backTexture, backPressTexture);



    }

    public static void playMusic(Music music){

    }
}
