package combat.artes.strike;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.*;

@SuppressWarnings({"rawtypes"})
public class ImpactCross extends Arte<Player> {
    public ImpactCross(Player owner) throws SlickException {
        super(owner);
        name = "Impact Cross";
        arteType = ArteType.STRIKE;
        aniType = AnimationType.TARGET;
        cost = 2;
        castDuration = 64;
    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) target.takeDamage((int) (owner.calculateDamage(ElementType.PHYSICAL) * 1.1), ElementType.PHYSICAL);
    }

    @Override
    public void queue() {

    }

    @Override
    public void unqueue() {

    }
}
