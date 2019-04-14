package com.octavian.game.windows;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.octavian.game.DodgerMain;

/**
 * Created by octavian on 4/9/19.
 */

public class MainMenuScreen extends AbstractGameScreen {

    private DodgerMain game;
    private SpriteBatch batch;

    public MainMenuScreen(DodgerMain main){
        super();
        this.game = main;
        this.batch = game.getBatch();
    }

    public void update(){

    }

    public void draw(){

    }

    public void render(float delta){
        update();
        draw();
    }
}