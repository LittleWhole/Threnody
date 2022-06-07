package combat.artes;

import entities.units.Unit;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import util.ThrenodyException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public class Card {
    private float level;
    private Class<? extends Arte> arte;
    private Image sprite;

    public Card(Class<? extends Arte> arte) {
        this.arte = arte;
        try {
            this.sprite = arte.getConstructor((Class<?>) ((ParameterizedType) arte.getGenericSuperclass()).getActualTypeArguments()[0]).newInstance((Object) null).getCard();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<? extends Arte> getArte() {
        return arte;
    }

    public Image getSprite() {
        return sprite;
    }

    public void render(Graphics g, int mouseX, int mouseY) {

    }
}
