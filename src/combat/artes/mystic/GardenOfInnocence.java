package combat.artes.mystic;

import combat.artes.ArteType;
import combat.artes.MysticArte;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

@SuppressWarnings({"rawtypes"})
public class GardenOfInnocence extends MysticArte<Player> {
    public GardenOfInnocence(Player owner) throws SlickException {
        super(owner);
        name = "Garden of Innocence";
        arteType = ArteType.MYSTIC;
        cost = 7;
    }

    @Override
    public void activation(Unit target) {

    }

    @Override
    public void queue() {

    }

    @Override
    public void unqueue() {

    }
}
