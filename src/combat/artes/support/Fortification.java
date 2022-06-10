package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import entities.units.player.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;

@SuppressWarnings({"rawtypes"})
public class Fortification extends Arte<Player> {
    private final int healingValue = 30;

    public Fortification(Player owner) throws SlickException {
        super(owner);
        name = "Fortification";
        arteType = ArteType.SUPPORT;
        aniType = AnimationType.OWNER;
        cost = 1;
    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) owner.addDefense(healingValue);
    }

    @Override
    public void queue() {

    }

    @Override
    public void dequeue() {

    }
}
