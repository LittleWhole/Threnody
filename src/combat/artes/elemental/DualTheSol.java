package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.*;
import playerdata.characters.PlayableCharacter;

public class DualTheSol extends Arte<Player> {

    public DualTheSol(Player owner) throws SlickException {
        super(owner);
        name = "Dual the Sol";
        arteType = ArteType.ELEMENTAL;
        aniType = AnimationType.TARGET;
        element = ElementType.LIGHT;
        cost = 5;
        castDuration = 70;
    }

    @Override
    public void activation(Unit target) {
        if (timer == 24) target.takeDamage((int) (owner.calculateDamage(element) * 2.2), element);
        if (timer == 34 || timer == 44 || timer == 54 || timer == 64) target.takeDamage((int) (owner.calculateDamage(ElementType.FIRE) * 0.3), ElementType.FIRE);
    }
}
