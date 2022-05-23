package combat.artes.martial;

import combat.artes.Arte;
import combat.artes.ArteType;
import entities.units.Unit;
import gamestates.BattleState;
import graphics.ui.combat.DamageNumber;
import org.newdawn.slick.*;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;
import util.DrawUtilities;

public class SonicSlash extends Arte {

    public static final Class<? extends PlayableCharacter> character = Sigur.class;

    public SonicSlash(PlayableCharacter owner) throws SlickException {
        super(owner);
        name = "Sonic Slash";
        arteType = ArteType.MARTIAL;
        cost = 1;
        castDuration = 74;
        this.card = new Image("res/martialCard.png");
        this.aniSheet = new SpriteSheet("res/animations/combat/sonic_slash.png",200,200);
    }

    @Override
    public void use(Unit target, GameContainer gc) {
        timer = 0;
        activation(target);
    }

    @Override
    public void animation(Unit target, Graphics g) {
        if(!finished()) {
            this.aniFrame = aniSheet.getSprite(timer, 0);
            g.drawImage(aniFrame,-target.getPosition().getX(), target.getPosition().getY()+target.getHeight());
        }

    }

    @Override
    public void activation(Unit target) {
        target.takeDamage(owner.getAttack());
    }
}
