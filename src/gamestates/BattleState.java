package gamestates;

import core.Main;
import entities.core.Coordinate;
import entities.units.Direction;
import entities.units.enemy.Enemy;
import entities.units.player.Player;
import graphics.ui.combat.DamageNumber;
import managers.CombatManager;
import managers.ImageManager;
import map.GameMap;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import util.DrawUtilities;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings({"division"})
public class BattleState extends ThrenodyGameState {
    private final int id;
    private GameContainer gc;
    public static List<Player> plrs;
    public static List<Enemy> enemies;
    private GameMap battlefield;
    private CombatManager combat;
    private CombatManager.CombatState result;
    private int resultDuration;
    public static int time;
    public static int expGain;
    public static int currencyGain;

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
        resultDuration = 255;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setFont(gc.getDefaultFont());
        battlefield.render(1000, -600);

        plrs.forEach(p -> p.battleRender(g, 0,0));
        for (int i = enemies.size() - 1; i >= 0; i--) {
            enemies.get(i).render(g, 0,0);
        }

        // Render the UI
        try { renderUI(g); } catch (IndexOutOfBoundsException ignored) {}

        try { result = combat.combat(g, gc); }
        catch (InterruptedException | IndexOutOfBoundsException e) { e.printStackTrace(); }

        switch (result) {
            case WIN -> {
                time++;
                expGain = 10;
                currencyGain = 10;
                if (time / 2 > Math.max(expGain, currencyGain)) g.setColor(Color.red);
                else g.setColor(Color.white);
                DrawUtilities.drawStringCentered(g, "EXP GAINED:" + (Math.min(time / 2, expGain)), Main.getScreenWidth() / 2, Main.getScreenHeight() / 2 - 100);
                DrawUtilities.drawStringCentered(g, "MONEY GAINED:" + (Math.min(time / 2, currencyGain)), Main.getScreenWidth() / 2, Main.getScreenHeight() / 2 + 100);
                //plrs.forEach(p -> p.getArteQueue().clear());
                if (time > resultDuration) {
                    sbg.enterState(Main.GAME_ID);
                }
            }
            case LOSE -> {
                time++;
                g.setColor(new Color(0, 0, 0, Math.min(time, 255)));
                g.fillRect(0, 0, Main.getScreenWidth(), Main.getScreenHeight());
                g.setColor(Color.red);
                DrawUtilities.drawStringCentered(g, "YOU DIED", Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 2);
                if (time > resultDuration) {
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

    private void renderUI(Graphics g) {
        Image mana = ImageManager.getImage("mana").getScaledCopy(2f);
        mana.drawCentered(Main.RESOLUTION_X / 17, Main.RESOLUTION_Y / 20 * 17);
        DrawUtilities.drawStringCentered(g, String.valueOf(plrs.get(turn()).getMana()), Main.fonts.VariableWidth.B60, Main.RESOLUTION_X / 17, Main.RESOLUTION_Y / 20 * 17 + 15);
        if (plrs.get(turn()).getQueuedManaExtra() > 0) {
            g.setColor(Color.green);
            DrawUtilities.drawStringCentered(g, "+" + plrs.get(turn()).getQueuedManaExtra(), Main.fonts.VariableWidth.B60, Main.RESOLUTION_X / 17, Main.RESOLUTION_Y / 20 * 17 - 55);
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
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        // This is where you put your game's logic that executes each frame that isn't about drawing
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        time = 0;
        for (int i = 0; i < plrs.size(); i++)   {
            //plrs.get(i).setPosition( -200 + i*200, i*1000);
            plrs.get(i).setPosition( -680, -460);
            plrs.get(i).setDirection(Direction.NORTH, Direction.EAST);
            plrs.get(i).startBattle();
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

        combat = new CombatManager(plrs, enemies);
        combat.roundStart();
        gc.getGraphics().setFont(new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true));
        gc.getGraphics().setBackground(new Color(100, 100, 100));

//        var temp = new Random();
//
//        for (var i = 0; i < 100; i++) {
//            damageNumbers.add(new DamageNumber(temp.nextInt(0, 3000), temp.nextInt(0, 1920), temp.nextInt(0, 1080)));
//        }
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame sbg) {
        // This code happens when you leave a gameState.
    }


    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if (key == Input.KEY_ENTER) plrs.forEach(p -> p.setState(Player.PlayerState.CASTING));
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
