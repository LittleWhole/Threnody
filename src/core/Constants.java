package core;

public final class Constants {

    public static final class LevelingConstants {
        public static final int MAX_LEVEL = 100;
        public static final int MAX_EXP(int level) { return (int) (555 * Math.log(level + 2)); }
    }

}
