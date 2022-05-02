package gamestates;

import entities.core.Entity;
import entities.core.EntityType;
import entities.units.player.Player;
import managers.DisplayManager;
import managers.KeyManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;

public class Game extends BasicGameState {

    private GameContainer gc;
    private final int id;

    private static int time;

    EnumMap<EntityType, ArrayList<Entity>> entities; // All Entities in the Game

    EnumMap<EntityType, ArrayList<Entity>> newEntities; // Add new entities to the game

    // Managers
    private KeyManager keyDown; // Key Manager
    public DisplayManager displayManager; // Display Manager 

    private Player plr;
    public TiledMap overworld;

    public Map<EntityType, ArrayList<Entity>> getEntities() { return entities; }
    public ArrayList<Entity> getEntitiesOf(EntityType type) { return entities.get(type); }

    public void keyInput() { KeyManager.KEY_DOWN_LIST.stream().filter(keyDown).forEach(keyDown::keyDown); }

    // Check cursor input
    public void cursorInput(){
        Input input = gc.getInput();

        float mouseX = displayManager.gameX(input.getAbsoluteMouseX());
        float mouseY = displayManager.gameY(input.getAbsoluteMouseY());

    }

    public Game(int id) throws SlickException {
        this.id = id;
    }


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a game state for the *first time.*
        overworld = new TiledMap("res/tilemap/overworld.tmx", true);
        gc.setShowFPS(true);
        this.gc = gc;
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        // Sets background to the specified RGB color
        g.setBackground(new Color(100, 100, 100));
        for (int i = 0; i < overworld.getLayerCount(); i++) overworld.render((int)(plr.getX()*-1),(int)(plr.getY()*-1), i);
        //overworld.render((int) plr.getX()/2+20, (int) plr.getY()/2-20);
        //overworld.render(0, 0, (int) plr.getX() / 100 - 20, (int) plr.getY() / 100 + 20, (int) plr.getX() / 100, (int) plr.getY() / 100);
        plr.render(g);
        g.drawString("Coords: " + plr.getPosition().toString(), 100, 100);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        // Increment timer
        time++;


        // Manage Key and Cursor Input
        keyInput(); // Manage keys that are down
        cursorInput(); // Manage the cursor

        // Update Player
        plr.update();

        // Update all entities, and remove those marked for removal
        Predicate<Entity> filter = Entity::isMarked;
        for(ArrayList<Entity> list : entities.values()){
            for(Entity e : list) e.update();
            list.removeIf(filter);
        }

        // Add new entities
        for (EntityType type : newEntities.keySet()) {
            for (Entity e : newEntities.get(type)) {
                entities.get(type).add(e);
            }
            newEntities.get(type).clear();
        }

    }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        System.out.println("Entering game");

        // Reset time
        time = 0;
        System.out.println("[VERBOSE] Time reset");

        // Initialize Both Entity Maps
        entities = new EnumMap<>(Map.of(
                EntityType.UNIT, new ArrayList<>(),
                EntityType.PROJECTILE, new ArrayList<>(),
                EntityType.INTERACTABLE, new ArrayList<>()
        ));
        newEntities = new EnumMap<>(Map.of(
                EntityType.UNIT, new ArrayList<>(),
                EntityType.PROJECTILE, new ArrayList<>(),
                EntityType.INTERACTABLE, new ArrayList<>()
        ));

        // Initialize the Player
        plr = new Player();
        System.out.println("[VERBOSE] Player initialized");

        // Initialize Managers
        keyDown = new KeyManager(gc.getInput(), this);
        System.out.println("[VERBOSE] KeyManager initialized");
        displayManager = new DisplayManager(this, plr.getPosition(), gc.getGraphics());
        System.out.println("[VERBOSE] DisplayManager initialized");

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
