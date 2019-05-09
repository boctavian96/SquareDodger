package com.octavian.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.octavian.game.DodgerMain;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;
import com.octavian.game.datamodel.Skin;
import com.octavian.game.util.FontFactory;
import com.octavian.game.util.GameInput;
import com.octavian.game.util.Utils;

/**
 * Created by octavian on 4/9/19.
 */

public class SkinsScreen extends AbstractGameScreen {

    private DodgerMain game;
    private Batch batch;
    private FontFactory factory;
    private GameInput touchInput;
    private Array<Skin> skins;
    private Skin selectedSkin;

    private BitmapFont font32;
    private BitmapFont font16Yellow;

    private boolean isTouchReleased;

    public SkinsScreen(DodgerMain game){
        this.game = game;
        batch = new SpriteBatch();
        factory = FontFactory.getInstance();
        touchInput = new GameInput();

        camera = new OrthographicCamera(Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        viewport = new FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera);

        font32 = factory.generateFont(FontFactory.FONT_PRESS_START2P, 32, Color.WHITE);
        font16Yellow = factory.generateFont(FontFactory.FONT_PRESS_START2P, 16, Color.YELLOW);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        skins = Utils.getDummySkins();
        isTouchReleased = true;

        instantiateUI();
    }

    @Override
    public void update(float delta) {
        Utils.clearScreen();

        selectedSkin = skins.get(Assets.selectedTexture);

        if(touchInput.isSwipeLeft()){
            Gdx.app.log("SWIPE", "Swiped left!");
            if(Assets.selectedTexture < skins.size - 1 && isTouchReleased) {
                Assets.selectedTexture++;
                isTouchReleased = false;
            }
        }

        if(touchInput.isSwipeRight()){
            Gdx.app.log("SWIPE", "Swiped right!");
            if(Assets.selectedTexture > 0 && isTouchReleased) {
                Assets.selectedTexture--;
                isTouchReleased = false;
            }
        }

        if (!Gdx.input.isTouched()){
            isTouchReleased = true;
        }


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
            font32.draw(batch, "Buy new Skins", Config.WORLD_WIDTH/4, Config.WORLD_HEIGHT - Config.WORLD_UNIT);
            font32.draw(batch, "Coins: 500", Config.WORLD_WIDTH/4, Config.WORLD_HEIGHT - 3 * Config.WORLD_UNIT );

            //Skin Details
            font16Yellow.draw(batch, selectedSkin.getName(), Config.WORLD_WIDTH/3, Config.WORLD_HEIGHT/2 + 4 * Config.WORLD_UNIT);
            batch.draw(selectedSkin.getTexture(), Config.WORLD_WIDTH/2, Config.WORLD_HEIGHT/2);
            font16Yellow.draw(batch, "Cost: " + selectedSkin.getCost(), Config.WORLD_WIDTH/3, Config.WORLD_HEIGHT/2 - 2 * Config.WORLD_UNIT);
        batch.end();

        stage.draw();
    }

    private void instantiateUI(){
        Table uiTable = new Table();
        Table skinsTable = new Table();

        uiTable.defaults().space(Config.WORLD_UNIT);
        skinsTable.defaults().space(2 * Config.WORLD_UNIT);

        Assets.back.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                Assets.music.pause();
                stage.dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Assets.buy.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
            }
        });

        stage.addActor(Assets.back);
        stage.addActor(Assets.buy);

        uiTable.add(Assets.back).space(Config.WORLD_WIDTH - 6 * Config.WORLD_UNIT);
        uiTable.add(Assets.buy);

        uiTable.setFillParent(true);
        uiTable.pad(Config.WORLD_UNIT);
        uiTable.align(Align.bottom);
        uiTable.pack();
        uiTable.setDebug(true);

        skinsTable.add(Assets.skinUI1);
        skinsTable.add(Assets.skinUI2);
        skinsTable.add(Assets.skinUI3).row();
        skinsTable.add(Assets.skinUI4);
        skinsTable.add(Assets.skinUI5);
        skinsTable.add(Assets.skinUI6).row();
        skinsTable.add(Assets.skinUI7);
        skinsTable.add(Assets.skinUI8);
        skinsTable.add(Assets.skinUI9).row();


        skinsTable.setFillParent(true);
        skinsTable.pad(Config.WORLD_UNIT);
        skinsTable.align(Align.center);
        skinsTable.pack();
        skinsTable.setDebug(true);

        stage.addActor(uiTable);
        //stage.addActor(skinsTable);
    }
}
