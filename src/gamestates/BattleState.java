package gamestates;

import core.Main;
import entities.units.Unit;
import entities.units.enemy.Enemy;
import entities.units.player.Player;
import managers.CombatManager;
import map.GameMap;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

public class BattleState extends BasicGameState {
    private final int id;
    public static ArrayList<Unit> plrs;
    public static ArrayList<Unit> enemies;
    private GameMap battlefield;
    private CombatManager combat;
    private char result;

    public BattleState(int id) throws SlickException {
        this.id = id;
    }


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a game state for the *first time.*
        battlefield = new GameMap("res/tilemap/battleworld.tmx");
        gc.setShowFPS(true);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if(sbg.getCurrentStateID() == Main.BATTLE_ID){
            g.setFont(new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true));
            g.setBackground(new Color(100, 100, 100));
            battlefield.render(1000, -200);
            for(Unit p : plrs) {
                ((Player) p).battleRender(g, 0,0);
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
                    sbg.enterState(Main.GAME_ID);
                    break;
                case 'l':
                    sbg.enterState(Main.LOADING_ID);
                    break;
            }
        }

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        // This is where you put your game's logic that executes each frame that isn't about drawing

    }

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        // This code happens when you enter a gameState.
        for(int i = 0; i < plrs.size(); i++)   {
            //plrs.get(i).setPosition( -200 + i*200, i*1000);
            plrs.get(i).setPosition( 0, 0);
            ((Player)(plrs.get(i))).startBattle();
        }
        for(int i = 0; i < enemies.size(); i++)   {
            //enemies.get(i).setPosition( i * 200,  i * 200);
            enemies.get(i).setPosition( -100,  0);
        }

        combat = new CombatManager(plrs, enemies);
        combat.roundStart();
    }

    public void leave(GameContainer gc, StateBasedGame sbg) {
        // This code happens when you leave a gameState.
    }


    public void keyPressed(int key, char c) {



    }

    public void mousePressed(int button, int x, int y) {

    }


    // Returns the ID code for this game state
    public int getID() {
        return id;
    }



}
