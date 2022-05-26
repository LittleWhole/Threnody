package combat.artes.elemental;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import playerdata.characters.PlayableCharacter;

public class AquaLimit extends Arte<Player> {

    public AquaLimit(Player owner) {
        super(owner);
        name = "Aqua Limit";
        arteType = ArteType.ELEMENTAL;
        element = ElementType.WATER;
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

    }
}
