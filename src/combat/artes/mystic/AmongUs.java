package combat.artes.mystic;

import combat.artes.ArteType;
import combat.artes.ElementType;
import combat.artes.MysticArte;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class AmongUs extends MysticArte<Player> {

    public AmongUs(Player owner) throws SlickException {
        super(owner);
        name = "Among Us";
        arteType = ArteType.MYSTIC;
        this.card = new Image("res/mysticCard.png");
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
        target.takeDamage(new BigInteger("Among Us".getBytes(StandardCharsets.UTF_8)).intValue(), ElementType.LIGHT);
    }

    @Override
    public void queue() {

    }

    @Override
    public void unqueue() {

    }
}
