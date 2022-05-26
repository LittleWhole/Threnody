package core;

import org.newdawn.slick.TrueTypeFont;

public final class Fonts {
    public TrueTypeFont MAIN, DAMAGE_NUMBER, DAMAGE_NUMBER_OUTLINE, MENU_TITLE, MENU_BODY, CARD, ARTE;

    public Fonts() {
        MAIN = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true);
        DAMAGE_NUMBER = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 40), true);
        DAMAGE_NUMBER_OUTLINE = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 45), true);
    }
}
