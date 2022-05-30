package entities.units.enemy;

import combat.artes.Arte;
import combat.artes.ElementType;
import core.Main;
import entities.core.Coordinate;
import entities.core.Team;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.BattleState;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import util.DrawUtilities;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import java.util.ArrayList;

@SuppressWarnings({"unchecked"})
public class Enemy<T extends Enemy<?>> extends Unit<T> {
    protected EnemyStates turn;
    protected ArrayList<Enemy> enemyTeam;
    protected int moveDuration;
    protected long moveTimeStamp;
    protected int timer;

    protected List<Arte<? super Enemy>> arteDeck;
    protected List<Arte<? super Enemy>> arteHand;
    protected Queue<Arte<? super Enemy>> arteQueue;
    protected Arte<? super Enemy> move;

    public void setCombatState(EnemyStates combatState) {
        this.combatState = combatState;
    }

    public EnemyStates getCombatState() {
        return combatState;
    }

    protected EnemyStates combatState;
    public Enemy(float x, float y) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        super();
        this.health = 100;
        this.attack = 20;
        this.defense = 10;
        this.critRate = 0.05;
        this.critDamage = 50;
        moveDuration = 100;
        this.width = 118;
        this.height = 205;
        this.position = new Coordinate(x, y);
        this.hitBox = new Rectangle(x,y, this.width, this.height/3);
        this.xSpeed = 10;
        this.ySpeed = 10;
        this.sheet = new SpriteSheet(new Image("res/animations/character/goblin.png"), (int)width, (int)height, 0 ,8);
        this.sprite = sheet.getSprite(0, 0);
        this.level = 1;
        this.timer = 0;
        turn = EnemyStates.IDLE;
        this.team = Team.ENEMY;
        this.arteQueue = new ConcurrentLinkedQueue<>();
    }

    public void render(Graphics g, float plrX, float plrY)  {
        g.drawImage(sprite, -plrX - position.getX() + width, -plrY/2 - position.getY() + height);

        hitBox.setX(-plrX - position.getX() + width);
        hitBox.setY((-plrY/2) -position.getY() + this.getHeight()*1.6f);
        var rect = new RoundedRectangle(hitBox.getX(), hitBox.getY(), 50, 50, RoundedRectangle.ALL);
        g.setColor(Color.red);
        g.fill(rect);
        g.setColor(Color.white);
        DrawUtilities.drawStringCentered(g, String.valueOf(health), rect);
    }

    public void overworldUpdate()    {

    }

    public void battleSelect()  {
        if(turn != EnemyStates.CHARGE && turn != EnemyStates.SPECIAL)  {
            this.turn = decideState();
        }
        timer = 0;
        this.combatState = EnemyStates.MOVING;
    }

    public void battleMove(Unit target, GameContainer gc)    {
        timer++;
        if( Main.debug) gc.getGraphics().drawString(String.valueOf(timer), 700, 100);

        animation();

        if(timer==moveDuration) {
            this.combatState = EnemyStates.DONE;
            this.sprite = sheet.getSprite(0,0);
            action(target);
        }

    }
    public void animation() {
       this.sprite = sheet.getSprite(timer% sheet.getHorizontalCount(), 0);
    }
    public void action(Unit target)    {
        switch(turn)   {
            case IDLE -> {
                break;
            }
            case ATTACK -> {
                target.takeDamage(this.attack, ElementType.PHYSICAL);
            }
            case CHARGE -> {
                this.turn = EnemyStates.SPECIAL;
            }
            case SPECIAL -> {
                target.takeDamage(this.attack*3, ElementType.PHYSICAL);
            }
        }
    }

    private EnemyStates decideState()  {
        switch((int)(Math.random()*4)) {
            case 0 -> { return EnemyStates.IDLE; }
            case 1 -> { return EnemyStates.CHARGE; }
            default -> { return EnemyStates.ATTACK; }
        }
    }

    public void drawHitBox(Graphics g)  {
        g.setColor(new Color(255, 0,0,0.5f));
        g.fill(hitBox);
    }

    public Enemy getEnemy() {
        return this;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "turn=" + turn +
                ", enemyTeam=" + enemyTeam +
                ", moveDuration=" + moveDuration +
                ", moveTimeStamp=" + moveTimeStamp +
                ", timer=" + timer +
                ", arteDeck=" + arteDeck +
                ", arteHand=" + arteHand +
                ", arteQueue=" + arteQueue +
                ", move=" + move +
                ", combatState=" + combatState +
                '}';
    }
}
