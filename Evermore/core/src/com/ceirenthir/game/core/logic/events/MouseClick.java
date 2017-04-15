package com.ceirenthir.game.core.logic.events;

public class MouseClick extends Event{
	public final int button;
	public final int x;
	public final int y;
	public final boolean shifted;
	public MouseClick(int button, int x, int y, boolean shifted) {
		super("MouseClick");
		this.button = button;
		this.x = x;
		this.y = y;
		this.shifted = shifted;
	}
}
