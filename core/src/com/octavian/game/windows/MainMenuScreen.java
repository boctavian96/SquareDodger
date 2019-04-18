package com.octavian.game.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.octavian.game.DodgerMain;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;
import com.octavian.game.util.FontFactory;
import com.octavian.game.util.Utils;

/**
 * Created by octavian on 4/9/19.
 */

public class MainMenuScreen extends AbstractGameScreen {

    final float PLAY_POSITION = Gdx.graphics.getHeight() / 4; //???????

    private DodgerMain game;
    private SpriteBatch batch;
    private FontFactory factory;
    private BitmapFont font;

    public MainMenuScreen(DodgerMain main){
        super();
        this.game = main;
        this.batch = game.getBatch();
        this.factory = FontFactory.getInstance();
        this.font = factory.generateFont(FontFactory.FONT_PRESS_START2P, 16, Color.WHITE);

        addListeners();
        stage.addActor(Assets.play);
        stage.addActor(Assets.about);
        stage.addActor(Assets.skins);
        stage.addActor(Assets.exit);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void update(float delta){
        camera.update();
    }

    @Override
    public void draw(){
        GL20 gl = Gdx.gl;
        Utils.clearScreen();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.disableBlending();

        batch.begin();
            //FIXME: Read score and coins from xml.
            font.draw(batch, "HighScore: " + "1000", Gdx.graphics.getWidth()/2 - 150, Gdx.graphics.getHeight()-200);
            font.draw(batch, "Coins: " + "500", Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight() - 150 );
        batch.end();
    }

    @Override
    public void render(float delta){
        update(delta);
        draw();
        stage.act(delta);
        stage.draw();
    }

    private void addListeners(){
        Assets.play.setPosition(Gdx.graphics.getWidth()/2 - Config.WORLD_UNIT, PLAY_POSITION);
        Assets.play.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                game.setScreen(new PlayScreen(game));
            }
        });

        Assets.about.setPosition(Gdx.graphics.getWidth()/5 - 10, PLAY_POSITION - 100F);
        Assets.about.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                game.setScreen(new AboutScreen(game));
            }
        });

        Assets.skins.setPosition(Gdx.graphics.getWidth()/5 - 10, PLAY_POSITION - 200F);
        Assets.skins.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                game.setScreen(new SkinsScreen(game));
            }
        });

        Assets.exit.setPosition(Gdx.graphics.getWidth() / 5 - 10, PLAY_POSITION - 300F);
        Assets.exit.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Gdx.app.exit();
            }
        });
    }
}