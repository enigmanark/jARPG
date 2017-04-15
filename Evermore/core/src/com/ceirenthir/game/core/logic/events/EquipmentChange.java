package com.ceirenthir.game.core.logic.events;

import gameObjects.Actor;

import com.ceirenthir.game.core.data.EquipmentTypes;

public class EquipmentChange extends Event{
	public final EquipmentTypes type;
	public final Actor actor;
	public EquipmentChange(Actor actor, EquipmentTypes type) {
		super("EquipmentChange");
		this.type = type;
		this.actor = actor;
	}

}
