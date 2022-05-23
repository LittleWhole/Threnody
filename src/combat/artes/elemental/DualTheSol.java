package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import playerdata.characters.PlayableCharacter;

public class DualTheSol extends Arte {

    public DualTheSol(PlayableCharacter owner) {
        super(owner);
        name = "Dual the Sol";
        arteType = ArteType.ELEMENTAL;
        element = ElementType.LIGHT;
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
