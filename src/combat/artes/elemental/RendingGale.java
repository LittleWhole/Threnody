package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.*;
import playerdata.characters.PlayableCharacter;

public class RendingGale extends Arte<Player> {


    public RendingGale(Player owner) throws SlickException {
        super(owner);
        name = "Rending Gale";
        arteType = ArteType.ELEMENTAL;
        aniType = AnimationType.TARGET;
        element = ElementType.WIND;
        castDuration = 64;
        //this.card = new Image("res/beta/elementalCard.png");
        this.aniSheet = new SpriteSheet("res/animations/combat/RendingGale.png",200, 200);
    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) target.takeDamage((int) (owner.calculateDamage(element) * 0.8), element);
    }
}
