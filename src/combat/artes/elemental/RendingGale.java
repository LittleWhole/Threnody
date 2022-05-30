package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.*;
import playerdata.characters.PlayableCharacter;

@SuppressWarnings({"rawtypes"})
public class RendingGale extends Arte<Player> {


    public RendingGale(Player owner) throws SlickException {
        super(owner);
        name = "Rending Gale";
        arteType = ArteType.ELEMENTAL;
        aniType = AnimationType.TARGET;
        element = ElementType.WIND;
        cost = 5;
        castDuration = 64;
    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) target.takeDamage((int) (owner.calculateDamage(element) * 1.8), element);
    }

    @Override
    public void queue() {

    }

    @Override
    public void unqueue() {

    }
}
