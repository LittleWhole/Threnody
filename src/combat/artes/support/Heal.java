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
        aniType = AnimationType.OWNER;
        cost = 1;
        castDuration = 60;
        this.aniSheet = new SpriteSheet("res/animations/combat/heal.png",200, 200);
    }

    /*@Override
    public void use(Unit target, GameContainer gc) {
        castTimestamp = BattleState.time;
        animation(target, gc.getGraphics());
        activation(target);
    }*/

    @Override
    public void activation(Unit target) {
        if (timer == 30) owner.regenerate(owner.getAttack());
    }
}
