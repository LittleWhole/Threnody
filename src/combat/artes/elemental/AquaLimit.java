package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import playerdata.characters.PlayableCharacter;

public class AquaLimit extends Arte {

    public static final String name = "Aqua Limit";
    public static final ArteType arteType = ArteType.ELEMENTAL;
    public static final ElementType element = ElementType.WATER;

    public AquaLimit(PlayableCharacter owner) {
        super(owner);
    }

    @Override
    public void use(Unit target, GameContainer gc) {
        castTimestamp = BattleState.time;
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
