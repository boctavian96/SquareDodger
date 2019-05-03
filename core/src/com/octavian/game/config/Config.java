package com.octavian.game.config;

public final class Config {

    public static final float WORLD_HEIGHT = 1334F;
    public static final float WORLD_WIDTH = 750F;
    public static final float WORLD_UNIT = 32F;

    //Score
   public static final float POINTS_PER_RECT = 5F;

    //Number of textures
    public static final int NUMBER_OF_TEXTURES = 48;

    public static final int MAXIMUM_OBSTACLES = 500;

    //Music
    public static final String MUSIC1 = "audio/PatakasWorld.mp3";

    //About Text File
    public static final String ABOUT_TEXT_FILE = "common/about.txt";

    //Images
    public static final String[] SQUARES = {
            "obstacle/square1.png",
            "obstacle/square2.png",
            "obstacle/square3.png",
            "obstacle/square4.png",
            "obstacle/square5.png",
            "obstacle/square6.png",
            "obstacle/square7.png",
            "obstacle/square8.png",
            "obstacle/square9.png",
            "obstacle/square10.png",
            "obstacle/square11.png",
            "obstacle/square12.png",
            "obstacle/tri1.png",
            "obstacle/tri2.png",
            "obstacle/tri3.png",
            "obstacle/tri4.png",
            "obstacle/tri5.png",
            "obstacle/tri6.png",
            "obstacle/tri7.png",
            "obstacle/tri8.png",
            "obstacle/tri9.png",
            "obstacle/tri10.png",
            "obstacle/tri11.png",
            "obstacle/tri12.png",
            "obstacle/circle1.png",
            "obstacle/circle2.png",
            "obstacle/circle3.png",
            "obstacle/circle4.png",
            "obstacle/circle5.png",
            "obstacle/circle6.png",
            "obstacle/circle7.png",
            "obstacle/circle8.png",
            "obstacle/circle9.png",
            "obstacle/circle10.png",
            "obstacle/circle11.png",
            "obstacle/circle12.png",
            "obstacle/star1.png",
            "obstacle/star2.png",
            "obstacle/star3.png",
            "obstacle/star4.png",
            "obstacle/star5.png",
            "obstacle/star6.png",
            "obstacle/star7.png",
            "obstacle/star8.png",
            "obstacle/star9.png",
            "obstacle/star10.png",
            "obstacle/star11.png",
            "obstacle/star12.png"
    };

    //Player Skins
    public static final String[] SKINS_ARRAY = {
            "player/skin1.png",
            "player/skin2.png",
            "player/skin3.png",
            "player/skin4.png",
            "player/skin5.png",
            "player/skin6.png",
            "player/skin7.png",
            "player/skin8.png",
            "player/skin9.png"
    };

    public static final String COIN = "various/coin.png";
    public static final String LOCK = "various/lock.png";
    public static final String LOCK_PRESS = "various/lockPress.png";

    //UI
    public static final String PLAY = "ui/play.png";
    public static final String PLAY_PRESS = "ui/playPress.png";
    public static final String ABOUT = "ui/about.png";
    public static final String ABOUT_PRESS = "ui/aboutPress.png";
    public static final String SKINS = "ui/skins.png";
    public static final String SKINS_PRESS = "ui/skinsPress.png";
    public static final String EXIT = "ui/exit.png";
    public static final String EXIT_PRESS = "ui/exitPress.png";
    public static final String BACK = "ui/back.png";
    public static final String BACK_PRESS = "ui/backPress.png";
    public static final String BUY = "ui/buy.png";
    public static final String BUY_PRESS = "ui/buyPress.png";
    public static final String RESET = "ui/reset.png";
    public static final String RESET_PRESS = "ui/resetPress.png";
    public static final String SOUND = "ui/sound.png";
    public static final String SOUND_PRESS = "ui/soundPress.png";
    public static final String SOUND_MUTE = "ui/soundMute.png";
    public static final String SOUND_MUTE_PRESS = "ui/soundMutePress.png";

    //Screen
    @Deprecated
    public static String GAMEOVER = "screen/gameover.png";

    //Highscore
    @Deprecated
    public static String HS_FILE = "highscore.txt";

    @Deprecated
    public static String LOADFILE = "load.xml";

    //Coins file
    @Deprecated
    public static String CN_FILE = "coins.txt";

    //Skins file
    @Deprecated
    public static String SKINS_LIST = "skins.txt";

    private Config(){
     //Do not instantiate.
    }
}
