// added a comment

package gamestates;

import core.Constants;
import core.Main;
import graphics.Background;
import managers.SoundManager;
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

public class LoadingScreen extends BasicGameState {
    public static final String RES = System.getProperty("user.dir") + "/res";

    private LoadingList loadingList;
    private String lastResource;

    private int id;

    private int totalTasks;
    private int tasksDone;

    public Background background;

    public LoadingScreen(int id)
    {
        this.id = id;
    }
    public int getID()
    {
        return id;
    } // Returns the ID code for this game state

    // Initialize LoadingList
    private void initializeLoadingList(File dir, LoadingList loadingList) {
        for(final File file: dir.listFiles()) {
            if(file.isDirectory()) {
                initializeLoadingList(file, loadingList);
            } else {
                loadingList.add(new Resource(file));
            }
        }
    }

    // Initializer, first time
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        gc.setShowFPS(false);
        gc.setDefaultFont(new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true));

        this.loadingList = LoadingList.get();
        Main.font = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true);
        background = new Background();
    }

    @Override // Begin file loading upon entering the gamestate
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        new Sound("res/audio/music/01.ogg").play();
        // Set Loading List to Deferred
        LoadingList.setDeferredLoading(true);

        // Initialize Loading List
        initializeLoadingList(new File(RES), loadingList);

        this.totalTasks = loadingList.getTotalResources();
        this.tasksDone = 0;

        gc.getGraphics().setBackground(Color.black);
    }

    @Override // Update, runs consistently
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
        background.update();
        // Load a new resource
        if(loadingList.getRemainingResources() > 0) {
            try {
                DeferredResource resource = loadingList.getNext();
                resource.load();
                lastResource = resource.getDescription();
            } catch(IOException e) {
                System.out.println("Failed to load a resource");
            }
        }
        // When loading is completed, automatically move to start menu
        else {
            // TextManager.initialize();
            sbg.enterState(Main.TITLE_ID);
            System.out.println("Leaving Loading");
        }
    }

    @Override // Render, all visuals
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
        g.setFont(Main.font);
        // Calculate the number of tasks done
        this.tasksDone = loadingList.getTotalResources() - loadingList.getRemainingResources();

        // Draw a Loading Bar
        final float BAR_WIDTH = gc.getWidth() - 0.25f * gc.getWidth();
        final float BAR_HEIGHT = 0.0726f * gc.getHeight();

        final float BAR_X = gc.getWidth() / 2 - BAR_WIDTH / 2;
        final float BAR_Y = gc.getHeight() / 2 - BAR_HEIGHT / 2;

        final float PERCENT_LOADED = (float) tasksDone / (float) totalTasks;

        background.renderPre(g, PERCENT_LOADED);

        DrawUtilities.drawImageCentered(g, new Image("/res/logo-ja.png"), Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 3);
        DrawUtilities.drawStringCentered(g, "Version " + Constants.VERSION, Main.RESOLUTION_X / 2, Main.RESOLUTION_Y / 3 - 200);
        g.setBackground(new Color((int) (167 * PERCENT_LOADED), (int) (231 * PERCENT_LOADED), (int) (255 * PERCENT_LOADED)));

        // max loading bar
        g.setColor(new Color(100, 75, 2));
        g.fill(new RoundedRectangle(BAR_X, BAR_Y, BAR_WIDTH, BAR_HEIGHT, RoundedRectangle.ALL));

        // current loaded
        g.setColor(new Color(255, 230, 2));
        g.fill(new RoundedRectangle(BAR_X, BAR_Y, BAR_WIDTH * PERCENT_LOADED, BAR_HEIGHT, RoundedRectangle.ALL));

        // white outline
        g.setColor(new Color(255, 255, 255));
        g.draw(new RoundedRectangle(BAR_X, BAR_Y, BAR_WIDTH, BAR_HEIGHT, RoundedRectangle.ALL));

        DrawUtilities.drawStringCentered(g, "Loading resource: " + lastResource + "...", Main.RESOLUTION_X / 2, BAR_Y + BAR_HEIGHT + 25f);
    }
}
