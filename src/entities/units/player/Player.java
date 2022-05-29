package entities.units.player;

import combat.artes.Arte;
import combat.artes.elemental.AquaLimit;
import combat.artes.elemental.DualTheSol;
import combat.artes.elemental.RendingGale;
import combat.artes.mystic.DivineConqueror;
import combat.artes.strike.DragonFang;
import combat.artes.strike.ImpactCross;
import combat.artes.strike.SonicSlash;
import combat.artes.mystic.Expiation;
import combat.artes.mystic.InnumerableWounds;
import combat.artes.mystic.TrillionDrive;
import combat.artes.support.Elixir;
import combat.artes.support.Heal;
import combat.artes.support.Mana;
import core.Constants;
import core.Main;
import entities.core.Coordinate;
import entities.units.Direction;
import entities.units.npc.NPC;
import entities.units.Unit;
import gamestates.Game;
import managers.AnimationManager;
import managers.CombatManager;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.StateBasedGame;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;
import util.DrawUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings({"unchecked"})
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
    private Queue<Arte<? super Player>> clickArteQueue;
    private Arte<? super Player> move;
    private int queue;
    private PlayableCharacter character;
    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF

    public Player(Coordinate pos) throws SlickException {
        super();
        this.health = 100;
        this.width = 104;
        this.height = 216;
        this.position = pos;
        this.xSpeed = 10;
        this.ySpeed = 10;
        this.sheet = new SpriteSheet(new Image("res/animations/character/player.png"), (int)width, (int)height, 8, 8);
        this.sprite = sheet.getSprite(0,0);
        this.character = new Sigur();
        this.arteDeck = new ArrayList<>();
        this.arteQueue = new ConcurrentLinkedQueue<>();
        this.clickArteQueue = new ConcurrentLinkedQueue<>();
        for(int i = 0; i < 20; i++) {
            arteDeck.add(new ImpactCross(this));
            //arteDeck.add(new DragonFang(this));
            arteDeck.add(new Elixir(this));
            arteDeck.add(new DragonFang(this));
            arteDeck.add(new RendingGale(this));
            arteDeck.add(new AquaLimit(this));
            //arteDeck.add(new DivineConqueror(this));
            arteDeck.add(new DualTheSol(this));
        }
        this.hitBox = new Rectangle((Main.getScreenWidth()/2) - this.getWidth()/2, (Main.getScreenHeight()/2) + this.height*0.6f, this.width, this.height/2);
    }

    public void resetHitbox()   {
        this.hitBox = new Rectangle((Main.getScreenWidth()/2) - this.getWidth()/2, (Main.getScreenHeight()/2) + this.height*.06f, this.width, this.height/2);
    }

    public void startBattle()   {
        AnimationManager.animationSelect(this);
        AnimationManager.animationCycle(this);
        queue = 5;
        this.arteHand = new ArrayList<>(arteDeck.subList(0,6));
        this.arteQueue = new ConcurrentLinkedQueue<>();
        this.clickArteQueue = new ConcurrentLinkedQueue<>();
        move = null;
    }

    public void move(Unit target, GameContainer gc, Graphics g) throws InterruptedException {
        for(int i = 0; i < arteHand.size(); i++) {
            var cardX = (Main.getScreenWidth()/7)*(i+1);
            var cardY = Main.getScreenHeight() - 300;
            if (onCard(gc.getInput(), i)) arteHand.get(i).getCard().getScaledCopy(1.3f).drawCentered(cardX, cardY);
            else arteHand.get(i).getCard().drawCentered(cardX, cardY);
            g.setColor(Color.white);
//            DrawUtilities.drawStringCentered(g, arteHand.get(i).getName(), Main.font, (Main.getScreenWidth()/7)*(i+1), Main.getScreenHeight()-300);
//            DrawUtilities.drawStringCentered(g, arteHand.get(i).getArteType().name, Main.font, (Main.getScreenWidth()/7)*(i+1), Main.getScreenHeight()-400);
        }
        if (!clickArteQueue.isEmpty()) {
            arteQueue.addAll(clickArteQueue);
            clickArteQueue.clear();
        } else {
            move = cardSelect(gc.getInput());
            if (move != null) arteQueue.add(move);
        }
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
        if (arte.finished()) {
            arteQueue.remove(arte);
            arteHand.add(arteDeck.get(queue));
            queue++;
        }
    }

    public void update(StateBasedGame sbg, Unit u, Game g) throws SlickException {
        ewDir = (dx>0?Direction.EAST:dx<0?Direction.WEST:Direction.NONE);
        nsDir = (dy<0?Direction.NORTH:dy>0?Direction.SOUTH:Direction.NONE);
        AnimationManager.animationSelect(this);

        if(dy != 0 || dx != 0  ) AnimationManager.animationCycle(this);
        else sprite = sheet.getSprite(0,spriteY);

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
//            case Input i && i.isMousePressed(0) && onCard(i, 0) -> selection(0);
//            case Input i && i.isMousePressed(0) && onCard(i, 1) -> selection(1);
//            case Input i && i.isMousePressed(0) && onCard(i, 2) -> selection(2);
//            case Input i && i.isMousePressed(0) && onCard(i, 3) -> selection(3);
//            case Input i && i.isMousePressed(0) && onCard(i, 4) -> selection(4);
//            case Input i && i.isMousePressed(0) && onCard(i, 5) -> selection(5);
            default -> selected;
        };
    }

    public boolean onCard(Input input, int i) {
        try {
            var card = arteHand.get(i).getCard();
            var mouseX = input.getMouseX();
            var mouseY = input.getMouseY();
            var cardX = (Main.getScreenWidth() / 7) * (i + 1);
            var cardY = Main.getScreenHeight() - 300;
            return ((mouseX > cardX - card.getWidth() / 2 && mouseX < cardX + card.getWidth() / 2) &&
                    (mouseY > cardY - card.getHeight() / 2 && mouseY < cardY + card.getHeight() / 2));
        } catch (IndexOutOfBoundsException ignored) {}
        return false;
    }

    public Arte<? super Player> selection(int i) {
        Arte<? super Player> selected = null;
        try {
            selected = arteHand.get(i);
            selected.reset();
            arteHand.remove(i);
        } catch (IndexOutOfBoundsException ignored) {}
        finally {
            this.state = PlayerState.SELECTING;
            return selected;
        }
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
        hitBox.setX(plrX - position.getX() + width/2);
        hitBox.setY((-plrY/2) + this.getHeight()*1.6f);
        var rect = new RoundedRectangle(hitBox.getX(), hitBox.getY(), 50, 50, RoundedRectangle.ALL);
        g.setColor(Color.red);
        g.fill(rect);
        g.setColor(Color.white);
        DrawUtilities.drawStringCentered(g, String.valueOf(health), rect);
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




    public PlayableCharacter getCharacter() {
        return character;
    }

    public void setMove(Arte<? super Player> move) {
        this.move = move;
    }

    public List<Arte<? super Player>> getArteHand() {
        return arteHand;
    }

    public Queue<Arte<? super Player>> getArteQueue() {
        return arteQueue;
    }

    public Queue<Arte<? super Player>> getClickArteQueue() {
        return clickArteQueue;
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
