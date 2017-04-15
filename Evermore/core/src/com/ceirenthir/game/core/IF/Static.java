package com.ceirenthir.game.core.IF;

import com.badlogic.gdx.math.Rectangle;

/*
 * This interface is for tiles that are solid and only are solid. They have no
 * logic other than rectangle collision.
 */

public interface Static {
	public boolean intersects(Rectangle r);
	public boolean isSolid();
	public void setSolid(boolean is);
	public Rectangle getStaticRect();
}
