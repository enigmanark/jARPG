package com.ceirenthir.game.core.logic.events;

import gameObjects.GameObject;

public class ActivatedObject extends Event {
	public final GameObject object;
	public ActivatedObject(GameObject object) {
		super("ActivatedObject");
		this.object = object;
	}
}
