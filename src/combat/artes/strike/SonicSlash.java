package combat.artes.strike;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import org.newdawn.slick.*;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;

@SuppressWarnings({"rawtypes"})
public class SonicSlash extends Arte<Player> {
    public static final Class<? extends PlayableCharacter> character = Sigur.class;

    public SonicSlash(Player owner) throws SlickException {
        super(owner);
        name = "Sonic Slash";
        arteType = ArteType.STRIKE;
        aniType = AnimationType.TARGET;
        cost = 1;
        castDuration = 74;
    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) target.takeDamage((int) (owner.calculateDamage(ElementType.PHYSICAL) * 0.9), ElementType.PHYSICAL);
    }

    @Override
    public void queue() {

    }

    @Override
    public void dequeue() {

    }
}
