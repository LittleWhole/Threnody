package combat.artes.mystic;

import combat.artes.ArteType;
import combat.artes.ElementType;
import combat.artes.MysticArte;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.*;
import playerdata.characters.PlayableCharacter;

import java.util.Random;

@SuppressWarnings({"rawtypes"})
public class Expiation extends MysticArte<Player> {
    Random R = new Random();
    public Expiation(Player owner) throws SlickException {
        super(owner);
        name = "Expiation";
        arteType = ArteType.MYSTIC;
        aniType = AnimationType.TARGET;
        cost = 1;
    }

    @Override
    public void activation(Unit target) {
        BattleState.enemies.forEach(e -> e.takeDamage((int) (owner.calculateDamage(ElementType.PHYSICAL) * 0.05), ElementType.PHYSICAL));
    }

    @Override
    public void queue() {

    }

    @Override
    public void dequeue() {

    }
}
