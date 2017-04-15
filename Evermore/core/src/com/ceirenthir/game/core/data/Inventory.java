package com.ceirenthir.game.core.data;

import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.graphics.GraphicsSystem;

public class Inventory {
	public final static int topPadding = 5;
	public final static int leftPadding = 5;
	public final static int rightPadding = 5;
	public final static int bottomPadding = 5;
	public final static int iconPadding = 2;
	private Rectangle inventoryRect;
	private Bag bag;
	private int x;
	private int y;
	
	public Rectangle getInventoryRect() {
		return inventoryRect;
	}
	
	public Inventory(Bag bag) {
		this.bag = bag;

	}
	
	public void updateRects() {
		inventoryRect = new Rectangle();
		
		inventoryRect.x = x;
		inventoryRect.y = y;
		
		inventoryRect.width = ( (GraphicsSystem.iconSize + Inventory.iconPadding) *
				bag.rows ) + ((leftPadding * 4) + (rightPadding * 2));
		inventoryRect.height = ((GraphicsSystem.iconSize + Inventory.iconPadding) *
				bag.columns) + ((topPadding * 2) + (bottomPadding * 4));
		

		for(int i = 0; i < bag.rows; i++ ) {
			for(int j = 0; j < bag.columns; j++) {
				
				Rectangle slotRect = new Rectangle();
				slotRect.width = GraphicsSystem.iconSize;
				slotRect.height = GraphicsSystem.iconSize;
				
				slotRect.x = (inventoryRect.x + (leftPadding * 2)) + 
						(i * (GraphicsSystem.iconSize + leftPadding));
				slotRect.y = inventoryRect.y + (topPadding * 2) + 
						(j * (GraphicsSystem.iconSize + bottomPadding));
				
				bag.slot[i][j].rect = slotRect;
			}
		}
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Bag getBag() {
		return bag;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
