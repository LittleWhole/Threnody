package combat.artes.strike;

import combat.artes.Arte;
import combat.artes.ArteType;
import combat.artes.ElementType;
import entities.units.Unit;
import entities.units.player.Player;
import org.newdawn.slick.*;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;

public class SonicSlash extends Arte<Player> {

    public static final Class<? extends PlayableCharacter> character = Sigur.class;

    public SonicSlash(Player owner) throws SlickException {
        super(owner);
        name = "Sonic Slash";
        arteType = ArteType.STRIKE;
        cost = 1;
        castDuration = 74;
        this.card = new Image("res/beta/martialCard.png");
        this.aniSheet = new SpriteSheet("res/animations/combat/sonic_slash.png",200,200);
    }

    /*@Override
    public void use(Unit target, GameContainer gc) {
        timer = 0;
        activation(target);
        animation(target, gc.getGraphics());
    }*/

    @Override
    public void animation(Unit target, Graphics g) {
        if(!finished()) {
            this.aniFrame = aniSheet.getSprite(timer, 0);
            g.drawImage(aniFrame,-target.getPosition().getX(), -target.getY() + target.getHeight());
        }

    }

    @Override
    public void activation(Unit target) {
        if (timer == 40) target.takeDamage((int) (owner.calculateDamage(ElementType.PHYSICAL) * 0.9), ElementType.PHYSICAL);
    }
}
