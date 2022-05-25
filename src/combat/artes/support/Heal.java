package combat.artes.support;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import playerdata.characters.Phaedra;
import playerdata.characters.PlayableCharacter;

public class Heal extends Arte<Unit> {

    public static final String name = "Heal";
    public static final ArteType type = ArteType.SUPPORT;
    private final int castDuration = 120;

    public Heal(Unit owner) throws SlickException {
        super(owner);
        this.aniSheet = new SpriteSheet("res/animations/heal.png",200, 200);
        cost = 1;
    }

    @Override
    public void use(Unit target, GameContainer gc) {
        castTimestamp = BattleState.time;
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
