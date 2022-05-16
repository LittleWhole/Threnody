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
        level = 0;
        gold = 0;
        maxExp = Constants.LevelingConstants.MAX_EXP(level);
    }

    public void update() {
        maxExp = Constants.LevelingConstants.MAX_EXP(level);
    }
}
