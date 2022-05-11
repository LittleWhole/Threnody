package entities.units.player;

import combat.artes.Arte;
import core.Constants;
import core.Main;
import entities.core.Coordinate;
import entities.core.Hitbox;
import entities.units.Unit;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import playerdata.playerState;
import util.DrawUtilities;

import javax.swing.text.Position;
import java.util.ArrayList;

public final class Player extends Unit {

    public playerState getState() {
        return state;
    }

    //private final float XSPEED_MAX = 5;
    //private final float YSPEED_MAX = 5;
    private playerState state;
    final public static float PLAYER_X_SPAWN = (float) Main.RESOLUTION_X / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;
    final public static float PLAYER_Y_SPAWN = (float) Main.RESOLUTION_Y / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;

    protected ArrayList<Arte> arteDeck;
    protected ArrayList<Arte> arteHand;
    protected Arte move;
    protected int queue;
    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF

    public Player(Coordinate pos) throws SlickException {
        this.width = 64;
        this.height = 135;
        this.position = pos;
        this.xSpeed = 20;
        this.ySpeed = 20;
        this.sheet = new SpriteSheet("res/experimentalCharacter.png", 256, 512);
        this.sprite = sheet.getSprite(0,0);
        this.level = 1;
        this.arteDeck = new ArrayList<>();
        this.hitBox = new Rectangle((Main.getScreenWidth()/2) - this.getWidth()/2, (Main.getScreenHeight()/2) + 170, this.width, this.height-100); // set size to tiles
    }

    public void startBattle()   {
        queue = 5;
        this.arteHand.addAll(new ArrayList<Arte>(arteDeck.subList(0,6)));
    }

    public void move(Unit target, GameContainer gc, Graphics g) throws InterruptedException {
        for(int i = 0; i < arteHand.size(); i++)    {
            DrawUtilities.drawImageCentered(g, arteHand.get(i).getSprite(), (Main.getScreenWidth()/5)*(i+1), Main.getScreenHeight()-300);
        }
        move = cardSelect(gc.getInput());
        if(move != null) {
            move.use(target, gc);
            if(move.finished(gc))this.setState(playerState.DONE);

        }
    }

    public void update(StateBasedGame sbg, Unit u)  {
        if(getHitBox().intersects(u.getHitBox())) sbg.enterState(Main.BATTLE_ID);
    }

    public Arte cardSelect(Input input) throws InterruptedException {//too many repeated crap but idc man
        Arte selected = null;
        if(input.isKeyDown(Input.KEY_1))    {
            selected = arteHand.get(0);
            arteHand.remove(0);
            queue++;
            arteHand.add(arteDeck.get(queue));
            this.state = playerState.CASTING;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_2))    {
            selected = arteHand.get(1);
            arteHand.remove(1);
            queue++;
            arteHand.add(arteDeck.get(queue));
            this.state = playerState.CASTING;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_3))    {
            selected = arteHand.get(2);
            arteHand.remove(2);
            queue++;
            arteHand.add(arteDeck.get(queue));
            this.state = playerState.CASTING;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_4))    {
            selected = arteHand.get(3);
            arteHand.remove(3);
            queue++;
            arteHand.add(arteDeck.get(queue));
            this.state = playerState.CASTING;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_5)) {
            selected = arteHand.get(4);
            arteHand.remove(4);
            queue++;
            arteHand.add(arteDeck.get(queue));
            this.state = playerState.CASTING;
            return selected;
        }
        if(input.isKeyDown(Input.KEY_6))    {
            selected = arteHand.get(5);
            arteHand.remove(5);
            queue++;
            this.state = playerState.CASTING;
            return selected;
        }
        return selected;
    }
    public void setState(playerState s) {
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
