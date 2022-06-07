package gamestates;

import core.Main;
import entities.core.Coordinate;
import entities.core.Entity;
import entities.core.EntityType;
import entities.units.enemy.Goblin;
import entities.units.npc.Carder;
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
import org.checkerframework.checker.units.qual.A;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.DrawUtilities;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

public class Game extends ThrenodyGameState {

    private static GameContainer gc;
    private final int id;
    public static boolean firstTime;
    public static int time;
    public static long battleTimeStamp;
    public static int battleCooldown;

    public static ArrayList<Player> plrTeam;
    public static ArrayList<Enemy> enemyTeam;

    private Queue<NPC> npcs;
    private Queue<Player> plrs;
    private Queue<Enemy> enemies;

    // Managers
    private KeyManager keyDown; // Key Manager
    public DisplayManager displayManager; // Display Manager
    public static Coordinate plrPosition;
    private Player plr;
    private Enemy enemy;
    private Carder npc;
    public GameMap overworld;
    public Background background;
    public DialogBox dialog;
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
        firstTime = true;
        overworld = new GameMap("res/tilemap/overworld.tmx");
        background = new Background();
        plrs = new ConcurrentLinkedQueue<>();
        npcs = new ConcurrentLinkedQueue<>();
        enemies = new ConcurrentLinkedQueue<>();
        gc.setShowFPS(true);
        Game.gc = gc;
        plrPosition = new Coordinate(0,0);
        enemyTeam = new ArrayList<>();
        plrTeam = new ArrayList<>();
        npcs.add(new Carder(200, 0));
        battleCooldown = 200;
        dialog = new DialogBox(700, 400, "Notice", "This is a test dialog box!!!!!", new Button("Got it", () -> dialog.close()));
        // Initialize Both Entity Maps

        // Initialize the Player
        plr = (Player) new Player(plrPosition).setAttack(20);
        plrTeam.add(plr);
        System.out.println("[VERBOSE] Player initialized");
        enemy = new Goblin(10, 0);
        // Initialize Managers
        keyDown = new KeyManager(gc.getInput(), this);
        System.out.println("[VERBOSE] KeyManager initialized");
        displayManager = new DisplayManager(this, plr.getPosition(), gc.getGraphics());
        System.out.println("[VERBOSE] DisplayManager initialized");

        // Play BGM
        SoundManager.playBackgroundMusic("02");

        overworld.generateHitboxes();
    }


    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setFont(Main.font);
        g.setColor(Color.white);
        g.setBackground(new Color(167, 231, 255));
        background.render(g);

        //overworld.render(plr);

        overworld.render((int)(((plr.getX()*-1)+(Main.getScreenWidth()/2)) - (plr.getWidth()/2)) /*-32*/,(int)(((plr.getY()*-0.5)-(Main.getScreenHeight()*2)-plr.getHeight()*1.5f)/*-132*/), 0);
        if(overworld.playerBehindTile(plr)) {
            plr.render(g);
            overworld.render((int)(((plr.getX()*-1)+(Main.getScreenWidth()/2)) - (plr.getWidth()/2)) /*-32*/,(int)(((plr.getY()*-0.5)-(Main.getScreenHeight()*2)-plr.getHeight()*1.5f)/*-132*/), 1);

        }
        else {
            overworld.render((int)(((plr.getX()*-1)+(Main.getScreenWidth()/2)) - (plr.getWidth()/2)) /*-32*/,(int)(((plr.getY()*-0.5)-(Main.getScreenHeight()*2)-plr.getHeight()*1.5f)/*-132*/), 1);
            plr.render(g);
        }
        //overworld.render((int)plr.getX(), (int)plr.getY());

        //overworld.render((int) plr.getX()/2+20, (int) plr.getY()/2-20);
        //overworld.render(0, 0, (int) plr.getX() / 100 - 20, (int) plr.getY() / 100 + 20, (int) plr.getX() / 100, (int) plr.getY() / 100);

        enemy.render(g, plr.getX(), plr.getY());

        g.drawString("Coords: " + plr.getPosition().toString(), 100, 200);
        DrawUtilities.drawStringCentered(g,"Level: " + Main.stats.level, 100, 50);
        DrawUtilities.drawStringCentered(g, "Exp: " + Main.stats.exp + "/" + Main.stats.maxExp, 100, 100);
        DrawUtilities.drawStringCentered(g, "Gold: " + Main.stats.gold, 100, 150);
        if(Main.debug)  {
            plr.drawHitBox(g);
            enemy.drawHitBox(g);
            overworld.drawDebugRects(g);
        }
        npcs.forEach(u -> {
            if (Main.debug) u.drawHitBox(g);
            u.render(gc, plrPosition.getX(), plrPosition.getY());
            if (plr.getHitBox().intersects(u.getHitBox()) && !u.isInteracting()) {
                var shape = new RoundedRectangle(u.getRenderX(plr.getPosition().getX()) + 50, u.getRenderY(plr.getPosition().getY()), 200, 50, RoundedRectangle.TOP_LEFT | RoundedRectangle.BOTTOM_LEFT);
                var keyShape = new RoundedRectangle(u.getRenderX(plr.getPosition().getX()) + 55, u.getRenderY(plr.getPosition().getY()) + 5, 40, 40, RoundedRectangle.ALL);
                keyShape.setCornerRadius(7);
                g.setColor(Color.gray);
                g.fill(shape);
                g.setColor(Color.white);
                g.fill(keyShape);
                g.setColor(Color.black);
                DrawUtilities.drawStringCentered(g, "F", keyShape);
                g.setColor(Color.white);
                DrawUtilities.drawStringCentered(g, "Open Shop", shape.getX() + 95, shape.getCenterY());
            }
        });
        dialog.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        super.render(gc, sbg, g);
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

        dialog.update(gc);

    }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {

        if(firstTime)   {
            init(gc, sbg);
            firstTime = false;
            time = battleCooldown;
            return;

        }
        time = 0;
        System.out.println("Entering game");
        plr.gainExp(BattleState.expGain);
        Main.stats.gainGold(BattleState.currencyGain);
        // Reset time
        time = 0;
        System.out.println("[VERBOSE] Time reset");
        //enemy = new Enemy(10,0);
        //plr.setPosition(0,0);
        //plrTeam.add(plr);

        // Initialize the Player
        plr.setPosition(plrPosition);
        plr.resetHitbox();
        overworld.generateHitboxes();
        //plrTeam.add(plr);
        System.out.println("[VERBOSE] Player initialized");
        //enemy = new Goblin(10, 0);
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
        enemyTeam = new ArrayList<>();

    }

    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        //if(key == Input.KEY_F3) Main.debug = !Main.debug;
        npcs.forEach(u -> {
            if(key == Input.KEY_F) plr.interact(u);
            if(key == Input.KEY_ESCAPE) plr.exit(u);
        });
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
