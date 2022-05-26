package entities.units.player;

import combat.artes.Arte;
import combat.artes.martial.DragonFang;
import combat.artes.martial.ImpactCross;
import combat.artes.martial.SonicSlash;
import combat.artes.mystic.Expiation;
import combat.artes.mystic.InnumerableWounds;
import combat.artes.mystic.TrillionDrive;
import core.Constants;
import core.Main;
import entities.core.Coordinate;
import entities.units.Direction;
import entities.units.npc.NPC;
import entities.units.Unit;
import gamestates.Game;
import managers.AnimationManager;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;
import util.DrawUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class Player<T extends Player<?>> extends Unit<T> {
    private int mana;
    public PlayerState getState() {
        return state;
    }

    //private final float XSPEED_MAX = 5;
    //private final float YSPEED_MAX = 5;
    private PlayerState state;
    final public static float PLAYER_X_SPAWN = (float) Main.RESOLUTION_X / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;
    final public static float PLAYER_Y_SPAWN = (float) Main.RESOLUTION_Y / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;

    private List<Arte<? super Player>> arteDeck;
    private List<Arte<? super Player>> arteHand;
    private Queue<Arte<? super Player>> arteQueue;
    private Arte<? super Player> move;
    private int queue;
    private PlayableCharacter character;
    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF

    public Player(Coordinate pos) throws SlickException {
        this.health = 100;
        this.width = 90;
        this.height = 175;
        this.position = pos;
        this.xSpeed = 10;
        this.ySpeed = 10;
        this.sheet = new SpriteSheet("res/animations/character/MCSPRITESHEET.png", 90, 175, 12);
        this.sprite = sheet.getSprite(0,0);
        this.character = new Sigur();
        this.arteDeck = new ArrayList<>();
        this.arteQueue = new ConcurrentLinkedQueue<>();
        for(int i = 0; i < 20; i++) {
            arteDeck.add(new SonicSlash(this));
            arteDeck.add(new DragonFang(this));
            arteDeck.add(new ImpactCross(this));
            arteDeck.add(new Expiation(this));
            arteDeck.add(new InnumerableWounds(this));
            arteDeck.add(new TrillionDrive(this));
        }
        this.hitBox = new Rectangle((Main.getScreenWidth()/2) - this.getWidth()/2, (Main.getScreenHeight()/2) + 170, this.width, this.height-100); // set size to tiles
    }

    public void resetHitbox()   {
        this.hitBox = new Rectangle((Main.getScreenWidth()/2) - this.getWidth()/2, (Main.getScreenHeight()/2) + 170, this.width, this.height-100);
    }

    public void startBattle()   {
        queue = 5;
        this.arteHand = new ArrayList<>(arteDeck.subList(0,6));
        this.arteQueue = new ConcurrentLinkedQueue<>();
    }

    public void move(Unit target, GameContainer gc, Graphics g) throws InterruptedException {
        for(int i = 0; i < arteHand.size(); i++)    {
            arteHand.get(i).getCard().drawCentered((Main.getScreenWidth()/7)*(i+1), Main.getScreenHeight()-300);
            g.setColor(Color.white);
            DrawUtilities.drawStringCentered(g, arteHand.get(i).getName(), Main.font, (Main.getScreenWidth()/7)*(i+1), Main.getScreenHeight()-300);
            DrawUtilities.drawStringCentered(g, arteHand.get(i).getArteType().name, Main.font, (Main.getScreenWidth()/7)*(i+1), Main.getScreenHeight()-400);
        }
        move = cardSelect(gc.getInput());
        if (move != null) arteQueue.add(move);
        /*if(move != null) {
            move.use(target, gc);
        }*/
    }

    public void attack(Unit target, GameContainer gc)   {
        if (arteQueue.isEmpty()) {
            this.state = PlayerState.DONE;
            return;
        }
        Arte<? super Player> arte = arteQueue.element();
        arte.use(target, gc);
        if (arte.finished()) arteQueue.remove(arte);
    }

    public void update(StateBasedGame sbg, Unit u, Game g) throws SlickException {
        ewDir = (dx>0?Direction.EAST:dx<0?Direction.WEST:Direction.NONE);
        nsDir = (dy<0?Direction.NORTH:dy>0?Direction.SOUTH:Direction.NONE);
        AnimationManager.animationSelect(this);
        this.position.updatePosition(dx,dy);
        this.dx = 0;
        this.dy = 0;
        if(getHitBox().intersects(u.getHitBox()) && Game.time >= Game.battleCooldown) {
            sbg.enterState(Main.BATTLE_ID);
        }
    }

    public void interact(NPC u)    {
        if(getHitBox().intersects(u.getHitBox()))   {
            u.interact();
        }
    }

    public Arte<? super Player> cardSelect(Input input) {
        Arte<Player> selected = null;
        if(queue >= arteDeck.size()) {
            this.health = 0;
            return selected;
        }
        return switch (input) {
            case Input i && i.isKeyPressed(Input.KEY_1) -> selection(0);
            case Input i && i.isKeyPressed(Input.KEY_2) -> selection(1);
            case Input i && i.isKeyPressed(Input.KEY_3) -> selection(2);
            case Input i && i.isKeyPressed(Input.KEY_4) -> selection(3);
            case Input i && i.isKeyPressed(Input.KEY_5) -> selection(4);
            case Input i && i.isKeyPressed(Input.KEY_6) -> selection(5);
            default -> selected;
        };
    }

    public Arte<? super Player> selection(int i) {
        Arte<? super Player> selected;
        selected = arteHand.get(i);
        arteHand.remove(i);
        queue++;
        arteHand.add(arteDeck.get(queue));
        this.state = PlayerState.SELECTING;
        return selected;
    }

    public void setState(PlayerState s) {
        this.state = s;
    }

    public Player getPlayer() {
        return this;
    }

    public void drawHitBox(Graphics g)    {
        g.draw(this.hitBox);
    }

    protected void drawSprite(Graphics g) { // Draw the entity sprite
        DrawUtilities.drawImageCentered(g,this.sprite, (Main.getScreenWidth()/2), (Main.getScreenHeight()/2) + 128);

    }
    public void battleRender(Graphics g, float plrX, float plrY)  {
        g.drawImage(sprite, -plrX - position.getX(), -plrY/2 - position.getY());
        g.setColor(new Color(255, 0,0,0.5f));
        hitBox.setX(-plrX - position.getX() + width);
        hitBox.setY((-plrY/2) + this.getHeight()*1.6f);

    }

    public void addToDeck(Arte<Player> a)   {
        this.arteDeck.add(a);
    }

    public T gainExp(int amount) {
        this.character.gainExp(amount);
        return (T) this;
    }

    public int getExp() {
        return character.getExp();
    }

    public int getLevel()   {
        return character.getLevel();
    }

    @Override
    public int getMana() {
        return mana;
    }

    public T setMana(int mana) {
        this.mana = mana;
        return (T) this;
    }

    public void setDirection(Direction ns, Direction ew )   {
        this.nsDir = ns;
        this.ewDir = ew;
    }
    public void setEWDirection(Direction ew )   {
        this.ewDir = ew;
    }
    public void setNSDirection(Direction ns)   {
        this.nsDir = ns;
    }


    public PlayableCharacter getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return "Player{" +
                "mana=" + mana +
                ", state=" + state +
                ", arteDeck=" + arteDeck +
                ", arteHand=" + arteHand +
                ", arteQueue=" + arteQueue +
                ", move=" + move +
                ", queue=" + queue +
                ", character=" + character +
                '}';
    }
}
