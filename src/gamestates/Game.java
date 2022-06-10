package gamestates;

import core.Main;
import entities.core.Coordinate;
import entities.core.Entity;
import entities.core.EntityType;
import entities.units.enemy.Goblin;
import entities.units.enemy.GoblinBoss;
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
    private StateBasedGame sbg;
    private final int id;
    public static boolean firstTime;
    public static int time;
    public static long battleTimeStamp;
    public static int battleCooldown;

    public static ArrayList<Player> plrTeam;
    public static ArrayList<Enemy> enemyTeam;

    private Queue<NPC> npcs;
    private Queue<Player> plrs;
    private ArrayList<Enemy> enemies;

    // Managers
    private KeyManager keyDown; // Key Manager
    public DisplayManager displayManager; // Display Manager
    private Button gainGold;
    private Button gainExp;
    private Player plr;
    private Enemy enemy;
    private Carder npc;
    private int enemyEncounterIndex;
    public GameMap overworld;
    public Background background;
    public DialogBox dialog;
    private Sound bg = new Sound("res/audio/music/overworld.wav");
    public static GameContainer getGc() { return gc; }
    public StateBasedGame getSbg() { return sbg; }

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


    public synchronized void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a game state for the *first time.*
        firstTime = true;
        overworld = new GameMap("res/tilemap/overworld.tmx");
        background = new Background();
        plrs = new ConcurrentLinkedQueue<>();
        npcs = new ConcurrentLinkedQueue<>();
        enemies = new ArrayList<>();
        gc.setShowFPS(true);
        Game.gc = gc;
        this.sbg = sbg;
        enemyTeam = new ArrayList<>();
        plrTeam = new ArrayList<>();
        npcs.add(new Carder(1700, 0));
        enemies.add(new Goblin(-200,0));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(5,7);
        enemies.add(new Goblin(-500,-50));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(5,7);
        enemies.add(new Goblin(-900,100));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(5,7);
        enemies.add(new Goblin(-872, -250));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(5,7);

        enemies.add(new Goblin(320, -64));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(3,4);
        enemies.add(new Goblin(721, 320));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(3,4);
        enemies.add(new Goblin(493, -200));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(3,4);

        enemies.add(new Goblin(600, -768));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(1,2);
        enemies.add(new Goblin(324, -567));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(1,2);
        enemies.add(new Goblin(435, -432));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(1,2);

        enemies.add(new Goblin(1000, -1000, new ArrayList<>(Arrays.asList(new Goblin(0, 0, new ArrayList<>())))));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(1,1);//first goblin

        enemies.add(new Goblin(120,1200));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(9,10);
        enemies.add(new Goblin(-700,1400));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(9,10);
        enemies.add(new Goblin(-500,1005));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(9,10);
        enemies.add(new Goblin(-850, 1600));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(9,10);

        enemies.add(new Goblin(-2000,0));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(15,18);
        enemies.add(new Goblin(-2304,430));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(15,18);
        enemies.add(new Goblin(-1930,-203));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(15,18);

        enemies.add(new Goblin(-2987,176));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(20,25);
        enemies.add(new Goblin(-3200,-232));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(20,25);
        enemies.add(new Goblin(-3100,432));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(20,25);
        enemies.add(new Goblin(-3000, -500));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(20,25);

        enemies.add(new Goblin(-4800,0));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(50,100);
        enemies.add(new Goblin(-5000,-104));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(50,100);
        enemies.add(new Goblin(-5100,-343));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(50,100);
        enemies.add(new Goblin(-4798, -534));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(50,100);

        enemies.add(new GoblinBoss(-600,1320));
        enemies.get(enemies.size()-1).setEnemyTeamLevels(15,15);
        battleCooldown = 200;
        dialog = new DialogBox(700, 400, "Notice", "This is a test dialog box!!!!!", new Button("Got it", () -> dialog.close()));
        // Initialize Both Entity Maps

        // Initialize the Player
        plr = (Player) new Player(Main.stats.worldPos).setAttack(20);
        plrTeam.add(plr);
        System.out.println("[VERBOSE] Player initialized");
        enemy = new Goblin(10, 0);
        // Initialize Managers
        keyDown = new KeyManager(gc.getInput(), this);
        System.out.println("[VERBOSE] KeyManager initialized");
        displayManager = new DisplayManager(this, plr.getPosition(), gc.getGraphics());
        System.out.println("[VERBOSE] DisplayManager initialized");
        gainExp = new Button(Main.getScreenWidth()-300, 200, "Exp add", () -> {
            try {
                plr.gainExp(1000);
            } catch (SlickException e) {
                throw new RuntimeException(e);
            }
        });
        gainGold = new Button(Main.getScreenWidth()-600, 200, "Gold add", () -> Main.stats.gainGold(1000));
        // Play BGM

        SoundManager.overrideBackgroundMusic(bg);

        overworld.generateHitboxes();
        overworld.updateHitboxes(-plr.getPosition().getX(), -plr.getPosition().getY()/2);
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

        //enemy.render(g, plr.getX(), plr.getY());
        enemies.forEach(u -> {
           u.render(g, plr.getX(), plr.getY());

        });
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
            u.render(gc, Main.stats.worldPos.getX(), Main.stats.worldPos.getY(), plr);
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
        plr.renderStats(gc);
        plr.renderInventory(gc);
        dialog.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        if(Main.debug)  {
            gainExp.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY());
            gainGold.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        }
        super.render(gc, sbg, g);
    }

    public synchronized void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        // Increment timer
        time++;


        // Manage Key and Cursor Input
        keyInput(); // Manage keys that are down

        cursorInput(); // Manage the cursor

        // Update Background
        background.update();

        // Update Player
        enemies.forEach(u -> {
            u.overworldUpdate();
            try {
                if(plr.encounteringEnemy(sbg, u, this)) {
                    enemyEncounterIndex = enemies.indexOf(u);
                }
                plr.update(sbg, u, this);

            } catch (SlickException e) {
                throw new RuntimeException(e);
            }
        });
        //plr.update(sbg, enemy, this);
        //enemy.overworldUpdate();

        dialog.update(gc);
        if(Main.debug)  {
            if(gc.getInput().isMouseButtonDown(0)) {
                if (gainExp.onButton(gc.getInput().getMouseX(), gc.getInput().getMouseY()))
                    gainExp.getCommand().command();
                if (gainGold.onButton(gc.getInput().getMouseX(), gc.getInput().getMouseY()))
                    gainGold.getCommand().command();
            }
        }

    }

    public synchronized void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {

        if(firstTime)   {
            init(gc, sbg);
            firstTime = false;
            time = battleCooldown;
            return;

        }
        time = 0;
        System.out.println("Entering game");
        if(BattleState.expGain > 0) {
            enemies.get(enemyEncounterIndex).kill();
        }
        plr.gainExp(BattleState.expGain);
        Main.stats.gainGold(BattleState.currencyGain);
        // Reset time
        time = 0;
        System.out.println("[VERBOSE] Time reset");
        //enemy = new Enemy(10,0);
        //plr.setPosition(0,0);
        //plrTeam.add(plr);

        // Initialize the Player
        plr.setPosition(Main.stats.worldPos);
        plr.resetHitbox();
        overworld.generateHitboxes();
        overworld.updateHitboxes(-Main.stats.worldPos.getX(), -Main.stats.worldPos.getY()/2);
        //plrTeam.add(plr);
        System.out.println("[VERBOSE] Player initialized");
        //enemy = new Goblin(10, 0);
        // Initialize Managers
        keyDown = new KeyManager(gc.getInput(), this);
        System.out.println("[VERBOSE] KeyManager initialized");
        displayManager = new DisplayManager(this, plr.getPosition(), gc.getGraphics());
        System.out.println("[VERBOSE] DisplayManager initialized");

        // Play BGM
        SoundManager.overrideBackgroundMusic(bg);
    }

    public synchronized void leave(GameContainer gc, StateBasedGame sbg) {
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
            if(key == Input.KEY_ENTER) plr.exit(u);
        });
        if(key == Input.KEY_TAB) plr.showStats();
        if(key == Input.KEY_E) plr.showInventory();
    }


    @Override
    public void mousePressed(int button, int x, int y) {

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
