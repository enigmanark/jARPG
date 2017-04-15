package com.ceirenthir.game.core.logic.events;

public class Event {
	public final String name;
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Event(String name) {
		this.name = name;
	}
}
