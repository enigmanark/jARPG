package com.ceirenthir.game.core.IF;

import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.Evermore;

/*
 * Interface for GameObjects that use scripts that are executed on Player collision.
 */

public interface Collidable {
	public boolean intersects(Rectangle r);
	public void collide(Evermore game);
	public Rectangle getColliderRect();
}
