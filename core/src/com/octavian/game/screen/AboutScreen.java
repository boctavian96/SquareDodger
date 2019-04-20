package com.octavian.game.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.octavian.game.DodgerMain;
import com.octavian.game.util.FontFactory;


/**
 * Created by octavian on 4/9/19.
 */

public class AboutScreen extends AbstractGameScreen{

    private DodgerMain game;
    private Batch batch;
    private FontFactory factory;
    private BitmapFont font;


    public AboutScreen(DodgerMain game){
        super();
        this.game = game;
        this.batch = game.getBatch();
        this.factory = FontFactory.getInstance();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void draw() {

    }
}
