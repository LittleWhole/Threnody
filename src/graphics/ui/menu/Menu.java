package graphics.ui.menu;

import core.Main;
import graphics.ui.UserInterfaceable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;
import util.DrawUtilities;

public abstract class Menu implements UserInterfaceable {
    protected final int width;
    protected final int height;
    protected final int x;
    protected final int y;

    protected Menu(int width, int height) {
        this.width = width;
        this.height = height;
        this.x = Main.RESOLUTION_X / 2;
        this.y = Main.RESOLUTION_Y / 2;
    };

    protected Menu(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    };

    @Override
    public void render(Graphics g, int mouseX, int mouseY) {
        g.setColor(new Color(0, 0, 0, 120));
        DrawUtilities.fillShapeCentered(g, new RoundedRectangle(0, 0, width, height, RoundedRectangle.ALL), x, y);
        this.subrender(g);
    };

    @Override
    public void update(GameContainer gc) {};

    protected abstract void subrender(Graphics g);
}
