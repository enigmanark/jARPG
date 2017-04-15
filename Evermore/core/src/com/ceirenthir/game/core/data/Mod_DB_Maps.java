package com.ceirenthir.game.core.data;

/*
 * This class is in charge of storing and managing Map objects.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public class Mod_DB_Maps {
	private Array<Map> maps;
	
	protected Mod_DB_Maps() {
		maps = new Array<Map>();
	}
	
	public Array<Map> getMaps() {
		return maps;
	}
	
	//Using the name of the map, retrieve it. The name it checks
	//for is assigned in Tiled under the map's properties. With property
	//of "Name" with value being the name.
	public Map getMapByName(String name) {
		for(Map map : maps) {
			try{
				if(map.name.equalsIgnoreCase(name)) {
					return map;
				}
			} catch(NullPointerException e) {
				Gdx.app.error("Map DB Error", "Map has no name");
			}
		}
		return null;
	}
	
	//Add a new map, obviously
	public void add(Map map) {
		maps.add(map);
	}
	
}
