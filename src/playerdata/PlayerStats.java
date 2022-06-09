package playerdata;

import core.Constants;

import java.io.Serializable;

public class PlayerStats extends PlayerData implements Serializable {
    public volatile int exp;
    public volatile int level;
    public volatile int gold;
    public transient volatile int maxExp;

    public PlayerStats() {
        super();
        exp = 0;
        level = 1;
        gold = 0;
        maxExp = Constants.LevelingConstants.MAX_EXP(level);
    }

    public void update() {
        maxExp = Constants.LevelingConstants.MAX_EXP(level);
        if (exp >= maxExp) {
            level++;
            exp = exp - maxExp;
        }
    }

    public void gainGold(int amount)   {
        gold += amount;
    }

    public void gainExp(int amount) {
        exp += amount;
        if (exp >= maxExp) {
            level++;
            exp = exp - maxExp;
        }
    }

    public void gainLevel(int amount) {
        level += amount;
    }
}
