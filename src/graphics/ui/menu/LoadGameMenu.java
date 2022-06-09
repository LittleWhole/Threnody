package graphics.ui.menu;

import gamestates.Game;
import graphics.ui.Button;
import graphics.ui.UserInterfaceable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import util.DrawUtilities;
import util.ThrenodyFont;

import java.util.*;

public class LoadGameMenu extends Menu implements UserInterfaceable {
    String title;
    String body;
    List<Button> buttons;
    List<SavegameList> pages;
    int currentPage;

    public LoadGameMenu(List<String> files) {
        super(1200, 800);
        this.title = "Load From Save";
        pages = new ArrayList<>();
        currentPage = 0;
        files.sort(Comparator.reverseOrder());
        var num = files.size();
        for (var i = 0; i < Math.ceil((double) num / 9); i++) {
            try {
                pages.add(new SavegameList(files.subList(i * 9, (i + 1) * 9)));
            } catch (IndexOutOfBoundsException ignored) {
                pages.add(new SavegameList(files.subList(i * 9, num)));
            }
        }
        this.buttons = new ArrayList<>();
        buttons.add(new Button("Previous Page", () -> {
            if (currentPage > 0) {
                currentPage--;
            }
        }));
        buttons.add(new Button("Next Page", () -> {
            if (currentPage < pages.size() - 1) {
                currentPage++;
            }
        }));
    }

    public void initializeButtons(Button... buttons) {
        for (Button b : buttons) {
            b.setParent(this);
            if (b instanceof CloseButton cb) cb.setCommand(() -> { cb.getParent().remove(); cb.getSecondCommand().command(); });
        }
        this.buttons = List.of(buttons);
    }

    public void initializeButtons() {
        for (Button b : this.buttons) b.setParent(this);
    }

    @Override
    public void initializeFonts() {
        this.fonts = new HashMap<>();
        fonts.put("title", new ThrenodyFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 50), true));
        fonts.put("body", new ThrenodyFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 30), true));
    }

    @Override
    protected void subrender(Graphics g) {
        g.setColor(Color.white);
        DrawUtilities.drawStringCentered(g, title, fonts.get("title"), x, y - height / 2 + 40);
        pages.get(currentPage).render(g, Game.getGc().getInput().getMouseX(), Game.getGc().getInput().getMouseY());
        buttons.get(0).setX(x - 350);
        buttons.get(0).setY(y + height / 2 - 40);
        buttons.get(0).render(g, Game.getGc().getInput().getMouseX(), Game.getGc().getInput().getMouseY());
        buttons.get(1).setX(x + 350);
        buttons.get(1).setY(y + height / 2 - 40);
        buttons.get(1).render(g, Game.getGc().getInput().getMouseX(), Game.getGc().getInput().getMouseY());
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
