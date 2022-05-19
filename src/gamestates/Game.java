package gamestates;

import core.Main;
import entities.core.Coordinate;
import entities.core.Entity;
import entities.core.EntityType;
import entities.units.npc.NPC;
import entities.units.Unit;
import entities.units.enemy.Enemy;
import entities.units.player.Player;
import graphics.Background;
import graphics.ui.Button;
import graphics.ui.menu.DialogBox;
import managers.DisplayManager;
import managers.KeyManager;
import managers.SoundManager;
import map.GameMap;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;

public class Game extends BasicGameState {

    private static GameContainer gc;
    private final int id;

    public static int time;
    public static long battleTimeStamp;
    public static int battleCooldown;
    EnumMap<EntityType, ArrayList<Entity>> entities; // All Entities in the Game

    EnumMap<EntityType, ArrayList<Entity>> newEntities; // Add new entities to the game

    public ArrayList<Unit> plrTeam;
    public ArrayList<Unit> enemyTeam;

    // Managers
    private KeyManager keyDown; // Key Manager
    public DisplayManager displayManager; // Display Manager 
    private Coordinate plrPosition;
    private Player plr;
    private Enemy enemy;
    private NPC npc;
    public GameMap overworld;
    public Background background;
    public DialogBox dialog;

    public Map<EntityType, ArrayList<Entity>> getEntities() { return entities; }
    public ArrayList<Entity> getEntitiesOf(EntityType type) { return entities.get(type); }
    public static GameContainer getGc() { return gc; }

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
        overworld = new GameMap("res/tilemap/overworld.tmx");
        background = new Background();
        gc.setShowFPS(true);
        this.gc = gc;
        plrPosition = new Coordinate(0,0);
        enemyTeam = new ArrayList<>();
        plrTeam = new ArrayList<>();
        enemy = new Enemy(10, 0);
        npc = new NPC(200,0);
        battleCooldown = 200;
        dialog = new DialogBox(700, 400, "Notice", "Testestestet!!!!@ EUFHEUIFH", new Button("Got it", () -> dialog.close()));
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setFont(Main.font);
        g.setColor(Color.white);
        g.setBackground(new Color(167, 231, 255));
        background.render(g);

        //overworld.render(plr);

        overworld.render((int)((plr.getX()*-1)+(Main.getScreenWidth()/2)-(plr.getWidth()/2)),(int)((plr.getY()*-0.5)-(Main.getScreenHeight()*2)-(plr.getHeight()*(3/2))));
        //overworld.render((int)plr.getX(), (int)plr.getY());

        //overworld.render((int) plr.getX()/2+20, (int) plr.getY()/2-20);
        //overworld.render(0, 0, (int) plr.getX() / 100 - 20, (int) plr.getY() / 100 + 20, (int) plr.getX() / 100, (int) plr.getY() / 100);

        enemy.render(g, plr.getX(), plr.getY());
        npc.render(gc, plrPosition.getX(), plrPosition.getY());
        plr.render(g);
        g.drawString("Coords: " + plr.getPosition().toString(), 100, 100);

        if(Main.debug)  {
            plr.drawHitBox(g);
            enemy.drawHitBox(g);
            npc.drawHitBox(g);
            overworld.drawDebugRects(g);
        }

        //dialog.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        // Increment timer
        time++;


        // Manage Key and Cursor Input
        keyInput(); // Manage keys that are down

        cursorInput(); // Manage the cursor

        // Update Background
        background.update();

        // Update Player
        plr.update(sbg, enemy, this);
        enemy.overworldUpdate();
        System.out.println(plr.getX()+ " " + plr.getY());

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

        //dialog.update(gc);

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
        plr = new Player(plrPosition);
        plrTeam.add(plr);
        System.out.println("[VERBOSE] Player initialized");
        enemy = new Enemy(10, 0);
        enemyTeam.add(enemy);
        // Initialize Managers
        keyDown = new KeyManager(gc.getInput(), this);
        System.out.println("[VERBOSE] KeyManager initialized");
        displayManager = new DisplayManager(this, plr.getPosition(), gc.getGraphics());
        System.out.println("[VERBOSE] DisplayManager initialized");

        // Play BGM
        SoundManager.playBackgroundMusic("02");
    }

    public void leave(GameContainer gc, StateBasedGame sbg) {
        // This code happens when you leave a gameState.
        BattleState.plrs = plrTeam;
        BattleState.enemies = enemyTeam;

    }


    public void keyPressed(int key, char c) {
        if(key == Input.KEY_F3) Main.debug = !Main.debug;
        if(key == Input.KEY_E) plr.interact(npc);
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

    public GameMap getOverworld() {
        return overworld;
    }
}
