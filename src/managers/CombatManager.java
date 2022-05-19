package managers;

import entities.units.enemy.Enemy;
import entities.units.Unit;
import entities.units.enemy.EnemyStates;
import entities.units.player.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import entities.units.player.PlayerState;

import java.util.ArrayList;

public class CombatManager {

    private volatile ArrayList<Unit> players;
    private volatile ArrayList<Unit> enemies;
    private int round;
    int turn = 0;
    public CombatManager(ArrayList<Unit> plrs, ArrayList<Unit> enemies)  {

        this.players = plrs;
        this.enemies = enemies;
        round = 1;
    }

    public void roundStart() {
        turn = 0;
        for(int i = 0; i < players.size(); i++) {
            ((Player)players.get(i)).setState(PlayerState.SELECTING);
        }
        for(int i = 0; i < enemies.size(); i++) {
            ((Enemy)enemies.get(i)).setCombatState(EnemyStates.MOVING);
        }
    }

    public char combat(Graphics g, GameContainer gc) throws InterruptedException {
        boolean plrsAlive = true;
        boolean enemiesAlive = true;
        if(turn < players.size()) {
            if (enemies.size() == 0) {
                return 'w';
            }
            if(((Player) players.get(turn)).getState() == PlayerState.SELECTING) {
                ((Player) players.get(turn)).move(enemies.get(0), gc, g);
            }
            if(((Player)players.get(turn)).getState() == PlayerState.CASTING)   {
                ((Player) players.get(turn)).attack(enemies.get(0), gc);
                g.drawString("CASTING", 100, 0);
            }
            if(((Player) players.get(turn)).getState() == PlayerState.DONE) {
                updateTeams(enemies);
                if (enemies.size() == 0) {
                    return 'w';
                }
                turn++;
            }
        }
        else if(turn > players.size() && turn <= enemies.size()+players.size())   {
            if (players.size() == 0) {
                return 'l';
            }
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

    private void updateTeams (ArrayList<Unit> units)    {
        for(int i = 0; i < units.size();i++)  {
            if(units.get(i).getHealth() <= 0) {
                units.remove(i);
            }
        }
    }

}
