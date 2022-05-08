package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import playerdata.PlayableCharacter;

public class DualTheSol extends Arte {

    public static final String name = "Dual the Sol";
    public static final ArteType arteType = ArteType.ELEMENTAL;
    public static final ElementType element = ElementType.LIGHT;

    public DualTheSol(PlayableCharacter owner) {
        super(owner);
    }

    @Override
    public void animation() {

    }

    @Override
    public void activation() {

    }
}
