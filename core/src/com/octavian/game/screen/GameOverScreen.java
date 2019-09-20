package com.octavian.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.octavian.game.DodgerMain;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;
import com.octavian.game.util.FontFactory;
import com.octavian.game.util.Utils;

/**
 * Created by octavian on 5/3/19.
 */

public class GameOverScreen extends AbstractGameScreen {
    private DodgerMain game;

    private int coins;
    private String score;
    private Batch batch;

    BitmapFont font16;

    public GameOverScreen(DodgerMain game, int coins, String score){
        super();
        this.game = game;

        FontFactory factory = FontFactory.getInstance();
        font16 = factory.generateFont(FontFactory.FONT_OPEN_SANS_BOLD, 16, Color.WHITE);

        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        this.coins = coins;
        this.score = score;

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        instantiateUI();
    }

    @Override
    public void render(float delta){
        update(delta);
        draw();
        stage.act(delta);

        if(Utils.checkBack()){
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }

    }

    @Override
    public void draw(){
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
            font16.draw(batch, "Congratulations!", Config.WORLD_WIDTH / 3, Config.WORLD_HEIGHT - 2 * Config.WORLD_UNIT);
            font16.draw(batch, "Your score is " + score, Config.WORLD_WIDTH / 3, Config.WORLD_HEIGHT - 4 * Config.WORLD_UNIT);
            font16.draw(batch, "and you collected " + coins + " coins", Config.WORLD_WIDTH / 4, Config.WORLD_HEIGHT - 5 * Config.WORLD_UNIT);
        batch.end();

        stage.draw();
    }

    @Override
    public void update(float delta){
        Utils.clearScreen();
        camera.update();
    }

    private void instantiateUI(){

        Assets.reset.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                dispose();
                Gdx.app.log("SWITCH", "Start a new game!");
                game.setScreen(new PlayScreen(game));
            }

        });

        Assets.back.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                dispose();
                Gdx.app.log("SWITCH", "Back to MainMenu");
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.addActor(Assets.reset);
        stage.addActor(Assets.back);

        Table buttonsTable = new Table();
        Table textTable = new Table();

        buttonsTable.defaults().space(Config.WORLD_UNIT);
        textTable.defaults().space(Config.WORLD_UNIT);

        buttonsTable.add(Assets.back);
        buttonsTable.add(Assets.reset);

        buttonsTable.setFillParent(true);
        buttonsTable.center();
        buttonsTable.pack();

        textTable.setFillParent(true);
        textTable.align(Align.top);
        textTable.pack();

        stage.addActor(buttonsTable);
        stage.addActor(textTable);

    }

    public void dispose(){
        stage.dispose();
        Assets.back.clearListeners();
        Assets.reset.clearListeners();
    }
}