package com.ceirenthir.game.core.data;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Item {
	public final String name;
	public final Items item_id;
	private boolean stackable;
	private int stackAmount;
	private int amount;
	public final Items type;
	private AtlasRegion icon;
	
	public Item(Items item_id, String name, Items type) {
		this.name = name;
		this.item_id = item_id;
		this.type = type;
		this.amount = 0;
	}
	
	public void setIcon(AtlasRegion icon) {
		this.icon = icon;
	}
	
	public AtlasRegion getIcon() {
		return icon;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public boolean isStackable() {
		return stackable;
	}
	
	public int getStackAmount() {
		return stackAmount;
	}
	
	public void setStackable(boolean is) {
		this.stackable = is;
	}
	
	public void setStackAmount(int amount) {
		this.stackAmount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int add(int amount) {
		if(this.amount + amount > stackAmount) {
			//Get how much to add to reach stackAmount
			int addTo = this.amount - stackAmount;
			//add it
			this.amount -= addTo;
			//Now subtract how much we added from the amount left and return it
			return (amount + addTo);
			
		}
		else {
			this.amount += amount;
			return 0;
		}
	}
	
	//returns true if the amount was removed
	//returns false if the amount could not be removed because
	//there wasn't enough of the item
	public boolean remove(int amount) {
		//remove it normally
		if(amount >= 0) {
			if((this.amount - amount) >= 0) {
				this.amount -= amount;
				return true;
			} else return false;
		}
		//If we had to do fancy maths, then the amount is a negative number
		//but we still want to remove that amount, so we need to add the number
		//instead
		else {
			if((this.amount + amount) >= 0) {
				this.amount += amount;
				return true;
			} else return false;	
		}
	}
	
	public Items getType() {
		return type;
	}
	
	public boolean equals(Item item) {
		if(item.item_id == this.item_id) return true;
		else return false;
	}
}
