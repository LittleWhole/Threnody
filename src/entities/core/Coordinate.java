package entities.core;

import core.Main;

import java.util.Objects;

public class Coordinate {
	float x, y, z;

	public Coordinate(float x, float y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}

	public Coordinate(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Coordinate(Coordinate c) {
		this.x = c.x;
		this.y = c.y;
		this.z = c.z;
	}

	public void updatePosition(float dx, float dy) {
		// Update the X and Y position
		this.x += dx;
		this.y += dy;

		// Going too far left / right offscreen will loop the unit to the other side
		// this.x += (float) Engine.RESOLUTION_X / Values.Pixels_Per_Unit;
		// this.x %= (float) Engine.RESOLUTION_X / Values.Pixels_Per_Unit;
	}

	public void updatePosition(float dx, float dy, float dz) {
		// Update the X and Y position
		this.x += dx;
		this.y += dy;
		this.z += dz;

		// Going too far left / right offscreen will loop the unit to the other side
		// this.x += (float) Engine.RESOLUTION_X / Values.Pixels_Per_Unit;
		// this.x %= (float) Engine.RESOLUTION_X / Values.Pixels_Per_Unit;
	}

	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }

	// Computes the distance between two coordinates
	public static float distance(Coordinate c1, Coordinate c2) {
		return (float) Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
	}

	public static float distanceZ(Coordinate c1, Coordinate c2) {
		return (float) Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2) + Math.pow(c1.z - c2.z, 2));
	}

	// Determine displacement from some coordinate
	public float[] displacement(float x2, float y2) {
		float[] displacement = new float[2];

		displacement[0] = x2 - x;
		displacement[1] = y2 - y;

		return displacement;
	}

	public float[] displacement(float x2, float y2, float z2) {
		float[] displacement = new float[3];

		displacement[0] = x2 - x;
		displacement[1] = y2 - y;
		displacement[2] = z2 - z;

		return displacement;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Coordinate)) return false;
		Coordinate that = (Coordinate) o;
		return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0 && Float.compare(that.z, z) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "Coordinate{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
  
}