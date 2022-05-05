package entities.core;

import core.Constants;
import core.Main;
import entities.units.Unit;
import gamestates.Game;
import managers.ImageManager;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

// Standard code for all entities in the game
public abstract class Entity {
	// For general use
	protected static Game game = Main.game;

	// Rendering Variables
	protected boolean remove; // When remove == true, the entity will be unloaded
	protected Image sprite; // The entity's sprite
	protected SpriteSheet sheet; // The entity's spritesheet
	
	// Descriptive Variables
	protected float mass; // Mass of the object (if we want to factor in collisions; in kg)
	protected float width;
	protected float height;
	
	// Linear Movement
	protected Coordinate position; // Coordinate of the Entity
	protected float xSpeed, ySpeed; // Speeds of the object (m/s)
	protected float dx, dy; // Rate of change (derivative) of position
	
	// Angular Movement
	protected float angle; // Rotation of the entity (counterclockwise, in radians)
	
	// Hit box of the entity
	protected Rectangle hitBox; // Hit box of the Entity

	protected EntityType type; // Type of the Entity

	protected Team team; // Team of the Entity

	public Entity() {}

	public Entity(float x, float y) {
		// Initializing Rendering Variables
		this.remove = false;
		this.sprite = ImageManager.getPlaceholder(); // Default sprite

		// Initializing Descriptive Variables
		this.width = 1f; // Default width
		this.height = 1f; // Default height
		this.mass = 1f; // Default mass
		
		// Initializing Linear Movement
		this.position = new Coordinate(x, y); // Initializing position 
		this.xSpeed = this.ySpeed = 0; // Initializing speeds
		
		// Initializing Angular Movement
		this.angle = 0f; // Default rotation for now

		// Initializing hitbox
		this.hitBox = new Rectangle(x, y, this.width, this.height);

		// Entity Type and Teams
		this.type = EntityType.NONE;
		this.team = Team.NEUTRAL;
	}
	
	// Accessor Methods
	public Rectangle getHitBox() { return hitBox; }
	public Image getSprite() { return sprite; }
	public Coordinate getPosition() { return position; }
	public EntityType getType() { return type; }
	public Team getTeam() { return team; }

	public float getX() { return position.getX(); }
	public float getY() { return position.getY(); }

	public float getMagSpeed() { return (float) Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed); }
	public float getSpeedX() { return xSpeed; }
	public float getSpeedY() { return ySpeed; }

	public float getRotation() { return angle; }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	public boolean isMarked() { return remove; }
	
	// Rendering Methods
	public void render(Graphics g) { // Main render method that is called
		//if(Main.debug) hitBox.drawHitBox(g); // Draw hitbox first
		renderOther(g); // Draw unique entity graphics
		drawSprite(g); // Draw entity sprite
	}

	protected abstract void renderOther(Graphics g); // Rendering method unique to the entity
	protected void drawSprite(Graphics g) { // Draw the entity sprite
		g.drawImage(this.sprite, (Main.getScreenWidth()/2) - 128, (Main.getScreenHeight()/2) - 256);//need to change
	}

	
	// Mutator Methods
	public void rotateCounter(float theta) { this.angle += theta; } // Rotations
	public void setRotation(float theta) { this.angle = theta; } // Rotations

	public void accelerateX(float amt) { this.dx += xSpeed*amt; }
	public void accelerateY(float amt) { this.dy -= ySpeed*amt; }

	// Helper Methods
	public void faceEntity(Entity e) {
		// Find angle (from the horizontal) to the other entity
		double theta = Math.atan2(
				position.y - e.position.y,
				position.x - e.position.x);

		// Set the angle of this entity
		this.angle = (float) theta;
	}

	// Update Method: Update Physics Variables
	public void update() {
		// Collision checking
		checkCollisions();

		// Update the position of the entity
		this.position.updatePosition(dx, dy);
		this.dy = 0;
		this.dx = 0;
	};

	protected void checkCollisions() {
		checkEntityCollisions(); // Entity Collisions
		checkScreenCollisions(); // Screen Collisions
	};

	// Check collision with the edge of the screen
	protected void checkScreenCollisions() {
		// Check left/right borders
		if (position.x - width / 2 < 0 || Main.RESOLUTION_X / Constants.ImageConstants.PIXELS_PER_UNIT < position.x + width / 2) {
			screenCollision();
			screenCollisionX();
		}
		// Check top/bottom borders
		if (position.y - height / 2< 0 || Main.RESOLUTION_Y / Constants.ImageConstants.PIXELS_PER_UNIT < position.y + height / 2) {
			screenCollision();
			screenCollisionY();
		}
	}

	// By default, entities will bounce off the screen borders
	protected void screenCollisionX() { }
	protected void screenCollisionY() { }

	protected void screenCollision() {}

	// Check collisions with other units
	protected void checkEntityCollisions() {
		ArrayList<Entity> units = game.getEntitiesOf(EntityType.UNIT);

		for (Entity e: units) {
			if(this.equals(e)) continue;
			else if(!sameTeam(e) && hitBox.intersects(e.getHitBox())) {
				collide(e);
				unitCollision((Unit) e);
				break; // Not sure if I should break or not, testing
			}
		}
	}
	// Determines if two entities are on the same team
	private boolean sameTeam(Entity e) {
		if(team == Team.NEUTRAL || team != e.team) {
			return false;
		} else return true;
	}

	// Unique collision method that can be overwritten in extensions of this class
	protected void unitCollision(Unit u) { }

	// Update velocities (using the law of conservation of momentum)
	protected void collide(Entity e) {
		// Updating X Velocities
		final float C1X = mass * xSpeed + e.mass * e.xSpeed;
		final float C2X = -Constants.MovementConstants.RESTITUTION_COEFFICIENT * (e.xSpeed - this.xSpeed);

		this.xSpeed = (C1X + mass * C2X) / (mass + e.mass) - C2X;
		e.xSpeed = (C1X + mass * C2X) / (mass + e.mass);

		// Updating Y Velocities
		final float C1Y = mass * ySpeed + e.mass * e.ySpeed;
		final float C2Y = -Constants.MovementConstants.RESTITUTION_COEFFICIENT * (e.ySpeed - ySpeed);

		this.ySpeed = (C1Y + mass * C2Y) / (mass + e.mass) - C2Y;
		e.ySpeed = (C1Y + mass * C2Y) / (mass + e.mass);
	}

	public float getDx() {
		return dx;
	}

	public float getDy() {
		return dy;
	}

	@Override
	public String toString() {
		return "Entity{" +
				"remove=" + remove +
				", sprite=" + sprite +
				", mass=" + mass +
				", width=" + width +
				", height=" + height +
				", position=" + position +
				", xSpeed=" + xSpeed +
				", ySpeed=" + ySpeed +
				", angle=" + angle +
				", hitBox=" + hitBox +
				", type=" + type +
				", team=" + team +
				'}';
	}
}
