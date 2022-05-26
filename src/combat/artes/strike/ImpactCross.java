package combat.artes.strike;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImpactCross extends Arte<Player> {

    public ImpactCross(Player owner) throws SlickException {
        super(owner);
        name = "Impact Cross";
        arteType = ArteType.STRIKE;
        this.card = new Image("res/martialCard.png");
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
