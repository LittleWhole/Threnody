package managers;

import entities.units.enemy.Enemy;
import entities.units.Unit;
import entities.units.enemy.EnemyStates;
import entities.units.player.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import entities.units.player.PlayerState;
import util.DrawUtilities;

import java.util.List;

public class CombatManager {

    private volatile List<Player> players;
    private volatile List<Enemy> enemies;
    private int round;
    private int plrTurn;
    private int enemyTurn;
    public CombatManager(List<Player> plrs, List<Enemy> enemies)  {

        this.players = plrs;
        this.enemies = enemies;
        round = 1;
    }

    public void roundStart() {
        plrTurn = 0;
        enemyTurn = 0;
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
        if(plrTurn < players.size()) {
            if (enemies.size() == 0) {
                return 'w';
            }
            if(players.get(plrTurn).getState() == PlayerState.SELECTING) {
                players.get(plrTurn).move(enemies.get(0), gc, g);
                g.drawString("SELECTING", 100, 0);
            }
            if(players.get(plrTurn).getState() == PlayerState.CASTING)   {
                players.get(plrTurn).attack(enemies.get(0), gc);
                g.drawString("CASTING", 100, 0);
            }
            if((players.get(plrTurn)).getState() == PlayerState.DONE) {
                updateTeams(enemies);
                if (enemies.size() == 0) {
                    return 'w';
                }
                plrTurn++;
            }
        }
        else if(plrTurn >= players.size() && enemyTurn < enemies.size()+players.size())   {
            if (players.size() == 0) {
                return 'l';
            }
            if((enemies.get(enemyTurn-(players.size()-1))).getCombatState() == EnemyStates.CHOOSING)  {
                (enemies.get(enemyTurn-(players.size()-1))).battleSelect();
            }
            if((enemies.get(enemyTurn-(players.size()-1))).getCombatState() == EnemyStates.MOVING)  {
                (enemies.get(enemyTurn-(players.size()-1))).battleMove(players.get(0), gc);
                DrawUtilities.drawStringCentered(g, "MOVING", 800, 100);
            }
            if((enemies.get(enemyTurn-(players.size()-1))).getCombatState() == EnemyStates.DONE) {
                updateTeams(players);
                if (players.size() == 0) {
                    return 'l';
                }
                enemyTurn++;
            }
        }
        else    {
            updateTeams(enemies);
            updateTeams(players);
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

    public int getPlrTurn() {
        return plrTurn;
    }
    public int getEnemyTurn()   {
        return enemyTurn;
    }

}
