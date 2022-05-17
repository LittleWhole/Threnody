// added a comment

package gamestates;

import core.Constants;
import core.Main;
import graphics.Background;
import managers.SoundManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import graphics.ui.Button;
import util.DrawUtilities;

public class TitleScreen extends BasicGameState {
    private int id;
    public Background background;
    private Button bNEW_GAME;
    private StateBasedGame sbg;
    public static Font font;

    public TitleScreen(int id)
    {
        this.id = id;
    }
    public int getID()
    {
        return id;
    } // Returns the ID code for this game state

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        this.sbg = sbg;
        background = Main.loading.background;
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        font = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 50), true);
        gc.getGraphics().setFont(font);
        bNEW_GAME = new Button(Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 2 + 100, gc.getGraphics().getFont().getWidth("< New Game >"), gc.getGraphics().getFont().getHeight("New Game"), "New Game");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
        background.update();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
        g.setFont(Main.font);
        background.renderPre(g, 1f);

        DrawUtilities.drawImageCentered(g, new Image("/res/logo-ja.png"), Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 3);
        DrawUtilities.drawStringCentered(g, "Version " + Constants.VERSION, Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 3 - 200);
        g.setBackground(new Color((int) (167 * 1f), (int) (231 * 1f), (int) (255 * 1f)));

        g.setColor(Color.black);
        bNEW_GAME.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }

    public void mousePressed(int button, int x, int y) {
        if (bNEW_GAME.onButton(x, y)) {
            SoundManager.stopBackgroundMusic();
            sbg.enterState(Main.GAME_ID, new FadeOutTransition(), new FadeInTransition());
        }
    }
}
