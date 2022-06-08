package graphics.ui;

import gamestates.Game;
import gamestates.TitleScreen;
import graphics.ui.menu.Menu;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.RoundedRectangle;
import util.Commandable;
import util.DrawUtilities;

public class PaddedButton extends Button implements UserInterfaceable {
    private RoundedRectangle rect;

    public PaddedButton(float width, String text, Commandable command) {
        super(width, 60, text);
        this.command = command;
        rect = new RoundedRectangle(0, 0, width, 60, RoundedRectangle.ALL);
        DrawUtilities.centerShape(rect, x, y);
    }

    public PaddedButton(float width, float height, String text, Commandable command) {
        super(width, height, text);
        this.command = command;
        rect = new RoundedRectangle(0, 0, width, height, RoundedRectangle.ALL);
        DrawUtilities.centerShape(rect, x, y);
    }

    public PaddedButton(float x, float y, float width, float height, String text, Commandable command) {
        super(x, y, width, height, text);
        this.command = command;
        rect = new RoundedRectangle(x, y, width, height, RoundedRectangle.ALL);
        DrawUtilities.centerShape(rect, x, y);
    }

    // Draw the image centered
    public void render(Graphics g, int mouseX, int mouseY) {
        if (onButton(mouseX, mouseY)) {
            g.setColor(new Color(255, 255, 255, 170));
            g.draw(rect);
            g.setColor(new Color(247, 168, 74));
            DrawUtilities.drawStringCentered(g, "> " + text + " <", new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 53), true), rect);
        }
        else {
            g.setColor(Color.white);
            DrawUtilities.drawStringCentered(g, text, TitleScreen.font, x, y);
        }
        g.setColor(Color.white);
    }

    public boolean onButton(int mouseX, int mouseY) {
        if(x - width / 2 < mouseX && mouseX < x + width / 2) {
            return y - height / 2 < mouseY && mouseY < y + height / 2;
        }
        return false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getText() {
        return text;
    }

    public Commandable getCommand() {
        return command;
    }

    public Menu getParent() {
        return parent;
    }

    public PaddedButton setX(float x) {
        this.x = x;
        rect = new RoundedRectangle(x, y, width, height, RoundedRectangle.ALL);
        DrawUtilities.centerShape(rect, x, y);
        return this;
    }

    public PaddedButton setY(float y) {
        this.y = y;
        rect = new RoundedRectangle(x, y, width, height, RoundedRectangle.ALL);
        DrawUtilities.centerShape(rect, x, y);
        return this;
    }

    public PaddedButton setWidth(float width) {
        this.width = width;
        rect = new RoundedRectangle(x, y, width, height, RoundedRectangle.ALL);
        DrawUtilities.centerShape(rect, x, y);
        return this;
    }

    public PaddedButton setHeight(float height) {
        this.height = height;
        rect = new RoundedRectangle(x, y, width, height, RoundedRectangle.ALL);
        DrawUtilities.centerShape(rect, x, y);
        return this;
    }

    public PaddedButton setText(String text) {
        this.text = text;
        return this;
    }

    public PaddedButton setCommand(Commandable command) {
        this.command = command;
        return this;
    }

    public PaddedButton setParent(Menu parent) {
        this.parent = parent;
        return this;
    }
}


