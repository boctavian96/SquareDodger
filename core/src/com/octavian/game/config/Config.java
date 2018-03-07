package com.octavian.game.config;

public interface Config {

    //Score
    float POINTS_PER_RECT = 5F;

    //Number of textures
    int NUMBER_OF_TEXTURES = 48;

    //
    int MAXIMUM_OBSTACLES = 1000;

    //Music
    String MUSIC1 = "audio/PatakasWorld.mp3";

    //Images

    String[] SQUARES = {
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

    String[] SKINS_ARRAY = {

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

    String LOCK = "various/lock.png";
    String LOCK_PRESS = "various/lockPress.png";

    //UI
    String PLAY = "ui/play.png";
    String PLAY_PRESS = "ui/playPress.png";
    String ABOUT = "ui/about.png";
    String ABOUT_PRESS = "ui/aboutPress.png";
    String SKINS = "ui/skins.png";
    String SKINS_PRESS = "ui/skinsPress.png";
    String EXIT = "ui/exit.png";
    String EXIT_PRESS = "ui/exitPress.png";
    String BACK = "ui/back.png";
    String BACK_PRESS = "ui/backPress.png";
    String BUY = "ui/pay.png";
    String BUY_PRESS = "ui/payPress.png";

    //Screen
    String GAMEOVER = "screen/gameover.png";

    //Highscore
    String HS_FILE = "highscore.txt";

    //Coins file
    String CN_FILE = "coins.txt";

    //Skins file
    String SKINS_LIST = "skins.txt";
}
