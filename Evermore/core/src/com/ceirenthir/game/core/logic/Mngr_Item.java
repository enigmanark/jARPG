package com.ceirenthir.game.core.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.Bag;
import com.ceirenthir.game.core.data.Item;
import com.ceirenthir.game.core.data.ItemFactory;
import com.ceirenthir.game.core.data.ItemSlot;
import com.ceirenthir.game.core.data.Items;
import com.ceirenthir.game.core.data.Mod_Assets;

/*
 * This class is a data manager, but this manager is attached to the player
 * object. It stores and managers the player's items.
 */

public class Mngr_Item {
	private Bag backPack;
	private Mod_Assets assets;

	public Bag getBackPack() {
		return backPack;
	}

	public Mngr_Item(Evermore game) {
		this.assets = game.dataSys.assets;
	}

	protected boolean setBackPack(Bag bag) {
		if(backPack == null) {
			backPack = bag;
			return true;
		}
		if(bag.size > backPack.size) {
			for(ItemSlot slot : backPack.getItemSlots()) {
				if(slot.item != null) {
					bag.getFreeSlot().item = slot.item;
				}
			}
			backPack = bag;
			return true;
		}
		else {
			return false;
		}
		
	}

	public Array<ItemSlot> getBackPackItemSlots() {
		return backPack.getItemSlots();
	}

	//Used to add items to the player's inventory
	//1st param is the enum item name, and the second is how many
	//you want to add. You add multiple unstackables as well, it will
	//automatically generate new items for each one
	public boolean addToBackPack(Items i, int amount) {
		ItemSlot itemSlot = this.getBackPackItemSlotWithSpace(i);
		if(itemSlot != null) {
			//If the item is stackable
			//we need to carefully add the items
			if(itemSlot.item.isStackable()) {
				if(addBackPackStackableAmount(itemSlot.item, amount)) return true;
				else return false;
			}
			//Not stackable, but we have an item already but more than one item to add
			else if(amount > 1) {
				if(backPack.hasFreeSlot(amount)) {
					for(int count = 0; count < amount; count++) {
						Item newItem = ItemFactory.makeItem(assets, i, 1);
						backPack.getFreeSlot().item = newItem;
						Gdx.app.debug("Item Manager", "Adding unstackable item multiple times " 
								+ i);
					}
					return true;
				}
				else return false;
			}
			//Not stackable, but only adding one item
			else {
				if(backPack.hasFreeSlot(amount)) {
					Gdx.app.debug("Item Manager", "Adding unstackable item " +
							i);
					Item newItem = ItemFactory.makeItem(assets, i, amount); 
					backPack.getFreeSlot().item = newItem;
					return true;
				} else return false;
			}
		}
		//Don't have an item already of it
		else {
			//Create a sample item to check against
			Item item = ItemFactory.makeItem(assets, i, 0);
			//If the item is stackable, add it
			if(item.isStackable()) {
				if(addBackPackStackableAmount(item, amount)) return true;
				else return false;
			}
			//Item is not stackable and adding more than one of it
			else if(amount > 1) {
				if(backPack.hasFreeSlot(amount)) {
					for(int c = 0; c < amount; c++) {
						item = ItemFactory.makeItem(assets, i, 1);
						backPack.getFreeSlot().item = item;
						Gdx.app.debug("Item Manager", "Adding unstackable item many times " +
								i);
					}
					return true;
				} else return false;
			}
			//Just add the item
			else {
				if(backPack.hasFreeSlot(amount)) {
					backPack.getFreeSlot().item = item;
					Gdx.app.debug("Item Manager", "Adding item " + i);
					return true;
				} else return false;
			}
		}
	}

	//Helper method for add to add stackAble items
	private boolean addBackPackStackableAmount(Item item, int amount) {
		//Get how many the item has
		int has = item.getAmount();
		//Get the max amount the item can hold in a stack
		int max = item.getStackAmount();
		//Adding stackable items but amount is greater than stacksize
		if((has + amount) > max) {
			if(backPack.hasFreeSlot(amount / max)) {	
				//Add the amount to the current item
				//get then back the amount left
				//System.out.println(amountToAdd);

				int amountToAdd;
				int currentAmount;

				amountToAdd = item.add(amount);

				if(backPack.hasFreeSlot(1))
					backPack.getFreeSlot().item = item;
				else return false;
				currentAmount = amountToAdd;
				//Create new items with the amount from currentAmount
				//then increase the itemCounter with the stackSize
				//each loop

				for(int itemCounter = 0; itemCounter < amountToAdd;
						itemCounter += max) {
					//System.out.println(itemCounter);
					//Create a new item with the currentAmount as the amount
					Item newItem = ItemFactory.makeItem(assets, 
							item.item_id, currentAmount);
					//Subtract from the currentAmount the total stacksize
					currentAmount -= max;
					//Add this item
					backPack.getFreeSlot().item = newItem;
					Gdx.app.debug("Item Manager", "Adding stackable amount item many times "
							+ item.item_id);
				}
				return true;
			} else return false;
		}
		//Just add the amount to the item
		else {
			item.add(amount);
			//Check if the items id is 0, if it is, that means this item
			//is not being held by the player, so add it
			backPack.getFreeSlot().item = item;
			Gdx.app.debug("Item Manager", "Adding stackable amount " + amount 
					+ " " + item.item_id);
			return true;
		}
	}

	//Counts how many of the item the player has in the backpack
	//then returns that much, returns 0 if none found
	public int getItemAmountInBackPack(Items name) {
		int amount = 0;
		for(ItemSlot slot : backPack.getItemSlots()) {
			if(slot.item != null) {
				if(slot.item.item_id == name) {
					amount += slot.item.getAmount();
				}
			}
		}
		return amount;
	}

	//Returns an itemSlot that contains an item of the matching item_id 
	//that also has space left for adding more items
	public ItemSlot getBackPackItemSlotWithSpace(Items name) {
		for(ItemSlot slot : backPack.getItemSlots()) {
			if(slot.item != null) {
				if(slot.item.item_id == name) {
					if(slot.item.getAmount() != slot.item.getStackAmount()) {
						return slot;
					}
				}
			}
		}
		return null;
	}

	//Returns the first occurence of an item slot matching the item_id
	public ItemSlot getBackPackItemSlot(Items item_id) {
		for(ItemSlot slot : backPack.getItemSlots()) {
			if(slot.item != null) {
				if(slot.item.item_id == item_id) {
					return slot;
				}
			}
		}
		return null;
	}

	//Remove first occurance of item by item_id by set amount
	//Will keep removing from other stackSizes until the required
	//amount is gone
	public void removeItemBackPack(Items name, int amount) {
		if(this.getItemAmountInBackPack(name) >= amount) {
			int totalAmountToRemove = amount;
			int amountToRemove = 0;
			int amountRemoved = 0;
			for(int i = 0; i < backPack.rows; i++){
				for(int j = 0; j < backPack.columns; j++) {
					if(backPack.slot[i][j].item != null) {
						Item item = backPack.slot[i][j].item;
						if(item.item_id == name) {
							//Get how many the item has
							int itemsAmount = item.getAmount();
							//If the item has less than the amount we need to remove
							//then remove all of the items amount
							if(itemsAmount <= totalAmountToRemove) amountToRemove = itemsAmount;
							//If the item has more than we need to remove then
							else amountToRemove = (totalAmountToRemove);
							if(item.remove(amountToRemove)) {
								//Successfully removed the amount
								//from that item
								amountRemoved += amountToRemove;
								totalAmountToRemove -= amountToRemove;
								//If the item is now empty then remove it
								if(item.getAmount() == 0) {
									backPack.slot[i][j].item = null;
								}
								//If we've removed the correct amount
								//then return
								if(amountRemoved == amount) {
									return;
								}
							}
						}
					}
				}
			}
		}
		else Gdx.app.error("Item Manager", "Tried to remove more " + name +
				" than the player had.");
	}
}
