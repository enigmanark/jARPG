package com.ceirenthir.game.core.logic.events;

public class TouchHold extends Event{
	public final int x;
	public final int y;
	public TouchHold(int x, int y) {
		super("TouchHold");
		this.x = x;
		this.y = y;
	}
}