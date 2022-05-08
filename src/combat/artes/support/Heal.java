package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import playerdata.Phaedra;
import playerdata.PlayableCharacter;
import playerdata.Sigur;

public class Heal extends Arte {

    public static final String name = "Heal";
    public static final Class<? extends PlayableCharacter> character = Phaedra.class;
    public static final ArteType type = ArteType.SUPPORT;
    private final int castDuration = 120;

    public Heal(PlayableCharacter owner) {
        super(owner);
    }

    @Override
    public void animation() {

    }

    @Override
    public void activation() {

    }
}