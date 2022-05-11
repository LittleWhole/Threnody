package combat.artes.mystic;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.MysticArte;
import entities.units.Unit;
import org.newdawn.slick.GameContainer;
import playerdata.PlayableCharacter;

public class TrillionDrive extends MysticArte {

    public static final String name = "Trillion Drive";
    public static final ArteType arteType = ArteType.MYSTIC;

    public TrillionDrive(PlayableCharacter owner) {
        super(owner);
    }

    @Override
    public void use(Unit target, GameContainer gc) {
        castTimestamp = gc.getTime();
        animation();
        activation(target);
    }

    @Override
    public void animation() {


    }

    @Override
    public void activation(Unit target) {

    }
}
