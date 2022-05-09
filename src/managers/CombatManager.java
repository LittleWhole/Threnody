package managers;

import entities.units.Enemy;
import entities.units.Unit;
import entities.units.player.Player;

import java.util.ArrayList;

public class CombatManager {

    private ArrayList<Player> players;
    private ArrayList<Enemy> enemies;

    public CombatManager(ArrayList<Player> plrs, ArrayList<Enemy> enemies)  {
        this.players = plrs;
        this.enemies = enemies;
    }

    public char combat()    {
        int round = 1;
        boolean plrsAlive = true;
        boolean enemiesAlive = true;
        while(plrsAlive && enemiesAlive)    {
            for(int i = 0; i < players.size(); i++) {
                players.get(i).move();
            }
            for(int i = 0; i < enemies.size(); i++) {
                enemies.get(i).battleMove();
            }
            if(!checkEnemiesAlive(enemies))   {
                return 'w';
            }
            if(!checkPlayersAlive(players)) {
                return 'l';
            }
            round++;
        }
        return 'd';
    }

    private void updateEnemies(ArrayList<Enemy> enemies)    {
        for(int i = 0; i < enemies.size();i++)  {
            if(enemies.get(i).)
        }
    }
    private boolean checkPlayersAlive(ArrayList<Player> plrs)   {
        for(Player u: plrs)  {
            if(u.getHealth() <= 0)  {
                return false;
            }
        }
        return true;
        /*if andrew = shorthair
                then andrew= rat
                        dsplay andrewnationality;
        else{
            return false;
        }*/
    }
}
