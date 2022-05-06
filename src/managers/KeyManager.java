package managers;

import core.Main;
import entities.units.player.Player;
import entities.units.player.PlayerOld;
import gamestates.Game;
import map.GameMap;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;

import java.util.List;
import java.util.function.Predicate;

public final class KeyManager implements Predicate<Integer> {
	private static final float PLAYER_ACCELERATION = 2f;
	public static final List<Integer> KEY_DOWN_LIST = List.of(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);

	private KeyManager() { throw new IllegalStateException("Utility class"); }

	private Player player;
	private Input input;
	private GameMap map;
	
	public KeyManager(Input input, Game game) {
		this.input = input;
		this.player = game.getPlayer();
		this.map = game.getOverworld();
	}
	
	public boolean test(Integer i) {
		return input.isKeyDown(i);
	}
	
	public void keyDown(int key) {
		switch (key) {
			case Input.KEY_W -> {
				for (Shape[] sa : map.getHitboxes()) for (Shape s : sa) if (!player.getHitBox().intersects(s)) player.accelerateY(PLAYER_ACCELERATION);
			}
			case Input.KEY_A -> {
				for (Shape[] sa : map.getHitboxes()) for (Shape s : sa) if (!player.getHitBox().intersects(s)) player.accelerateX(-PLAYER_ACCELERATION);
			}
			case Input.KEY_S -> {
				for (Shape[] sa : map.getHitboxes()) for (Shape s : sa) if (!player.getHitBox().intersects(s)) player.accelerateY(-PLAYER_ACCELERATION);
			}
			case Input.KEY_D -> {
				for (Shape[] sa : map.getHitboxes()) for (Shape s : sa) if (!player.getHitBox().intersects(s)) player.accelerateX(PLAYER_ACCELERATION);
			}
		}
	}
}