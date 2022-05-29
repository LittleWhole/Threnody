package gamestates;

import combat.artes.Arte;
import core.Main;
import entities.units.Direction;
import entities.units.Unit;
import entities.units.enemy.Enemy;
import entities.units.player.Player;
import entities.units.player.PlayerState;
import graphics.ui.combat.DamageNumber;
import managers.CombatManager;
import map.GameMap;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.DrawUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

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

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setFont(gc.getDefaultFont());
        battlefield.render(1000, -600);
        for(Player p : plrs) {
            p.battleRender(g, 0,0);

        }
        for(int i = enemies.size() - 1; i >= 0; i--) {
            enemies.get(i).render(g, 0,0);
        }
        try {
            result = combat.combat(g, gc);
        } catch (InterruptedException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

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
            for(Player p : plrs) {
                DrawUtilities.drawStringCentered(g, String.valueOf(p.getHealth()), 100, 100);
            }
            g.drawString("" + combat.getRound(), 0, 0);
        }
        super.render(gc, sbg, g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        // This is where you put your game's logic that executes each frame that isn't about drawing
    }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        time = 0;
        for(int i = 0; i < plrs.size(); i++)   {
            //plrs.get(i).setPosition( -200 + i*200, i*1000);
            plrs.get(i).setPosition( -680, -460);
            plrs.get(i).setDirection(Direction.NORTH, Direction.EAST);
            plrs.get(i).startBattle();
        }
        for(int i = 0; i < enemies.size(); i++)   {

            switch(i)   {
                case 0 ->   {
                    enemies.get(i).setPosition( -1075 ,  0);
                }
                case 1  ->  {
                    enemies.get(i).setPosition( -1075,  150);
                }
                case 2 ->   {
                    enemies.get(i).setPosition( -1250 ,  0);
                }
            }
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

    public void leave(GameContainer gc, StateBasedGame sbg) {
        // This code happens when you leave a gameState.
    }


    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if (key == Input.KEY_ENTER) plrs.forEach(p -> p.setState(PlayerState.CASTING));
    }

    public void mousePressed(int button, int x, int y) {
        for (var i = 0; i < 6; i++) {
            if (plrs.get(0).onCard(gc.getInput(), i)) plrs.get(0).getClickArteQueue().offer(plrs.get(0).selection(i));
        }
    }


    // Returns the ID code for this game state
    public int getID() {
        return id;
    }



}
