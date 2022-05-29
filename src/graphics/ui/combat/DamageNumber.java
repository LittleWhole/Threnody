package graphics.ui.combat;

import core.Fonts;
import core.Main;
import graphics.ui.Displayable;
import graphics.ui.Updatable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.font.effects.OutlineEffect;
import util.DrawUtilities;

public class DamageNumber implements Updatable {
    private final int value;
    private final float x;
    private final float y;
    private TrueTypeFont font;
    private TrueTypeFont fontOutline;
    private Color color;
    private Color outlineColor;
    private int lifetime, fontSize;


    public DamageNumber(int value, float x, float y) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.font = Main.fonts.DAMAGE_NUMBER;
        this.fontOutline = Main.fonts.DAMAGE_NUMBER_OUTLINE;
        this.color = Color.white;
        this.outlineColor = Color.black;
        this.lifetime = 120;
        this.fontSize = 40;
    }
    public DamageNumber(int value, float x, float y, Color c) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.font = Main.fonts.DAMAGE_NUMBER;
        this.fontOutline = Main.fonts.DAMAGE_NUMBER_OUTLINE;
        this.color = c;
        this.outlineColor = Color.black;
        this.lifetime = 120;
        this.fontSize = 40;
    }

    @Override
    public void render(Graphics g, int mouseX, int mouseY) {
        g.setColor(outlineColor);
        DrawUtilities.drawStringCentered(g, String.valueOf(value), fontOutline, x, y);
        g.setColor(color);
        DrawUtilities.drawStringCentered(g, String.valueOf(value), font, x, y);
        g.setColor(Color.white);
    }

    @Override
    public void update(GameContainer gc) {
        if (lifetime > 100) {
            font = Main.fonts.VariableWidth.plainFont(fontSize);
            fontOutline = Main.fonts.VariableWidth.boldFont(fontSize);
            fontSize++;
        } else if (lifetime < 60) {
            font = Main.fonts.VariableWidth.plainFont(fontSize);
            fontOutline = Main.fonts.VariableWidth.boldFont(fontSize);
            fontSize--;
        }
        this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (255 / 60) * lifetime);
        this.outlineColor = new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), (255 / 60) * lifetime);
        lifetime--;
    }

    public boolean isExpired() { return lifetime <= 0; }

    public DamageNumber setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
        return this;
    }
}
