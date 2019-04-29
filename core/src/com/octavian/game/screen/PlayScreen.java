package com.octavian.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.octavian.game.DodgerMain;
import com.octavian.game.Score;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;
import com.octavian.game.datamodel.Player;
import com.octavian.game.util.FontFactory;
import com.octavian.game.util.GameInput;
import com.octavian.game.util.Utils;
import com.octavian.game.world.WorldRenderer;

import javax.rmi.CORBA.Util;

/**
 * Created by octavian on 4/9/19.
 */

public class PlayScreen extends AbstractGameScreen {

    private DodgerMain game;
    private SpriteBatch batch;
    private FontFactory factory;
    private WorldRenderer worldRenderer;
    private Player player;
    private GameInput input;
    private Score score;
    private Vector3 touchPoint;

    private BitmapFont font32;

    public PlayScreen(DodgerMain main){
        camera = new OrthographicCamera(Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        camera.position.set(510, 810, 0);
        viewport = new FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera);
        stage = new Stage();

        game = main;
        batch = new SpriteBatch();
        factory = FontFactory.getInstance();
        font32 = factory.generateFont(FontFactory.FONT_PRESS_START2P, 32, Color.WHITE);
        worldRenderer = new WorldRenderer();
        player = new Player(Assets.skinTextures.first(), 100, 100);
        input = new GameInput();
        score = new Score();
        touchPoint = new Vector3(0, 0,0);

        Gdx.input.setInputProcessor(stage);
        instantiateHUD();

    }

    @Override
    public void update(float delta) {
        camera.update();
        if(Gdx.input.isTouched()){
            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            player.update(touchPoint);

            Gdx.app.log("Touch X", "" + touchPoint.x);
            Gdx.app.log("Touch Y", "" + touchPoint.y);
        }

        worldRenderer.updateObstacles(delta);
        worldRenderer.checkIfObstacleIsNeeded();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenuScreen(game));
        }

        if(worldRenderer.isPlayerColliding(player)){
            //game.setScreen(new MainMenuScreen(game));
        }

        Utils.clearScreen();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
        stage.act(delta);
    }

    @Override
    public void draw() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
            player.draw(batch);
            font32.draw(batch, "Score: " + worldRenderer.getScore(), Config.WORLD_WIDTH / 2, Config.WORLD_HEIGHT - Config.WORLD_UNIT);

            worldRenderer.drawObstacles(batch);
        batch.end();

        stage.draw();
    }

    private void instantiateHUD(){
        Table table = new Table();
        table.defaults().space(Config.WORLD_UNIT);

        table.setFillParent(true);
        table.center();
        table.pack();

        stage.addActor(table);
    }
}
