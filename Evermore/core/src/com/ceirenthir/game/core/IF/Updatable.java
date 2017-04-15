package com.ceirenthir.game.core.IF;

import com.ceirenthir.game.core.Evermore;


/*
 * Only GameObjects that have scripts that are updatable should implement this interface.
 */

public interface Updatable {
	public void update(Evermore game);
}
