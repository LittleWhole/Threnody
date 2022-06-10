package graphics.ui;

import core.Main;
import gamestates.Game;
import gamestates.TitleScreen;
import graphics.ui.menu.Menu;
import managers.SoundManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import util.Commandable;
import util.DrawUtilities;

import java.util.function.Function;

public class Button implements UserInterfaceable {
    protected float x, y, width, height;
    protected String text;
    protected Commandable command;
    protected Menu parent;

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
        this.width = Main.fonts.VariableWidth.P50.getWidth(text);
        this.height = Main.fonts.VariableWidth.P50.getHeight(text);
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

    public Button(float x, float y, String text, Commandable command) {
        this.x = x;
        this.y = y;
        this.width = Main.fonts.VariableWidth.P50.getWidth(text);
        this.height = Main.fonts.VariableWidth.P50.getHeight(text);
        this.text = text;
        this.command = command;
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
        if (onButton(mouseX, mouseY)) {
            g.setColor(new Color(247, 168, 74));
            DrawUtilities.drawStringCentered(g, "> " + text + " <", Main.fonts.VariableWidth.P53, x, y);
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

    @Override
    public void update(GameContainer gc) {
        if (onButton(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
            if (gc.getInput().isMousePressed(0)) {
                SoundManager.playSoundEffect("click");
                command.command();
            }
        }
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

    public Button setParent(Menu parent) {
        this.parent = parent;
        return this;
    }
}


