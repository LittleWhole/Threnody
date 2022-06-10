package combat.artes;

import core.Main;
import entities.units.Unit;
import entities.units.enemy.Enemy;
import entities.units.player.Player;
import managers.ImageManager;
import managers.SoundManager;
import org.newdawn.slick.*;
import playerdata.characters.PlayableCharacter;
import util.DrawUtilities;

@SuppressWarnings({"rawtypes"})
public abstract class Arte<T extends Unit> {

    protected Class<? extends PlayableCharacter> character; // The character that can use the Arte
    protected T owner; // The owner of the arte
    protected String name; // The name displayed by the Arte
    protected ArteType arteType;
    protected boolean using; // If the arte is activated or not
    protected long castTimestamp; // The exact timestamp when the Arte started casting
    protected int castDuration; // The amount of time casting takes
    protected int timer;
    protected int spritesheetX;
    protected int spritesheetY;
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
    protected Sound sound;

    protected ElementType element;

    protected enum AnimationType { OWNER, TARGET, REVERSE_TARGET }

    protected AnimationType aniType;

    protected Arte(T owner) throws SlickException {
        this();
        assert character != null;
        this.owner = owner;
        timer = 0;
        spritesheetX = 0;
        spritesheetY = 0;
        this.castTimestamp = -1;
    }

    /* Skeleton constructor for assets. */
    protected Arte() throws SlickException {
        try {
            this.card = new Image("res/ui/cards/" + this.getClass().getPackageName().substring(13) + "/" + this.getClass().getSimpleName() + ".png");
            //ImageManager.getImage("res/ui/cards/" + this.getClass().getPackageName().substring(13) + "/" + this.getClass().getSimpleName());
        } catch (RuntimeException ignored) {}
        try {
            this.aniSheet = new SpriteSheet("res/animations/combat/" + this.getClass().getSimpleName() + ".png", 200, 200);
            this.castDuration = aniSheet.getHorizontalCount() * aniSheet.getVerticalCount();
        } catch (RuntimeException ignored) {}
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
        if (timer == 0) {
            owner.generateMana(-cost);
            owner.setQueuedManaRemoval(owner.getQueuedManaRemoval() - cost);
            try {
                SoundManager.playSoundEffect(this.getClass().getSimpleName());
            } catch (RuntimeException ignored) {}
        }
        if (!finished()) {
            activation(target);
            if (timer < 60) {
                if (timer < 20) gc.getGraphics().setColor(new Color(221, 201, 85, (255 / 20) * timer));
                else if (timer < 40) gc.getGraphics().setColor(new Color(221, 201, 85));
                else gc.getGraphics().setColor(new Color(221, 201, 85, (255 / 20) * (60 - timer)));

                gc.getGraphics().fill(DrawUtilities.createRectangleCentered(Main.RESOLUTION_X / 2, 100, 400, 80));


                if (target instanceof Enemy) {
                    if (timer < 20) gc.getGraphics().setColor(new Color(12, 46, 100, (255 / 20) * timer));
                    else if (timer < 40) gc.getGraphics().setColor(new Color(12, 46, 100));
                    else gc.getGraphics().setColor(new Color(12, 46, 100, (255 / 20) * (60 - timer)));
                } else {
                    if (timer < 20) gc.getGraphics().setColor(new Color(142, 27, 35, (255 / 20) * timer));
                    else if (timer < 40) gc.getGraphics().setColor(new Color(142, 27, 35));
                    else gc.getGraphics().setColor(new Color(142, 27, 35, (255 / 20) * (60 - timer)));
                }

                var rect = DrawUtilities.createRectangleCentered(Main.RESOLUTION_X / 2, 100, 400, 60);
                gc.getGraphics().fill(rect);

                if (timer < 30) gc.getGraphics().setColor(new Color(255, 255, 255, (255 / 20) * timer));
                else if (timer < 40) gc.getGraphics().setColor(new Color(255, 255, 255));
                else gc.getGraphics().setColor(new Color(255, 255, 255, (255 / 20) * (60 - timer)));


                gc.getGraphics().setFont(Main.fonts.VariableWidth.P40);
                DrawUtilities.drawStringCentered(gc.getGraphics(), this.name, Main.fonts.VariableWidth.P40, rect);
                gc.getGraphics().setFont(Main.font);
            }
            animation(target, gc.getGraphics());
            DrawUtilities.drawStringCentered(gc.getGraphics(), String.valueOf(timer), 100, 0);
        }
        timer++;
        spritesheetX++;
        if (aniSheet != null && spritesheetX >= aniSheet.getHorizontalCount()) {
            spritesheetX = 0;
            spritesheetY++;
        }
    }

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

    public void animation(Unit target, Graphics g) {
        switch (aniType) {
            case OWNER -> animationOwner(g);
            case TARGET -> animationTarget(target, g);
            case REVERSE_TARGET -> animationReverseTarget(target, g);
            default -> animationOwner(g);
        }
    }
    public void animationOwner(Graphics g) {
        if (!finished()) {
            this.aniFrame = aniSheet.getSprite(spritesheetX, spritesheetY);
            g.drawImage(aniFrame, -owner.getPosition().getX() + (owner.getWidth()/2) + 5, owner.getPosition().getY()/2 + owner.getHeight()*2.75f);
        }
    }

    public void animationTarget(Unit target, Graphics g) {
        if(!finished()) {
            this.aniFrame = aniSheet.getSprite(spritesheetX, spritesheetY);
            if (this.owner instanceof Player) g.drawImage(aniFrame, -target.getPosition().getX(), -target.getY() + target.getHeight());
            else g.drawImage(aniFrame.getFlippedCopy(true, false), -target.getPosition().getX(), -target.getY() + target.getHeight());
        }
    }

    public void animationReverseTarget(Unit target, Graphics g) {
        if(!finished()) {
            this.aniFrame = aniSheet.getSprite(spritesheetX, spritesheetY);
            g.drawImage(aniFrame.getFlippedCopy(true, false), -target.getPosition().getX(), -target.getY() + target.getHeight());
        }
    }

    /* The actual effects of the arte, independent of the animation when the arte is used. */
    public abstract void activation(Unit u);

    /* The code called when the card is initially queued. */
    public abstract void queue();

    /* The code called when the card is dequeued.
    This method should *always* reverse all effects of queue() when called. */
    public abstract void dequeue();

    public String getName() {
        return name;
    }

    public ArteType getArteType() {
        return arteType;
    }

    public int getCost() {
        return cost;
    }

    public void reset() throws SlickException {
        timer = 0;
        spritesheetX = 0;
        spritesheetY = 0;
        this.castTimestamp = -1;
    }
}
