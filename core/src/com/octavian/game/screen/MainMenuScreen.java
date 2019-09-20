package com.octavian.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.octavian.game.util.SaveState;
import com.octavian.game.config.Settings;
import com.octavian.game.util.FontFactory;
import com.octavian.game.util.Utils;
import com.octavian.game.world.WorldRenderer;
import com.octavian.tools.DebugGrid;

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
        font32 = factory.generateFont(FontFactory.FONT_OPEN_SANS, 32, Color.WHITE);
        stage = new Stage(viewport, batch);
        worldRenderer = new WorldRenderer();

        instantiateUI();

        Assets.playMusic(Assets.music);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void update(float delta){
        camera.update();
        worldRenderer.updateObstacles(delta);
        Assets.playMusic(Assets.music);
        Utils.clearScreen();
    }

    @Override
    public void draw(){
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
            //FIXME: Read score and coins from xml.
            font32.draw(batch, "High Score: " + SaveState.readHighscore().getScore(), Config.WORLD_WIDTH / 3 + Config.WORLD_UNIT, 100);
            font32.draw(batch, "Coins: " + SaveState.readCoins().getCoins(), Config.WORLD_WIDTH / 3 + 2 * Config.WORLD_UNIT, Config.WORLD_HEIGHT - Config.WORLD_UNIT );

            worldRenderer.drawObstacles(batch);
        batch.end();

        stage.draw();

    }

    public void drawDebug(){
        DebugGrid.render(shapeRenderer);
    }

    @Override
    public void render(float delta){
        update(delta);
        draw();
        stage.act(delta);

        if(Config.DEBUG_MODE){
            drawDebug();
        }
    }

    private void instantiateUI(){
        Table table = new Table();
        final Table settingsTable = new Table();

        table.defaults().space(Config.WORLD_UNIT);

        Assets.play.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                worldRenderer = null;
                dispose();
                Gdx.app.log("SWITCH", "Changed to PlayScreen");
                game.setScreen(new PlayScreen(game));
            }
        });

        Assets.about.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Assets.stopMusic(Assets.music);
                Assets.playMusic(Assets.aboutMusic);
                worldRenderer = null;
                dispose();
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
                dispose();
                Gdx.app.log("SWITCH", "Changed to SkinsScreen");
                game.setScreen(new SkinsScreen(game));
            }
        });

        Assets.exit.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Gdx.app.log("EXIT", "Exiting the app");
                dispose();
                Gdx.app.exit();
            }
        });


        Assets.sound.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Settings.switchSound();
                Assets.playMusic(Assets.music);
                settingsTable.removeActor(Assets.sound);
                settingsTable.add(Assets.soundMute);
                Assets.soundMute.setVisible(true);
                Gdx.app.log("INFO", "Stop the music");
            }
        });

        Assets.soundMute.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Settings.switchSound();
                Assets.playMusic(Assets.music);
                settingsTable.removeActor(Assets.soundMute);
                settingsTable.add(Assets.sound);
                Assets.sound.setVisible(true);
                Gdx.app.log("INFO", "Play the music");
            }
        });

        stage.addActor(Assets.play);
        stage.addActor(Assets.about);
        stage.addActor(Assets.skins);
        stage.addActor(Assets.exit);
        stage.addActor(Assets.sound);
        stage.addActor(Assets.soundMute);

        table.add(Assets.play).row();
        table.add(Assets.skins).row();
        table.add(Assets.about).row();
        table.add(Assets.exit).row();

        if(Settings.isSoundEnabled) {
            settingsTable.add(Assets.sound);
            Assets.soundMute.setVisible(false);
        }else{
            settingsTable.add(Assets.soundMute);
            Assets.sound.setVisible(false);
        }

        table.setFillParent(true);
        table.center();
        table.pack();

        settingsTable.setFillParent(true);
        settingsTable.align(Align.topLeft);
        settingsTable.pack();

        stage.addActor(table);
        stage.addActor(settingsTable);
    }

    public void dispose(){
        stage.dispose();
        Assets.sound.clearListeners();
        Assets.soundMute.clearListeners();
        Assets.play.clearListeners();
        Assets.skins.clearListeners();
        Assets.about.clearListeners();
    }
}