package units.player;

import core.Constants;
import core.Game;
import org.jetbrains.annotations.Contract;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public sealed class Player permits Sigur, Phaedra   {
    private Rectangle hitBox;
    private final double XSPEED_MAX = 20;
    private final double YSPEED_MAX = 20;
    private double x, y, dx, dy, xSpeed, ySpeed;
    private int level, exp, maxExp;
    private int health, attack, defense, critRate, critDamage, eAttack, eDefense, affinity;
    private SpriteSheet sheet;
    private Image sprite;
    private static final float Player_Acceleration = 2f;

    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF

    public Player() throws SlickException {
        this.xSpeed = 10;
        this.ySpeed = 5;
        this.sheet = new SpriteSheet("res/experimentalCharacter.png", 256, 512);
        this.sprite = sheet.getSprite(0,0);
        this.level = 1;
        this.exp = 0;
        this.maxExp = Constants.LevelingConstants.MAX_EXP(1);
        this.hitBox = new Rectangle((float)this.x, (float)this.y, 256, 128);//set size to tiles
    }

    public void accelerateX(float amt)   {
        this.dx += xSpeed*amt;
    }
    public void accelerateY(float amt)   {
        this.dy -= ySpeed*amt;
    }

    public void update(TiledMap map)    {
        this.x += dx;
        this.y+=dy;
        this.dy =0;
        this.dx = 0;
    }

    public void render (Graphics g) {
        g.drawImage(this.sprite, (float)this.x, (float)this.y);
    }




    public Player getPlayer() {
        return this;
    }
}
