package com.octavian.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.octavian.game.DodgerMain;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;
import com.octavian.game.util.FontFactory;
import com.octavian.game.util.Utils;
import com.octavian.game.world.WorldRenderer;

/**
 * Created by octavian on 4/9/19.
 */

public class MainMenuScreen extends AbstractGameScreen {

    private final DodgerMain game;
    private SpriteBatch batch;
    private FontFactory factory;
    private BitmapFont font32;
    private WorldRenderer worldRenderer;

    public MainMenuScreen(DodgerMain main){
        game = main;
        batch = game.getBatch();
        factory = FontFactory.getInstance();
        font32 = factory.generateFont(FontFactory.FONT_PRESS_START2P, 32, Color.WHITE);
        stage = new Stage(viewport, batch);
        worldRenderer = new WorldRenderer();

        instantiateUI();

        Assets.music.play();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void update(float delta){
        camera.update();
        worldRenderer.updateObstacles(delta);
        Utils.clearScreen();
    }

    @Override
    public void draw(){
        batch.setProjectionMatrix(camera.combined);
        //batch.disableBlending();

        batch.begin();
            //FIXME: Read score and coins from xml.
            font32.draw(batch, "High Score: 1000", Config.WORLD_WIDTH / 6, 100);
            font32.draw(batch, "Coins: " + "500", Config.WORLD_WIDTH / 3 - Config.WORLD_UNIT, Config.WORLD_HEIGHT - Config.WORLD_UNIT );

            worldRenderer.drawObstacles(batch);
        batch.end();

        stage.draw();

    }

    @Override
    public void render(float delta){
        update(delta);
        draw();
        stage.act(delta);
    }

    private void instantiateUI(){
        Table table = new Table();
        table.defaults().space(Config.WORLD_UNIT);

        Assets.play.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                worldRenderer = null;
                stage.dispose();
                dispose();
                game.setScreen(new PlayScreen(game));
            }
        });

        Assets.about.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Assets.music.pause();
                worldRenderer = null;
                stage.dispose();
                dispose();
                game.setScreen(new AboutScreen(game));
            }
        });

        Assets.skins.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                game.setScreen(new SkinsScreen(game));
            }
        });

        Assets.exit.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Gdx.app.exit();
            }
        });

        stage.addActor(Assets.play);
        stage.addActor(Assets.about);
        stage.addActor(Assets.skins);
        stage.addActor(Assets.exit);

        table.add(Assets.play).row();
        table.add(Assets.skins).row();
        table.add(Assets.about).row();
        table.add(Assets.exit).row();

        table.setFillParent(true);
        table.center();
        table.pack();

        stage.addActor(table);
    }
}