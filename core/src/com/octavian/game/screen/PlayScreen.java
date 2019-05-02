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
import com.octavian.game.datamodel.Coins;
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
    private Vector3 touchPoint;
    private Coins coins;

    private BitmapFont font32;

    public PlayScreen(DodgerMain game){
        this.game = game;

        camera = new OrthographicCamera(Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        viewport = new FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

        stage = new Stage(viewport, batch);

        factory = FontFactory.getInstance();
        font32 = factory.generateFont(FontFactory.FONT_PRESS_START2P, 32, Color.WHITE);
        worldRenderer = new WorldRenderer();
        player = new Player(Assets.skinTextures.get(Assets.selectedTexture), 100, 100);
        touchPoint = new Vector3(0, 0,0);
        coins = new Coins(0);

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

        worldRenderer.createCoin();
        worldRenderer.updateCoin(delta);
        worldRenderer.updateObstacles(delta);
        worldRenderer.checkIfObstacleIsNeeded();

        if(worldRenderer.isPlayerColliding(player)){
            //game.setScreen(new MainMenuScreen(game));
        }

        if(worldRenderer.isCoinColliding(player)){
            coins.addCoins();
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
            font32.draw(batch, "Score: " + worldRenderer.getScore(), Config.WORLD_WIDTH / 3, Config.WORLD_HEIGHT - Config.WORLD_UNIT );

            worldRenderer.drawObstacles(batch);
            worldRenderer.dropCoin(batch);
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
