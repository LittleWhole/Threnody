package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import entities.units.player.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import playerdata.characters.PlayableCharacter;

public class Mana extends Arte<Player> {

    public static final String name = "Fortification";
    public static final ArteType type = ArteType.SUPPORT;
    private final int healingValue = 10;

    public Mana(Player owner) {
        super(owner);
        cost = 0;
        castDuration = 100;
    }

    @Override
    public void use(Unit target, GameContainer gc) {
        castTimestamp = gc.getTime();
        animation(target, gc.getGraphics());
        activation(target);
    }

    @Override
    public void animation(Unit target, Graphics g) {

    }

    @Override
    public void activation(Unit target) {
        target.generateMana(healingValue);
    }
}
