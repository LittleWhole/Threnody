package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import org.newdawn.slick.GameContainer;
import playerdata.characters.PlayableCharacter;

public class Fortification extends Arte {

    public static final String name = "Fortification";
    public static final ArteType type = ArteType.SUPPORT;
    private final int healingValue = 10;

    public Fortification(PlayableCharacter owner) {
        super(owner);
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

    }
}
