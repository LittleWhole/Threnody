package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.*;
import playerdata.characters.PlayableCharacter;

public class AquaLimit extends Arte<Player> {

    public AquaLimit(Player owner) throws SlickException {
        super(owner);
        name = "Aqua Limit";
        arteType = ArteType.ELEMENTAL;
        aniType = AnimationType.TARGET;
        element = ElementType.WATER;
        castDuration = 61;
        //this.card = new Image("res/beta/elementalCard.png");
        this.aniSheet = new SpriteSheet("res/animations/combat/aqua_limit.png",200, 200);
    }

    @Override
    public void activation(Unit target) {
        if (timer == 20) target.takeDamage((int) (owner.calculateDamage(ElementType.WATER) * 1.2), ElementType.WATER);
    }
}
