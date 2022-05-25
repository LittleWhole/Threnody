package managers;

import entities.units.enemy.Enemy;
import entities.units.Unit;
import entities.units.enemy.EnemyStates;
import entities.units.player.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import entities.units.player.PlayerState;
import util.DrawUtilities;

import java.util.ArrayList;
import java.util.List;

public class CombatManager {

    private volatile List<Player> players;
    private volatile List<Enemy> enemies;
    private int round;
    private int turn = 0;
    public CombatManager(List<Player> plrs, List<Enemy> enemies)  {

        this.players = plrs;
        this.enemies = enemies;
        round = 1;
    }

    public void roundStart() {
        turn = 0;
        for(int i = 0; i < players.size(); i++) {
            (players.get(i)).setState(PlayerState.SELECTING);
        }
        for(int i = 0; i < enemies.size(); i++) {
            (enemies.get(i)).setCombatState(EnemyStates.CHOOSING);
        }
    }

    public char combat(Graphics g, GameContainer gc) throws InterruptedException {
        boolean plrsAlive = true;
        boolean enemiesAlive = true;
        if(turn < players.size()) {
            if (enemies.size() == 0) {
                return 'w';
            }
            if(players.get(turn).getState() == PlayerState.SELECTING) {
                players.get(turn).move(enemies.get(0), gc, g);
                g.drawString("SELECTING", 100, 0);
            }
            if(players.get(turn).getState() == PlayerState.CASTING)   {
                players.get(turn).attack(enemies.get(0), gc);
                g.drawString("CASTING", 100, 0);
            }
            if((players.get(turn)).getState() == PlayerState.DONE) {
                updateTeams(enemies);
                if (enemies.size() == 0) {
                    return 'w';
                }
                turn++;
            }
        }
        else if(turn >= players.size() && turn <= enemies.size()+players.size())   {
            if (players.size() == 0) {
                return 'l';
            }
            if((enemies.get(turn-(players.size()-1))).getCombatState() == EnemyStates.CHOOSING)  {
                (enemies.get(turn-(players.size()-1))).battleSelect();
            }
            if((enemies.get(turn-(players.size()-1))).getCombatState() == EnemyStates.MOVING)  {
                (enemies.get(turn-(players.size()-1))).battleMove(players.get(0), gc);
                DrawUtilities.drawStringCentered(g, "MOVING", 800, 100);
            }
            if((enemies.get(turn-(players.size()-1))).getCombatState() == EnemyStates.DONE) {
                updateTeams(players);
                if (players.size() == 0) {
                    return 'l';
                }
                turn++;
            }
        }
        else    {
            turn = 0;
            round++;
            roundStart();
            return 'a';
        }

        return 'h';
    }

    public int getRound()  {
        return round;
    }

    private void updateTeams (List<? extends Unit> units)    {
        for(int i = 0; i < units.size();i++)  {
            if(units.get(i).getHealth() <= 0) {
                units.remove(i);
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getTurn() {
        return turn;
    }
}
