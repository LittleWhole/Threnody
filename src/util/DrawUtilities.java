package util;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public final class DrawUtilities {
    private DrawUtilities() {
        throw new UnsupportedOperationException("You cannot instantiate this utility class!");
    }

    /*================================================================*/
    /*                      CENTERING UTILITIES                       */
    /*================================================================*/
    public static Font defaultFont = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true);
    public static Rectangle createRectangleCentered(float x, float y, float width, float height) {
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public static void centerShape(Shape shape, float x, float y) {
        shape.setCenterX(x);
        shape.setCenterY(y);
    }

    public static Shape createShapeCentered(Class<? extends Shape> clazz, float x, float y, BiBundle... params) {
        Class<?>[] types = new Class<?>[params.length];
        Bundle<?>[] values = new Bundle<?>[params.length];
        Shape temp = null;
        for (var i = 0; i < params.length; i++) {
            types[i] = params[i].getType();
            values[i] = params[i].getValue();
        }
        try {
            temp = clazz.getConstructor(types).newInstance(Arrays.asList(values).stream().map(Bundle::getT).toArray());
            temp.setCenterX(x);
            temp.setCenterY(y);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return temp;
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
        org.newdawn.slick.Font font = defaultFont;
        int width = font.getWidth(string);
        int height = font.getHeight(string);
        Rectangle r = createRectangleCentered(x, y, width, height);

        g.drawString(string, (r.getX() + r.getWidth() / 2) - (width / 2),
                (r.getY() + r.getHeight() / 2) - (height / 2));
    }

    public static void drawStringCentered(Graphics g, String string, Image image) {
        org.newdawn.slick.Font font = defaultFont;
        int width = font.getWidth(string);
        int height = font.getHeight(string);
        Rectangle r = createRectangleCentered(image.getCenterOfRotationX(), image.getCenterOfRotationY(), width, height);

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
        org.newdawn.slick.Font font = defaultFont;
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

    public static void drawShapeCentered(Graphics g, Shape shape, float x, float y) {
        centerShape(shape, x, y);
        g.draw(shape);
    }

    public static void fillShapeCentered(Graphics g, Shape shape, float x, float y) {
        centerShape(shape, x, y);
        g.fill(shape);
    }
}
