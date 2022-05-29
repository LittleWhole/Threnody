package graphics.ui.menu;

import gamestates.Game;
import graphics.ui.Button;
import graphics.ui.UserInterfaceable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import util.DrawUtilities;
import util.StringUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DialogBox extends Menu implements UserInterfaceable {
    String title;
    String body;
    List<Button> buttons;
    List<String> bodyLines = new ArrayList<>();

    public DialogBox(int width, int height, String title, String body) {
        super(width, height);
        this.title = title;
        this.body = body;
        formatBody();
    }

    public DialogBox(int width, int height, String title, String body, Button... buttons) {
        super(width, height);
        this.title = title;
        this.body = body;
        this.buttons = Arrays.asList(buttons);
        this.initializeButtons(buttons);
        formatBody();
    }

    public DialogBox(int x, int y, int width, int height, String title, String body, Button... buttons) {
        super(x, y, width, height);
        this.title = title;
        this.body = body;
        this.buttons = Arrays.asList(buttons);
        this.initializeButtons(buttons);
        formatBody();
    }

    public void initializeButtons(Button... buttons) {
        for (Button b : buttons) {
            b.setParent(this);
            if (b instanceof CloseButton) b.setCommand(this::remove);
        }
        this.buttons = List.of(buttons);
    }

    public void initializeButtons() {
        for (Button b : this.buttons) b.setParent(this);
    }

    @Override
    public void initializeFonts() {
        this.fonts = new HashMap<>();
        fonts.put("title", new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 50), true));
        fonts.put("body", new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 30), true));
    }

    public void formatBody() {
        bodyLines.add(body);
        /*String temp = "";
        String clone = body;
        int lastIndex = 0;
        for (var i = 0; i < clone.length(); i++) {
            temp = clone.substring(lastIndex, i);
            if (fonts.get("body").getWidth(temp) > this.width) {
                bodyLines.add(clone.substring(lastIndex, i));
                lastIndex = i;
            }
        }*/
    }

    @Override
    protected void subrender(Graphics g) {
        g.setColor(Color.white);
        DrawUtilities.drawStringCentered(g, title, fonts.get("title"), x, y - height / 2 + 40);
        for (var i = 0; i < bodyLines.size(); i++) {
            DrawUtilities.drawStringCentered(g, bodyLines.get(i), fonts.get("body"), x, y/* + (height / bodyLines.size() * (i + 1))*/);
        }
        for (var i = 0; i < buttons.size(); i++) {
            buttons.get(i).setX(x / buttons.size() * (i + 1));
            buttons.get(i).setY(y + height / 2 - 40);
            buttons.get(i).render(g, Game.getGc().getInput().getMouseX(), Game.getGc().getInput().getMouseY());
        }
    }

    @Override
    public void update(GameContainer gc) {
        super.update(gc);
        if (gc.getInput().isMouseButtonDown(0)) {
            buttons.forEach(b -> {
                if (b.onButton(gc.getInput().getMouseX(), gc.getInput().getMouseY())) b.getCommand().command();
            });
        }
    }
}
