package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import org.newdawn.slick.GameContainer;
import playerdata.PlayableCharacter;

public class RendingGale extends Arte {

    public static final String name = "Rending Gale";
    public static final ArteType arteType = ArteType.ELEMENTAL;
    public static final ElementType element = ElementType.WIND;

    public RendingGale(PlayableCharacter owner) {
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
