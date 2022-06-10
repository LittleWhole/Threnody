package combat.artes.mystic;

import combat.artes.ArteType;
import combat.artes.ElementType;
import combat.artes.MysticArte;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

@SuppressWarnings({"rawtypes"})
public class DivineConqueror extends MysticArte<Player> {
    public DivineConqueror(Player owner) throws SlickException {
        super(owner);
        name = "Divine Conqueror";
        arteType = ArteType.MYSTIC;
        aniType = AnimationType.TARGET;
        cost = 9;
        castDuration = 74;
    }

    @Override
    public void activation(Unit target) {
        if (timer == 50) target.takeDamage((int) (owner.calculateDamage(ElementType.LIGHT) * 4.2), ElementType.LIGHT);
        else if (timer > 50 && timer < 60) target.takeDamage((int) (owner.calculateDamage(ElementType.LIGHT) * 0.2), ElementType.LIGHT);
    }

    @Override
    public void queue() {

    }

    @Override
    public void dequeue() {

    }
}
