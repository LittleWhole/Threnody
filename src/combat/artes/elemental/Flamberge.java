package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Flamberge extends Arte<Player> {

    public Flamberge(Player owner) throws SlickException {
        super(owner);
        name = "Flamberge";
        arteType = ArteType.ELEMENTAL;
        element = ElementType.FIRE;
        cost = 5;
        castDuration = 61;
    }

    @Override
    public void animation(Unit target, Graphics g) {
//        if(!finished()) {
//            this.aniFrame = aniSheet.getSprite(timer, 0);
//            g.drawImage(aniFrame,-target.getPosition().getX(), -target.getY() + target.getHeight());
//        }
    }

    @Override
    public void activation(Unit target) {
        if (timer == 20) target.takeDamage((int) (owner.calculateDamage(ElementType.FIRE) * 2.2), ElementType.FIRE);
    }
}
