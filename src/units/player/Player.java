package units.player;

import core.Constants;
import org.jetbrains.annotations.Contract;

public sealed class Player permits Sigur, Phaedra   {

    private double x, y, xSpeed, ySpeed;
    private int level, exp, maxExp;
    private int health, attack, defense, critRate, critDamage, eAttack, eDefense, affinity;

    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF

    public Player() {
        this.level = 1;
        this.exp = 0;
        this.maxExp = Constants.LevelingConstants.MAX_EXP(1);
    }

    public void accelerateX(float amt)   {

    }
    public void accelerateY(float amt)   {

    }



    public Player getPlayer() {
        return this;
    }
}
