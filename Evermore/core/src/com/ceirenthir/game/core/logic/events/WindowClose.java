package com.ceirenthir.game.core.logic.events;

import com.ceirenthir.game.core.data.Windows;

public class WindowClose extends Event {
	public final Windows windowType;
	public WindowClose(Windows window) {
		super("WindowClose");
		windowType = window;
	}
}
