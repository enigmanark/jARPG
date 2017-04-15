package gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.IF.Collidable;

public class Tile_Collidable extends GameObject implements Collidable {
	
	public Tile_Collidable(float x, float y) {
		super(x, y);
	}
	
	public Tile_Collidable() {
		
	}
	
	@Override
	public Rectangle getColliderRect() {
		return this.getRect();
	}
}
