package com.ceirenthir.game.core.logic.events;

import gameObjects.GameObject;

public class CollidedObject extends Event{
	public final GameObject object;
	
	public CollidedObject(GameObject object) {
		super("CollidedObject");
		this.object = object;
	}
}
