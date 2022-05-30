package entities.units.enemy;

import org.newdawn.slick.SlickException;

public class Goblin<T extends Goblin<?>> extends Enemy<T> {
    public Goblin(float x, float y) throws SlickException {
        super(x, y);
        this.attack = 9;
    }
}
