package entities.units.player;

import combat.artes.Arte;
import combat.artes.martial.SonicSlash;
import core.Constants;
import core.Main;
import entities.core.Coordinate;
import entities.units.Unit;
import gamestates.BattleState;
import gamestates.Game;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import playerdata.PlayableCharacter;
import playerdata.Sigur;
import playerdata.PlayerState;
import util.DrawUtilities;

import java.util.ArrayList;

public final class Player extends Unit {

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
        this.level = 1;
        this.character = new Sigur();
        this.arteDeck = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            arteDeck.add(new SonicSlash(character));
        }
        this.hitBox = new Rectangle((Main.getScreenWidth()/2) - this.getWidth()/2, (Main.getScreenHeight()/2) + 170, this.width, this.height-100); // set size to tiles
    }

    public void startBattle()   {
        queue = 5;
        this.arteHand = new ArrayList<Arte>(arteDeck.subList(0,6));
    }

    public void move(Unit target, GameContainer gc, Graphics g) throws InterruptedException {
        for(int i = 0; i < arteHand.size(); i++)    {
            DrawUtilities.drawImageCentered(g, arteHand.get(i).getSprite(), (Main.getScreenWidth()/5)*(i+1), Main.getScreenHeight()-300);

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

    public void update(StateBasedGame sbg, Unit u, Game g)  {
        this.position.updatePosition(dx,dy);
        this.dx = 0;
        this.dy = 0;
        if(getHitBox().intersects(u.getHitBox())) {
            g.plrTeam = new ArrayList<>();
            g.plrTeam.add(this);
            BattleState.plrs = g.plrTeam;
            BattleState.enemies = g.enemyTeam;
            sbg.enterState(Main.BATTLE_ID);
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

    protected void drawSprite(Graphics g) { // Draw the entity sprite
        g.drawImage(this.sprite, (Main.getScreenWidth()/2) - 128, (Main.getScreenHeight()/2) - 256);
        g.draw(this.hitBox);
    }
}
