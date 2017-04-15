package com.ceirenthir.game.core.data;

/*
 * The DataSystem manages and stores all of the games data. It mostly consists of
 * DataBase modules that hold their respective data types. The information from here
 * is manipulated and acted upon by the LogicSystem. 
 * 
 * Graphics assets are also stored here in the Mod_Assets object. This is
 * the only Module that the DataSystem initializes on it's on.
 * 
 * This System does not modify or create any of the data stored here, in any way - 
 * to avoid confusion, except Mod_Assets.
 */

import gameObjects.Actor_Player;

import com.ceirenthir.game.core.Evermore;

public class DataSystem {
	final Evermore game;
	
	public Mod_Assets assets;

	public Mod_DB_Maps mapDB;
	public Mod_DB_NPCs npcDB;
	public Mod_DB_Quests questDB;
	public Actor_Player player;
	
	public DataSystem(Evermore gameCore) {
		this.game = gameCore;
		assets = new Mod_Assets(game);
		mapDB = new Mod_DB_Maps();
		npcDB = new Mod_DB_NPCs(gameCore);
		questDB = new Mod_DB_Quests();
	}
	
	public void init() {
		assets.init();
	}
	
	public void dispose() {
		
	}
}
