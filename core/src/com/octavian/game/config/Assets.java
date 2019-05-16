package com.octavian.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    public static Texture coinAnimation;

    public static Music music;
    public static Music aboutMusic;
    public static Sound gotCoin;

    public static ImageButton play;
    public static ImageButton about;
    public static ImageButton skins;
    public static ImageButton exit;
    public static ImageButton back;
    public static ImageButton buy;
    public static ImageButton lock;
    public static ImageButton reset;
    public static ImageButton sound;
    public static ImageButton soundMute;

    public static Array<Texture> obstacleTextures = new Array<>();
    public static Array<Texture> skinTextures = new Array<>();
    public static Texture coinTexture;

    private static Texture loadTexture (String file) {
        return new Texture(file);
    }

    private static Music loadMusic(String file){
        return Gdx.audio.newMusic(Gdx.files.internal(file));
    }

    private static Sound loadSound(String file){
        return Gdx.audio.newSound(Gdx.files.internal(file));
    }

    private static Array<Texture> loadTexture(String[] files){
        Array<Texture> textures = new Array<>();
        for(int i = 0; i < files.length; i++){
            textures.add(loadTexture(files[i]));
        }

        return textures;
    }

    private static ImageButton loadButton(Texture buttonUp, Texture buttonDown){
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(buttonUp)), new TextureRegionDrawable(new TextureRegion(buttonDown)));

    }

    public static void load(){

        //============TEXTURES============================
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
        obstacleTextures.addAll(loadTexture(Config.SQUARES));
        skinTextures.addAll(loadTexture(Config.SKINS_ARRAY));
        coinTexture = loadTexture(Config.COIN);

        //==================ANIMATIONS=====================
        coinAnimation = loadTexture(Config.COIN_ANIMATION);

        //==================MUSIC & SOUNDS=================
        music = loadMusic(Config.MUSIC1);
        aboutMusic = loadMusic(Config.MUSIC2);
        music.setLooping(true);
        aboutMusic.setLooping(true);

        gotCoin = loadSound(Config.GOT_COIN);

        //==================BUTTONS========================
        play = loadButton(playTexture, playPressTexture);
        about = loadButton(aboutTexture, aboutPressTexture);
        skins = loadButton(skinsTexture,skinsPressTexture);
        exit = loadButton(exitTexture, exitPressTexture);
        back = loadButton(backTexture, backPressTexture);
        buy = loadButton(buyTexture, buyPressTexture);
        lock = loadButton(exitTexture, exitPressTexture);
        reset = loadButton(resetTexture, resetPressTexture);
        sound = loadButton(soundTexture, soundPressTexture);
        soundMute = loadButton(soundMuteTexture, soundMutePressTexture);
    }

    public static void playMusic(Music music){
        if(Settings.isSoundEnabled) {
            music.play();
        }else{
            music.pause();
        }
    }

    public static void stopMusic(Music music){
        music.pause();
    }
}
