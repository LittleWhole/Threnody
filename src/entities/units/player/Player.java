package entities.units.player;

import core.Constants;
import core.Main;
import entities.core.Coordinate;
import entities.core.Hitbox;
import entities.units.Unit;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public final class Player extends Unit {

    private final float XSPEED_MAX = 20;
    private final float YSPEED_MAX = 10;

    final public static float PLAYER_X_SPAWN = (float) Main.RESOLUTION_X / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;
    final public static float PLAYER_Y_SPAWN = (float) Main.RESOLUTION_Y / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;

    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF

    public Player(Coordinate pos) throws SlickException {
        this.width = 256;
        this.height = 128;
        this.position = pos;
        this.xSpeed = 10;
        this.ySpeed = 5;//y speed needs to be half of x speed so the movement looks isometric
        this.sheet = new SpriteSheet("res/experimentalCharacter.png", 256, 512);
        this.sprite = sheet.getSprite(0,0);
        this.level = 1;
        this.hitBox = new Hitbox(this);//set size to tiles
    }

    public Player getPlayer() {
        return this;
    }

    protected void drawSprite(Graphics g) { // Draw the entity sprite
        g.drawImage(this.sprite, (Main.getScreenWidth()/2) - 128, (Main.getScreenHeight()/2) - 256);
    }
}
