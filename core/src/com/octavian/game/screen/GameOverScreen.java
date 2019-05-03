package com.octavian.game.screen;

import com.octavian.game.DodgerMain;
import com.octavian.game.Score;
import com.octavian.game.datamodel.Coins;
import com.octavian.game.util.Utils;

/**
 * Created by octavian on 5/3/19.
 */

public class GameOverScreen extends AbstractGameScreen {
    private DodgerMain game;

    private int coins;
    private String score;

    public GameOverScreen(DodgerMain game, int coins, String score){
        super();
        this.game = game;

        this.coins = coins;
        this.score = score;
    }

    @Override
    public void render(float delta){
        update(delta);
        draw();
    }

    @Override
    public void draw(){

    }

    @Override
    public void update(float delta){
        Utils.clearScreen();
    }
}log