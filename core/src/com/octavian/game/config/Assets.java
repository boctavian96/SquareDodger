package com.octavian.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

/**
 * Created by octavian on 4/14/19.
 */

public final class Assets {
    //Selected Texture
    public static int selectedTexture = 0;

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
    public static Texture resetTexture;
    public static Texture resetPressTexture;
    public static Texture soundTexture;
    public static Texture soundPressTexture;
    public static Texture soundMuteTexture;
    public static Texture soundMutePressTexture;

    public static Texture lockTexture;
    public static Texture lockTexturePress;
    public static Texture gameover;
    //public static Texture selection; //Selected texture

    public static Music music;

    public static ImageButton play;
    public static ImageButton about;
    public static ImageButton skins;
    public static ImageButton exit;
    public static ImageButton back;
    public static ImageButton buy;
    public static ImageButton lock;
    public static ImageButton skinUI1;
    public static ImageButton skinUI2;
    public static ImageButton skinUI3;
    public static ImageButton skinUI4;
    public static ImageButton skinUI5;
    public static ImageButton skinUI6;
    public static ImageButton skinUI7;
    public static ImageButton skinUI8;
    public static ImageButton skinUI9;

    public static Array<Texture> obstacleTextures = new Array<Texture>();
    public static Array<Texture> skinTextures = new Array<Texture>();
    public static Texture coinTexture;


    private static Texture loadTexture (String file) {
        return new Texture(file);
    }

    private static Music loadMusic(String file){
        return Gdx.audio.newMusic(Gdx.files.internal(file));
    }

    private static Array<Texture> loadTexture(String[] files){
        Array<Texture> textures = new Array<Texture>();
        for(int i = 0; i < files.length; i++){
            textures.add(loadTexture(files[i]));
        }

        return textures;
    }

    private static ImageButton loadButton(Texture buttonUp, Texture buttonDown){
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
        resetTexture = loadTexture(Config.RESET);
        resetPressTexture = loadTexture(Config.RESET_PRESS);
        soundTexture = loadTexture(Config.SOUND);
        soundPressTexture = loadTexture(Config.SOUND_PRESS);
        soundMuteTexture = loadTexture(Config.SOUND_MUTE);
        soundMutePressTexture = loadTexture(Config.SOUND_MUTE_PRESS);
        gameover = loadTexture(Config.GAMEOVER);

        obstacleTextures.addAll(loadTexture(Config.SQUARES));
        skinTextures.addAll(loadTexture(Config.SKINS_ARRAY));
        coinTexture = loadTexture(Config.COIN);

        music = loadMusic(Config.MUSIC1);
        music.setLooping(true);

        play = loadButton(playTexture, playPressTexture);
        about = loadButton(aboutTexture, aboutPressTexture);
        skins = loadButton(skinsTexture,skinsPressTexture);
        exit = loadButton(exitTexture, exitPressTexture);
        back = loadButton(backTexture, backPressTexture);
        buy = loadButton(buyTexture, buyPressTexture);
        lock = loadButton(exitTexture, exitPressTexture);

        //Skins UI
        skinUI1 = loadButton(skinTextures.get(0), skinTextures.get(0));
        skinUI2 = loadButton(skinTextures.get(1), skinTextures.get(1));
        skinUI3 = loadButton(skinTextures.get(2), skinTextures.get(2));
        skinUI4 = loadButton(skinTextures.get(3), skinTextures.get(3));
        skinUI5 = loadButton(skinTextures.get(4), skinTextures.get(4));
        skinUI6 = loadButton(skinTextures.get(5), skinTextures.get(5));
        skinUI7 = loadButton(skinTextures.get(6), skinTextures.get(6));
        skinUI8 = loadButton(skinTextures.get(7), skinTextures.get(7));
        skinUI9 = loadButton(skinTextures.get(8), skinTextures.get(8));
    }

    public static void playMusic(Music music){
        if(Settings.isSoundEnabled) {
            music.play();
        }
    }

    public static void stopMusic(Music music){
        music.pause();
    }
}
