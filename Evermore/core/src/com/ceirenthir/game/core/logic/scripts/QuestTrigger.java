package com.ceirenthir.game.core.logic.scripts;

import gameObjects.GameObject;

import java.util.Iterator;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.QuestsType;
import com.ceirenthir.game.core.logic.events.QuestUpdate;

/*
 * Attach this script to an activatable, or a collidable to trigger a quest progress
 * update. This script is highly flexible. The different params you see
 * in the fields are optional. They're filled in via the maploader upon
 * examining the host's blueprint tile in Tiled.
 * 
 * Just use this script to notify a quest of well..whatever. Have the quest
 * loop for the event this script generates upon execution and just throw
 * in some data that needs to be passed along.
 */

public class QuestTrigger extends Script {
	private final GameObject host;
	public QuestsType type;
	public int param1;
	public int param2;
	public int param3;
	public String stringParam1;
	public String stringParam2;
	public String stringParam3;

	public QuestTrigger(GameObject host) {
		this.host = host;
	}

	@Override
	public void activate(Evermore game, Iterator<Script> scriptIter) {
		QuestUpdate update = new QuestUpdate(host);
		update.type = type;
		if(param1 != 0) update.param1 = param1;
		if(param2 != 0) update.param2 = param2;
		if(param3 != 0) update.param3 = param3;
		if(stringParam1 != null) update.stringParam1 = stringParam1;
		if(stringParam2 != null) update.stringParam2 = stringParam2;
		if(stringParam3 != null) update.stringParam3 = stringParam3;
		game.logicSys.post(update);

		//remove this script after it's done
		scriptIter.remove();
	}
	public void collide(Evermore game, Iterator<Script> scriptIter) {
		QuestUpdate update = new QuestUpdate(host);
		update.type = type;
		if(param1 != 0) update.param1 = param1;
		if(param2 != 0) update.param2 = param2;
		if(param3 != 0) update.param3 = param3;
		if(stringParam1 != null) update.stringParam1 = stringParam1;
		if(stringParam2 != null) update.stringParam2 = stringParam2;
		if(stringParam3 != null) update.stringParam3 = stringParam3;
		game.logicSys.post(update);

		//remove this script after it's done
		scriptIter.remove();
	}
}
