package graphics.ui.combat;

import graphics.ui.Displayable;
import graphics.ui.Updatable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import util.DrawUtilities;

public class DamageNumber implements Updatable {
    private int value;
    private int x;
    private int y;
    private TrueTypeFont font;
    private Color color;
    private int lifetime;

    public DamageNumber(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.font = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 40), true);
        this.color = new Color(Color.white);
        this.lifetime = 60;
    }

    @Override
    public void render(Graphics g, int mouseX, int mouseY) {
        g.setColor(color);
        DrawUtilities.drawStringCentered(g, String.valueOf(value), font, x, y);
        g.setColor(Color.white);
    }

    @Override
    public void update(GameContainer gc) {
        this.color = new Color(255, 255, 255, (255 / 60) * lifetime);
        lifetime--;
    }

    public boolean isExpired() { return lifetime <= 0; }
}