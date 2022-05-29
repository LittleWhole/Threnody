package graphics;

import core.Main;
import org.newdawn.slick.Graphics;

public class Star {
    private final int size;
    private final int x;
    private final int y;

    public Star() {
        this.size = (int) (Math.random() * 5) + 2;

        this.x = (int) (Math.random() * Main.RESOLUTION_X);
        this.y = (int) (Math.random() * Main.RESOLUTION_Y);
    }

    public void render(Graphics g) { g.fillOval(x,y,size,size); }
}
