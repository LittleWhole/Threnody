package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;

public class Fortification extends Arte<Unit> {

    private final int healingValue = 10;

    public Fortification(Unit owner) throws SlickException {
        super(owner);
        name = "Fortification";
        arteType = ArteType.SUPPORT;
        aniType = AnimationType.OWNER;
        cost = 1;
    }

    @Override
    public void activation(Unit target) {
        target.setHealth(target.getHealth() + this.healingValue);
    }
}
