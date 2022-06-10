package combat.artes;

import core.Main;
import entities.units.Unit;
import entities.units.enemy.Enemy;
import managers.ImageManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import entities.units.player.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import util.DrawUtilities;

public abstract class MysticArte<T extends Unit> extends Arte<T> {
    private boolean start = false;

    protected MysticArte(T owner) throws SlickException {
        super(owner);
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
        } else {
            super.use(target, gc);
        }
    }

    /*public void render(Unit target, Graphics g) {

        if (!finished()) {
            animation(target, g);
            DrawUtilities.drawStringCentered(g, String.valueOf(timer), 100, 0);
        }
        if(timer == castDuration){
            BattleState.damageNumbers.add(new DamageNumber(owner.getAttack(), (int)target.getPosition().getX(), (int)(target.getPosition().getY()+target.getHeight()/2)));
        }
        timer++;
        if (BattleState.time - castTimestamp < castDuration) {
            g.drawArc(owner.getEntity().getX(), owner.getEntity().getY(), 50f, 50f, 0f, (float) Math.toRadians((float) castTimestamp / castDuration * 360));
        }
    }*/
    public boolean finished() {
        return (timer >= castDuration);
    }

    public void animation(Unit target, Graphics g) {
        switch (aniType) {
            case OWNER -> animationOwner(g);
            case TARGET -> animationTarget(target, g);
            case REVERSE_TARGET -> animationReverseTarget(target, g);
            default -> animationOwner(g);
        }
    }
    public void animationOwner(Graphics g) {
        if (!finished()) {
            this.aniFrame = aniSheet.getSprite(spritesheetX, spritesheetY);
            g.drawImage(aniFrame, -owner.getPosition().getX() + (owner.getWidth()/2) + 5, owner.getPosition().getY()/2 + owner.getHeight()*2.75f);
        }
    }

    public void animationTarget(Unit target, Graphics g) {
        if(!finished()) {
            this.aniFrame = aniSheet.getSprite(spritesheetX, spritesheetY);
            if (this.owner instanceof Player) g.drawImage(aniFrame, -target.getPosition().getX(), -target.getY() + target.getHeight());
            else g.drawImage(aniFrame.getFlippedCopy(true, false), -target.getPosition().getX(), -target.getY() + target.getHeight());
        }
    }

    public void animationReverseTarget(Unit target, Graphics g) {
        if(!finished()) {
            this.aniFrame = aniSheet.getSprite(spritesheetX, spritesheetY);
            g.drawImage(aniFrame.getFlippedCopy(true, false), -target.getPosition().getX(), -target.getY() + target.getHeight());
        }
    }

    @Override
    public void reset() throws SlickException {
        super.reset();
        start = false;
    }
}
