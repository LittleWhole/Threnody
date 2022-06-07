package managers;

import entities.units.Direction;
import entities.units.player.Player;
import gamestates.Game;
import map.GameMap;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

public final class KeyManager implements Predicate<Integer> {
	private static final float PLAYER_ACCELERATION = 1f;
	public static final List<Integer> KEY_DOWN_LIST = List.of(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
	public static final List<Integer> AMOGUS_LIST = List.of(Input.KEY_A, Input.KEY_M, Input.KEY_O, Input.KEY_G, Input.KEY_U, Input.KEY_S);

	private KeyManager() { throw new IllegalStateException("Utility class"); }

	private final Player player;
	private final Input input;
	private final GameMap map;
	
	public KeyManager(Input input, Game game) {
		this.input = input;
		this.player = game.getPlayer();
		this.map = game.getOverworld();
	}
	
	public boolean test(Integer i) {
		return input.isKeyDown(i);
	}
	
	public void keyDown(int key) {
		boolean temp = false;
		switch (key) {
			case Input.KEY_W -> {
				for (Shape[] sa : map.getHitboxes()) for (Shape s : sa) if(s != null) {
					player.accelerateY(PLAYER_ACCELERATION);
					map.updateHitboxes(0, player.getSpeedY() * 0.5f);
					if (player.getHitBox().intersects(s)) temp = true;
					//if(temp){player.accelerateY(-PLAYER_ACCELERATION);map.updateHitboxes(0, player.getSpeedY() * -0.5f);}
					player.accelerateY(-PLAYER_ACCELERATION);map.updateHitboxes(0, player.getSpeedY() * -0.5f);
				}
				if(!temp)	{player.accelerateY(PLAYER_ACCELERATION);map.updateHitboxes(0, player.getSpeedY()*0.5f);}

				//player.accelerateY(PLAYER_ACCELERATION);
				//map.updateHitboxes(0, player.getSpeedY()*0.5f);
			}
			case Input.KEY_A -> {
				for (Shape[] sa : map.getHitboxes()) for (Shape s : sa) if(s != null)	{
					player.accelerateX(-PLAYER_ACCELERATION);map.updateHitboxes(player.getSpeedX(), 0);
					if (player.getHitBox().intersects(s)) temp = true;
					//if(temp){player.accelerateX(PLAYER_ACCELERATION);map.updateHitboxes(player.getSpeedX()*-1, 0);}
					player.accelerateX(PLAYER_ACCELERATION);map.updateHitboxes(player.getSpeedX()*-1, 0);
				}
				if(!temp)	{player.accelerateX(-PLAYER_ACCELERATION);map.updateHitboxes(player.getSpeedX(), 0);}

				//player.accelerateX(-PLAYER_ACCELERATION);
				//map.updateHitboxes(player.getSpeedX(), 0);
			}
			case Input.KEY_S -> {
				for (Shape[] sa : map.getHitboxes()) for (Shape s : sa) if(s != null)	{
					player.accelerateY(-PLAYER_ACCELERATION);map.updateHitboxes(0, player.getSpeedY()*-0.5f);
					if (player.getHitBox().intersects(s))	temp = true;
					//if(temp){player.accelerateY(PLAYER_ACCELERATION);map.updateHitboxes(0, player.getSpeedY() * 0.5f);}
					player.accelerateY(PLAYER_ACCELERATION);map.updateHitboxes(0, player.getSpeedY() * 0.5f);
				}
				if(!temp)	{player.accelerateY(-PLAYER_ACCELERATION);map.updateHitboxes(0, player.getSpeedY()*-0.5f);}

				//player.accelerateY(-PLAYER_ACCELERATION);
				//map.updateHitboxes(0, player.getSpeedY()*-0.5f);
			}
			case Input.KEY_D -> {
				for (Shape[] sa : map.getHitboxes()) for (Shape s : sa) if(s != null)	{
					player.accelerateX(PLAYER_ACCELERATION);map.updateHitboxes(player.getSpeedX()*-1, 0);
					if (player.getHitBox().intersects(s))	temp = true;
					//if(temp){player.accelerateX(-PLAYER_ACCELERATION);map.updateHitboxes(player.getSpeedX(), 0);}
					player.accelerateX(-PLAYER_ACCELERATION);map.updateHitboxes(player.getSpeedX(), 0);
				}
				if(!temp)	{player.accelerateX(PLAYER_ACCELERATION);map.updateHitboxes(player.getSpeedX()*-1, 0);}

				//player.accelerateX(PLAYER_ACCELERATION);
				//map.updateHitboxes(player.getSpeedX()*-1, 0);
			}
		}
	}

	public boolean amogus(List<Integer> keys) {
		if (new HashSet<>(keys).containsAll(AMOGUS_LIST)) return true;
		return false;
	}
}