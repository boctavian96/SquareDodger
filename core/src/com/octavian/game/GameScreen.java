package com.octavian.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.octavian.game.config.Config;
import com.octavian.game.config.GameState;
import com.octavian.game.entity.Player;
import com.octavian.game.entity.Obstacle;
import com.octavian.game.util.GameInput;
import com.octavian.game.util.Utils;

import java.util.List;

/**
 * Created by octavian on 2/21/18.
 */

public class GameScreen extends ScreenAdapter {

    private GameInput gameInput;

    private Score playerScore;
    private Coins playerCoins;
    private Music music;

    private SpriteBatch batch;
    private List<Texture> textures;
    private List<Texture> playerTextures;

    private Player player;
    private static int obstacle_nr = 8;
    private static int obstacle_create = 0;
    private Array<Obstacle> obstacles;


    private static GameState state = GameState.MENU;

    //Camera
    private Viewport viewport;
    private Camera camera;
    private GlyphLayout layout;
    private BitmapFont bitmapFont;

    //User interface
    private Stage stage;
    private Stage stageAbout;
    private Stage stageSkins;
    private Texture playTexture;
    private Texture playPressTexture;
    private Texture aboutTexture;
    private Texture aboutPressTexture;
    private Texture skinsTexture;
    private Texture skinsPressTexture;
    private Texture exitTexture;
    private Texture exitPressTexture;
    private Texture backTexture;
    private Texture backPressTexture;
    private Texture gameover;

    private ImageButton play;
    private ImageButton about;
    private ImageButton skins;
    private ImageButton exit;
    private ImageButton back;



    @Override
    public void show(){

        Gdx.input.setCatchBackKey(true); // Back from phone

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2,  Gdx.graphics.getHeight() / 2, 0);

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        layout = new GlyphLayout();
        bitmapFont = new BitmapFont();

        playerTextures = com.octavian.game.util.Utils.loadTextures(Config.SKINS_ARRAY);
        textures = com.octavian.game.util.Utils.loadTextures(Config.SQUARES);
        gameover = new Texture(Gdx.files.internal(Config.GAMEOVER));

        music = Gdx.audio.newMusic(Gdx.files.internal(Config.MUSIC1));
        gameInput = new GameInput();


        playerScore = new Score();
        playerCoins = new Coins();
        player = new Player(playerTextures.get(com.octavian.game.util.Utils.randomNumber(playerTextures.size())), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        obstacles = new Array<Obstacle>();
        batch = new SpriteBatch();

        instantiateUI();
    }

    @Override
    public void render(float delta){
        setInputProcessor(state);
        com.octavian.game.util.Utils.clearScreen();
        music.play();

        switch (state){
            case PLAYING:
                    gameInput.queryInput(player);
                    update(delta);
                    draw();
                break;

            case GAMEOVER:
                    draw();
                    stageSkins.act(delta);
                    stageSkins.draw();
                break;

            case ABOUT:
                    draw();
                    state = com.octavian.game.util.Utils.checkBack(state); // Goes Back to menu
                    stageAbout.act(delta);
                    stageAbout.draw();
                break;

            case SKINS:
                    draw();
                    state = com.octavian.game.util.Utils.checkBack(state);
                    stageSkins.act(delta);
                    stageSkins.draw();
                break;

            case MENU:
                    update(delta);
                    draw();
                    stage.act(delta);
                    stage.draw();
                break;
        }

    }

    private void update(float delta){
        updateObstacles(delta);

        if(checkCollision() && state.equals(GameState.PLAYING)){
            playerCoins.addCoins();
            if(playerScore.isScoreBetter(playerScore.getScore())) {
                com.octavian.game.util.Utils.writeGameFile(playerScore.getScore(), 'h');
            }
            com.octavian.game.util.Utils.writeGameFile(playerCoins.getCoins(), 'c');
            state = GameState.GAMEOVER;

        }
    }

    private void createNewObstacle(){
        if(obstacles.size < Config.MAXIMUM_OBSTACLES) {
            Obstacle newObstacle = new Obstacle(textures.get(com.octavian.game.util.Utils.randomNumber(Config.NUMBER_OF_TEXTURES)));
            obstacles.add(newObstacle);
            obstacle_create++;
        }
    }

    private boolean checkCollision(){
        for(Obstacle i : obstacles){
            if(i.isPlayerColliding(player)){
                return true;
            }
        }
        return false;
    }

    private void checkIfObstacleIsNeeded(){
        if(obstacles.size < obstacle_nr){
            createNewObstacle();
        }
        if(obstacle_create == 10){
            obstacle_nr++;
            obstacle_create = 0;
        }
    }

    private void updateObstacles(float delta){
        for(Obstacle i : obstacles){
            i.update(delta);
        }

        checkIfObstacleIsNeeded();
        removeObstaclesIfPassed();
    }


    private void removeObstaclesIfPassed(){
        for(Obstacle i : obstacles){
            if (i.getY() < 0){
                obstacles.removeValue(i, true);
                if(state.equals(GameState.PLAYING)) {
                    playerScore.addScore();
                }
            }
        }
    }

    private void drawObstacles(){
       for(Obstacle i : obstacles){
           i.draw(batch);
       }
    }

    private void drawAbout(){
        StringBuilder sb = new StringBuilder();

        sb.append("A mini game made using Libgdx. \nI want to thank to everybody who is playing.\n");
        sb.append("And to everybody who supported me.\nHave fun and a nice day :)");

        BitmapFont message = bitmapFont;
        message.getData().setScale(3, 3);

        message.draw(batch, sb.toString(), 0, Gdx.graphics.getHeight());

    }

    private void drawSkinMenu(){
        for(int i = 0; i < playerTextures.size(); i++){
            batch.draw(playerTextures.get(i), 40, Gdx.graphics.getHeight() - i * 200);
        }
    }

    private void draw(){

        switch(state) {

            case PLAYING:
                batch.begin();
                    batch.draw(player.getTexture(), player.getX(), player.getY());
                    drawObstacles();
                    com.octavian.game.util.Utils.drawText(String.valueOf(playerScore.getScore()), bitmapFont, batch, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - 100);
                batch.end();
                break;

            case MENU:
                batch.begin();
                    drawObstacles();
                    Utils.drawText("HighScore : " + Utils.getGameFile('h'), bitmapFont, batch, Gdx.graphics.getWidth()/2 - 150, Gdx.graphics.getHeight() - 200);
                    Utils.drawText("Coins : " + Utils.getGameFile('c'), bitmapFont, batch, Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight() - 150);
                batch.end();
                break;

            case ABOUT:
                batch.begin();
                    drawAbout();
                batch.end();

                break;

            case SKINS:
                batch.begin();
                    drawSkinMenu();
                    Utils.drawText("Coins : " + Utils.getGameFile('c'), bitmapFont, batch, Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight() - 150);
                batch.end();
                break;

            case GAMEOVER:
                batch.begin();
                    Utils.drawText("You've made : " + playerScore.getScore() + "\nCoins : 5", bitmapFont, batch, Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight()/2);
                batch.end();
                break;
        }

    }

    private void restart(){

        if(!state.equals(GameState.MENU)) {
            playerScore.clear();
            obstacle_create = 0;
            obstacle_nr = 8;
            player = new Player(playerTextures.get(com.octavian.game.util.Utils.randomNumber(playerTextures.size())), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            obstacles.clear();
        }
    }

    /**
     * This function executes only once
     */
    private void instantiateUI(){
        playTexture = new Texture(Gdx.files.internal(Config.PLAY));
        playPressTexture = new Texture(Gdx.files.internal(Config.PLAY_PRESS));

        aboutTexture = new Texture(Gdx.files.internal(Config.ABOUT));
        aboutPressTexture = new Texture(Gdx.files.internal(Config.ABOUT_PRESS));

        skinsTexture = new Texture(Gdx.files.internal(Config.SKINS));
        skinsPressTexture = new Texture(Gdx.files.internal(Config.SKINS_PRESS));

        exitTexture = new Texture(Gdx.files.internal(Config.EXIT));
        exitPressTexture = new Texture(Gdx.files.internal(Config.EXIT_PRESS));

        backTexture = new Texture(Gdx.files.internal(Config.BACK));
        backPressTexture = new Texture(Gdx.files.internal(Config.BACK_PRESS));



        play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)), new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        play.setPosition(Gdx.graphics.getWidth()/5 - 10, Gdx.graphics.getHeight()/6);
        play.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                if(state.equals(GameState.MENU)){
                    state = GameState.PLAYING;
                    restart();
                }
            }
        });

        about = new ImageButton(new TextureRegionDrawable(new TextureRegion(aboutTexture)), new TextureRegionDrawable(new TextureRegion(aboutPressTexture)));
        about.setPosition(0, 0);
        about.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                if(state.equals(GameState.MENU)){
                    state = GameState.ABOUT;
                }
            }
        });

        skins = new ImageButton(new TextureRegionDrawable(new TextureRegion(skinsTexture)), new TextureRegionDrawable(new TextureRegion(skinsPressTexture)));
        skins.setPosition(Gdx.graphics.getWidth()/3 + 50, 0);
        skins.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                if(state.equals(GameState.MENU)){
                    state = GameState.SKINS;
                }
            }

        });

        exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)), new TextureRegionDrawable(new TextureRegion(exitPressTexture)));
        exit.setPosition(Gdx.graphics.getWidth() / 5 - 10, 0);
        exit.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(state.equals(GameState.MENU)){
                    System.exit(0);
                }
            }
        });

        back = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)), new TextureRegionDrawable(new TextureRegion(backPressTexture)));
        back.setPosition(Gdx.graphics.getWidth() / 5, 0);
        back.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(state.equals(GameState.SKINS) || state.equals(GameState.GAMEOVER)){
                    state = GameState.MENU;
                    restart();
                }
            }
        });

        stage = new Stage(new FillViewport(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
        stageAbout = new Stage(new FillViewport(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
        stageSkins = new Stage(new FillViewport(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));

        setInputProcessor(state);

        stage.addActor(play);
        stage.addActor(skins);
        stage.addActor(about);
        stage.addActor(exit);

        stageAbout.addActor(back);

        stageSkins.addActor(back);

    }

    private void setInputProcessor(GameState state){
        switch (state){
            case MENU:
                Gdx.input.setInputProcessor(stage);
                break;
            case ABOUT:
                Gdx.input.setInputProcessor(stageAbout);
                break;
            case SKINS:
                Gdx.input.setInputProcessor(stageSkins);
                break;
            case GAMEOVER:
                Gdx.input.setInputProcessor(stageSkins);
                break;
        }
    }
}
