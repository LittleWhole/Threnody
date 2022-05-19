package combat.artes.martial;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;

public class SonicSlash extends Arte {

    public static final Class<? extends PlayableCharacter> character = Sigur.class;

    public SonicSlash(PlayableCharacter owner) throws SlickException {
        super(owner);
        name = "Sonic Slash";
        arteType = ArteType.MARTIAL;
        cost = 1;
        castDuration = 100;
        this.sprite = new Image("res/martialCard.png");
    }

    @Override
    public void use(Unit target, GameContainer gc) {
        castTimestamp = BattleState.time;
        animation();
        activation(target);
    }

    @Override
    public void animation() {

    }

    @Override
    public void activation(Unit target) {
        target.takeDamage(owner.getAttack());
    }
}
