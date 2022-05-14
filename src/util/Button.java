package util;

import gamestates.TitleScreen;
import managers.ImageManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

public class Button {
    private float x,y;
    private float width, height;
    private String text;
    private Color color;

    public Button(float x, float y, float width, float height, String text) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
        this.text = text;
    }

    // Draw the image centered
    public void render(Graphics g) {
        g.setFont(TitleScreen.font);
        g.setColor(color);
        DrawUtilities.drawStringCentered(g, text, TitleScreen.font, x, y);
        g.setColor(Color.white);
    }

    public void update(float mouseX, float mouseY) {
        if(onButton(mouseX, mouseY)) {
            Color lightBlue = new Color(173, 216, 230);
            color = lightBlue;
        } else {
            color = Color.white;
        }
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


