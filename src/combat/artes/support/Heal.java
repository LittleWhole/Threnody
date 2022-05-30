package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import org.newdawn.slick.*;

@SuppressWarnings({"rawtypes"})
public class Heal extends Arte<Unit> {
    public Heal(Unit owner) throws SlickException {
        super(owner);
        name = "Heal";
        arteType = ArteType.SUPPORT;
        aniType = AnimationType.OWNER;
        cost = 1;
    }

    @Override
    public void activation(Unit target) {
        if (timer == 30) owner.regenerate(owner.getAttack());
    }
}
