package combat.artes.martial;

import combat.artes.Arte;
import combat.artes.ArteType;
import playerdata.PlayableCharacter;
import playerdata.Sigur;

public class SonicSlash extends Arte {

    public static final String name = "Sonic Slash";
    public static final Class<? extends PlayableCharacter> character = Sigur.class;
    public static final ArteType arteType = ArteType.MARTIAL;

    public SonicSlash(PlayableCharacter owner) {
        super(owner);
    }

    @Override
    public void animation() {

    }

    @Override
    public void activation() {

    }
}
