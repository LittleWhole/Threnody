package entities.units.npc;

import entities.core.Coordinate;
import entities.units.Unit;
import entities.units.enemy.EnemyStates;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import util.DrawUtilities;

@SuppressWarnings({"unchecked"})
public class NPC<T extends NPC<?>> extends Unit<T> {


    public void setCombatState(EnemyStates combatState) {
        this.combatState = combatState;
    }
    private final int interactLength = 120;
    private int timer;
    private boolean isInteracting;
    public EnemyStates getCombatState() {
        return combatState;
    }

    protected EnemyStates combatState;
    public NPC(float x, float y) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        this.width = 104;
        this.height = 216;
        this.position = new Coordinate(x, y);
        this.hitBox = new Rectangle(x,y, this.width, this.height/3);
        this.xSpeed = 10;
        this.ySpeed = 10;
        this.sheet = new SpriteSheet(new Image("res/animations/character/player.png"), (int)width, (int)height, 0, 8);
        this.sprite = sheet.getSprite(0, 0);
        this.level = 1;
        this.timer = interactLength;
    }

    public void render(GameContainer gc, float plrX, float plrY)  {
        timer++;
        gc.getGraphics().drawImage(sprite, -plrX - position.getX() + width, -plrY/2 - position.getY() + height);
        gc.getGraphics().setColor(new Color(255, 0,0,0.5f));
        hitBox.setX(-plrX - position.getX() + width);
        hitBox.setY((-plrY/2) + this.getHeight()*1.6f);

        if(timer < interactLength) {
            DrawUtilities.drawStringCentered(gc.getGraphics(),"Hello!!", -plrX - position.getX(),-plrY/2 + this.getHeight());
        }
    }

    public void interact()    {
        timer = 0;
    }


    public void animation() {

    }

    public void drawHitBox(Graphics g)  {
        g.fill(hitBox);
    }


    public NPC getNPC() {
        return this;
    }
}
