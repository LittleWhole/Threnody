package gamestates;

import combat.artes.mystic.AmongUs;
import core.Fonts;
import core.Main;
import entities.core.Coordinate;
import entities.units.Direction;
import entities.units.enemy.Enemy;
import entities.units.enemy.Goblin;
import entities.units.enemy.GoblinBoss;
import entities.units.player.Player;
import graphics.ui.combat.DamageNumber;
import graphics.ui.menu.Menu;
import managers.CombatManager;
import managers.ImageManager;
import managers.KeyManager;
import managers.SoundManager;
import map.GameMap;
import org.checkerframework.checker.units.qual.A;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.StateBasedGame;
import playerdata.PlayerStats;
import util.DrawUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.newdawn.slick.Input.*;

@SuppressWarnings({"division"})
public class BattleState extends ThrenodyGameState {
    private final int id;
    private GameContainer gc;
    public static List<Player> plrs;
    public static List<Enemy> enemies;
    private GameMap battlefield;
    private CombatManager combat;
    private CombatManager.CombatState result;
    private KeyManager km;
    private int resultDuration;
    public static int time;
    private int resultbuffer;
    public static int expGain;
    public static int currencyGain;

    private Sound bg;

    public static Queue<DamageNumber> damageNumbers;

    public BattleState(int id) throws SlickException {
        this.id = id;
    }


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a game state for the *first time.*
        battlefield = new GameMap("res/tilemap/battleworld.tmx");
        gc.setShowFPS(true);
        damageNumbers = new ConcurrentLinkedQueue<>();
        expGain = 0;
        currencyGain = 0;
        this.gc = gc;
        resultDuration = 100;
        km = new KeyManager(gc.getInput(), Main.game);
        bg = new Sound("res/audio/music/battle.wav");
        resultbuffer = 0;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setFont(gc.getDefaultFont());
        battlefield.render(1000, -600);

        plrs.forEach(p -> p.battleRender(g, 0,0));
        for (int i = enemies.size() - 1; i >= 0; i--) {
            enemies.get(i).battleRender(g, 0,0);
        }

        // Render the UI
        try { renderUI(g); } catch (IndexOutOfBoundsException ignored) {}

        try { result = combat.combat(g, gc); }
        catch (InterruptedException | IndexOutOfBoundsException e) { e.printStackTrace(); }

        switch (result) {
            case WIN -> {
                time++;
                g.setColor(new Color(0,0,0,170));

                DrawUtilities.fillShapeCentered(g, new RoundedRectangle(Main.getScreenWidth()/2f, Main.getScreenHeight()/2f, 1000, 500, 50), Main.getScreenWidth()/2, Main.getScreenHeight()/2);
                DrawUtilities.drawStringCentered(g, "Victory", Main.fonts.VariableWidth.B50, Main.getScreenWidth() / 2, Main.getScreenHeight() / 2 - 400);
                if (time > Math.max(expGain, currencyGain)) {
                    g.setColor(Color.red);
                    resultbuffer++;
                }
                else {
                    g.setColor(Color.white);

                }
                DrawUtilities.drawStringCentered(g, "EXP GAINED:" + (Math.min(time, expGain)), Main.getScreenWidth() / 2, Main.getScreenHeight() / 2 - 50);
                DrawUtilities.drawStringCentered(g, "MONEY GAINED:" + (Math.min(time, currencyGain)), Main.getScreenWidth() / 2, Main.getScreenHeight() / 2 + 50);
                //plrs.forEach(p -> p.getArteQueue().clear());
                if (resultbuffer > resultDuration) {

                    sbg.enterState(Main.GAME_ID);
                }
            }
            case LOSE -> {
                time++;
                expGain = 0;
                currencyGain = 0;
                g.setColor(Color.black);
                DrawUtilities.fillShapeCentered(g, new RoundedRectangle(Main.getScreenWidth()/2f, Main.getScreenHeight()/2f, 500, 250, 50), Main.getScreenWidth()/2, Main.getScreenHeight()/2);
                g.setColor(new Color(0, 0, 0, Math.min(time, 255)));
                g.fillRect(0, 0, Main.getScreenWidth(), Main.getScreenHeight());
                g.setColor(Color.red);
                DrawUtilities.drawStringCentered(g, "YOU DIED", Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 2);
                Main.stats = new PlayerStats();
                SoundManager.overrideBackgroundMusic(LoadingScreen.music);
                if (time > 300) {
                    sbg.enterState(Main.TITLE_ID);
                }
            }
            case ADVANCE -> {}
            case HALT -> {}
        }
        damageNumbers.forEach(n -> {
            n.update(gc);
            n.render(g, 0, 0);
            if (n.isExpired()) damageNumbers.remove(n);
        });
        if(Main.debug)  {
            plrs.forEach(p -> DrawUtilities.drawStringCentered(g, String.valueOf(p.getHealth()), 100, 100));
            g.drawString("" + combat.getRound(), 0, 0);
        }
        super.render(gc, sbg, g);
    }

    private void renderUI(Graphics g) throws SlickException {
        Image mana = ImageManager.getImage("mana").getScaledCopy(2f);
        mana.drawCentered(Main.RESOLUTION_X / 17, Main.RESOLUTION_Y / 20 * 17);
        DrawUtilities.drawStringCentered(g, String.valueOf(plrs.get(turn()).getMana() + plrs.get(turn()).getQueuedManaExtra()), Main.fonts.VariableWidth.B60, Main.RESOLUTION_X / 17, Main.RESOLUTION_Y / 20 * 17 + 15);
        if (plrs.get(turn()).getQueuedManaExtra() > 0) {
            g.setColor(Color.green);
            DrawUtilities.drawStringCentered(g, "+" + plrs.get(turn()).getQueuedManaExtra(), Main.fonts.VariableWidth.P40, Main.RESOLUTION_X / 17, Main.RESOLUTION_Y / 20 * 17 - 55);
            g.setColor(Color.white);
        }
        if (plrs.get(turn()).getQueuedManaRemoval() > 0) {
            g.setColor(Color.red);
            DrawUtilities.drawStringCentered(g, "-" + plrs.get(turn()).getQueuedManaRemoval(), Main.fonts.VariableWidth.B60, Main.RESOLUTION_X / 17, Main.RESOLUTION_Y / 20 * 17 + 70);
            g.setColor(Color.white);
        }
        if (plrs.get(turn()).getManaAdd() > 0) {
            g.setColor(Color.green);
            DrawUtilities.drawStringCentered(g, "Next turn: +" + plrs.get(turn()).getManaAdd(), Main.fonts.VariableWidth.P20, Main.RESOLUTION_X / 17, Main.RESOLUTION_Y / 20 * 17 + 100);
            g.setColor(Color.white);
        }
        if (km.amogus(KeyManager.AMOGUS_LIST.stream().filter(km).toList())) {
            plrs.get(turn()).amogus();
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        // This is where you put your game's logic that executes each frame that isn't about drawin
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        time = 0;
        resultbuffer = 0;
        for (int i = 0; i < plrs.size(); i++)   {
            //plrs.get(i).setPosition( -200 + i*200, i*1000);
            plrs.get(i).setPosition( -580, -260);
            plrs.get(i).setDirection(Direction.NORTH, Direction.EAST);
            plrs.get(i).startBattle();
//            if (Main.cheat) {
//                Main.constructCheatDeck(plrs.get(i));
//                plrs.get(i).setArteDeck(Main.cheatDeck);
//            }
        }
        for (int i = 0; i < enemies.size(); i++)   {
            enemies.get(i).setPosition(switch (i) {
                case 0 ->  new Coordinate(-1075 , 0);
                case 1 -> new Coordinate(-1075, 150);
                case 2 -> new Coordinate( -1325 , 0);
                default -> throw new IllegalStateException("Unexpected value: " + i);
            });
            //enemies.get(i).setPosition( i * 200,  i * 200);
            enemies.get(i).setDirection(Direction.SOUTH, Direction.WEST);
        }
        enemies.forEach(e ->    {
            if(e instanceof GoblinBoss)  {
                expGain+=(100*Math.log(e.getLevel()));
                currencyGain+=(75*Math.log(e.getLevel()));
            }
            if(e instanceof Goblin<?>)  {
                expGain+=(15*Math.log(e.getLevel() + 2));
                currencyGain+=(10*Math.log(e.getLevel() + 2));
            }
        });

        combat = new CombatManager(plrs, enemies);
        combat.roundStart();
        gc.setDefaultFont(new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true));
        gc.getGraphics().setBackground(new Color(100, 100, 100));

//        var temp = new Random();
//
//        for (var i = 0; i < 100; i++) {
//            damageNumbers.add(new DamageNumber(temp.nextInt(0, 3000), temp.nextInt(0, 1920), temp.nextInt(0, 1080)));
//        }
        plrs.forEach(p ->{
            p.renderStats(gc);
        });
        SoundManager.overrideBackgroundMusic(bg);
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame sbg) {
        plrs = new ArrayList<>();
        enemies = new ArrayList<>();
        // This code happens when you leave a gameState.
    }


    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if (key == Input.KEY_ENTER) plrs.forEach(p -> p.setState(Player.PlayerState.CASTING));
        if(key == Input.KEY_TAB) plrs.forEach(Player::showStats);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        try { for (var i = 0; i < 6; i++) if (plrs.get(turn()).onCard(gc.getInput(), i)) plrs.get(turn()).getClickArteQueue().offer(plrs.get(turn()).selection(i)); }
        catch (IndexOutOfBoundsException | NullPointerException ignored) {};
    }

    public int turn() {
        return Math.min(combat.getPlrTurn(), plrs.size() - 1);
    }


    // Returns the ID code for this game state
    public int getID() {
        return id;
    }



}
