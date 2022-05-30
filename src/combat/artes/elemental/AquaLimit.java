package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.*;
import playerdata.characters.PlayableCharacter;

@SuppressWarnings({"rawtypes"})
public class AquaLimit extends Arte<Player> {

    public AquaLimit(Player owner) throws SlickException {
        super(owner);
        name = "Aqua Limit";
        arteType = ArteType.ELEMENTAL;
        aniType = AnimationType.TARGET;
        element = ElementType.WATER;
        cost = 4;
        castDuration = 61;
    }

    @Override
    public void activation(Unit target) {
        if (timer == 20) target.takeDamage((int) (owner.calculateDamage(ElementType.WATER) * 2), ElementType.WATER);
    }
}
