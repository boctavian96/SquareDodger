package com.octavian.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.octavian.game.DodgerMain;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;
import com.octavian.game.config.Settings;
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

    public MainMenuScreen(DodgerMain game){
        super();
        this.game = game;
        batch = new SpriteBatch();
        factory = FontFactory.getInstance();
        font32 = factory.generateFont(FontFactory.FONT_PRESS_START2P, 32, Color.WHITE);
        stage = new Stage(viewport, batch);
        worldRenderer = new WorldRenderer();

        instantiateUI();

        Assets.playMusic(Assets.music);
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
        Table settingsTable = new Table();

        table.defaults().space(Config.WORLD_UNIT);
        settingsTable.defaults().space(Config.WORLD_UNIT);

        Assets.play.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                worldRenderer = null;
                stage.dispose();
                Gdx.app.log("SWITCH", "Changed to PlayScreen");
                game.setScreen(new PlayScreen(game));
            }
        });

        Assets.about.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Assets.stopMusic(Assets.music);
                worldRenderer = null;
                stage.dispose();
                Gdx.app.log("SWITCH", "Changed to AboutScreen");
                game.setScreen(new AboutScreen(game));
            }
        });

        Assets.skins.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Assets.stopMusic(Assets.music);
                worldRenderer = null;
                stage.dispose();
                Gdx.app.log("SWITCH", "Changed to SkinsScreen");
                game.setScreen(new SkinsScreen(game));
            }
        });

        Assets.exit.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Gdx.app.log("EXIT", "Exiting the app");
                Gdx.app.exit();
            }
        });


        Assets.sound.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Settings.switchSound();
                Assets.playMusic(Assets.music);
            }
        });


        stage.addActor(Assets.play);
        stage.addActor(Assets.about);
        stage.addActor(Assets.skins);
        stage.addActor(Assets.exit);
        stage.addActor(Assets.sound);

        table.add(Assets.play).row();
        table.add(Assets.skins).row();
        table.add(Assets.about).row();
        table.add(Assets.exit).row();

        settingsTable.add(Assets.sound);

        table.setFillParent(true);
        table.center();
        table.pack();

        settingsTable.setFillParent(true);
        settingsTable.align(Align.topLeft);
        settingsTable.pack();

        stage.addActor(table);
        stage.addActor(settingsTable);
    }
}