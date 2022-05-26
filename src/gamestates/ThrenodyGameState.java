package gamestates;

import core.Main;
import graphics.ui.Button;
import graphics.ui.menu.CloseButton;
import graphics.ui.menu.DialogBox;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ThrenodyGameState extends BasicGameState {
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        Main.displayables.forEach(d -> d.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY()));
        Main.menus.forEach(m -> {
            m.update(gc);
            m.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        });
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_F2) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
            String dateAndTime = dateFormat.format(new Date());
            String filename = "screenshots/" + dateAndTime + ".png";
            File file = new File(filename);
            file.getParentFile().mkdirs();

            try {
                Image target = new Image(Main.game.getGc().getWidth(), Main.game.getGc().getHeight());
                Main.game.getGc().getGraphics().copyArea(target, 0, 0);
                ImageOut.write(target, filename);
                target.destroy();
            } catch (SlickException e) {
                throw new RuntimeException(e);
            }

            Main.menus.add(new DialogBox(700, 400, "Screenshot", "Screenshot saved in screenshots/ folder.", new CloseButton("Got it")));
        }
    }
}
