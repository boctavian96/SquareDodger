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
import com.octavian.game.datamodel.Coins;
import com.octavian.game.datamodel.Player;
import com.octavian.game.datamodel.Obstacle;
import com.octavian.game.datamodel.Skin;
import com.octavian.game.statistics.MemoryUsage;
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
    private List<String> skin_values;
    private List<Boolean> available_skins;

    private Player player;
    private static int obstacle_nr = 8;
    private static int obstacle_create = 0;
    private Array<Obstacle> obstacles;
    private Skin all_skins;


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
    private Stage stageGameOver;
    private Stage stagePlayerSkins;
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
    private Texture buyTexture;
    private Texture buyPressTexture;

    private Texture lockTexture;
    private Texture lockTexturePress;
    private Texture gameover;
    private Texture selection; //Selected texture

    private ImageButton play;
    private ImageButton about;
    private ImageButton skins;
    private ImageButton exit;
    private ImageButton back;


    //Memory INFO
    MemoryUsage mem;



    @Override
    public void show(){

        Gdx.input.setCatchBackKey(true); // Back from phone
        state = GameState.MENU;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2,  Gdx.graphics.getHeight() / 2, 0);

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        layout = new GlyphLayout();
        bitmapFont = new BitmapFont();

        //skinManager = new Skin();
        playerTextures = Utils.loadTextures(Config.SKINS_ARRAY);
        textures = Utils.loadTextures(Config.SQUARES);
        gameover = new Texture(Gdx.files.internal(Config.GAMEOVER));

        music = Gdx.audio.newMusic(Gdx.files.internal(Config.MUSIC1));
        gameInput = new GameInput();


        selection = playerTextures.get(0);
        playerScore = new Score();
        playerCoins = new Coins();
        playerCoins.setCoins(Utils.getGameFile('c'));
        player = new Player(selection, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        obstacles = new Array<Obstacle>();
        batch = new SpriteBatch();
        lockTexture = new Texture(Gdx.files.internal(Config.LOCK));
        lockTexturePress = new Texture(Gdx.files.internal(Config.LOCK_PRESS));
        all_skins = new Skin();
        skin_values = all_skins.readValues();
        available_skins = Utils.availableSkins();

        instantiateUI();
    }

    @Override
    public void render(float delta){
        setInputProcessor(state);
        Utils.clearScreen();
        music.play();

        switch (state){
            case PLAYING:
                    gameInput.queryInput(player);
                    update(delta);
                    draw();
                break;

            case GAMEOVER:
                    draw();
                    stageGameOver.act(delta);
                    stageGameOver.draw();
                break;

            case ABOUT:
                    draw();
                    state = Utils.checkBack(state); // Goes Back to menu
                    stageAbout.act(delta);
                    stageAbout.draw();
                break;

            case SKINS:
                    draw();
                    state = Utils.checkBack(state);
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

        //Debug
        mem = new MemoryUsage();
        Gdx.app.log("Mem Use", mem.getInfo());
    }

    private void update(float delta){
        updateObstacles(delta);

        if(checkCollision() && state.equals(GameState.PLAYING)){
            playerCoins.addCoins();
            if(playerScore.isScoreBetter(playerScore.getScore())) {
                Utils.writeGameFile(playerScore.getScore(), 'h');
            }
            Utils.writeGameFile(playerCoins.getCoins(), 'c');
            state = GameState.GAMEOVER;

        }
    }

    private void createNewObstacle(){
        if(obstacles.size < Config.MAXIMUM_OBSTACLES) {
            Obstacle newObstacle = new Obstacle(textures.get(Utils.randomNumber(Config.NUMBER_OF_TEXTURES)));
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
                    obstacle_nr--;
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

    //Deprecated
    private void drawSkinMenu(){

    }

    private void draw(){

        switch(state) {

            case PLAYING:
                batch.begin();
                    batch.draw(player.getTexture(), player.getX(), player.getY());
                    drawObstacles();
                    Utils.drawText(String.valueOf(playerScore.getScore()), bitmapFont, batch, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - 100);
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
                    Utils.drawText("For next skin you need \n \t \t \t" + Utils.lowestSkinCost() + " coins", bitmapFont, batch, Gdx.graphics.getWidth()/2 - 150, Gdx.graphics.getHeight()/3);
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
            player = new Player(selection, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            obstacles.clear();
        }
    }

    /**
     * This function executes only once
     */
    private void instantiateUI(){

        final float LEFT = 50 / 2;
        final float CENTER = (Gdx.graphics.getWidth()/2 - 50) / 2;
        final float RIGHT = (Gdx.graphics.getWidth() - 150) / 2;
        final float SCREEN_HEIGHT = (Gdx.graphics.getHeight()) / 2;

        final float PLAY_POSITION = Gdx.graphics.getHeight() / 4;

        ImageButton buy;
        ImageButton back2;
        ImageButton back3;
        final ImageButton[] skinss = new ImageButton[9];

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

        buyTexture = new Texture(Gdx.files.internal(Config.BUY));
        buyPressTexture = new Texture(Gdx.files.internal(Config.BUY_PRESS));

        play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)), new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        play.setPosition(Gdx.graphics.getWidth()/5 - 10, PLAY_POSITION);
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
        about.setPosition(Gdx.graphics.getWidth()/5 - 10, PLAY_POSITION - 100);
        about.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                if(state.equals(GameState.MENU)){
                    state = GameState.ABOUT;
                }
            }
        });

        skins = new ImageButton(new TextureRegionDrawable(new TextureRegion(skinsTexture)), new TextureRegionDrawable(new TextureRegion(skinsPressTexture)));
        skins.setPosition(Gdx.graphics.getWidth()/5 - 10, PLAY_POSITION - 200);
        skins.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                if(state.equals(GameState.MENU)){
                    state = GameState.SKINS;
                }
            }

        });

        exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)), new TextureRegionDrawable(new TextureRegion(exitPressTexture)));
        exit.setPosition(Gdx.graphics.getWidth() / 5 - 10, PLAY_POSITION - 300);
        exit.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(state.equals(GameState.MENU)){
                    Gdx.app.exit();
                }
            }
        });

        back = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)), new TextureRegionDrawable(new TextureRegion(backPressTexture)));
        back.setPosition(Gdx.graphics.getWidth() / 5, 0);
        back.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                    state = GameState.MENU;
                    restart();
            }
        });

        back2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)), new TextureRegionDrawable(new TextureRegion(backPressTexture)));
        back2.setPosition(Gdx.graphics.getWidth() / 5, 0);
        back2.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                state = GameState.MENU;
                restart();
            }
        });

        back3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)), new TextureRegionDrawable(new TextureRegion(backPressTexture)));
        back3.setPosition(Gdx.graphics.getWidth() / 5, 0);
        back3.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                state = GameState.MENU;
                restart();
            }
        });

        buy = new ImageButton(new TextureRegionDrawable(new TextureRegion(buyTexture)), new TextureRegionDrawable(new TextureRegion(buyPressTexture)));
        buy.setPosition(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);
        buy.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if (playerScore.getScore() > Utils.lowestSkinCost()){
                    playerScore.pay(Long.valueOf(Utils.lowestSkinCost()));
                }
            }
        });

        for(int i = 0; i < skinss.length; i++){
            if(skin_values.get(i).equals("0")){
                skinss[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(playerTextures.get(i))));
            }else{
                skinss[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(lockTexture)), new TextureRegionDrawable(new TextureRegion(lockTexturePress)));
            }
        }

        skinss[0].setPosition(LEFT, SCREEN_HEIGHT - 100);
        skinss[0].addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                selection = playerTextures.get(0);
            }
        });

        skinss[1].setPosition(CENTER, SCREEN_HEIGHT - 100);
        skinss[1].addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(available_skins.get(1))
                    selection = playerTextures.get(1);
            }
        });

        skinss[2].setPosition(RIGHT, SCREEN_HEIGHT - 100);
        skinss[2].addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(available_skins.get(2))
                    selection = playerTextures.get(2);
                else{
                    if(playerScore.getScore() > Long.valueOf(skin_values.get(2))){
                        playerScore.pay(Long.valueOf(skin_values.get(2)));
                        skinss[2] = new ImageButton(new TextureRegionDrawable(new TextureRegion(playerTextures.get(2))));
                        skinss[2].setPosition(RIGHT, SCREEN_HEIGHT - 100);

                    }
                }
            }
        });

        skinss[3].setPosition(LEFT, SCREEN_HEIGHT - 300);
        skinss[3].addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(available_skins.get(3))
                    selection = playerTextures.get(3);
            }
        });

        skinss[4].setPosition(CENTER, SCREEN_HEIGHT - 300);
        skinss[4].addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(available_skins.get(4))
                    selection = playerTextures.get(4);
            }
        });

        skinss[5].setPosition(RIGHT, SCREEN_HEIGHT - 300);
        skinss[5].addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(available_skins.get(5))
                    selection = playerTextures.get(5);
            }
        });

        skinss[6].setPosition(LEFT, SCREEN_HEIGHT - 500);
        skinss[6].addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(available_skins.get(6))
                    selection = playerTextures.get(6);
            }
        });

        skinss[7].setPosition(CENTER, SCREEN_HEIGHT - 500);
        skinss[7].addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(available_skins.get(7))
                    selection = playerTextures.get(7);
            }
        });

        skinss[8].setPosition(RIGHT, SCREEN_HEIGHT - 500);
        skinss[8].addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                if(available_skins.get(8))
                    selection = playerTextures.get(8);
            }
        });



        stage = new Stage(new FillViewport(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
        stageAbout = new Stage(new FillViewport(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
        stageSkins = new Stage(new FillViewport(Gdx.graphics.getWidth() /2, Gdx.graphics.getHeight() /2));
        stageGameOver = new Stage(new FillViewport(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));

        setInputProcessor(state);

        stage.addActor(play);
        stage.addActor(skins);
        stage.addActor(about);
        stage.addActor(exit);

        stageAbout.addActor(back);

        stageSkins.addActor(back2);
        stageSkins.addActor(buy);

        for(int i = 0; i < skinss.length; i++){
            stageSkins.addActor(skinss[i]);
        }

        stageGameOver.addActor(back3);

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
                Gdx.input.setInputProcessor(stageGameOver);
                break;
        }
    }
}
