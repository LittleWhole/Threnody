package entities.units.enemy;

import core.Main;
import entities.core.Coordinate;
import entities.core.Hitbox;
import entities.core.Team;
import entities.units.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;

public class Enemy extends Unit {
    protected EnemyStates turn;
    protected int moveDuration;
    protected long moveTimeStamp;

    public void setCombatState(EnemyStates combatState) {
        this.combatState = combatState;
    }

    public EnemyStates getCombatState() {
        return combatState;
    }

    protected EnemyStates combatState;
    public Enemy(float x, float y) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        moveDuration = 200;
        this.width = 100;
        this.height = 256;
        this.position = new Coordinate(x, y);
        this.hitBox = new Rectangle(this.position.getX() + (this.width/2), this.position.getY()+(this.height), this.width, this.height-100);
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
        hitBox.setX(-plrX - position.getX());
        hitBox.setY((-plrY/2 - position.getY())+this.height-100);
        g.fill(hitBox);
    }

    public void overworldUpdate()    {

    }

    public void battleMove(Unit target, GameContainer gc)    {
        this.moveTimeStamp = gc.getTime();
        if(turn != EnemyStates.CHARGE)  {
            this.turn = decideState();
        }
        this.combatState = EnemyStates.MOVING;
        animation();

        if(gc.getTime()-moveTimeStamp>=moveDuration) {
            this.combatState = EnemyStates.DONE;
            action(target);
        }

    }
    public void animation() {

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

    public Enemy getEnemy() {
        return this;
    }
}
