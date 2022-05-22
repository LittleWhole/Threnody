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
import entities.units.npc.NPC;
import entities.units.Unit;
import gamestates.Game;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;
import util.DrawUtilities;

import java.util.ArrayList;

public final class Player extends Unit {
    protected int mana;

    public PlayerState getState() {
        return state;
    }

    //private final float XSPEED_MAX = 5;
    //private final float YSPEED_MAX = 5;
    private PlayerState state;
    final public static float PLAYER_X_SPAWN = (float) Main.RESOLUTION_X / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;
    final public static float PLAYER_Y_SPAWN = (float) Main.RESOLUTION_Y / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;

    protected ArrayList<Arte> arteDeck;
    protected ArrayList<Arte> arteHand;
    protected Arte move;
    protected int queue;
    protected PlayableCharacter character;
    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF

    public Player(Coordinate pos) throws SlickException {
        this.width = 64;
        this.height = 135;
        this.position = pos;
        this.xSpeed = 10;
        this.ySpeed = 10;
        this.sheet = new SpriteSheet("res/experimentalCharacter.png", 256, 512);
        this.sprite = sheet.getSprite(0,0);
        this.character = new Sigur();
        this.arteDeck = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            arteDeck.add(new SonicSlash(character));
            arteDeck.add(new DragonFang(character));
            arteDeck.add(new ImpactCross(character));
            arteDeck.add(new Expiation(character));
            arteDeck.add(new InnumerableWounds(character));
            arteDeck.add(new TrillionDrive(character));
        }
        this.hitBox = new Rectangle((Main.getScreenWidth()/2) - this.getWidth()/2, (Main.getScreenHeight()/2) + 170, this.width, this.height-100); // set size to tiles
    }

    public void startBattle()   {
        queue = 5;
        this.arteHand = new ArrayList<Arte>(arteDeck.subList(0,6));
    }

    public void move(Unit target, GameContainer gc, Graphics g) throws InterruptedException {
        for(int i = 0; i < arteHand.size(); i++)    {
            arteHand.get(i).getSprite().drawCentered((Main.getScreenWidth()/7)*(i+1), Main.getScreenHeight()-300);
            g.setColor(Color.white);
            DrawUtilities.drawStringCentered(g, arteHand.get(i).name, (Main.getScreenWidth()/7)*(i+1), Main.getScreenHeight()-300);
            DrawUtilities.drawStringCentered(g, String.valueOf(arteHand.get(i).arteType) + "ARTE", (Main.getScreenWidth()/7)*(i+1), Main.getScreenHeight()-400);
        }
        move = cardSelect(gc.getInput());
        if(move != null) {
            move.use(target, gc);
        }
    }

    public void attack(Unit target, GameContainer gc)   {
        if(move != null) {
            if(move.finished(gc))this.setState(PlayerState.DONE);
        }
    }

    public void update(StateBasedGame sbg, Unit u, Game g) throws SlickException {
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

    public Arte cardSelect(Input input) throws InterruptedException {//too many repeated crap but idc man
        Arte selected = null;
        if(queue >= arteDeck.size()) {
            this.health = 0;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_1)) {
            selected = arteHand.get(0);
            arteHand.remove(0);
            queue++;
            arteHand.add(arteDeck.get(queue));
            this.state = PlayerState.CASTING;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_2))    {
            selected = arteHand.get(1);
            arteHand.remove(1);
            queue++;
            arteHand.add(arteDeck.get(queue));
            this.state = PlayerState.CASTING;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_3))    {
            selected = arteHand.get(2);
            arteHand.remove(2);
            queue++;
            arteHand.add(arteDeck.get(queue));
            this.state = PlayerState.CASTING;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_4))    {
            selected = arteHand.get(3);
            arteHand.remove(3);
            queue++;
            arteHand.add(arteDeck.get(queue));
            this.state = PlayerState.CASTING;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_5)) {
            selected = arteHand.get(4);
            arteHand.remove(4);
            queue++;
            arteHand.add(arteDeck.get(queue));
            this.state = PlayerState.CASTING;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_6))    {
            selected = arteHand.get(5);
            arteHand.remove(5);
            queue++;
            this.state = PlayerState.CASTING;
            return selected;
        }
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
        g.drawImage(this.sprite, (Main.getScreenWidth()/2) - 128, (Main.getScreenHeight()/2) - 256);

    }
    public void battleRender(Graphics g, float plrX, float plrY)  {
        g.drawImage(sprite, -plrX - position.getX(), -plrY/2 - position.getY());
        g.setColor(new Color(255, 0,0,0.5f));
        hitBox.setX(-plrX - position.getX() + width);
        hitBox.setY((-plrY/2) + this.getHeight()*1.6f);

    }

    public void gainExp(int amount) {
        this.character.gainExp(amount);
    }
    public void gainMoney(int amount)   {
        this.character.gainMoney(amount);
    }

    public int getExp() {
        return this.character.getExp();
    }
    public int getMoney()   {
        return this.character.getMoney();
    }

    public int getLevel()   {
        return this.character.getLevel();
    }

    @Override
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
}
