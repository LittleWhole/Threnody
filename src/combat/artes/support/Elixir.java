package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import entities.units.player.Player;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Elixir extends Arte<Player> {
    private final int healingValue = 10;
    private final int manaValue = 1;

    public Elixir(Player owner) throws SlickException {
        super(owner);
        name = "Elixir";
        arteType = ArteType.SUPPORT;
        aniType = AnimationType.OWNER;
        cost = 2;
    }

    @Override
    public void activation(Unit target) { target.regenerate(healingValue); target.generateMana(manaValue); }
}
