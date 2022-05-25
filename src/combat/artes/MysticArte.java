package combat.artes;

import entities.units.Unit;
import playerdata.characters.PlayableCharacter;

public abstract class MysticArte<T extends Unit> extends Arte<T> {

    public MysticArte(T owner) {
        super(owner);
    }

}
