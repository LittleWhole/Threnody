package managers;

import entities.units.enemy.Enemy;
import entities.units.Unit;
import entities.units.enemy.EnemyStates;
import entities.units.player.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import playerdata.playerState;

import java.util.ArrayList;

public class CombatManager {

    private ArrayList<Unit> players;
    private ArrayList<Unit> enemies;
    private int round;
    int turn = 0;
    public CombatManager(ArrayList<Unit> plrs, ArrayList<Unit> enemies)  {

        this.players = plrs;
        this.enemies = enemies;
        round = 1;
    }

    public char combat(Graphics g, GameContainer gc) throws InterruptedException {
        boolean plrsAlive = true;
        boolean enemiesAlive = true;
        boolean plrsDone = false;
        boolean enemiesDone = false;

        if(turn < players.size() && !plrsDone) {
            ((Player) players.get(turn)).setState(playerState.SELECTING);
            ((Player) players.get(turn)).move(enemies.get(0), gc, g);
            if(((Player) players.get(turn)).getState() == playerState.DONE) {
                updateTeams(enemies);
                if (enemies.size() == 0) {
                    return 'w';
                }
                turn++;
            }
        }
        if(turn == players.size())   {
            plrsDone = true;
        }
        if(plrsDone) {
            ((Enemy) enemies.get(turn-(players.size()-1))).setCombatState(EnemyStates.MOVING);
            ((Enemy) enemies.get(turn-(players.size()-1))).battleMove(players.get(0), gc);
            if(((Enemy) enemies.get(turn-(players.size()-1))).getCombatState()== EnemyStates.DONE) {
                updateTeams(players);
                if (players.size() == 0) {
                    return 'l';
                }
                turn++;
            }
        }
        if(enemiesDone) {
            turn = 0;
            round++;
            return 'a';
        }
        return 'h';
    }

    public int getRound()  {
        return round;
    }

    private void updateTeams (ArrayList<Unit> units)    {
        for(int i = 0; i < units.size();i++)  {
            if(units.get(i).getHealth() <= 0) {
                units.remove(i);
            }
        }
    }

}
