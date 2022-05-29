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

public class DualTheSol extends Arte<Player> {

    public DualTheSol(Player owner) throws SlickException {
        super(owner);
        name = "Dual the Sol";
        arteType = ArteType.ELEMENTAL;
        element = ElementType.LIGHT;
        castDuration = 74;
        //this.card = new Image("res/beta/elementalCard.png");
    }

    @Override
    public void animation(Unit target, Graphics g) {

    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) target.takeDamage((int) (owner.calculateDamage(element) * 1.5), element);
    }
}
