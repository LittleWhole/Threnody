package entities.units.npc;

import entities.core.Coordinate;
import entities.units.Unit;
import entities.units.enemy.EnemyStates;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import util.DrawUtilities;

public class NPC extends Unit {


    public void setCombatState(EnemyStates combatState) {
        this.combatState = combatState;
    }
    private int interactLength = 1000;
    private int timer;
    private boolean isInteracting;
    public EnemyStates getCombatState() {
        return combatState;
    }

    protected EnemyStates combatState;
    public NPC(float x, float y) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        this.width = 80;
        this.height = 256;
        this.position = new Coordinate(x, y);
        this.hitBox = new Rectangle(x,y, this.width, this.height-200);
        this.xSpeed = 10;
        this.ySpeed = 10;
        this.sheet = new SpriteSheet("res/experimentalEnemy.png", 256, 512);
        this.sprite = sheet.getSprite(0, 0);
        this.level = 1;
        this.timer = -1;
    }

    public void render(GameContainer gc, float plrX, float plrY)  {
        if(isInteracting) timer++;
        gc.getGraphics().drawImage(sprite, -plrX - position.getX(), -plrY/2 - position.getY());
        gc.getGraphics().setColor(new Color(255, 0,0,0.5f));
        hitBox.setX(-plrX - position.getX() + width);
        hitBox.setY((-plrY/2) + this.getHeight()*1.6f);

        if(timer <= interactLength && timer > -1) {
            DrawUtilities.drawStringCentered(gc.getGraphics(),"Hello!!", -plrX - position.getX(),-plrY/2 + this.getHeight());
        }
        else    {
            isInteracting = false;
            timer = -1;
        }
    }

    public void interact()    {
        timer = 0;
        isInteracting = true;
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
