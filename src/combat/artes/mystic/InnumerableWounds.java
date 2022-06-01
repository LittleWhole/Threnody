package combat.artes.mystic;

import combat.artes.ArteType;
import combat.artes.MysticArte;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;

@SuppressWarnings({"rawtypes"})
public class InnumerableWounds extends MysticArte<Player> {

    public InnumerableWounds(Player owner) throws SlickException {
        super(owner);
        name = "Innumerable Wounds";
        arteType = ArteType.MYSTIC;
        cost = 8;
    }

    @Override
    public void activation(Unit target) {

    }

    @Override
    public void queue() {

    }

    @Override
    public void dequeue() {

    }
}
