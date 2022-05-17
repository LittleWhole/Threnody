package graphics.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import util.DevDebugException;

public interface UserInterfaceable {
    void render(Graphics g, int mouseX, int mouseY);

    default void update(GameContainer gc) throws DevDebugException {
        throw new DevDebugException("""
        You attempted to call update() to a UserInterfaceable that did not have an update() defined.
        Did you make a typo? Did you forget to define an update() for this class (""" + this.getClass().getCanonicalName() + ")?");
    }
}
