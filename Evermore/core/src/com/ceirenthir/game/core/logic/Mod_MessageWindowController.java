package com.ceirenthir.game.core.logic;

import com.badlogic.gdx.utils.TimeUtils;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.Windows;
import com.ceirenthir.game.core.logic.events.Activate;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.Message;
import com.ceirenthir.game.core.logic.events.WindowClose;
import com.ceirenthir.game.core.logic.events.WindowOpen;

public class Mod_MessageWindowController extends Mod_Logic {
	final Evermore game;
	private boolean open = false;
	private boolean available = true;
	private Message message;
	private long lastWindowOpenTime;
	
	public Message getMessage() {
		return message;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	protected Mod_MessageWindowController(Evermore game) {
		this.game = game;
	}
	
	protected void setMessage(Message message) {
		this.message = message;
	}
	
	@Override
	protected void post(Evermore game, Event e) {
		if(e instanceof Activate && open) {
			clearMessage();
			open = false;
			lastWindowOpenTime = TimeUtils.nanoTime();
			game.logicSys.post(new WindowClose(Windows.Message));
		}
		else if(e instanceof Message) {
			if(available) {
				this.message = (Message) e;
				open = true;
				available = false;
				game.logicSys.post(new WindowOpen(Windows.Message));
			}
		}
	}
	
	private void clearMessage() {
		this.message = null;
	}
	
	@Override
	protected void update() {
		if(!(available) && !(open)) {
			if(TimeUtils.nanoTime() - lastWindowOpenTime > (1000000000 / 3)) {
				available = true;
			}
		}
	}
}
