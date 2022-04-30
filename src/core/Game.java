package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import units.player.Player;

public class Game extends BasicGameState {
    private final int id;

    private int xPos = 0;
    private int xSpeed = 3;
    private int mCounter = 0;
    private boolean displayCircle = false;
    private Player plr = new Player();
    private TiledMap overworld;

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

    }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a gameState.
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
}
