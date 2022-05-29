package combat.artes.strike;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.*;

public class ImpactCross extends Arte<Player> {

    public ImpactCross(Player owner) throws SlickException {
        super(owner);
        name = "Impact Cross";
        arteType = ArteType.STRIKE;
        aniType = AnimationType.TARGET;
        castDuration = 64;
        this.aniSheet = new SpriteSheet("res/animations/combat/ImpactCross.png",200, 200);
    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) target.takeDamage((int) (owner.calculateDamage(ElementType.PHYSICAL) * 1.1), ElementType.PHYSICAL);
    }
}
