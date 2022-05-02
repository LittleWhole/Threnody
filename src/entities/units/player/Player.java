package entities.units.player;

import core.Constants;
import core.Main;
import entities.core.Coordinate;
import entities.units.Unit;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public final class Player extends Unit {
    private Rectangle hitBox;
    private final float XSPEED_MAX = 20;
    private final float YSPEED_MAX = 20;
    private float dx, dy, xSpeed, ySpeed;
    private SpriteSheet sheet;
    private Image sprite;
    private static final float Player_Acceleration = 2f;

    final public static float PLAYER_X_SPAWN = (float) Main.RESOLUTION_X / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;
    final public static float PLAYER_Y_SPAWN = (float) Main.RESOLUTION_Y / 2 / Constants.ImageConstants.PIXELS_PER_UNIT;

    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF

    public Player() throws SlickException {
        this.width = 256;
        this.height = 128;
        this.position = new Coordinate(0, 0);
        this.xSpeed = 10;
        this.ySpeed = 5;
        this.sheet = new SpriteSheet("res/tilemap/experimentalCharacter.png", 256, 512);
        this.sprite = sheet.getSprite(0,0);
        this.level = 1;
        this.hitBox = new Rectangle(getX(), getY(), this.width, this.height);//set size to tiles
    }

    public void accelerateX(float amt)   {
        this.dx += xSpeed*amt;
    }
    public void accelerateY(float amt)   {
        this.dy -= ySpeed*amt;
    }

    public void update(TiledMap map)    {
        this.position.updatePosition(dx, dy);
        this.dy = 0;
        this.dx = 0;
    }

    public void render (Graphics g) {
        g.drawImage(this.sprite, (Main.getScreenWidth()/2) - 128, (Main.getScreenHeight()/2) - 256);
    }




    public Player getPlayer() {
        return this;
    }
}
