package entities.units.player;

import combat.artes.Arte;
import combat.artes.elemental.AquaLimit;
import combat.artes.elemental.DualTheSol;
import combat.artes.elemental.RendingGale;
import combat.artes.strike.DragonFang;
import combat.artes.strike.ImpactCross;
import combat.artes.strike.SonicSlash;
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
import managers.ImageManager;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;
import util.DrawUtilities;
import util.ThrenodyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings({"unchecked"})
public final class Player<T extends Player<?>> extends Unit<T> {
    public enum PlayerState { SELECTING, CASTING, DONE }
    public PlayerState getState() {
        return state;
    }
    private PlayerState state;

    private final List<Arte<? extends Unit>> arteDeck;
    private List<Arte<? extends Unit>> arteHand;
    private Queue<Arte<? extends Unit>> arteQueue;
    private Queue<Arte<? extends Unit>> clickArteQueue;
    private Arte<? extends Unit> move;
    private int queue;
    private final PlayableCharacter character;
    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF

    public Player(Coordinate pos) throws SlickException {
        super();
        this.health = 100;
        this.mana = 3;
        this.turnMana = 3;
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
            arteDeck.add(new Heal(this));
            arteDeck.add(new Mana(this));
            arteDeck.add(new SonicSlash(this));
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
            var cardY = Main.getScreenHeight() - 200;
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
        Arte<? extends Unit> arte = arteQueue.element();
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

    public Arte<? extends Unit> cardSelect(Input input) {
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
            var cardY = Main.getScreenHeight() - 200;
            return ((mouseX > cardX - card.getWidth() / 2 && mouseX < cardX + card.getWidth() / 2) &&
                    (mouseY > cardY - card.getHeight() / 2 && mouseY < cardY + card.getHeight() / 2));
        } catch (IndexOutOfBoundsException ignored) {}
        return false;
    }

    public Arte<? extends Unit> selection(int i) {
        Arte<? extends Unit> selected = null;
        try {
            selected = arteHand.get(i);
            if (selected.getCost() > mana + queuedManaExtra - queuedManaRemoval) throw new ThrenodyException("Insufficient mana");
            selected.reset();
            queuedManaRemoval += selected.getCost();
            selected.queue();
            arteHand.remove(i);
        } catch (IndexOutOfBoundsException | ThrenodyException ignored) { selected = null; }
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

        hitBox.setX(-plrX - position.getX() + width/3);
        hitBox.setY((-plrY/2) - position.getY() + height/4);
        ImageManager.getImage("health").drawCentered(hitBox.getX() + hitBox.getWidth() / 3.5f, hitBox.getY() - this.getHeight() / 2 + 15);
        g.setColor(Color.white);
        DrawUtilities.drawStringCentered(g, String.valueOf(health), hitBox.getX() + hitBox.getWidth() / 3.5f, hitBox.getY() - this.getHeight() / 2 + 15);
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

    public T setMove(Arte<? extends Unit> move) {
        this.move = move;
        return (T) this;
    }

    public List<Arte<? extends Unit>> getArteHand() {
        return arteHand;
    }

    public Queue<Arte<? extends Unit>> getArteQueue() {
        return arteQueue;
    }

    public Queue<Arte<? extends Unit>> getClickArteQueue() {
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
