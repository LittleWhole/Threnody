package core;

import entities.units.player.Player;
import gamestates.Game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import playerdata.Sigur;

import java.util.ArrayList;
import java.util.List;

public class Main extends StateBasedGame {
    public static final int RESOLUTION_X = 1920;
    public static final int RESOLUTION_Y = 1080;
    public static final int FRAMES_PER_SECOND = 144;

    private static AppGameContainer appgc;

    public static final int GAME_ID = 0;
    public static Game game;

    public static ArrayList<Character> characters;

    public Main(String name) {
        super(name);

        game = new Game(GAME_ID);
    }

    public static int getScreenWidth() {
        return appgc.getScreenWidth();
    }

    public static int getScreenHeight() {
        return appgc.getScreenHeight();
    }


    public void initStatesList(GameContainer gc) throws SlickException {
        addState(game);
    }

    public static void main(String[] args) {
        try {
            appgc = new AppGameContainer(new Main("Example Slick Game"));
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

            appgc.setDisplayMode(appgc.getScreenWidth(), appgc.getScreenHeight(), false);
            appgc.setTargetFrameRate(FRAMES_PER_SECOND);
            appgc.start();
            appgc.setVSync(true);

        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
}