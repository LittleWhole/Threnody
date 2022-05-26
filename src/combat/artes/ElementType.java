package combat.artes;

import org.newdawn.slick.Color;

public enum ElementType {
    PHYSICAL(Color.white),
    FIRE(Color.red),
    WATER(Color.blue),
    EARTH(new Color(181, 101, 30)),
    ICE(Color.cyan), WIND(Color.green),
    ELECTRIC(Color.magenta),
    LIGHT(Color.yellow),
    DARK(Color.gray);

    public final Color color;

    ElementType(Color color) {
        this.color = color;
    }

}
