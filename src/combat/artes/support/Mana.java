package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import entities.units.player.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import playerdata.characters.PlayableCharacter;

public class Mana extends Arte<Player> {
    private final int healingValue = 10;

    public Mana(Player owner) throws SlickException {
        super(owner);
        name = "Mana";
        arteType = ArteType.SUPPORT;
        aniType = AnimationType.OWNER;
        cost = 0;
        castDuration = 75;
        this.aniSheet = new SpriteSheet("res/animations/combat/Mana.png",200, 200);
    }

    @Override
    public void activation(Unit target) {
        target.generateMana(healingValue);
    }
}
