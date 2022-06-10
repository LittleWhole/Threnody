package entities.units.player;

import combat.artes.Arte;
import combat.artes.Card;
import combat.artes.elemental.AquaLimit;
import combat.artes.elemental.DualTheSol;
import combat.artes.elemental.RendingGale;
import combat.artes.mystic.AmongUs;
import combat.artes.mystic.Expiation;
import combat.artes.strike.DragonFang;
import combat.artes.strike.ImpactCross;
import combat.artes.strike.SonicSlash;
import combat.artes.support.Elixir;
import combat.artes.support.Heal;
import combat.artes.support.Mana;
import core.Main;
import entities.core.Coordinate;
import entities.units.Direction;
import entities.units.enemy.Enemy;
import entities.units.npc.NPC;
import entities.units.Unit;
import gamestates.Game;
import graphics.ui.Button;
import graphics.ui.menu.InventoryMenu;
import graphics.ui.menu.Menu;
import managers.AnimationManager;
import managers.ImageManager;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;
import util.DrawUtilities;
import util.ThrenodyException;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings({"unchecked"})
public final class Player<T extends Player<?>> extends Unit<T> {
    public enum PlayerState { SELECTING, CASTING, DONE }
    public PlayerState getState() {
        return state;
    }
    private PlayerState state;

    private boolean displayStats;
    private boolean displayInventory;

    private Menu stats;

    private final List<Arte<? extends Unit>> arteDeck;
    private List<Arte<? extends Unit>> arteHand;
    private Queue<Arte<? extends Unit>> arteQueue;
    private Queue<Arte<? extends Unit>> clickArteQueue;
    private ArrayList<InventoryMenu> cardInventory;
    private Arte<? extends Unit> move;

    private int inventoryIndex;
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
        this.character = Main.characters.get(0);
        this.arteDeck = new ArrayList<>();
        this.arteQueue = new ConcurrentLinkedQueue<>();
        this.clickArteQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 1; i++) {
            arteDeck.add(new ImpactCross(this));
            //arteDeck.add(new AmongUs(this));
            arteDeck.add(new Expiation(this));
            arteDeck.add(new Expiation(this));
            arteDeck.add(new Expiation(this));
            arteDeck.add(new Expiation(this));
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
        cardInventory = new ArrayList<>();
        cardInventory.add(addInventory(arteDeck.subList(0,arteDeck.size()>15?15:arteDeck.size())));
        this.hitBox = new Rectangle((Main.getScreenWidth()/2) - this.getWidth()/2, (Main.getScreenHeight()/2) + this.height*0.85f, this.width, this.height/4);
        this.displayStats = false;
        this.displayInventory = false;
        this.inventoryIndex = 0;
        stats = new Menu(Main.getScreenWidth()-175, Main.getScreenHeight()-225, 300, 400) {

            @Override
            protected void subrender(Graphics g) {
                g.setColor(Color.white);
                g.drawString("STATS", stats.getX() - stats.getWidth()/2 + 50, stats.getY()- stats.getHeight()/2 + 50);
                g.setColor(Color.yellow);
                g.drawString("Level: " + Main.stats.level, stats.getX() - stats.getWidth()/2 + 50, stats.getY()- stats.getHeight()/2 + 100);
                g.drawString("Exp: " + Main.stats.exp + "/" + Main.stats.maxExp, stats.getX()- stats.getWidth()/2 + 50, stats.getY()- stats.getHeight()/2 + 150);
                g.drawString("Gold: " + Main.stats.gold, stats.getX() - stats.getWidth()/2 + 50, stats.getY()- stats.getHeight()/2 + 200);
                g.setColor(Color.green);
                g.drawString("Health - " + character.getHealth(), stats.getX()- stats.getWidth()/2 + 50, stats.getY()- stats.getHeight()/2 + 250);
                g.setColor(Color.gray);
                g.drawString("Defense - " + character.getDefense(), stats.getX()- stats.getWidth()/2 + 50, stats.getY()- stats.getHeight()/2 + 300);
                g.setColor(Color.red);
                g.drawString("Atk - " + character.getAttack(), stats.getX()- stats.getWidth()/2 + 50, stats.getY()- stats.getHeight()/2 + 350);
            }

            @Override
            protected void initializeFonts() {

            }
        };
    }

    public void resetHitbox()   {
        this.hitBox = new Rectangle((Main.getScreenWidth()/2) - this.getWidth()/2, (Main.getScreenHeight()/2) + this.height*.85f, this.width, this.height/4);
    }

    public void startBattle()   {
        AnimationManager.animationSelect(this);
        AnimationManager.animationCycle(this);
        queue = 5;
        Collections.shuffle(arteDeck);
        this.arteHand = new ArrayList<>(arteDeck.subList(0,6));
        this.arteQueue = new ConcurrentLinkedQueue<>();
        this.clickArteQueue = new ConcurrentLinkedQueue<>();
        move = null;
    }

    public void move(GameContainer gc, Graphics g) throws InterruptedException {
        for (int i = 0; i < arteHand.size(); i++) {
            var cardX = (Main.getScreenWidth()/7)*(i+1);
            var cardY = Main.getScreenHeight() - 200;
            if (onCard(gc.getInput(), i)) arteHand.get(i).getCard().getScaledCopy(1.3f).drawCentered(cardX, cardY);
            else arteHand.get(i).getCard().drawCentered(cardX, cardY);
            g.setColor(Color.white);
        }

        if (!clickArteQueue.isEmpty()) {
            arteQueue.addAll(clickArteQueue);
            clickArteQueue.clear();
        } else {
            move = cardSelect(gc.getInput());
            if (move != null) arteQueue.add(move);
        }
    }

    public void attack(Unit target, GameContainer gc)   {
        Arte<? extends Unit> arte = arteQueue.peek();
        if (arte == null) {
            this.state = PlayerState.DONE;
            return;
        }
        arte.use(target, gc);
        if (arte.finished()) {
            arteQueue.poll();
            arteHand.add(arteDeck.get(queue));
            queue++;
        }
    }

    public void showInventory()    {
        displayInventory = !displayInventory;
    }

    private InventoryMenu addInventory(List<Arte<? extends Unit>> c)  {
        ArrayList<Card> temp = new ArrayList<>();
        c.forEach(a ->{
            temp.add(new Card(a.getClass()));
        });

        return new InventoryMenu(1100, 1000, temp, 5);
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
        if(encounteringEnemy(sbg, u, g))    {
            Game.enemyTeam.addAll(((Enemy<?>) u).getEnemyTeam());
            sbg.enterState(Main.BATTLE_ID);
        }
        /*if(getHitBox().intersects(u.getHitBox()) && Game.time >= Game.battleCooldown) {
            if(u instanceof Enemy<?>)   {
                Game.enemyTeam.addAll(((Enemy<?>) u).getEnemyTeam());
                sbg.enterState(Main.BATTLE_ID);
            }

        }*/
    }

    public boolean encounteringEnemy(StateBasedGame sbg, Unit u, Game g)    {
        if(getHitBox().intersects(u.getHitBox()) && Game.time >= Game.battleCooldown) {
            if(u instanceof Enemy<?> && !((Enemy)u).isDead())   {
                return true;
            }

        }
        return false;
    }

    public void interact(NPC u)    {
        if(getHitBox().intersects(u.getHitBox()))   {
            u.interact();
        }
    }
    public void exit(NPC u) {
        if(getHitBox().intersects(u.getHitBox()))   {
            u.exit();
        }
    }

    public Arte<? extends Unit> cardSelect(Input input) {
        Arte<Player> selected = null;
        if (queue >= arteDeck.size()) {
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

    public void showStats() {
        displayStats = !displayStats;
    }

    public boolean isDisplayStats() {
        return displayStats;
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

    @Override
    protected void drawSprite(Graphics g) { // Draw the entity sprite
        DrawUtilities.drawImageCentered(g,this.sprite, getRenderX(), getRenderY());
    }
    public void battleRender(Graphics g, float plrX, float plrY)  {
        entityRender(g, plrX,plrY);
        g.setColor(new Color(255, 0,0,0.5f));

        hitBox.setX(getRenderX(plrX) + width/3);
        hitBox.setY(getRenderY(plrY) + height/4);
        ImageManager.getImage("health").drawCentered(hitBox.getX() + hitBox.getWidth() / 3.5f, hitBox.getY() - this.getHeight() / 2 + 30);
        g.setColor(Color.white);
        DrawUtilities.drawStringCentered(g, String.valueOf(health), hitBox.getX() + hitBox.getWidth() / 3.5f, hitBox.getY() - this.getHeight() / 2 + 30);
    }

    public void renderStats(GameContainer gc)   {
        if(isDisplayStats()) stats.render(gc.getGraphics(), gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }
    public void renderInventory(GameContainer gc)   {
        if(displayInventory) {

            cardInventory.get(inventoryIndex).render(gc.getGraphics(), gc.getInput().getMouseX(), gc.getInput().getMouseY());
            if(inventoryIndex > 0)  {
                cardInventory.get(inventoryIndex).renderPrevious = true;
                cardInventory.get(inventoryIndex).renderPrevious(gc);
            }
            else {
                cardInventory.get(inventoryIndex).renderPrevious = false;
            }
            if(inventoryIndex < cardInventory.size()-1)   {
                cardInventory.get(inventoryIndex).renderNext = true;
                cardInventory.get(inventoryIndex).renderNext(gc);
            }
            else {
                cardInventory.get(inventoryIndex).renderNext = false;
            }

            if(gc.getInput().isMousePressed(0)) {
                if(cardInventory.get(inventoryIndex).renderPrevious)    {
                    if(cardInventory.get(inventoryIndex).getPrevious().onButton(gc.getInput().getMouseX(), gc.getInput().getMouseY())) inventoryIndex--;
                }
                if(cardInventory.get(inventoryIndex).renderNext)    {
                    if(cardInventory.get(inventoryIndex).getNext().onButton(gc.getInput().getMouseX(), gc.getInput().getMouseY())) inventoryIndex++;
                }
            }
        }
    }

    public void addToDeck(Arte<Player> a)   {
        this.arteDeck.add(a);
        if(this.cardInventory.get(cardInventory.size()-1).getInventory().size() >= 15) cardInventory.add(addInventory(new ArrayList<>()));
        this.cardInventory.get(cardInventory.size()-1).addToInv(new Card(a.getClass()));
    }

    public T gainExp(int amount) {
        this.character.gainExp(amount);
        this.health = character.getHealth();
        this.defense = character.getDefense();
        this.eDefense = character.geteDefense();
        this.attack = character.getAttack();
        this.eAttack = character.geteAttack();
        this.critDamage = character.getCritDamage();
        this.critRate = character.getCritRate();

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

    public void amogus() throws SlickException {
        this.arteHand.set(0, new AmongUs(this));
        this.arteHand.set(1, new AmongUs(this));
        this.arteHand.set(2, new AmongUs(this));
        this.arteHand.set(3, new AmongUs(this));
        this.arteHand.set(4, new AmongUs(this));
        this.arteHand.set(5, new AmongUs(this));
        arteDeck.clear();
        for (int i = 0; i < 20; i++) {
            arteDeck.add(new AmongUs(this));
            arteDeck.add(new AmongUs(this));
            arteDeck.add(new AmongUs(this));
            arteDeck.add(new AmongUs(this));
            arteDeck.add(new AmongUs(this));
        }
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
    public float getRenderX()   {
        return (Main.getScreenWidth()/2);
    }
    public float getRenderY()   {
        return (Main.getScreenHeight()/2) + 128;
    }
}
