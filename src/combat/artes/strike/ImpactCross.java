package combat.artes.strike;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImpactCross extends Arte<Player> {

    public ImpactCross(Player owner) throws SlickException {
        super(owner);
        name = "Impact Cross";
        arteType = ArteType.STRIKE;
        castDuration = 74;
        this.card = new Image("res/beta/martialCard.png");
    }

    @Override
    public void animation(Unit target, Graphics g) {

    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) target.takeDamage((int) (owner.calculateDamage(ElementType.PHYSICAL) * 1.1), ElementType.PHYSICAL);
    }
}
