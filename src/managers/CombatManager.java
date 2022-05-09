package managers;

import entities.units.Enemy;
import entities.units.Unit;
import entities.units.player.Player;

import java.util.ArrayList;

public class CombatManager {

    private ArrayList<Unit> players;
    private ArrayList<Unit> enemies;

    public CombatManager(ArrayList<Unit> plrs, ArrayList<Unit> enemies)  {
        this.players = plrs;
        this.enemies = enemies;
    }

    public char combat()    {
        int round = 1;
        boolean plrsAlive = true;
        boolean enemiesAlive = true;
        while(plrsAlive && enemiesAlive)    {
            for(int i = 0; i < players.size(); i++) {
                ((Player) players.get(i)).move();
            }
            for(int i = 0; i < enemies.size(); i++) {
                ((Enemy) enemies.get(i)).battleMove();
            }
            if(!checkUnitsAlive(enemies))   {
                return 'w';
            }
            if(!checkUnitsAlive(players)) {
                return 'l';
            }
            round++;
        }
        return 'd';
    }

    private boolean checkUnitsAlive(ArrayList<Unit> units)    {
        for(Unit u: units)  {
            if(u.getHealth() <= 0)  {
                return false;
            }
        }
        return true;
    }

}
