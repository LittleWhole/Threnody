package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {
    private final int id;

    private int xPos = 0;
    private int xSpeed = 3;
    private int mCounter = 0;
    private boolean displayCircle = false;

    public Game(int id) {
        this.id = id;
    }


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a game state for the *first time.*
        gc.setShowFPS(true);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        // Sets background to the specified RGB color
        g.setBackground(new Color(100, 100, 100));

        // Draws the outline of a rectangle or oval.  Uses x, y, w, h.
        g.drawRect(50, 50, 50, 50);
        g.drawOval(100, 200, 50, 50);

        // Draws a line between two points
        g.drawLine(400, 25, 500, 75);

        //Sets color using a preset color.
        g.setColor(Color.cyan);

        // Draws a filled rectangle.  Uses x, y, w, h.
        g.fillRect(200, 200, 500, 500);

        //Sets color using a custom RGB.  The fourth parameter is "alpha value" or transparency (0-255)
        g.setColor(new Color(255, 0, 0, 50));
        g.fillRect(400, 300, 200, 700);

        // You can use variables to create animations for these images
        g.setColor(new Color(0, 200, 0));
        g.fillOval(xPos, 100, 200, 200);

        xPos = xPos + xSpeed;
        if (xPos > gc.getHeight()) {
            xPos = -200;
        }

        // You can use keyPress events to change values that will trigger drawing as needed
        if (displayCircle) {
            g.setColor(new Color(0, 0, 200));
            g.fillOval(gc.getWidth() - 200, gc.getHeight() - 200, 150, 150);
        }

        g.setColor(new Color(255, 255, 255));
        g.drawString("You have pressed m " + mCounter + " times.", gc.getWidth() / 2, 10);

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


}
