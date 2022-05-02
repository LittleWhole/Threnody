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
    private final float YSPEED_MAX = 20;

    final public static float PLAYER_X_SPAWN = (float) Main.RESOLUTION_X / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;
    final public static float PLAYER_Y_SPAWN = (float) Main.RESOLUTION_Y / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;

    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF

    public Player() throws SlickException {
        this.width = 256;
        this.height = 128;
        this.position = new Coordinate(0, 0);
        this.xSpeed = 10;
        this.ySpeed = 10;
        this.sheet = new SpriteSheet("res/tilemap/experimentalCharacter.png", 256, 512);
        this.sprite = sheet.getSprite(0,0);
        this.level = 1;
        this.hitBox = new Hitbox(this);//set size to tiles
    }

    public Player getPlayer() {
        return this;
    }
}
