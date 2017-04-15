package com.ceirenthir.game.core.util;

import gameObjects.Actor_Player;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.Bag;
import com.ceirenthir.game.core.data.Items;

public class Loader_Content {
	
	private static void loadMaps(Evermore game) {
		//MapLoader creates and loads the maps into the database for you
		MapLoader2.load(game);
	}
	
	private static void loadPlayer(Evermore game) {
		//Load Player
		game.dataSys.player = new Actor_Player(game);
		game.dataSys.player.setSpriteSheetName("charSheet1");
		game.dataSys.player.loadEntity
		(game.dataSys.assets, game.logicSys.currentMap.startX, game.logicSys.currentMap.startY);

		//Load player's backPack
		game.logicSys.getInventoryController().setBackPack(new Bag(5, 5));
		
		//Add some starter items
		game.dataSys.player.itemMngr.addToBackPack(Items.Minor_Healing_Potion_Good,
				25);
		game.dataSys.player.itemMngr.addToBackPack(Items.Minor_Healing_Potion_Good,  5);
		game.dataSys.player.itemMngr.addToBackPack(Items.Short_Sword_Poor, 1);
	}
	
	public static void load(Evermore game) {
		loadMaps(game);
		loadPlayer(game);
	}
	
}
