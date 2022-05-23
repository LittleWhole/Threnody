package combat.artes.martial;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;

public class DragonFang extends Arte {

    public DragonFang(PlayableCharacter owner) throws SlickException {
        super(owner);
        name = "Dragon Fang";
        arteType = ArteType.MARTIAL;
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

    }
}
