package combat.artes.martial;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import org.newdawn.slick.GameContainer;
import playerdata.PlayableCharacter;
import playerdata.Sigur;

public class SonicSlash extends Arte {

    public static final String name = "Sonic Slash";
    public static final Class<? extends PlayableCharacter> character = Sigur.class;
    public static final ArteType arteType = ArteType.MARTIAL;

    public SonicSlash(PlayableCharacter owner) {
        super(owner);
        cost = 1;
        castDuration = 100;
    }

    @Override
    public void use(Unit target, GameContainer gc) {
        castTimestamp = gc.getTime();
        animation();
        activation(target);
    }

    @Override
    public void animation() {

    }

    @Override
    public void activation(Unit target) {
        target.takeDamage(owner.getAttack());
    }
}
