package graphics.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import util.DevDebugException;

public interface Updatable extends Displayable {
    void update(GameContainer gc);
}
