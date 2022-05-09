package combat.artes.martial;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Enemy;
import entities.units.Unit;
import playerdata.PlayableCharacter;
import playerdata.Sigur;

public class DragonFang extends Arte {

    public static final String name = "Dragon Fang";
    public static final ArteType arteType = ArteType.MARTIAL;

    public DragonFang(PlayableCharacter owner) {
        super(owner);
    }

    @Override
    public void animation() {

    }

    public void activation(Unit target) {

    }
}
