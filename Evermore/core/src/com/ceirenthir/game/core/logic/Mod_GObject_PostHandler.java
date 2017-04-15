package com.ceirenthir.game.core.logic;

import gameObjects.GameObject;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.Postable;
import com.ceirenthir.game.core.logic.events.Event;

public class Mod_GObject_PostHandler extends Mod_Logic {
	
	public void post(Evermore game, Event e) {
		game.dataSys.player.post(game, e);
		for(GameObject o : game.logicSys.currentMap.gameObjects) {
			if(o instanceof Postable) {
				((Postable) o).post(game, e);
			}
		}
	}
}
