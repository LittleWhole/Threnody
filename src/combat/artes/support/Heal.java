package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import gamestates.BattleState;
import org.newdawn.slick.*;
import playerdata.characters.Phaedra;
import playerdata.characters.PlayableCharacter;

public class Heal extends Arte<Unit> {


    public Heal(Unit owner) throws SlickException {
        super(owner);
        name = "Heal";
        arteType = ArteType.SUPPORT;
        cost = 1;
        castDuration = 120;
        this.aniSheet = new SpriteSheet("res/animations/combat/heal.png",200, 200);
        this.card = new Image("res/healCard.png");
    }

    /*@Override
    public void use(Unit target, GameContainer gc) {
        castTimestamp = BattleState.time;
        animation(target, gc.getGraphics());
        activation(target);
    }*/

    @Override
    public void animation(Unit target, Graphics g) {

    }

    @Override
    public void activation(Unit target) {
        if (timer == 119) owner.regenerate(owner.getAttack());
    }
}
