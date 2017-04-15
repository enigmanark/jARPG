package com.ceirenthir.game.core.IF;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.logic.scripts.Script;

/*
 * Only GameObjects that should have their scripts executed via Activate should
 * implement this interface.
 */

public interface Activatable {
	public ArrayList<Script> getScripts();
	public void activate(Evermore game);
	public Rectangle getActivatorRect();
}
