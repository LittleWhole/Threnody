package combat.artes.mystic;

import combat.artes.ArteType;
import combat.artes.MysticArte;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class DivineConqueror extends MysticArte<Player> {

    public DivineConqueror(Player owner) throws SlickException {
        super(owner);
        name = "Divine Conqueror";
        arteType = ArteType.MYSTIC;
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
