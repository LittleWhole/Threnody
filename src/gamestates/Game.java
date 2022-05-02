package gamestates;

import core.Main;
import entities.core.Entity;
import entities.units.player.Player;
import managers.DisplayManager;
import managers.KeyManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import entities.units.player.PlayerOld;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import static core.Constants.ImageConstants.CENTER_X;
import static core.Constants.ImageConstants.CENTER_Y;

public class Game extends BasicGameState {
    private final int id;

    private static int time;

    PlayerOld player; // Player
    EnumMap<Entity.EntityType, ArrayList<Entity>> entities; // All Entities in the Game

    EnumMap<Entity.EntityType, ArrayList<Entity>> newEntities; // Add new entities to the game

    // Managers
    private KeyManager keyDown; // Key Manager
    public DisplayManager displayManager; // Display Manager 

    private Player plr;
    public TiledMap overworld;

    public Map<Entity.EntityType, ArrayList<Entity>> getEntities() { return entities; }
    public ArrayList<Entity> getEntitiesOf(Entity.EntityType type) { return entities.get(type); }

    public void keyInput() { KeyManager.KEY_DOWN_LIST.stream().filter(keyDown).forEach(keyDown::keyDown); }

    public Game(int id) throws SlickException {
        this.id = id;
    }


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a game state for the *first time.*
        overworld = new TiledMap("res/tilemap/overworld.tmx", true);
        gc.setShowFPS(true);
        plr = new Player();
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        // Sets background to the specified RGB color
        g.setBackground(new Color(100, 100, 100));
        overworld.render((int)(plr.getX()*-1),(int)(plr.getY()*-1));
        //overworld.render((int) plr.getX()/2+20, (int) plr.getY()/2-20);
        //overworld.render(0, 0, (int) plr.getX() / 100 - 20, (int) plr.getY() / 100 + 20, (int) plr.getX() / 100, (int) plr.getY() / 100);
        plr.render(g);
        g.drawString("Coords: " + plr.getPosition().toString(), 100, 100);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        // This is where you put your game's logic that executes each frame that isn't about drawing
        time++;
        plr.update(overworld);
        keyInput();
    }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a gameState.

        // Reset time
        time = 0;

        // Initialize the KeyManager
        keyDown = new KeyManager(gc.getInput(), this);
    }

    public void leave(GameContainer gc, StateBasedGame sbg) {
        // This code happens when you leave a gameState.
    }


    public void keyPressed(int key, char c) {

    }


    public void mousePressed(int button, int x, int y) {
        if (button == 0) {
            System.out.println("You left clicked at " + x + ", " + y);
        }
    }


    // Returns the ID code for this game state
    public int getID() {
        return id;
    }



    public Player getPlayer() {
        return plr.getPlayer();
    }

    public static int getTime() {
        return time;
    }
}
