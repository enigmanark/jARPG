package com.ceirenthir.game.core.data;

import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.graphics.GraphicsSystem;

public class Equipment {
	public final static int leftPadding = 10;
	public final static int bottomPadding = 8;
	public final static int windowWidth = 220;
	public final static int windowHeight = 400;
	private Rectangle windowRect;
	private int x = 0;
	private int y = 0;
	
	private ItemSlot[] slots = new ItemSlot[16];
	
	public Equipment() {
		slots [0] = new ItemSlot();
		slots [1] = new ItemSlot();
		slots [2] = new ItemSlot();
		slots [3] = new ItemSlot();
		slots [4] = new ItemSlot();
		slots [5] = new ItemSlot();
		slots [6] = new ItemSlot();
		slots [7] = new ItemSlot();
		slots [8] = new ItemSlot();
		slots [9] = new ItemSlot();
		slots [10] = new ItemSlot();
		slots [11] = new ItemSlot();
		slots [12] = new ItemSlot();
		slots [13] = new ItemSlot();
		slots [14] = new ItemSlot();
		slots [15] = new ItemSlot();
		
		for(ItemSlot slot : slots) {
			slot.rect = new Rectangle();
			slot.rect.width = GraphicsSystem.iconSize + 4;
			slot.rect.height = GraphicsSystem.iconSize + 4;
		}
		
		windowRect = new Rectangle();
	}
	
	public Rectangle getWindowRect() {
		return windowRect;
	}
	
	public void updateRects() {
		windowRect.width = windowWidth;
		windowRect.height = windowHeight;
		
		slots[0].rect.x = (this.x + (windowWidth / 2) ) - (GraphicsSystem.iconSize + leftPadding); //Main-Hand
		slots[1].rect.x = (this.x + (windowWidth / 2) ) + leftPadding; //Off-Hand
		
		slots[2].rect.x = (this.x + windowWidth) - (GraphicsSystem.iconSize + leftPadding); //Trinket
		slots[3].rect.x = (this.x + windowWidth) - (GraphicsSystem.iconSize + leftPadding); //Necklace
		slots[4].rect.x = (this.x + windowWidth) - (GraphicsSystem.iconSize + leftPadding); //Back
		slots[5].rect.x = (this.x + windowWidth) - (GraphicsSystem.iconSize + leftPadding); //LeftEar
		slots[6].rect.x = (this.x + windowWidth) - (GraphicsSystem.iconSize + leftPadding); // RightEar
		slots[7].rect.x = (this.x + windowWidth) - (GraphicsSystem.iconSize + leftPadding); //LeftRing
		slots[8].rect.x = (this.x + windowWidth) - (GraphicsSystem.iconSize + leftPadding); //RightRing
		
		slots[9].rect.x = this.x + leftPadding; //Head
		slots[10].rect.x = this.x + leftPadding; //Shoulders
		slots[11].rect.x = this.x + leftPadding; //Body
		slots[12].rect.x = this.x + leftPadding; //Hands
		slots[13].rect.x = this.x + leftPadding; //Waist
		slots[14].rect.x = this.x + leftPadding; //Legs
		slots[15].rect.x = this.x + leftPadding; //Feet		
		
		//------------------------------------------------------------------------------------------
		
		slots[0].rect.y = this.y + bottomPadding; //Main-hand
		slots[1].rect.y = this.y + bottomPadding; //Off-hand
		
		slots[2].rect.y = (this.y + (GraphicsSystem.iconSize * 7)) + bottomPadding * 8; //Trinket		
		slots[3].rect.y = (this.y + (GraphicsSystem.iconSize * 6)) + bottomPadding * 7; //Necklace
		slots[4].rect.y = (this.y + (GraphicsSystem.iconSize * 5)) + bottomPadding * 6; //Back
		slots[5].rect.y = (this.y + (GraphicsSystem.iconSize * 4)) + bottomPadding * 5; //LeftEar
		slots[6].rect.y = (this.y + (GraphicsSystem.iconSize * 3)) + bottomPadding * 4; //RightEar
		slots[7].rect.y = (this.y + (GraphicsSystem.iconSize * 2)) + bottomPadding * 3; //LeftRing
		slots[8].rect.y = (this.y + (GraphicsSystem.iconSize)) + bottomPadding * 2; //RightRing
		
		slots[9].rect.y = (this.y + (GraphicsSystem.iconSize * 7)) + bottomPadding * 8; //Head
		slots[10].rect.y = (this.y + (GraphicsSystem.iconSize * 6)) + bottomPadding * 7; //Shoulders
		slots[11].rect.y = (this.y + (GraphicsSystem.iconSize * 5)) + bottomPadding * 6; //Body
		slots[12].rect.y = (this.y + (GraphicsSystem.iconSize * 4)) + bottomPadding * 5; //Hands
		slots[13].rect.y = (this.y + (GraphicsSystem.iconSize * 3)) + bottomPadding * 4; //Waist
		slots[14].rect.y = (this.y + (GraphicsSystem.iconSize * 2)) + bottomPadding * 3; //Legs
		slots[15].rect.y = (this.y + (GraphicsSystem.iconSize)) + bottomPadding * 2; //Feet
	}
	
	public void setX(int x) {
		this.x = x;
		windowRect.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
		windowRect.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ItemSlot[] getEquipSlots() {
		return slots;
	}
	
}
