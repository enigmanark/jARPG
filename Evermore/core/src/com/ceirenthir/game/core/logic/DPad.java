package com.ceirenthir.game.core.logic;

import com.badlogic.gdx.math.Rectangle;

public class DPad {
	//Android
	public final static int LEFT = 0;
	public final static int UP = 1;
	public final static int RIGHT = 2;
	public final static int DOWN = 3;
	public final static int ACT = 4;
	
	private Rectangle[] dpad = new Rectangle[5];
	
	private final static int leftX = 0;
	private final static int leftY = 42;
	private final static int upX = 47;
	private final static int upY = 120;
	private final static int rightX = 130;
	private final static int rightY = 42;
	private final static int downX = 47;
	private final static int downY = 0;
	
	private final static int actX = 65;
	private final static int actY = 60;

	private final static int horWidth = 40;
	private final static int horHeight = 70;
	private final static int verWidth = 70;
	private final static int verHeight = 40;
	
	private final static int actWidth = 40;
	private final static int actHeight = 40;
	
	public DPad() {
		dpad[LEFT] = new Rectangle(leftX, leftY, horWidth, horHeight);
		dpad[UP] = new Rectangle(upX, upY, verWidth, verHeight);
		dpad[RIGHT] = new Rectangle(rightX, rightY, horWidth, horHeight);
		dpad[DOWN] = new Rectangle(downX, downY, verWidth, verHeight);
		dpad[ACT] = new Rectangle(actX, actY, actWidth, actHeight);
	}
	
	public Rectangle[] getRects() {
		return dpad;
	}
	
	public void updateRects(int x, int y) {
		dpad[LEFT].x = x + leftX;
		dpad[LEFT].y = y + leftY;
		
		dpad[RIGHT].x = x + rightX;
		dpad[RIGHT].y = y + rightY;
		
		dpad[DOWN].x = x + downX;
		dpad[DOWN].y = y + downY;
		
		dpad[UP].x = x + upX;
		dpad[UP].y = y + upY;
		
		dpad[ACT].x = x + actX;
		dpad[ACT].y = y + actY;
	}
	
}
