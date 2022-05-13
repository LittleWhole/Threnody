package core;

public final class Constants {

    public static final String VERSION = "Alpha v0.1.0 Release Candidate 1";

    public static final class LevelingConstants {
        public static final int MAX_LEVEL = 100;
        public static final int MAX_EXP(int level) { return (int) (555 * Math.log(level + 2)); }
    }

    public static final class MovementConstants {
        public static final float DRAG = 0.05f;
        public static final float RESTITUTION_COEFFICIENT = 0.75f;
    }

    public static final class ImageConstants {
        public static final int PIXELS_PER_UNIT = 15;
        final public static float CENTER_X = Main.RESOLUTION_X / 2f;
        final public static float CENTER_Y = Main.RESOLUTION_Y / 2f;
    }

}
