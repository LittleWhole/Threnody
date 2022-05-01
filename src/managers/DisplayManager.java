package managers;

import core.Constants;
import core.Main;
import entities.core.Coordinate;
import entities.core.Entity;
import entities.units.player.Player;
import gamestates.Game;
import graphics.Background;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class DisplayManager {
	private Graphics graphics;

	private Background background;

	private Coordinate center; // The entity the camera will be rendered around
	private Game game; // The game (so we can reference it)
	
	public DisplayManager(Game g, Coordinate center, Graphics graphics) {
		this.game = g;
		// this.center = center;

		this.center = new Coordinate(Player.PLAYER_X_SPAWN, Player.PLAYER_Y_SPAWN);
		//		game.getPlayer().getPosition();

		this.graphics = graphics;

		// Initialize Background
		this.background = new Background();
	}

	// Returns pixel coordinates on screen of some game position
	public float screenX(float x) { return (x - center.getX()) * Constants.ImageConstants.PIXELS_PER_UNIT + Constants.ImageConstants.CENTER_X; }
	public float screenY(float y) { return Main.RESOLUTION_Y - ((y - center.getY()) * Constants.ImageConstants.PIXELS_PER_UNIT + Constants.ImageConstants.CENTER_Y); }

	// Returns game coordinate of some pixel screen coordinate
	public float gameX(float x) { return center.getX() + (x - Constants.ImageConstants.CENTER_X) / Constants.ImageConstants.PIXELS_PER_UNIT; }
	public float gameY(float y) { return center.getY() + 1 + (Constants.ImageConstants.CENTER_Y - y) / Constants.ImageConstants.PIXELS_PER_UNIT; }

	// Main rendering method
	public void renderEntities(Graphics g) {
		// Render entities in game
		for(ArrayList<Entity> list: game.getEntities().values()) {
			for(Entity e: list) { e.render(g); }
		}
	}
	// Display player score and lives
	public void renderInterface(Graphics g) {
		// Rendering player score
		g.setColor(Color.white);
		g.resetLineWidth();

		/*String scoreDisplay = "Score: " + game.getPlayer().getScore();

		g.drawRect(Engine.RESOLUTION_X * 0.021f, Engine.RESOLUTION_Y * 0.037f,
				g.getFont().getWidth(scoreDisplay) + Engine.RESOLUTION_X * 0.005f * 2f,
				g.getFont().getHeight(scoreDisplay) + Engine.RESOLUTION_Y * 0.009f * 2f);
		g.drawString(scoreDisplay, Engine.RESOLUTION_X * 0.026f, Engine.RESOLUTION_Y * 0.046f);*/
	}
	// Render Background
	public void renderBackground(Graphics g) { background.render(g); }
}