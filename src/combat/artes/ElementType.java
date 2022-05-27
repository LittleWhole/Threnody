package combat.artes;

import org.newdawn.slick.Color;

public enum ElementType {
    PHYSICAL(Color.white),
    FIRE(new Color(224, 100, 65)),
    WATER(Color.blue),
    EARTH(new Color(181, 101, 30)),
    ICE(Color.cyan),
    WIND(new Color(66, 245, 144)),
    ELECTRIC(Color.magenta),
    LIGHT(Color.yellow),
    DARK(Color.gray);

    public final Color color;

    ElementType(Color color) {
        this.color = color;
    }

}
