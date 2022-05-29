package managers;

import combat.artes.Arte;
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
import java.util.stream.Collectors;

public final class CombatManager {

    private volatile List<Player> players;
    private volatile List<Enemy> enemies;
    private int round;
    private int plrTurn;
    private int enemyTurn;
    public enum CombatState { WIN, LOSE, ADVANCE, HALT }
    public CombatManager(List<Player> plrs, List<Enemy> enemies)  {
        this.players = plrs;
        this.enemies = enemies;
        round = 1;
    }

    public void roundStart() {
        plrTurn = 0;
        enemyTurn = 0;
        players.forEach(p -> p.setState(PlayerState.SELECTING));
        enemies.forEach(e -> e.setCombatState(EnemyStates.CHOOSING));
    }

    public CombatState combat(Graphics g, GameContainer gc) throws InterruptedException {
        boolean plrsAlive = true;
        boolean enemiesAlive = true;
        if(plrTurn < players.size()) {
            if (enemies.size() == 0) {
                return CombatState.WIN;
            }
            if(players.get(plrTurn).getState() == PlayerState.SELECTING) {
                players.get(plrTurn).move(enemies.get(0), gc, g);
                g.drawString("SELECTING", 100, 0);
                try { g.drawString("Cards selected: " + players.get(plrTurn).getArteQueue().stream().map(a -> ((Arte) a).getName())
                        .collect(Collectors.joining(", ")), 500, 500); } catch (Exception ignored) {};
            }
            if(players.get(plrTurn).getState() == PlayerState.CASTING)   {
                players.get(plrTurn).attack(enemies.get(0), gc);
                g.drawString("CASTING", 100, 0);
            }
            if((players.get(plrTurn)).getState() == PlayerState.DONE) {
                updateTeams(enemies);
                if (enemies.size() == 0) {
                    return CombatState.WIN;
                }
                plrTurn++;
            }
        }
        else if(plrTurn >= players.size() && enemyTurn < enemies.size())   {
            if (players.size() == 0) {
                return CombatState.LOSE;
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
                    return CombatState.LOSE;
                }
                enemyTurn++;
            }
        }
        else    {
            updateTeams(enemies);
            updateTeams(players);
            round++;
            roundStart();
            return CombatState.ADVANCE;
        }

        return CombatState.HALT;
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
