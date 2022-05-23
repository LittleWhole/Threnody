package combat.artes.mystic;

import combat.artes.ArteType;
import combat.artes.MysticArte;
import entities.units.Unit;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;

public class Expiation extends MysticArte {

    public Expiation(PlayableCharacter owner) throws SlickException {
        super(owner);
        name = "Expiation";
        arteType = ArteType.MYSTIC;
        this.sprite = new Image("res/mysticCard.png");
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
