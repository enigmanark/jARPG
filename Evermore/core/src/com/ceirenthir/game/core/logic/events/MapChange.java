package com.ceirenthir.game.core.logic.events;

public class MapChange extends Event{
	public final String name;
	
	public MapChange(String name) {
		super("MapChange");
		this.name = name;
	}
}
