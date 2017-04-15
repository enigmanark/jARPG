package com.ceirenthir.game.core.data;

import java.util.HashMap;

import com.ceirenthir.game.core.logic.events.Message;
import com.ceirenthir.game.core.logic.scripts.QuestStart;

/*
 * This class is a helper class used by Mod_DB_NPCs. To generate NPCs from this factory
 * use the method in the database.
 */

public class Factory_NPC {
	private HashMap<String, Template_NPC> npcs;
	
	public Factory_NPC() {
		npcs = new HashMap<String, Template_NPC>();
		/*
		 * Haven West
		 */
		//Brian
		Template_NPC brian = new Template_NPC();
		brian.name = "Brian";
		brian.spriteSheet = "charSheet1";
		brian.message = new Message(brian.name, "Bring me 3 Good Minor Healing Potion and I'll give "
				+ "you a Rusty Waraxe");
		brian.mainHandWeapon = Items.Short_Sword_Poor;
		brian.quest = new QuestStart();
		brian.quest.type = QuestsType.Deliver;
		brian.quest.item = Items.Minor_Healing_Potion_Good;
		brian.quest.itemAmount = 3;
		brian.quest.reward = Items.WarAxe_Rusty;
		brian.quest.rewardAmount = 1;
		brian.wander = 5;
		npcs.put(brian.name, brian);
		
		//Peter
		Template_NPC peter = new Template_NPC();
		peter.name = "Peter";
		peter.spriteSheet = "charSheet1";
		peter.message = new Message(peter.name,"Explore the Town of Haven and come back");
		peter.mainHandWeapon = Items.Short_Sword_Rusty;
		peter.quest = new QuestStart();
		peter.quest.type = QuestsType.ExploreAndReturn;
		peter.quest.mapName = "Haven";
		peter.quest.reward = Items.Minor_Healing_Potion_Good;
		peter.quest.rewardAmount = 2;
		peter.wander = 1;
		npcs.put(peter.name, peter);
		
		//Lois
		Template_NPC lois = new Template_NPC();
		lois.name = "Lois";
		lois.spriteSheet = "charSheet1";
		lois.message = new Message(lois.name, "Go to " + peter.name + " and Give him this Light Armor, "
				+ "then come back and I'll give you a Sword");
		lois.quest = new QuestStart();
		lois.quest.type = QuestsType.CourierToAndReturn;
		lois.quest.to = peter.name;
		lois.quest.item = Items.ArmorLight_Leather;
		lois.quest.itemAmount = 1;
		lois.quest.reward = Items.Short_Sword_Poor;
		lois.quest.rewardAmount = 1;
		lois.wander = 2;
		npcs.put(lois.name, lois);
	}

	
	public Template_NPC getNPCTemplate(String name) {
		return npcs.get(name);
	}
}
