package com.ceirenthir.game.core.logic.scripts;

import java.util.Iterator;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.logic.events.Teleport;
/*
 * This is a Script that is created at runtime. This script is used for
 * teleporting the player to other maps by stepping on it's host GameObject. TeleX and TeleY are
 * supposed to be injected in from TiledMaps as well as the mapName which is
 * the teleport to map.
 * 
 * This script can only be attached to a GameObject that implements Collide.
 * 
 * This Script is handled by Mod_MapController and Mod_Collide_Handler. Check there for how this
 * script is handled.
 */
public class TeleportScript extends Script {
	public int teleX;
	public int teleY;
	public String mapName;
	
	public TeleportScript(String mapName, int teleX, int teleY) {
		this.mapName = mapName;
		this.teleX = teleX;
		this.teleY = teleY;
	}
	
	@Override
	public void collide(Evermore game, Iterator<Script> scriptIter) {
		game.logicSys.post(new Teleport(mapName, teleX, teleY));
	}
}
