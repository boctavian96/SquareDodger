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
import com.octavian.game.util.SaveState;
import com.octavian.game.datamodel.Coins;
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
    private BitmapFont font16Green;

    private Coins availableCoins;

    private boolean isTouchReleased;
    private boolean isSwiped;

    public SkinsScreen(DodgerMain game){
        super();
        this.game = game;
        SaveState.create(false);
        batch = new SpriteBatch();
        factory = FontFactory.getInstance();
        touchInput = new GameInput();
        skins = SaveState.readSkins();

        camera = new OrthographicCamera(Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        viewport = new FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera);

        font32 = factory.generateFont(FontFactory.FONT_PRESS_START2P, 32, Color.WHITE);
        font16Yellow = factory.generateFont(FontFactory.FONT_PRESS_START2P, 16, Color.YELLOW);
        font16Green = factory.generateFont(FontFactory.FONT_PRESS_START2P, 16, Color.GREEN);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        availableCoins = SaveState.readCoins();

        isTouchReleased = true;
        isSwiped = false;

        instantiateUI();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
        stage.act(delta);

        if(Utils.checkBack()){
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void update(float delta) {
        Utils.clearScreen();

        selectedSkin = skins.get(Assets.selectedTexture);
        swipe();
    }

    @Override
    public void draw() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
            font32.draw(batch, "Buy new Skins", Config.WORLD_WIDTH/4, Config.WORLD_HEIGHT - Config.WORLD_UNIT);
            font32.draw(batch, "Coins: " + availableCoins.getCoins(), Config.WORLD_WIDTH/4 + Config.WORLD_UNIT, Config.WORLD_HEIGHT - 3 * Config.WORLD_UNIT );

            if(!isSwiped){
                font16Green.draw(batch, "Swipe Right", Config.WORLD_WIDTH/4 + 2 * Config.WORLD_UNIT, Config.WORLD_HEIGHT / 2 + 6 * Config.WORLD_UNIT);
            }

            //Skin Details
            if(selectedSkin.isUnlocked()) {
                batch.draw(selectedSkin.getTexture(), Config.WORLD_WIDTH / 2 - Config.WORLD_UNIT, Config.WORLD_HEIGHT / 2);
                font16Yellow.draw(batch, "Selected skin: " + selectedSkin.getName(), Config.WORLD_WIDTH/4, Config.WORLD_HEIGHT/2 + 4 * Config.WORLD_UNIT);
            }else{
                batch.draw(Assets.lockTexture, Config.WORLD_WIDTH/2 - 2 * Config.WORLD_UNIT, Config.WORLD_HEIGHT/2);
                font16Yellow.draw(batch, "Cost: " + selectedSkin.getCost(), Config.WORLD_WIDTH/2 - 3 * Config.WORLD_UNIT, Config.WORLD_HEIGHT/2 - 2 * Config.WORLD_UNIT);
            }

        batch.end();

        stage.draw();
    }

    private void swipe(){
        if(touchInput.isSwipeLeft()){
            Gdx.app.log("SWIPE", "Swiped left!");
            if(Assets.selectedTexture < skins.size - 1 && isTouchReleased) {
                Assets.selectedTexture++;
                isTouchReleased = false;
                isSwiped = true;
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
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Assets.buy.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                selectedSkin = skins.get(Assets.selectedTexture);
                if(selectedSkin.isUnlocked()){
                    Gdx.app.log("WARN", "Item already bought!");
                }else {
                    if(availableCoins.payCoins(selectedSkin.getCost())) {
                        selectedSkin.unlock();
                        Assets.buySkin.play();git
                        SaveState.saveCoins(availableCoins, false);
                        SaveState.saveSkins(skins);
                        Gdx.app.log("INFO", "Skin " + selectedSkin.getName() + " is unlocked");
                    }
                }
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
        uiTable.setDebug(false);

        stage.addActor(uiTable);
    }

    public void dispose(){
        stage.dispose();
        Assets.buy.clearListeners();
        Assets.back.clearListeners();
    }
}