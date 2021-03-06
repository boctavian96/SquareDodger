package com.octavian.game.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.octavian.game.DodgerMain;
import com.octavian.game.util.FontFactory;

/**
 * Created by octavian on 4/9/19.
 */

//TODO: For the next release. Not for P.1.3.100
public class HighScoreScreen extends AbstractGameScreen{

    private DodgerMain game;
    private Batch batch;
    private FontFactory factory;
    private BitmapFont font;


    public HighScoreScreen(DodgerMain game){
        super();
        this.game = game;
        this.batch = new SpriteBatch();
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
