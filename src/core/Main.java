package core;

import gamestates.BattleState;
import gamestates.Game;
import gamestates.LoadingScreen;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import playerdata.PlayableCharacter;

import java.awt.*;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

public class Main extends StateBasedGame {
    public static final int RESOLUTION_X = 1920;
    public static final int RESOLUTION_Y = 1080;
    public static final int FRAMES_PER_SECOND = 60;

    private static AppGameContainer appgc;
    public static final int LOADING_ID = 0;
    public static final int GAME_ID = 1;
    public static final int BATTLE_ID = 2;
    public static LoadingScreen loading;
    public static Game game;
    public static BattleState battle;
    public static boolean debug;

    public static ArrayList<PlayableCharacter> characters;

    public Main(String name) throws SlickException {
        super(name);

        loading = new LoadingScreen(LOADING_ID);
        battle = new BattleState(BATTLE_ID);
        game = new Game(GAME_ID);
    }

    public static int getScreenWidth() {
        return appgc.getScreenWidth();
    }

    public static int getScreenHeight() {
        return appgc.getScreenHeight();
    }


    public void initStatesList(GameContainer gc) throws SlickException {
        addState(loading);
        addState(game);
        addState(battle);
    }

    public static void main(String[] args) {
        try {
            debug = false;
            appgc = new AppGameContainer(new Main("Threnody"));
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

            appgc.setDisplayMode(appgc.getScreenWidth(), appgc.getScreenHeight(), false);
            appgc.setTargetFrameRate(FRAMES_PER_SECOND);
            appgc.start();
            appgc.setVSync(true);
            appgc.setDefaultFont(new TrueTypeFont(new Font("Bahnschrift", Font.PLAIN, 20), true));
            appgc.getGraphics().setFont(new TrueTypeFont(new Font("Bahnschrift", Font.PLAIN, 20), true));

        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
}