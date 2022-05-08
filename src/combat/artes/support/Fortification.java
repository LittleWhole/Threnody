package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import playerdata.Phaedra;
import playerdata.PlayableCharacter;

public class Fortification extends Arte {

    public static final String name = "Fortification";
    public static final ArteType type = ArteType.SUPPORT;
    private final int healingValue = 10;

    public Fortification(PlayableCharacter owner) {
        super(owner);
    }

    @Override
    public void animation() {

    }

    @Override
    public void activation() {

    }
}