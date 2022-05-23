package combat.artes.mystic;

import combat.artes.ArteType;
import combat.artes.MysticArte;
import entities.units.Unit;
import gamestates.BattleState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;

public class TrillionDrive extends MysticArte {

    public TrillionDrive(PlayableCharacter owner) throws SlickException {
        super(owner);
        name = "Trillion Drive";
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

    }
}
