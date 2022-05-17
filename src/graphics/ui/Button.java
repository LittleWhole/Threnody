package graphics.ui;

import gamestates.Game;
import gamestates.TitleScreen;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Commandable;
import util.DrawUtilities;

import java.util.function.Function;

public class Button implements UserInterfaceable {
    private float x, y, width, height;
    private String text;
    private Commandable command;

    public Button(String text) {
        this.x = 0;
        this.y = 0;
        this.width = Game.getGc().getGraphics().getFont().getWidth(text);
        this.height = Game.getGc().getGraphics().getFont().getHeight(text);
        this.text = text;
    }

    public Button(String text, Commandable command) {
        this.x = 0;
        this.y = 0;
        this.width = Game.getGc().getGraphics().getFont().getWidth(text);
        this.height = Game.getGc().getGraphics().getFont().getHeight(text);
        this.text = text;
        this.command = command;
    }

    public Button(float width, float height, String text) {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public Button(float x, float y, float width, float height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    // Draw the image centered
    public void render(Graphics g, int mouseX, int mouseY) {
        Color color;
        g.setFont(TitleScreen.font);
        if (onButton(mouseX, mouseY)) {
            g.setColor(new Color(247, 168, 74));
            DrawUtilities.drawStringCentered(g, "> " + text + " <", TitleScreen.font, x, y);
        }
        else {
            g.setColor(Color.white);
            DrawUtilities.drawStringCentered(g, text, TitleScreen.font, x, y);
        }
        g.setColor(Color.white);
    }

    public boolean onButton(int mouseX, int mouseY) {
        if(x - width / 2 < mouseX && mouseX < x + width / 2) {
            if(y - height / 2 < mouseY && mouseY < y + height / 2) {
                return true;
            }
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

    public Button setX(float x) {
        this.x = x;
        return this;
    }

    public Button setY(float y) {
        this.y = y;
        return this;
    }

    public Button setWidth(float width) {
        this.width = width;
        return this;
    }

    public Button setHeight(float height) {
        this.height = height;
        return this;
    }

    public Button setText(String text) {
        this.text = text;
        return this;
    }

    public Button setCommand(Commandable command) {
        this.command = command;
        return this;
    }
}


