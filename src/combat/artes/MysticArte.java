package combat.artes;

import core.Main;
import entities.units.Unit;
import entities.units.enemy.Enemy;
import managers.ImageManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import playerdata.characters.PlayableCharacter;
import util.DrawUtilities;

public abstract class MysticArte<T extends Unit> extends Arte<T> {
    private boolean start;
    protected MysticArte(T owner) throws SlickException {
        super(owner);
        start = false;
    }

    public void use(Unit target, GameContainer gc) {
        if (!start) {
            var image = ImageManager.getImage("mysticActivate");
            if (timer < 10) {
                image.setImageColor((float) 1 / timer, (float) 1 / timer, (float) 1 / timer);
                image.getScaledCopy(1.5f - (0.05f * timer)).drawCentered(Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 2);
            } else if (timer < 40) {
                image.setImageColor(1, 1, 1, 1);
                image.drawCentered(Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 2);
            } else {
                image.getScaledCopy(1.1f * (timer - 40)).drawCentered(Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 2);
            }


            if (timer > 60) {
                start = true;
                timer = 0;
            }

            if (timer < 60) {
                if (timer < 20) gc.getGraphics().setColor(new Color(221, 201, 85, (255 / 20) * timer));
                else if (timer < 40) gc.getGraphics().setColor(new Color(221, 201, 85));
                else gc.getGraphics().setColor(new Color(221, 201, 85, (255 / 20) * (60 - timer)));

                gc.getGraphics().fill(DrawUtilities.createRectangleCentered(Main.RESOLUTION_X / 2, 100, 400, 80));


                if (target instanceof Enemy) {
                    if (timer < 20) gc.getGraphics().setColor(new Color(12, 46, 100, (255 / 20) * timer));
                    else if (timer < 40) gc.getGraphics().setColor(new Color(12, 46, 100));
                    else gc.getGraphics().setColor(new Color(12, 46, 100, (255 / 20) * (60 - timer)));
                } else {
                    if (timer < 20) gc.getGraphics().setColor(new Color(142, 27, 35, (255 / 20) * timer));
                    else if (timer < 40) gc.getGraphics().setColor(new Color(142, 27, 35));
                    else gc.getGraphics().setColor(new Color(142, 27, 35, (255 / 20) * (60 - timer)));
                }

                var rect = DrawUtilities.createRectangleCentered(Main.RESOLUTION_X / 2, 100, 400, 60);
                gc.getGraphics().fill(rect);

                if (timer < 30) gc.getGraphics().setColor(new Color(255, 255, 255, (255 / 20) * timer));
                else if (timer < 40) gc.getGraphics().setColor(new Color(255, 255, 255));
                else gc.getGraphics().setColor(new Color(255, 255, 255, (255 / 20) * (60 - timer)));


                gc.getGraphics().setFont(Main.fonts.VariableWidth.P40);
                DrawUtilities.drawStringCentered(gc.getGraphics(), "Mystic Arte", Main.fonts.VariableWidth.P40, rect);
                gc.getGraphics().setFont(Main.font);
            }
            timer++;
        }
        else {
            if (timer == 0) {
                owner.generateMana(-cost);
                owner.setQueuedManaRemoval(owner.getQueuedManaRemoval() - cost);
            }
            if (!finished()) {
                activation(target);
                if (timer < 60) {
                    if (timer < 20) gc.getGraphics().setColor(new Color(221, 201, 85, (255 / 20) * timer));
                    else if (timer < 40) gc.getGraphics().setColor(new Color(221, 201, 85));
                    else gc.getGraphics().setColor(new Color(221, 201, 85, (255 / 20) * (60 - timer)));

                    gc.getGraphics().fill(DrawUtilities.createRectangleCentered(Main.RESOLUTION_X / 2, 100, 400, 80));


                    if (target instanceof Enemy) {
                        if (timer < 20) gc.getGraphics().setColor(new Color(12, 46, 100, (255 / 20) * timer));
                        else if (timer < 40) gc.getGraphics().setColor(new Color(12, 46, 100));
                        else gc.getGraphics().setColor(new Color(12, 46, 100, (255 / 20) * (60 - timer)));
                    } else {
                        if (timer < 20) gc.getGraphics().setColor(new Color(142, 27, 35, (255 / 20) * timer));
                        else if (timer < 40) gc.getGraphics().setColor(new Color(142, 27, 35));
                        else gc.getGraphics().setColor(new Color(142, 27, 35, (255 / 20) * (60 - timer)));
                    }

                    var rect = DrawUtilities.createRectangleCentered(Main.RESOLUTION_X / 2, 100, 400, 60);
                    gc.getGraphics().fill(rect);

                    if (timer < 30) gc.getGraphics().setColor(new Color(255, 255, 255, (255 / 20) * timer));
                    else if (timer < 40) gc.getGraphics().setColor(new Color(255, 255, 255));
                    else gc.getGraphics().setColor(new Color(255, 255, 255, (255 / 20) * (60 - timer)));


                    gc.getGraphics().setFont(Main.fonts.VariableWidth.P40);
                    DrawUtilities.drawStringCentered(gc.getGraphics(), this.name, Main.fonts.VariableWidth.P40, rect);
                    gc.getGraphics().setFont(Main.font);
                }
                animation(target, gc.getGraphics());
                DrawUtilities.drawStringCentered(gc.getGraphics(), String.valueOf(timer), 100, 0);
            }
            timer++;
            spritesheetX++;
            if (aniSheet != null && spritesheetX >= aniSheet.getHorizontalCount()) {
                spritesheetX = 0;
                spritesheetY++;
            }
        }
    }

    @Override
    public void reset() throws SlickException {
        super.reset();
        start = false;
    }
}
