package com.ceirenthir.game.core.logic.events;

import com.ceirenthir.game.core.IF.Activatable;
import com.ceirenthir.game.core.IF.Collidable;
import com.ceirenthir.game.core.data.QuestsType;

import gameObjects.GameObject;

/*
 * Used to carry quest progress updates to the quest scripts.
 * Usually posted by QuestTriggerActivate or QuestTriggerCollide.
 */

public class QuestUpdate extends Event {
	public final GameObject host;
	public QuestsType type;
	public int param1;
	public int param2;
	public int param3;
	public String stringParam1;
	public String stringParam2;
	public String stringParam3;
	
	public QuestUpdate(GameObject host) {
		super("QuestUpdate");
		this.host = host;
	}
	
	public QuestUpdate(Activatable activatable) {
		super("QuestUpdate");
		this.host = (GameObject) activatable;
	}
	
	public QuestUpdate(Collidable collidable) {
		super("QuestUpdate");
		this.host = (GameObject) collidable;
	}
}
