package com.ceirenthir.game.core.logic;

/*
 * This Module receives teleport to different maps events from the LogicSystem via
 * other modules via the Post System. If it receives it takes care of changing the
 * map.
 */

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.Map;
import com.ceirenthir.game.core.graphics.GraphicsSystem;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.MapChange;
import com.ceirenthir.game.core.logic.events.Teleport;

public class Mod_MapController extends Mod_Logic{
	
	protected Mod_MapController(Evermore game) {

	}
	
	@Override
	protected void init() {
		//Finalize map creation

	}
	
	//Receive teleport events from the LogicSystem
	@Override
	protected void post(Evermore game, Event e) {
		//Teleport to new map
		if(e instanceof Teleport) {
			Teleport teleport = (Teleport)e;
			//Fade out the screen to avoid crashing
			//as well as to give a nice well..fade out effect
			//This suspends all rendering except for clearing the screen
			//So it makes the screen black
			game.graphicsSys.setFade(true);
			
			//Unload the current map
			game.logicSys.currentMap.unloadMap();
			
			//We're teleporting on the same map so don't change it
			//Just set the player's coordinates
			if(game.logicSys.currentMap.name == teleport.name) {
				game.dataSys.player.setX(teleport.xTile * GraphicsSystem.TILE_SIZE);
				int yTile = teleport.yTile;
				yTile = game.logicSys.currentMap.height - yTile - 1;
				game.dataSys.player.setY(yTile * GraphicsSystem.TILE_SIZE);
			}
			else {
				//Get the new map and then set it, then set the player's coordinates
				//to the teleport to coordinates
				Map newMap = game.dataSys.mapDB.getMapByName(teleport.name);
				newMap.loadMap();
				game.logicSys.currentMap = newMap; 
				game.dataSys.player.setX(teleport.xTile * GraphicsSystem.TILE_SIZE);
				int yTile = teleport.yTile;
				yTile = newMap.height - yTile - 1;
				game.dataSys.player.setY(yTile * GraphicsSystem.TILE_SIZE);
				game.logicSys.post(new MapChange(newMap.name));
			}
			
			game.graphicsSys.setFade(false);
		}
	}
}
