package combat.artes;

import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import gamestates.Game;
import graphics.ui.combat.DamageNumber;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import playerdata.characters.PlayableCharacter;
import util.DrawUtilities;

public abstract class Arte<T extends Unit> {

    protected Class<? extends PlayableCharacter> character; // The character that can use the Arte
    protected T owner; // The owner of the arte
    protected String name; // The name displayed by the Arte
    protected ArteType arteType;
    protected boolean using; // If the arte is activated or not
    protected long castTimestamp; // The exact timestamp when the Arte started casting
    protected int castDuration; // The amount of time casting takes
    protected int timer;
    protected int cost;

    public SpriteSheet getAniSheet() {
        return aniSheet;
    }

    public Image getAniFrame() {
        return aniFrame;
    }

    protected SpriteSheet aniSheet;
    protected Image aniFrame;

    public Image getCard() {
        return card;
    }

    protected Image card;

    protected ElementType element;

    protected Arte(T owner) {
        assert character != null;
        this.owner = owner;
        timer = 0;
        this.castTimestamp = -1;
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
    public void use(Unit target, GameContainer gc) {
        if (!finished()) {
            activation(target);
            animation(target, gc.getGraphics());
            DrawUtilities.drawStringCentered(gc.getGraphics(), String.valueOf(timer), 100, 0);
        }
        timer++;
    };

    /*public void render(Unit target, Graphics g) {

        if (!finished()) {
            animation(target, g);
            DrawUtilities.drawStringCentered(g, String.valueOf(timer), 100, 0);
        }
        if(timer == castDuration){
            BattleState.damageNumbers.add(new DamageNumber(owner.getAttack(), (int)target.getPosition().getX(), (int)(target.getPosition().getY()+target.getHeight()/2)));
        }
        timer++;
        if (BattleState.time - castTimestamp < castDuration) {
            g.drawArc(owner.getEntity().getX(), owner.getEntity().getY(), 50f, 50f, 0f, (float) Math.toRadians((float) castTimestamp / castDuration * 360));
        }
    }*/
    public boolean finished()   {
        return (timer >= castDuration);
    }
    public abstract void animation(Unit target, Graphics g);

    public abstract void activation(Unit u);

    public String getName() {
        return name;
    }

    public ArteType getArteType() {
        return arteType;
    }
}
