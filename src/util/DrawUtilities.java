package util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;


public final class DrawUtilities {

    private DrawUtilities() {
        throw new UnsupportedOperationException("You cannot instantiate this utility class!");
    }

    /*================================================================*/
    /*                      CENTERING UTILITIES                       */
    /*================================================================*/

    public static Rectangle createRectangleCentered(float x, float y, float width, float height) {
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public static void drawImageCentered(Graphics g, Image image, float x, float y) {
        int width = image.getWidth();
        int height = image.getHeight();
        Rectangle r = createRectangleCentered(x, y, width, height);

        g.drawImage(image, (r.getX() + r.getWidth() / 2) - (width / 2),
                (r.getY() + r.getHeight() / 2) - (height / 2));
    }

    /*===================================*/
    /*              Strings              */
    /*===================================*/

    public static void drawStringCentered(Graphics g, String string, float x, float y) {
        org.newdawn.slick.Font font = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true);
        int width = font.getWidth(string);
        int height = font.getHeight(string);
        Rectangle r = createRectangleCentered(x, y, width, height);

        g.drawString(string, (r.getX() + r.getWidth() / 2) - (width / 2),
                (r.getY() + r.getHeight() / 2) - (height / 2));
    }

    public static void drawStringCentered(Graphics g, String string, org.newdawn.slick.Font font, float x, float y) {
        org.newdawn.slick.Font prevFont = g.getFont();
        int width = font.getWidth(string);
        int height = font.getHeight(string);
        Rectangle r = createRectangleCentered(x, y, width, height);

        g.setFont(font);
        g.drawString(string, (r.getX() + r.getWidth() / 2) - (width / 2),
                (r.getY() + r.getHeight() / 2) - (height / 2));
        g.setFont(prevFont);
    }

    public static void drawStringCentered(Graphics g, String string, Rectangle r) {
        org.newdawn.slick.Font font = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true);
        int width = font.getWidth(string);
        int height = font.getHeight(string);

        g.drawString(string, (r.getX() + r.getWidth() / 2) - (width / 2),
                (r.getY() + r.getHeight() / 2) - (height / 2));
    }

    public static void drawStringCentered(Graphics g, String string, org.newdawn.slick.Font font, Rectangle r) {
        int width = font.getWidth(string);
        int height = font.getHeight(string);

        g.drawString(string, (r.getX() + r.getWidth() / 2) - (width / 2),
                (r.getY() + r.getHeight() / 2) - (height / 2));
    }

}
