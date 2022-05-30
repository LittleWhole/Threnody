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
public class Expiation extends MysticArte<Player> {
    public Expiation(Player owner) throws SlickException {
        super(owner);
        name = "Expiation";
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
