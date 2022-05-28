package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;

public class AquaLimit extends Arte<Player> {

    public AquaLimit(Player owner) throws SlickException {
        super(owner);
        name = "Aqua Limit";
        arteType = ArteType.ELEMENTAL;
        element = ElementType.WATER;
        castDuration = 74;
        this.card = new Image("res/beta/elementalCard.png");
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
        if (timer == 20) target.takeDamage((int) (owner.calculateDamage(ElementType.WATER) * 1.2), ElementType.WATER);
    }
}
