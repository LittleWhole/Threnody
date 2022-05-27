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

public class RendingGale extends Arte<Player> {


    public RendingGale(Player owner) throws SlickException {
        super(owner);
        name = "Rending Gale";
        arteType = ArteType.ELEMENTAL;
        element = ElementType.WIND;
        castDuration = 74;
        this.card = new Image("res/elementalCard.png");
    }

    @Override
    public void animation(Unit target, Graphics g) {

    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) target.takeDamage((int) (owner.calculateDamage(element) * 0.8), element);
    }
}
