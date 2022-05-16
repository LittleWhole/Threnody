package combat.artes.martial;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;

public class ImpactCross extends Arte {

    public ImpactCross(PlayableCharacter owner) throws SlickException {
        super(owner);
        String name = "Impact Cross";
        ArteType arteType = ArteType.MARTIAL;
        this.sprite = new Image("res/martialCard.png");
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
