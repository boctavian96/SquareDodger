package com.octavian.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;
import com.octavian.game.config.GameState;
import com.octavian.game.database.IDataSource;
import com.octavian.game.database.XMLDataSource;
import com.octavian.game.datamodel.Coins;
import com.octavian.game.datamodel.HighScore;
import com.octavian.game.datamodel.Obstacle;
import com.octavian.game.datamodel.Player;
import com.octavian.game.datamodel.Skin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by octavian on 2/21/18.
 */

public class Utils {

    public static void clearScreen(){
        //Black
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static int randomNumber(int max){
        Random random = new Random();
        return random.nextInt(max);
    }

    /**
     * Used for highscore and coin system
     * @param value
     * @param mode - c = COINS; h = HIGHSCORE
     */
    @Deprecated
    public static void writeGameFile(long value, char mode){

        FileHandle file = null;

            switch (mode) {
                case 'c':
                    file = Gdx.files.local(Config.CN_FILE);
                    break;
                case 'h':
                    file = Gdx.files.local(Config.HS_FILE);
                    break;
                default:
            }

        if(!file.exists()) {
                try {
                    file.file().createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
        }

        file.writeString(String.valueOf(value), false);
    }

    /**
     *
     * @param mode h - highscore / c - coins
     * @return
     * @throws IOException
     */
    @Deprecated
    public static long getGameFile(char mode) {

        FileHandle file = null;

        switch (mode) {
            case 'h':
                file = Gdx.files.local(Config.HS_FILE);
                break;
            case 'c':
                file = Gdx.files.local(Config.CN_FILE);
                break;
            default:
        }

        if(!file.exists()){
            writeGameFile(0, mode);
        }
        String result = file.readString();

        if(result.isEmpty()){
            return Long.valueOf("0");
        }else{
            return Long.valueOf(file.readString());
        }
    }

    public static void setSkinsStatus(List<String> status){
        FileHandle file;

        file = Gdx.files.local(Config.SKINS_LIST);

        if(!file.exists()){
            try {
                file.file().createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }

            for(int i = 0; i < status.size(); i++){
                file.writeString(String.valueOf(i * 10), false);
            }
        }else{
            for(String i : status)
                file.writeString(i, false);
        }
    }

    @Deprecated
    public static List<String> getSkinsStatus(){
        FileHandle file;
        String[] result;
        ArrayList<String> list = new ArrayList<String>();

        file = Gdx.files.local(Config.SKINS_LIST);

        if(!file.exists()){
            file = Gdx.files.internal("hard_skins.txt");
            result = file.readString().split(" ");
        }else{
            result = file.readString().split(" ");
        }

        list.addAll(Arrays.asList(result));

        return list;
    }

    @Deprecated
    public static GameState checkBack(GameState state){
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if(state.equals(GameState.SKINS) || state.equals(GameState.ABOUT))
                return GameState.MENU;
        }

        return state;
    }

    public static boolean checkBack(){
        return Gdx.input.isKeyJustPressed(Input.Keys.BACK);
    }

    @Deprecated
    public static List<Texture> loadTextures(String[] args) {
        ArrayList<Texture> list = new ArrayList<Texture>();

        for (String i : args) {
            list.add(new Texture(i));
        }

        return list;
    }

    @Deprecated
    public static void drawText(String text, BitmapFont bitmapFont, SpriteBatch batch, float x, float y){
            GlyphLayout gl = new GlyphLayout();
            BitmapFont scoreBounds = bitmapFont;
            scoreBounds.getData().setScale(3, 3);
            gl.setText(scoreBounds, text);
            scoreBounds.draw(batch, text, x, y);
    }

    public static void drawDebugWidthHeight(BitmapFont bitmapFont, SpriteBatch batch){

        StringBuilder sb = new StringBuilder();

        sb.append("W: ");
        sb.append(Gdx.graphics.getWidth());
        sb.append(", H: ");
        sb.append(Gdx.graphics.getHeight());

        BitmapFont coords = bitmapFont;

        coords.draw(batch, sb.toString(), 400, 400 );
    }

    public static void drawDebugPlayerCoords(Player player, BitmapFont bitmapFont, SpriteBatch batch){
            String ics = Float.toString(player.getX());
            String igrec = Float.toString(player.getY());
            String result = ics + ", " + igrec;

            GlyphLayout gl = new GlyphLayout();
            BitmapFont coords = bitmapFont;
            coords.getData().setScale(3, 3);
            gl.setText(coords, ics + igrec);

            coords.draw(batch, result, 100, 100 );
    }

    @Deprecated
    public static int lowestSkinCost(){
        List<String> dummy = Utils.getSkinsStatus();
        List<Integer> lst = new ArrayList<Integer>();

        for(String i : dummy){
            lst.add(Integer.valueOf(i));
        }

        return Utils.getMinimum(lst);
    }

    private static int getMinimum(List<Integer> list){
        int minim = Integer.MAX_VALUE;
        for(Integer i : list){
            if(i < minim && i != 0)
                minim = i;
        }

        return minim;
    }

    @Deprecated
    public static List<Boolean> availableSkins(){
        List<String> skn_list = Utils.getSkinsStatus();
        List<Boolean> bool_list = new ArrayList<Boolean>();

        for(String i : skn_list){
           if(i.equals("0")){
                bool_list.add(true);
            }else{
                bool_list.add(false);
            }
        }
        return bool_list;
    }

    public static String getHighScore(){
        IDataSource dataSource = XMLDataSource.getInstance();
        HighScore highScore = dataSource.getScores();

        //FIXME: Think about this...
        return String.valueOf(highScore.getScore());
    }

    public static String getCoins(){
        IDataSource dataSource = XMLDataSource.getInstance();
        Coins coins = dataSource.getCoins();

        //FIXME: Think about this...
        return String.valueOf(coins.getCoins());
    }

    /**
     * Little easter egg.
     * @return TRUE
     */
    public static boolean isRichardARooster(){
        return true;
    }

    public static String readAbout(){
        FileHandle file;
        String about;
        file = Gdx.files.internal(Config.ABOUT_TEXT_FILE);
        return file.readString();
    }

    /**
     * Dummy Skins generator
     * @return 9 dummy skins.
     */
    public static Array<Skin> getDummySkins(){
        Array<Skin> skins = new Array<Skin>();
        boolean isEven;

        for(int i = 0; i < 9; i++){
            isEven = i % 2 == 0 ? true : false;
            skins.add(new Skin("dummy" + i, Assets.skinTextures.get(i), i * 10, isEven));
        }

        return skins;
    }

}
