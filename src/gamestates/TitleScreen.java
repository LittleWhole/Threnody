// added a comment

package gamestates;

import core.Constants;
import core.Main;
import entities.core.Coordinate;
import graphics.Background;
import graphics.ui.menu.CloseButton;
import graphics.ui.menu.DialogBox;
import managers.SoundManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import graphics.ui.Button;
import util.DrawUtilities;

public class TitleScreen extends ThrenodyGameState {
    private final int id;
    public Background background;

    private Image title;
    private Button bNEW_GAME;
    private Button bCONTINUE;
    private Button bLOAD_GAME;
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
        title = new Image("res/ui/start/logo.png");
        this.sbg = sbg;
        background = Main.loading.background;
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        font = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 50), true);
        gc.getGraphics().setFont(font);
        bNEW_GAME = new Button(Main.getScreenWidth() / 2, Main.getScreenHeight() / 2 + 100, gc.getGraphics().getFont().getWidth("New Game"), gc.getGraphics().getFont().getHeight("New Game"), "New Game");
        bCONTINUE = new Button(Main.getScreenWidth() / 2, Main.getScreenHeight() / 2 + 200, gc.getGraphics().getFont().getWidth("Continue"), gc.getGraphics().getFont().getHeight("Continue"), "Continue");
        bLOAD_GAME = new Button(Main.getScreenWidth() / 2, Main.getScreenHeight() / 2 + 300, gc.getGraphics().getFont().getWidth("Load Game"), gc.getGraphics().getFont().getHeight("Load Game"), "Load Game");
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

        DrawUtilities.drawImageCentered(g, title, Main.getScreenWidth() / 2, Main.getScreenHeight() / 3);
        DrawUtilities.drawStringCentered(g, "Version " + Constants.VERSION, Main.getScreenWidth() / 2, Main.getScreenHeight() / 3 - 200);
        g.setBackground(new Color((int) (167 * 1f), (int) (231 * 1f), (int) (255 * 1f)));

        g.setColor(Color.black);
        bNEW_GAME.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        bCONTINUE.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        bLOAD_GAME.render(g, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        super.render(gc, sbg, g);
    }

    public void mousePressed(int button, int x, int y) {
        if (!Main.menus.isEmpty()) return;
        if (bNEW_GAME.onButton(x, y)) {
            SoundManager.stopBackgroundMusic();
            Game.firstTime = true;
            sbg.enterState(Main.GAME_ID, new FadeOutTransition(), new FadeInTransition());
        }
        if (bCONTINUE.onButton(x, y)) {
            Main.menus.add(new DialogBox(700, 400, "Sorry!", "Savegame loading is currently still WIP!", new CloseButton("Got it")));
        }
        if (bLOAD_GAME.onButton(x, y)) {
            Main.menus.add(new DialogBox(700, 400, "Sorry!", "Savegame loading is currently still WIP!", new CloseButton("Got it")));
        }
    }
}
