package combat.artes;

import entities.units.Unit;
import gamestates.BattleState;
import gamestates.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import playerdata.characters.PlayableCharacter;

public abstract class Arte<C extends PlayableCharacter> {

    protected Class<? extends PlayableCharacter> character; // The character that can use the Arte
    protected C owner; // The owner of the arte
    public static String name; // The name displayed by the Arte
    public static ArteType arteType;
    protected int useTimestamp; // The exact timestamp when the Arte started use
    protected boolean using; // If the arte is activated or not
    protected long castTimestamp; // The exact timestamp when the Arte started casting
    protected int castDuration; // The amount of time casting takes
    protected int cost;

    public Image getSprite() {
        return sprite;
    }

    protected Image sprite;

    protected ArteType type;
    protected ElementType element;

    protected Arte(C owner) {
        assert character != null;
        /*if (character.isInstance(owner))*/ this.owner = owner;
        this.castTimestamp = -1;
        this.useTimestamp = -1;
    }

    /*   if (!using) {
            if (this.castDuration == 0) {
                using = true;
                useTimestamp = Game.getTime();
            } else {
                if (castTimestamp == -1) castTimestamp = Game.getTime();
                if (Game.getTime() - castTimestamp >= castDuration) {
                    using = true;
                    castTimestamp = -1;
                    useTimestamp = Game.getTime();
                }
            }
        }
    }

    public void update() {
        if (using) {
            activation();
        }
    }*/
    public abstract void use(Unit target, GameContainer gc);

    public void render(Graphics g) {
        if (using) {
            animation();
        }

        if (BattleState.time - castTimestamp < castDuration) {
            g.drawArc(owner.getEntity().getX(), owner.getEntity().getY(), 50f, 50f, 0f, (float) Math.toRadians((float) castTimestamp / castDuration * 360));
        }
    }
    public boolean finished(GameContainer gc)   {
        return (BattleState.time -castTimestamp >= castDuration);
    }
    public abstract void animation();

    public abstract void activation(Unit u);

}
