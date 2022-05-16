package util;

import gamestates.TitleScreen;
import managers.ImageManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

public record Button(float x, float y, float width, float height, String text) {
    // Draw the image centered
    public void render(Graphics g, float mouseX, float mouseY) {
        Color color;
        if (onButton(mouseX, mouseY)) color = new Color(247, 168, 74);
        else color = Color.white;
        g.setFont(TitleScreen.font);
        g.setColor(color);
        DrawUtilities.drawStringCentered(g, text, TitleScreen.font, x, y);
        g.setColor(Color.white);
    }

    public boolean onButton(float mouseX, float mouseY) {
        if(x - width / 2 < mouseX && mouseX < x + width / 2) {
            if(y - height / 2 < mouseY && mouseY < y + height / 2) {
                return true;
            }
        }
        return false;
    }
}


