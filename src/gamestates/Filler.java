// added a comment

package gamestates;

import core.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Filler extends BasicGameState {
    private int id;
    private StateBasedGame sbg;
    private int counter = 0;

    public Filler(int id)
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
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
        if (++counter > 80) sbg.enterState(Main.LOADING_ID, new FadeOutTransition(), new FadeInTransition());
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
        g.setBackground(Color.black);
    }

}
