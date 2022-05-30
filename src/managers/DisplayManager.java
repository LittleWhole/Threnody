package managers;

import core.Constants;
import core.Main;
import entities.core.Coordinate;
import gamestates.Game;
import org.newdawn.slick.Graphics;

public final class DisplayManager {
	private Graphics graphics;

	private final Coordinate center; // The entity the camera will be rendered around
	private final Game game; // The game (so we can reference it)
	
	public DisplayManager(Game g, Coordinate center, Graphics graphics) {
		this.game = g;
		// this.center = center;

		this.center = game.getPlayer().getPosition();
	}

	// Returns pixel coordinates on screen of some game position
	public float screenX(float x) { return (x - center.getX()) * Constants.ImageConstants.PIXELS_PER_UNIT + Constants.ImageConstants.CENTER_X; }
	public float screenY(float y) { return Main.RESOLUTION_Y - ((y - center.getY()) * Constants.ImageConstants.PIXELS_PER_UNIT + Constants.ImageConstants.CENTER_Y); }

	// Returns game coordinate of some pixel screen coordinate
	public float gameX(float x) { return center.getX() + (x - Constants.ImageConstants.CENTER_X) / Constants.ImageConstants.PIXELS_PER_UNIT; }
	public float gameY(float y) { return center.getY() + 1 + (Constants.ImageConstants.CENTER_Y - y) / Constants.ImageConstants.PIXELS_PER_UNIT; }

	// Render Player HUD
	public void renderInterface(Graphics g) {
	}
}