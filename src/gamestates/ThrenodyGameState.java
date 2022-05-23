package gamestates;

import core.Main;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.state.BasicGameState;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ThrenodyGameState extends BasicGameState {
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
        }
    }
}
