package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import entities.units.player.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import playerdata.characters.Phaedra;
import playerdata.characters.PlayableCharacter;

public class Catalysis extends Arte<Player> {

    public static final String name = "Heal";
    public static final Class<? extends PlayableCharacter> character = Phaedra.class;
    public static final ArteType type = ArteType.SUPPORT;
    private final int affinity = 20;

    public Catalysis(Player owner) {
        super(owner);
        cost = 1;
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
        target.regenerate(owner.getAttack());
    }
}
