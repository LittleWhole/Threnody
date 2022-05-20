package graphics.ui;

import org.newdawn.slick.Graphics;

@FunctionalInterface
public interface Displayable {
    void render(Graphics g, int mouseX, int mouseY);
}
