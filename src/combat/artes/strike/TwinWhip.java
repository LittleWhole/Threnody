package combat.artes.strike;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;

public class TwinWhip extends Arte<Player> {

    public static final Class<? extends PlayableCharacter> character = Sigur.class;

    public TwinWhip(Player owner) throws SlickException {
        super(owner);
        name = "Twin Whip";
        arteType = ArteType.STRIKE;
        cost = 1;
        castDuration = 74;
    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) target.takeDamage((int) (owner.calculateDamage(ElementType.PHYSICAL) * 0.5), ElementType.PHYSICAL);
        if (timer == 60) target.takeDamage((int) (owner.calculateDamage(ElementType.PHYSICAL) * 0.5), ElementType.PHYSICAL);
    }
}
