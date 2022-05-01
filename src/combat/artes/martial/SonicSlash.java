package combat.artes.martial;

import combat.artes.Arte;
import playerdata.PlayableCharacter;
import playerdata.Sigur;

public class SonicSlash extends Arte {

    public static final String name = "Sonic Slash";
    public static final Class<?> character = Sigur.class;

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
