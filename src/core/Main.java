package core;

import gamestates.*;
import graphics.ui.Displayable;
import graphics.ui.menu.DialogBox;
import graphics.ui.menu.Menu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import playerdata.PlayerStats;
import playerdata.characters.PlayableCharacter;

import java.awt.*;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class Main extends StateBasedGame {
    public static int RESOLUTION_X = 1920;
    public static int RESOLUTION_Y = 1080;
    public static final int FRAMES_PER_SECOND = 120;
    public static TrueTypeFont font;
    public static Fonts fonts;
    public static final Object LOCK = new Object();

    private static AppGameContainer appgc;
    public static final int FILLER_ID = -1;
    public static final int INTRO_ID = 0;
    public static final int LOADING_ID = 1;
    public static final int GAME_ID = 2;
    public static final int BATTLE_ID = 3;
    public static final int TITLE_ID = 999;
    public static LoadingScreen loading;
    public static IntroCredit intro;
    public static Game game;
    public static BattleState battle;
    public static TitleScreen title;
    public static Filler filler;
    public static boolean debug;

    public static PlayerStats stats = new PlayerStats();
    public static ArrayList<PlayableCharacter> characters;

    public static AbstractQueue<Displayable> displayables = new ConcurrentLinkedQueue<>();
    public static AbstractQueue<Menu> menus = new ConcurrentLinkedQueue<>();

    public Main(String name) throws SlickException {
        super(name);

        intro = new IntroCredit(INTRO_ID);
        loading = new LoadingScreen(LOADING_ID);
        battle = new BattleState(BATTLE_ID);
        game = new Game(GAME_ID);
        title = new TitleScreen(TITLE_ID);
        filler = new Filler(FILLER_ID);
    }

    public static int getScreenWidth() {
//        return appgc.getScreenWidth();
        return RESOLUTION_X;
    }

    public static int getScreenHeight() {
//        return appgc.getScreenHeight();
        return RESOLUTION_Y;
    }


    public void initStatesList(GameContainer gc) throws SlickException {
        addState(intro);
        addState(loading);
        addState(game);
        addState(battle);
        addState(title);
        addState(filler);
    }

    public static void main(String[] args) {
        try {
            debug = false;
            appgc = new AppGameContainer(new Main("Threnody"));
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

            RESOLUTION_X = getScreenWidth();
            RESOLUTION_Y = getScreenHeight();

            appgc.setDisplayMode(getScreenWidth(), getScreenHeight(), false);
            appgc.setTargetFrameRate(FRAMES_PER_SECOND);
            appgc.setVSync(false);
            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
}