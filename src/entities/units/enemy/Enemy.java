package entities.units.enemy;

import combat.artes.Arte;
import combat.artes.ElementType;
import core.Main;
import entities.core.Coordinate;
import entities.core.Entity;
import entities.core.Team;
import entities.units.Unit;
import managers.ImageManager;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import util.DrawUtilities;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import java.util.ArrayList;

@SuppressWarnings({"unchecked"})
public class Enemy<T extends Enemy<?>> extends Unit<T> {
    public enum EnemyState {
        //specific moves//
        ATTACK, CHARGE, SPECIAL, IDLE,//specific moves it can make
        //combat system states//
        CHOOSING, MOVING, DONE
    }

    protected EnemyState turn;

    public ArrayList<Enemy> getEnemyTeam() {
        return enemyTeam;
    }

    public void setEnemyTeam(ArrayList<Enemy> enemyTeam) {
        this.enemyTeam = enemyTeam;
    }

    protected ArrayList<Enemy> enemyTeam;
    protected int moveDuration;
    protected long moveTimeStamp;
    protected int timer;

    protected List<Arte<? super Enemy>> arteDeck;
    protected List<Arte<? super Enemy>> arteHand;
    protected Queue<Arte<? super Enemy>> arteQueue;
    protected Arte<? super Enemy> move;

    protected int dead;
    public void setCombatState(EnemyState combatState) {
        this.combatState = combatState;
    }

    public EnemyState getCombatState() {
        return combatState;
    }

    protected EnemyState combatState;
    public Enemy(float x, float y) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        super();
        this.dead = 0;
        this.health = 100;
        this.attack = 5;
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
        turn = EnemyState.IDLE;
        this.team = Team.ENEMY;
        this.arteQueue = new ConcurrentLinkedQueue<>();
        this.enemyTeam = new ArrayList<Enemy>();
        for(int i = 0 ; i <= (int) (Math.random()*4);i++)   {
            enemyTeam.add(this);
        }
    }
    public Enemy(float x, float y, int level) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        super();
        this.health = 100;
        this.attack = 5;
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
        this.level = level;
        this.timer = 0;
        turn = EnemyState.IDLE;
        this.team = Team.ENEMY;
        this.enemyTeam = new ArrayList<Enemy>();
        for(int i = 0 ; i <= (int) (Math.random()*4);i++)   {
            enemyTeam.add(this);
        }
        this.arteQueue = new ConcurrentLinkedQueue<>();
    }
    public Enemy(float x, float y, ArrayList<Enemy> enemies) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        super();
        this.health = 100;
        this.attack = 5;
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
        this.enemyTeam = enemies;
        turn = EnemyState.IDLE;
        this.team = Team.ENEMY;
        this.arteQueue = new ConcurrentLinkedQueue<>();
    }

    public void render(Graphics g, float plrX, float plrY)  {
        if(!isDead()) entityRender(g, plrX,plrY);

        hitBox.setX( getRenderX(plrX));
        hitBox.setY((-plrY/2) -position.getY() + this.getHeight()*1.6f);


    }
    public void battleRender(Graphics g, float plrX, float plrY) {
        if (!isDead()) entityRender(g, plrX, plrY);

        hitBox.setX(getRenderX(plrX));
        hitBox.setY((-plrY / 2) - position.getY() + this.getHeight() * 1.6f);
        ImageManager.getImage("health").drawCentered(hitBox.getX() + hitBox.getWidth() / 2, hitBox.getY() - this.getHeight() / 2 - 15);
        g.setColor(Color.white);
        DrawUtilities.drawStringCentered(g, String.valueOf(health), hitBox.getX() + hitBox.getWidth() / 2, hitBox.getY() - this.getHeight() / 2 - 15);
    }

    public void overworldUpdate()    {
        if(isDead())   {
           dead--;
        }
    }

    public void battleSelect()  {
        if(turn != EnemyState.CHARGE && turn != EnemyState.SPECIAL)  {
            this.turn = decideState();
        }
        timer = 0;
        this.combatState = EnemyState.MOVING;
    }

    public void battleMove(Unit target, GameContainer gc)    {
        timer++;
        if( Main.debug) gc.getGraphics().drawString(String.valueOf(timer), 700, 100);

        animation();

        if(timer==moveDuration) {
            this.combatState = EnemyState.DONE;
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
                this.turn = EnemyState.SPECIAL;
            }
            case SPECIAL -> {
                target.takeDamage(this.attack*3, ElementType.PHYSICAL);
            }
        }
    }

    private EnemyState decideState()  {
        switch((int)(Math.random()*4)) {
            case 0 -> { return EnemyState.IDLE; }
            case 1 -> { return EnemyState.CHARGE; }
            default -> { return EnemyState.ATTACK; }
        }
    }

    public void drawHitBox(Graphics g)  {
        g.setColor(new Color(255, 0,0,0.5f));
        g.fill(hitBox);
    }

    public boolean isDead() {
        return dead > 0;
    }

    public void kill()  {
        this.dead = 300;
        resetTeam();
    }

    public void resetTeam() {

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
