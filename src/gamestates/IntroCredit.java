// added a comment

package gamestates;

import core.Constants;
import core.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.DrawUtilities;
import util.Resource;

import java.io.File;
import java.io.IOException;

public class IntroCredit extends BasicGameState {

    private int id;
    private Image logo;
    private StateBasedGame sbg;

    private int counter = 0;
    private int counter2 = 100;

    public IntroCredit(int id)
    {
        this.id = id;
    }
    public int getID()
    {
        return id;
    } // Returns the ID code for this game state

    // Initializer, first time
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        gc.setShowFPS(false);
        logo = new Image("res/seahazestudios.png").getScaledCopy(700, 700);
        this.sbg = sbg;
    }

    @Override // Begin file loading upon entering the gamestate
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        counter = 0;
        counter2 = 100;
    }

    @Override // Update, runs consistently
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
        counter++;
    }

    @Override // Render, all visuals
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
        g.setBackground(Color.white);
        logo.setImageColor(1, 1, 1, 1 * ((float) counter / 100));
        if (counter > 200) logo.setImageColor(1, 1, 1, 1 * ((float) --counter2 / 100));
        if (counter > 300) sbg.enterState(Main.LOADING_ID);
        DrawUtilities.drawImageCentered(g, logo, Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 2);
    }

    public void mousePressed(int button, int x, int y) {
        sbg.enterState(Main.LOADING_ID);
    }
}
