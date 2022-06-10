package graphics.ui.menu;

import core.Main;
import graphics.ui.UserInterfaceable;
import managers.SoundManager;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.RoundedRectangle;
import util.DrawUtilities;

import java.util.Map;

public abstract class Menu implements UserInterfaceable {
    public int getWidth() {
        return width;
    }

    protected final int width;
    protected final int height;

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected final int x;
    protected final int y;
    protected boolean show = true;
    protected boolean initialPlay = false;
    protected Map<String, Font> fonts;

    protected Menu(int width, int height) {
        this.width = width;
        this.height = height;
        this.x = Main.RESOLUTION_X / 2;
        this.y = Main.RESOLUTION_Y / 2;
        this.initializeFonts();
    }

    protected Menu(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.initializeFonts();
    }

    @Override
    public void render(Graphics g, int mouseX, int mouseY) {
        if (!show) return;
        if (!initialPlay) {
            SoundManager.playSoundEffect("ping");
            this.initialPlay = true;
        }
        g.setColor(new Color(0, 0, 0, 170));
        DrawUtilities.fillShapeCentered(g, new RoundedRectangle(0, 0, width, height, RoundedRectangle.ALL), x, y);
        g.setColor(new Color(219, 202, 106));
        DrawUtilities.drawShapeCentered(g, new RoundedRectangle(0, 0, width, height, RoundedRectangle.ALL), x, y);
        this.subrender(g);
    }

    @Override
    public void update(GameContainer gc) { }

    public void close() { this.show = false; }
    public void toggle() { this.show ^= true; }
    public void open() { this.show = true; }

    public void remove() { Main.menus.remove(this); }

    protected abstract void subrender(Graphics g);
    protected abstract void initializeFonts();
}
