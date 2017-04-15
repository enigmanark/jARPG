package com.ceirenthir.game.core.logic;

import com.badlogic.gdx.utils.TimeUtils;

public class Mod_GameClock extends Mod_Logic {
	private Clock clock;
	protected boolean draw = false;
	
	public boolean isDrawing() {
		return draw;
	}
	
	protected Mod_GameClock() {
		clock = new Clock();
	}
	
	public void init() {
		draw = true;
	}
	
	public int getGameSeconds() {
		return clock.gameSeconds;
	}
	
	@Override
	public void update() {
		clock.update();	
	}
	
	private class Clock {
		public int gameSeconds = 0;
		
		private long lastNanoTime;
		
		public Clock() {
			lastNanoTime = TimeUtils.nanoTime();
		}
		
		public void update() {
			if(TimeUtils.nanoTime() - lastNanoTime > 1000000000) {
				gameSeconds++;
				lastNanoTime = TimeUtils.nanoTime();
			}
			
		}
		
	}
}
