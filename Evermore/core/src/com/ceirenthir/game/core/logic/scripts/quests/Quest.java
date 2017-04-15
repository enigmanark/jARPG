package com.ceirenthir.game.core.logic.scripts.quests;

/*
 * Base Class for all Quests.
 * 
 * To create a new quest, either use QuestStart to start the quest from a mapobject in tiled
 * or use Mod_DB_Quests to generate the quest directly from code
 */

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.QuestsType;
import com.ceirenthir.game.core.logic.events.Event;

public class Quest {
	public final QuestsType type;
	public final int instanceID;
	protected boolean complete = false;
	

	public Quest(int id, QuestsType q) {
		this.instanceID = id;
		this.type = q;
		
	}
	
	//Override for quests to receive posts from the logicsystem
	public void post(Evermore game, Event e) {

	}
	
	//Method so quests can post TO the Logicsystem
	protected void postToLogic(Evermore game, Event e) {
		game.logicSys.post(e);
	}
	
	//Quests override this method, NOT update(Evermore game, Iterator<Script> from Script
	public void update(Evermore game) {

	}
	
	public boolean isComplete() {
		return complete;
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof Quest) {
			Quest quest = (Quest) object;
			if(quest.instanceID == this.instanceID) return true;
			if(quest == this) return true;
		}
		return false;
	}
}
