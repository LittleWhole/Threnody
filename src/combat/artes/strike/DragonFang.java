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

public class DragonFang extends Arte<Player> {

    public DragonFang(Player owner) throws SlickException {
        super(owner);
        name = "Dragon Fang";
        arteType = ArteType.STRIKE;
        castDuration = 74;
        this.card = new Image("res/martialCard.png");
    }

    @Override
    public void animation(Unit target, Graphics g) {

    }

    @Override
    public void activation(Unit target) {
        if (timer == 50) target.takeDamage((int) (owner.calculateDamage(ElementType.PHYSICAL) * 1.15), ElementType.PHYSICAL);
    }
}
