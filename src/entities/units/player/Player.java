package entities.units.player;

import core.Constants;
import core.Main;
import entities.units.Unit;

public class Player extends Unit {

    final public static float PLAYER_X_SPAWN = (float) Main.RESOLUTION_X / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;
    final public static float PLAYER_Y_SPAWN = (float) Main.RESOLUTION_Y / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;

    public Player() {
        super();
    }

    public Player(int level, int health, int attack, int defense, int critRate, int critDamage, int eAttack, int eDefense, int affinity) {
        super(level, health, attack, defense, critRate, critDamage, eAttack, eDefense, affinity);
        this.level = level;
    }

    public Player getPlayer() {
        return this;
    }
}
