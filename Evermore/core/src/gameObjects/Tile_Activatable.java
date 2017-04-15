package gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.IF.Activatable;

public class Tile_Activatable extends GameObject implements Activatable {
	
	public Tile_Activatable(float x, float y) {
		super(x, y);
	}
	
	public Tile_Activatable() {
		
	}
	
	@Override
	public Rectangle getRect() {
		return super.getRect();
	}
	
}
