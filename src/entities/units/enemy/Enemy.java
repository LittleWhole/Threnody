package entities.units.enemy;

import entities.core.Coordinate;
import entities.core.Team;
import entities.units.Unit;
import gamestates.BattleState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import util.DrawUtilities;

public class Enemy extends Unit {
    protected EnemyStates turn;
    protected int moveDuration;
    protected long moveTimeStamp;
    protected int timer;

    public void setCombatState(EnemyStates combatState) {
        this.combatState = combatState;
    }

    public EnemyStates getCombatState() {
        return combatState;
    }

    protected EnemyStates combatState;
    public Enemy(float x, float y) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        this.moveDuration = 1000;
        this.width = 80;
        this.height = 256;
        this.position = new Coordinate(x, y);
        this.hitBox = new Rectangle(x,y, this.width, this.height-200);
        this.xSpeed = 10;
        this.ySpeed = 10;
        this.sheet = new SpriteSheet("res/experimentalEnemy.png", 256, 512);
        this.sprite = sheet.getSprite(0, 0);
        this.level = 1;
        turn = EnemyStates.IDLE;
        this.team = Team.ENEMY;
    }

    public void render(Graphics g, float plrX, float plrY)  {
        g.drawImage(sprite, -plrX - position.getX(), -plrY/2 - position.getY());
        g.setColor(new Color(255, 0,0,0.5f));
        hitBox.setX(-plrX - position.getX() + width);
        hitBox.setY((-plrY/2) + this.getHeight()*1.6f);
        if(this.getCombatState() == EnemyStates.MOVING) {
            DrawUtilities.drawStringCentered(g, "MOVING", 800, 100);
        }
    }

    public void overworldUpdate()    {

    }

    public void battleSelect()  {
        if(turn != EnemyStates.CHARGE)  {
            this.turn = decideState();
        }
        timer = 0;
        this.combatState = EnemyStates.MOVING;
    }

    public void battleMove(Unit target, GameContainer gc)    {
        timer++;

        animation();

        if(timer>=moveDuration) {
            this.combatState = EnemyStates.DONE;
            this.sprite = sheet.getSprite(0,0);
            action(target);
        }

    }
    public void animation() {
        if(timer < sheet.getWidth()) this.sprite = sheet.getSprite(timer, 0);
    }
    public void action(Unit target)    {
        switch(turn)   {
            case IDLE -> {
                break;
            }
            case ATTACK -> {
                target.takeDamage(this.attack);
            }
            case CHARGE -> {
                this.turn = EnemyStates.SPECIAL;
            }
            case SPECIAL -> {
                target.takeDamage(this.attack*3);
            }
        }
    }

    private EnemyStates decideState()  {
        switch((int)(Math.random()*4)) {
            case 0:
                return EnemyStates.IDLE;
            case 1:
                return EnemyStates.CHARGE;
            default: return EnemyStates.ATTACK;
        }
    }

    public void drawHitBox(Graphics g)  {
        g.fill(hitBox);
    }

    public Enemy getEnemy() {
        return this;
    }
}
