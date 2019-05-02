package com.octavian.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.octavian.game.DodgerMain;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;
import com.octavian.game.util.FontFactory;
import com.octavian.game.util.Utils;


/**
 * Created by octavian on 4/9/19.
 */

public class AboutScreen extends AbstractGameScreen{

    private final DodgerMain game;
    private String thankYou;
    private Batch batch;
    private BitmapFont font16;
    private BitmapFont font32;
    private FontFactory factory;


    public AboutScreen(DodgerMain game){
        super();
        this.game = game;
        batch = new SpriteBatch();
        factory = FontFactory.getInstance();
        factory = FontFactory.getInstance();
        font32 = factory.generateFont(FontFactory.FONT_PRESS_START2P, 32, Color.YELLOW);
        font16 = factory.generateFont(FontFactory.FONT_PRESS_START2P, 16, Color.WHITE);
        stage = new Stage(viewport, batch);
        thankYou = Utils.readAbout();
        loadUI();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float delta) {
        camera.update();
        stage.act(delta);
        Utils.clearScreen();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void draw() {
        batch.begin();
            font32.draw(batch, "Square Dodger", screenWidth/4, screenHeight - Config.WORLD_UNIT);
            font16.draw(batch, thankYou, screenWidth/6,  screenHeight - Config.WORLD_UNIT * 9);
        batch.end();

        stage.draw();
    }

    private void loadUI(){

        Assets.back.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Assets.back.setPosition(screenWidth/2 - Assets.back.getWidth()/2, 100);

        stage.addActor(Assets.back);
    }
}
