package graphics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Background {
    private final Cloud[] clouds;

    public Background() {
        clouds = new Cloud[7];

        for(int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud();
        }
    }

    public void update() { for (Cloud c : clouds) c.update(); }

    public void render(Graphics g) {
        for (Cloud c : clouds) c.render(g);
    }

    public void renderPre(Graphics g, float alpha) throws SlickException {
        for (Cloud c : clouds) c.renderPre(g, alpha);
    }

}
