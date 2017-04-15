package gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.IF.Static;

public class Tile_Static extends GameObject implements Static{
	
	public Tile_Static(float x, float y) {
		super(x, y);
		this.setSolid(true);
	}
	
	public Tile_Static() {
		this.setSolid(true);
	}
	
	@Override
	public Rectangle getStaticRect() {
		return this.getRect();
	}
}
