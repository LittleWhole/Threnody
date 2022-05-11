package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import org.newdawn.slick.GameContainer;
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
        cost = 1;
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
        target.regenerate(owner.getAttack());
    }
}
