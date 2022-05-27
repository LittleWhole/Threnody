package combat.artes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Card<A extends Arte> {
    private float level;
    private A arte;
    private Image sprite;

    public A getArte() {
        return arte;
    }

    public Class<? extends Arte> getArteType() {
        return getArte().getClass();
    }

    public void render(Graphics g, int mouseX, int mouseY) {

    }
}
