package com.ceirenthir.game.core.logic;

import gameObjects.GameObject;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.Updatable;

/*
 * Runs update on all updatables. Updatables are typically children
 * of GameObject. See "Script" for more explanation.
 */

public class Mod_GObjectUpdate_Handler extends Mod_Logic {
	private final Evermore game;
	
	public Mod_GObjectUpdate_Handler(Evermore game) {
		this.game = game;
	}
	
	//Just get the maps current Updatables and update them.
	@Override
	public void update() {
		for(GameObject object : game.logicSys.currentMap.gameObjects) {
			if(object instanceof Updatable) {
				Updatable updatable = (Updatable) object;
				updatable.update(game);
			}
		}
	}
}
