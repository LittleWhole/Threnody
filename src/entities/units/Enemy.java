package entities.units;

import entities.core.Coordinate;
import entities.core.Hitbox;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Enemy extends Unit{
    public Enemy(float x, float y) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        this.width = 256;
        this.height = 128;
        this.position = new Coordinate(x, y);
        this.xSpeed = 10;
        this.ySpeed = 5;//y speed needs to be half of x speed so the movement has that isometric feel
        this.sheet = new SpriteSheet("res/experimentalEnemy.png", 256, 512);
        this.sprite = sheet.getSprite(0, 0);
        this.level = 1;
        this.hitBox = new Hitbox(this);//set size to tiles
    }

    public void update()    {
        //have two update modes. 1 is overworld which is just moving. 2 is battlestate which includes attacking
    }

    public Enemy getEnemy() {
        return this;
    }
}
