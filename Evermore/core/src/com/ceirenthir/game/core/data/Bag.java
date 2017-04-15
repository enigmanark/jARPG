package com.ceirenthir.game.core.data;

import com.badlogic.gdx.utils.Array;

public class Bag {
	public final int size;
	public final int rows;
	public final int columns;
	public final ItemSlot[][] slot;
	
	public Bag(int rows, int columns) {
		slot = new ItemSlot[rows][columns];
		this.rows = rows;
		this.columns = columns;
		size = rows * columns;
		
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				slot[r][c] = new ItemSlot();
			}
		}
	}
	
	public ItemSlot[][] getFullBag() {
		return slot;
	}
	
	public Array<ItemSlot> getItemSlots() {
		Array<ItemSlot> items = new Array<ItemSlot>();
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				items.add(slot[r][c]);
			}
		}
		return items;
	}
	
	public boolean hasFreeSlot(int amount) {
		int free = 0;
		for(ItemSlot slot : getItemSlots()) {
			if(slot.item == null)
				free++;
		}
		if(free >= amount) return true;
		else return false;
	}
	
	public ItemSlot getFreeSlot() {
		for(ItemSlot slot : getItemSlots()) {
			if(slot.item == null)
				return slot;
		}
		return null;
	}
}
