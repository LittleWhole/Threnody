package combat.artes;

import gamestates.Game;
import org.newdawn.slick.Graphics;
import playerdata.PlayableCharacter;
import playerdata.Sigur;

public abstract class Arte {

    protected PlayableCharacter owner;

    protected String name;
    protected int useTimestamp;
    protected boolean using;
    protected int castTimestamp;
    protected int castDuration;

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
