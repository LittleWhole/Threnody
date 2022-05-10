package managers;

import entities.units.Enemy;
import entities.units.Unit;
import entities.units.player.Player;
import java.util.ArrayList;

public class CombatManager {

    private ArrayList<Unit> players;
    private ArrayList<Unit> enemies;
    private int round;

    public CombatManager(ArrayList<Unit> plrs, ArrayList<Unit> enemies)  {

        this.players = plrs;
        this.enemies = enemies;
        round = 1;
    }

    public char combat()    {
        boolean plrsAlive = true;
        boolean enemiesAlive = true;
        for(int i = 0; i < players.size(); i++) {
            ((Player) players.get(i)).move(enemies.get(0));
            updateTeams(enemies);
            if(enemies.size() == 0)   {
                return 'w';
            }
        }
        for(int i = 0; i < enemies.size(); i++) {
            ((Enemy) enemies.get(i)).battleMove(players.get(0));
            updateTeams(players);
            if(players.size() == 0) {
                return 'l';
            }
        }
        round++;
        return 'd';
    }

    private void updateTeams (ArrayList<Unit> units)    {
        for(int i = 0; i < units.size();i++)  {
            if(units.get(i).getHealth() <= 0) {
                units.remove(i);
            }
        }
    }

}
