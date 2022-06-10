package graphics.ui.menu;

import core.Main;
import gamestates.Game;
import graphics.ui.Button;
import graphics.ui.UserInterfaceable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import util.DrawUtilities;
import util.ThrenodyFont;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PauseMenu extends Menu implements UserInterfaceable {
    private List<Button> buttons;

    public PauseMenu() {
        super(Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 2, 700, 400);
        this.buttons = new ArrayList<>();
            buttons.add(new Button(x, y - 20, "Resume", () -> { this.remove(); Main.paused = false; }));
            buttons.add(new Button(x, y + 40,"Save", Main::save));
            buttons.add(new Button(x, y + 100,"Quit", () -> Main.game.getSbg().enterState(Main.TITLE_ID, new FadeOutTransition(), new FadeInTransition())));
            buttons.add(new Button(x, y + 160,"Activate Cheat Mode", () ->
                Main.addMenu(new DialogBox(1000, 600, "Enable Cheat Mode?", """
                                Are you sure you want to enable Cheat Mode?
                                This mode gives you access to every card and makes
                                you have unlimited mana, gold, and EXP (press F3 to add).
                                Only use for testing/to experience all combat.""",
                        new CloseButton("No"), new CloseButton("Yes", Main::cheatOn)))
            ));
    }

    @Override
    protected void subrender(Graphics g) {
        g.setColor(Color.white);
        DrawUtilities.drawStringCentered(g, "Menu", fonts.get("title"), x, y - height / 2 + 40);
        buttons.get(0).render(g, Game.getGc().getInput().getMouseX(), Game.getGc().getInput().getMouseY());
        buttons.get(1).render(g, Game.getGc().getInput().getMouseX(), Game.getGc().getInput().getMouseY());
        buttons.get(2).render(g, Game.getGc().getInput().getMouseX(), Game.getGc().getInput().getMouseY());
        buttons.get(3).render(g, Game.getGc().getInput().getMouseX(), Game.getGc().getInput().getMouseY());
    }

    @Override
    protected void initializeFonts() {
        this.fonts = new HashMap<>();
        fonts.put("title", new ThrenodyFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 50), true));
        fonts.put("body", new ThrenodyFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 30), true));
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
