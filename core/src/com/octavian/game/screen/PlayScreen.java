package com.octavian.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.octavian.game.DodgerMain;
import com.octavian.game.config.Assets;
import com.octavian.game.datamodel.Player;
import com.octavian.game.util.FontFactory;
import com.octavian.game.util.GameInput;
import com.octavian.game.util.Utils;
import com.octavian.game.world.WorldRenderer;

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

    private BitmapFont font;

    public PlayScreen(DodgerMain main){
        game = main;
        batch = game.getBatch();
        factory = FontFactory.getInstance();
        font = factory.generateFont(FontFactory.FONT_PRESS_START2P, 16, Color.WHITE);
        worldRenderer = new WorldRenderer();
        player = new Player(Assets.skinTextures.first(), 100, 100);
        input = new GameInput();
    }

    @Override
    public void update(float delta) {
        camera.update();
        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPoint);

        worldRenderer.updateObstacles(delta);
        worldRenderer.checkIfObstacleIsNeeded();
        //player.update(Gdx.input.getX(), Gdx.input.getY());

        input.queryInput(player);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenuScreen(game));
        }

        if(worldRenderer.isPlayerColliding(player)){
           // game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        Utils.clearScreen();
        draw();
        update(delta);
    }

    @Override
    public void draw() {

        batch.begin();
            worldRenderer.drawObstacles(batch);
            player.draw(batch);
        batch.end();
    }
}
