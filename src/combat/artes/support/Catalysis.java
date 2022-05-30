package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import entities.units.player.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import playerdata.characters.Phaedra;
import playerdata.characters.PlayableCharacter;

@SuppressWarnings({"rawtypes"})
public class Catalysis extends Arte<Player> {
    private final int affinity = 20;

    public Catalysis(Player owner) throws SlickException {
        super(owner);
        name = "Catalysis";
        arteType = ArteType.SUPPORT;
        aniType = AnimationType.OWNER;
        cost = 1;
    }

    @Override
    public void activation(Unit target) {
        target.regenerate(owner.getAttack());
    }

    @Override
    public void queue() {

    }

    @Override
    public void unqueue() {

    }
}
