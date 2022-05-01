package managers;

import entities.units.player.Player;
import entities.units.player.PlayerOld;
import gamestates.Game;
import org.newdawn.slick.Input;

import java.util.List;
import java.util.function.Predicate;

public class KeyManager implements Predicate<Integer> {
	private static final float PLAYER_ACCELERATION = 2f;
	public static final List<Integer> KEY_DOWN_LIST = List.of(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);

	private KeyManager() { throw new IllegalStateException("Utility class"); }

	private Player player;
	private Input input;
	
	public KeyManager(Input input, Game game) {
		this.input = input;
		this.player = game.getPlayer();
	}
	
	public boolean test(Integer i) {
		return input.isKeyDown(i);
	}
	
	public void keyDown(int key) {
		switch (key) {
			case Input.KEY_W -> player.accelerateY(PLAYER_ACCELERATION);
			case Input.KEY_A -> player.accelerateX(-PLAYER_ACCELERATION);
			case Input.KEY_S -> player.accelerateY(-PLAYER_ACCELERATION);
			case Input.KEY_D -> player.accelerateX(PLAYER_ACCELERATION);
		}
	}
}