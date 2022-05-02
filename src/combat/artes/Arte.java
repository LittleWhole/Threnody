package combat.artes;

import gamestates.Game;
import org.newdawn.slick.Graphics;
import playerdata.PlayableCharacter;
import playerdata.Sigur;

public abstract class Arte {

    protected PlayableCharacter owner; // The owner of the Arte

    protected String name; // The name displayed by the Arte
    protected int useTimestamp; // The exact timestamp when the Arte started use
    protected boolean using; // If the arte is activated or not
    protected int castTimestamp; // The exact timestamp when the Arte started casting
    protected int castDuration; // The amount of time casting takes

    protected ArteType arteType;
    protected ElementType damageType;

    public Arte(PlayableCharacter owner) {
        this.owner = owner;
        this.castTimestamp = -1;
        this.useTimestamp = -1;
    }

    public void use() {
        if (!using) {
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
    }

    public void render(Graphics g) {
        if (using) {
            animation();
        }

        if (Game.getTime() - castTimestamp < castDuration) {
            g.drawArc(owner.getEntity().getX(), owner.getEntity().getY(), 50f, 50f, 0f, (float) Math.toRadians((float) castTimestamp / castDuration * 360));
        }
    }

    abstract public void animation();
    abstract public void activation();

}
