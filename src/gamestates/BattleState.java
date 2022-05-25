package gamestates;

import combat.artes.Arte;
import core.Main;
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
import java.util.Random;
import java.util.stream.Collectors;

public class BattleState extends ThrenodyGameState {
    private final int id;
    public static ArrayList<Unit> plrs;
    public static ArrayList<Unit> enemies;
    private GameMap battlefield;
    private CombatManager combat;
    private char result;
    public static long time;
    public static int expGain;
    public static int currencyGain;

    public static ArrayList<DamageNumber> damageNumbers;
    private ArrayList<DamageNumber> toRemove = new ArrayList<>();

    public BattleState(int id) throws SlickException {
        this.id = id;
    }


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a game state for the *first time.*
        battlefield = new GameMap("res/tilemap/battleworld.tmx");
        gc.setShowFPS(true);
        damageNumbers = new ArrayList<>();
        expGain = 0;
        currencyGain = 0;
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        time++;
        if(sbg.getCurrentStateID() == Main.BATTLE_ID){

            battlefield.render(1000, -200);
            for(Unit p : plrs) {
                ((Player) p).battleRender(g, 0,0);
                DrawUtilities.drawStringCentered(g, String.valueOf(((Player) p).getHealth()), 100, 100);
            }
            for(Unit e:enemies) {
                ((Enemy) e).render(g, 0,0);
            }
            try {
                result = combat.combat(g, gc);
            } catch (InterruptedException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            g.drawString("" + combat.getRound(), 0, 0);
            switch (result) {
                case 'w':
                    expGain = 10;
                    currencyGain = 10;
                    sbg.enterState(Main.GAME_ID);
                    break;
                case 'l':
                    sbg.enterState(Main.TITLE_ID);
                    break;
            }
        }

        damageNumbers.forEach(n -> {
            n.update(gc);
            n.render(g, 0, 0);
            if (n.isExpired()) toRemove.add(n);
        });
        damageNumbers.removeAll(toRemove);
        super.render(gc, sbg, g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        // This is where you put your game's logic that executes each frame that isn't about drawing
    }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        time = 0;
        // This code happens when you enter a gameState.
        for(int i = 0; i < plrs.size(); i++)   {
            //plrs.get(i).setPosition( -200 + i*200, i*1000);
            plrs.get(i).setPosition( -800, -25);
            ((Player)(plrs.get(i))).startBattle();
        }
        for(int i = 0; i < enemies.size(); i++)   {
            //enemies.get(i).setPosition( i * 200,  i * 200);
            enemies.get(i).setPosition( -1000 - (i*100),  0 - (i*50));
        }

        combat = new CombatManager(plrs, enemies);
        combat.roundStart();
        gc.getGraphics().setFont(new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true));
        gc.getGraphics().setBackground(new Color(100, 100, 100));

        //var temp = new Random();

        /*for (var i = 0; i < 100; i++) {
            damageNumbers.add(new DamageNumber(temp.nextInt(0, 3000), temp.nextInt(0, 1920), temp.nextInt(0, 1080)));
        }*/
    }

    public void leave(GameContainer gc, StateBasedGame sbg) {
        // This code happens when you leave a gameState.
    }


    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
    }

    public void mousePressed(int button, int x, int y) {

    }


    // Returns the ID code for this game state
    public int getID() {
        return id;
    }



}
