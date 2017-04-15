package com.ceirenthir.game.core.data;

import gameObjects.Actor_NPC;

import com.badlogic.gdx.utils.Array;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.logic.scripts.MessageScript;
import com.ceirenthir.game.core.logic.scripts.Wander;

/*
 * Will eventually used by global systems that modify the world. Generally NPCs are accessed directly from
 * the map, but, global systems that alter the entire world will use the database instead.
 * 
 * Also the database is used to generate NPCs from templates as well that are defined in Factory_NPC.
 */

public class Mod_DB_NPCs {
	private final Evermore game;
	Factory_NPC npcFactory = new Factory_NPC();
	
	private Array<Actor_NPC> npcs;
	
	public Mod_DB_NPCs(Evermore game) {
		npcs = new Array<Actor_NPC>();
		this.game = game;
	}
	
	public void addNPC(Actor_NPC npc) {
		npcs.add(npc);
	}
	
	public Actor_NPC generateNPCByName(String name) {
		Template_NPC t = npcFactory.getNPCTemplate(name);
		Actor_NPC npc = new Actor_NPC();
		npc.setName(name);
		npc.setSpriteSheetName(t.spriteSheet);
		if(t.mainHandWeapon != null) {
			npc.setMainHand((Weapon)ItemFactory.makeItem(game.dataSys.assets, t.mainHandWeapon, 1)
					, game.dataSys.assets);
		}
		if(t.wander != 0) {
			npc.addScript(new Wander(npc, t.wander));
		}
		if(t.quest != null) {
			t.quest.questGiver = npc;
			npc.addScript(t.quest);
		}
		if(t.message != null) {
			npc.addScript(new MessageScript(npc, t.message));
		}
		return npc;
	}
	
	public Actor_NPC getNPCByName(String name) {
		for(Actor_NPC npc : npcs) {
			if(npc.getName().equalsIgnoreCase(name)) {
				return npc;
			}
		}
		return null;
	}
	
	public boolean npcExistsByName(String name) {
		for(Actor_NPC npc : npcs) {
			if(npc.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
}
