package graphics;

import core.Main;
import managers.ImageManager;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Cloud {
    private final int size;
    private int x;
    private final int y;

    public Cloud() {
        this.size = (int) (Math.random() * 500) + 100;

        this.x = (int) (Math.random() * Main.RESOLUTION_X);
        this.y = (int) (Math.random() * Main.RESOLUTION_Y);
    }

    public void update() {
        this.x -= 1;
        if (this.x < 0 - size) this.x = Main.RESOLUTION_X + size;
    }

    public void render(Graphics g) {
        //g.fillOval(x, y, size, size/3);
        g.drawImage(ImageManager.getImage("cloud").getScaledCopy(size, size / 3), x, y);
    }

    public void renderPre(Graphics g, float alpha) throws SlickException {
        Image sprite = new Image("res/background/cloud.png").getScaledCopy(size, size / 3);
        sprite.setImageColor(1, 1, 1, 1 * alpha);
        g.drawImage(sprite, x, y);
    }

}
