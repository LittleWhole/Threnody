package graphics.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import util.DevDebugException;

public interface UserInterfaceable extends Updatable {
    void render(Graphics g, int mouseX, int mouseY);

    default void update(GameContainer gc) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("""
        You attempted to call update() on a UserInterfaceable that did not have an update() defined.
        Did you make a typo? Did you forget to define an update() for this class (""" + this.getClass().getCanonicalName() + ")?");
    }
}
