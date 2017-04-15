package com.ceirenthir.game.core.logic.events;

import com.ceirenthir.game.core.data.Windows;

public class WindowOpen extends Event {
	public final Windows windowType;
	public WindowOpen(Windows type) {
		super("WindowOpen");
		windowType = type;
	}
}
