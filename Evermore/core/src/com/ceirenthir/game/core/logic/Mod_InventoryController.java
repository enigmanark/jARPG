package com.ceirenthir.game.core.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.Bag;
import com.ceirenthir.game.core.data.Inventory;
import com.ceirenthir.game.core.data.Item;
import com.ceirenthir.game.core.data.ItemFactory;
import com.ceirenthir.game.core.data.Windows;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.MouseClick;
import com.ceirenthir.game.core.logic.events.WindowClose;
import com.ceirenthir.game.core.logic.events.WindowOpen;

public class Mod_InventoryController extends Mod_Logic {
	private final Evermore game;
	private boolean backPackOpen = false;
	private Inventory backPack;
	
	@Override
	protected void post(Evermore game, Event e) {
		if(backPackOpen) {
			if(e instanceof MouseClick) {
				MouseClick click = (MouseClick) e;
				//Loop through itemSlots and see if mouseClicked in one
				for(int r = 0; r < backPack.getBag().rows; r++) {
					for(int c = 0; c < backPack.getBag().columns; c++) {
						//If clicked in this slot
						if(backPack.getBag().getFullBag()[r][c].rect.contains(click.x, click.y)) {
							//If the button was the left mouse button
							if(click.button == 0) {
								//If we don't have an item picked up
								if(game.logicSys.getItemPickUpController().pickedUp == null) {
									//See if the slot has an item..if so
									if(backPack.getBag().getFullBag()[r][c].item != null) {
										//Pick up the item
										game.logicSys.getItemPickUpController().pickedUp = 
												backPack.getBag().getFullBag()[r][c].item;
										game.logicSys.getItemPickUpController().type = 0;
										game.logicSys.getItemPickUpController().place = new Vector2(r, c);
										backPack.getBag().getFullBag()[r][c].item = null;
									}
									else return;
								}
								//We do have an item picked up
								else {
									//If there's an item here try to combine them
									if(backPack.getBag().getFullBag()[r][c].item != null) {
										//If they're the same item and stackable
										if(game.logicSys.getItemPickUpController().pickedUp.item_id == 
												backPack.getBag().getFullBag()[r][c].item.item_id
												&& game.logicSys.getItemPickUpController().pickedUp.isStackable()) {
											int pickedUpAmount = game.logicSys.getItemPickUpController()
													.pickedUp.getAmount();
											game.logicSys.getItemPickUpController().pickedUp.setAmount
											(backPack.getBag().getFullBag()[r][c].item.add(pickedUpAmount));
											if(game.logicSys.getItemPickUpController().pickedUp.getAmount() == 0) {
												game.logicSys.getItemPickUpController().pickedUp = null;
											}
											return;
										}
										//They're not the same item or not stackable,
										//switch them
										else{
											Item temp = game.logicSys.getItemPickUpController().pickedUp;
											game.logicSys.getItemPickUpController().pickedUp = 
													backPack.getBag().getFullBag()[r][c].item;
											backPack.getBag().getFullBag()[r][c].item = temp;
											return;
										}
									}
									//No item here in the slot
									else {
										//If shift is held down, then split the items
										if(click.shifted) {
											if(game.logicSys.getItemPickUpController().pickedUp.getAmount() > 1) {
												int amountToSet = game.logicSys.getItemPickUpController()
														.pickedUp.getAmount() / 2;
												Item newItem = ItemFactory.makeItem(
														game.dataSys.assets,
														game.logicSys.getItemPickUpController().pickedUp.item_id,
														amountToSet);
												backPack.getBag().getFullBag()[r][c].item = newItem;
												game.logicSys.getItemPickUpController().pickedUp.remove(amountToSet);
											}

										}
										//Not shifted so place the item in the slot
										else {
											backPack.getBag().getFullBag()[r][c].item = 
													game.logicSys.getItemPickUpController().pickedUp;
											game.logicSys.getItemPickUpController().pickedUp = null;
											return;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public Mod_InventoryController(Evermore game) {
		this.game = game;
	}

	public Inventory getBackPack() {
		return backPack;
	}

	public boolean setBackPack(Bag bag) {
		if(game.dataSys.player.itemMngr.setBackPack(bag)) {
			backPack = new Inventory(bag);
			return true;
		} else {
			Gdx.app.debug("Inventory Controller", "Bag size is not large enough");
			return false;
		}
	}

	public void openBackPack() {
		if(!backPackOpen) {
			backPackOpen = true;
			game.logicSys.post(new WindowOpen(Windows.Inventory));
		}
	}

	public void closeBackPack() {
		if(backPackOpen) {
			backPackOpen = false;
			game.logicSys.post(new WindowClose(Windows.Inventory));
		}
	}

	public boolean isBackPackOpen() {
		return backPackOpen;
	}

}
