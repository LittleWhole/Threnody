package entities.units.npc;

import entities.core.Coordinate;
import entities.units.Unit;
import entities.units.enemy.Enemy;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import util.DrawUtilities;

import java.util.function.Predicate;

@SuppressWarnings({"unchecked"})
public class NPC<T extends NPC<?>> extends Unit<T> {


    public void setCombatState(Enemy.EnemyState combatState) {
        this.combatState = combatState;
    }
    private final int interactLength = 120;
    private int timer;
    private boolean isInteracting;
    public Enemy.EnemyState getCombatState() {
        return combatState;
    }

    protected Enemy.EnemyState combatState;

    public NPC(float x, float y) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        this.width = 104;
        this.height = 216;
        this.position = new Coordinate(x, y);
        this.hitBox = new Rectangle(position.getX(), position.getY(), this.width * 2, this.height/3 * 2);
        this.xSpeed = 10;
        this.ySpeed = 10;
        this.sheet = new SpriteSheet(new Image("res/animations/character/player.png"), (int)width, (int)height, 0, 8);
        this.sprite = sheet.getSprite(0, 0);
        this.level = 1;
        this.timer = interactLength;
    }

    public void render(GameContainer gc, float plrX, float plrY)  {
        timer++;
        renderSprite(gc, plrX, plrY);

        if(timer < interactLength) {
            DrawUtilities.drawStringCentered(gc.getGraphics(),"Hello!!", -plrX - position.getX(),-plrY/2 + this.getHeight());
        }
    }

    public void renderSprite(GameContainer gc, float plrX, float plrY)  {
        gc.getGraphics().drawImage(sprite, -plrX - position.getX() + width, -plrY/2 - position.getY() + height);
        gc.getGraphics().setColor(new Color(255, 0,0,0.5f));
        hitBox.setX(-plrX - position.getX() + width/2);
        hitBox.setY((-plrY/2) - position.getY() + this.getHeight()*1.6f);
    }

    public void interact()    {
        timer = 0;
        isInteracting = true;
    }

    public void exit()  {
        //stopping interaction
        isInteracting = false;
    }

    public void animation() {

    }

    public void drawHitBox(Graphics g)  {
        g.fill(hitBox);
    }

    public boolean isInteracting() {
        return isInteracting;
    }

    public NPC getNPC() {
        return this;
    }
}
