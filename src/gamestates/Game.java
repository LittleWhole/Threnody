package gamestates;

import entities.core.Entity;
import managers.DisplayManager;
import managers.KeyManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import entities.units.player.Player;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class Game extends BasicGameState {
    private final int id;

    private int time;

    Player player; // Player
    EnumMap<Entity.EntityType, ArrayList<Entity>> entities; // All Entities in the Game

    EnumMap<Entity.EntityType, ArrayList<Entity>> newEntities; // Add new entities to the game

    // Managers
    private KeyManager keyDown; // Key Manager
    public DisplayManager displayManager; // Display Manager 

    private int xPos = 0;
    private int xSpeed = 3;
    private int mCounter = 0;
    private boolean displayCircle = false;
    private Player plr = new Player();
    private TiledMap overworld;

    public Map<Entity.EntityType, ArrayList<Entity>> getEntities() { return entities; }
    public ArrayList<Entity> getEntitiesOf(Entity.EntityType type) { return entities.get(type); }

    public Game(int id) {
        this.id = id;
    }


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a game state for the *first time.*
        overworld = new TiledMap("res/tilemap/overworld.tmx", true);
        gc.setShowFPS(true);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        // Sets background to the specified RGB color
        g.setBackground(new Color(100, 100, 100));
        overworld.render(3, 3);


    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        // This is where you put your game's logic that executes each frame that isn't about drawing
        time++;
    }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a gameState.
        time = 0;
    }

    public void leave(GameContainer gc, StateBasedGame sbg) {
        // This code happens when you leave a gameState.
    }


    public void keyPressed(int key, char c) {
        if (key == Input.KEY_1) {
            System.out.println("You pressed 1, slowing the animation");
            if (xSpeed > 1) {
                xSpeed--;
            }
        }

        if (key == Input.KEY_2) {
            System.out.println("You pressed 2, speeding up the animation");
            if (xSpeed < 10) {
                xSpeed++;
            }
        }

        if (key == Input.KEY_SPACE) {
            System.out.println("You pressed space, toggling the circle's display");
            displayCircle = !displayCircle;
        }

        if (c == 'm') {
            mCounter++;
        }
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
