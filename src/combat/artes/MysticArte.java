package combat.artes;

import entities.units.Unit;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;

public abstract class MysticArte<T extends Unit> extends Arte<T> {

    public MysticArte(T owner) throws SlickException {
        super(owner);
    }

}
