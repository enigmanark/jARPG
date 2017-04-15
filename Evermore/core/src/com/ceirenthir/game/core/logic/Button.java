package com.ceirenthir.game.core.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {
	private final static Vector2 pos = new Vector2();
	private final String text;
	private Rectangle rect = new Rectangle();
	
	public Button(String text, int width, int height) {
		rect.width = width;
		rect.height = height;
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void setPosition(float x, float y) {
		pos.x = x;
		pos.y = y;
		
		rect.x = x;
		rect.y = y;
	}
	
	public Vector2 getPosition() {
		return pos;
	}
	
	public Rectangle getRect() {
		return rect;
	}
}
