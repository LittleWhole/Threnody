package entities.units;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Direction {
    NONE, NORTH, SOUTH, EAST, WEST;
    private static final List<Direction> all = List.of(values());
    private static final List<Direction> northSouth = all.subList(1,3);
    private static final List<Direction> eastWest = all.subList(3,all.size());
    private static final Random  RANDOM = new Random();
    public static Direction randomNorthSouth()  {
        return Math.random()>0.3?northSouth.get(RANDOM.nextInt(northSouth.size())):NONE;
    }
    public static Direction randomEastWest()  {
        return Math.random()>0.3?eastWest.get(RANDOM.nextInt(eastWest.size())):NONE;
    }

}
