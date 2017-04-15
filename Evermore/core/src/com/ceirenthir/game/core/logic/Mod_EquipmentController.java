package com.ceirenthir.game.core.logic;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.Armor;
import com.ceirenthir.game.core.data.Equipment;
import com.ceirenthir.game.core.data.EquipmentTypes;
import com.ceirenthir.game.core.data.Item;
import com.ceirenthir.game.core.data.ItemFactory;
import com.ceirenthir.game.core.data.ItemSlot;
import com.ceirenthir.game.core.data.Weapon;
import com.ceirenthir.game.core.data.Windows;
import com.ceirenthir.game.core.logic.events.EquipmentChange;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.MouseClick;
import com.ceirenthir.game.core.logic.events.WindowClose;
import com.ceirenthir.game.core.logic.events.WindowOpen;


public class Mod_EquipmentController extends Mod_Logic {
	private final Evermore game;
	private boolean equipOpen = false;
	private Equipment equipWindow;
	
	public Mod_EquipmentController(Evermore game) {
		this.game = game;
		equipWindow = new Equipment();
	}
	
	public void closeEquipment() {
		if(equipOpen) {
			equipOpen = false;
			game.logicSys.post(new WindowClose(Windows.Equip));
		}
	}
	
	public Equipment getEquipment() {
		return equipWindow;
	}
	
	public boolean isOpen() {
		return equipOpen;
	}
	
	public void openEquipmentWindow() {
		if(!this.equipOpen) {
			equipOpen = true;
			game.logicSys.post(new WindowOpen(Windows.Equip));
		}
	}
	
	public Weapon getMainHand() {
		return (Weapon)equipWindow.getEquipSlots()[0].item;
	}
	
	public Armor getOffHand() {
		return (Armor)equipWindow.getEquipSlots()[1].item;
	}
	
	public Armor getBack() {
		return (Armor)equipWindow.getEquipSlots()[4].item;
	}
	
	public Armor getHead() {
		return (Armor)equipWindow.getEquipSlots()[9].item;
	}
	
	public Armor getShoulders() {
		return (Armor)equipWindow.getEquipSlots()[10].item;
	}
	
	public Armor getBody() {
		return (Armor)equipWindow.getEquipSlots()[11].item;
	}
	
	public Armor getHands() {
		return (Armor)equipWindow.getEquipSlots()[12].item;
	}
	
	public Armor getWaist() {
		return (Armor)equipWindow.getEquipSlots()[13].item;
	}
	
	public Armor getLegs() {
		return (Armor)equipWindow.getEquipSlots()[14].item;
	}
	
	public Armor getFeet() {
		return (Armor)equipWindow.getEquipSlots()[15].item;
	}
	
	private void postEquipChangeEvent(Evermore game, int index) {
		switch(index) {	
		//Main_Hand
		case 0:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Main_Hand));
			break;
		//Off_Hand
		case 1:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Off_Hand));
			break;
		//Trinket
		case 2:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Trinket));
			break;
		//Necklace
		case 3:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Necklace));
			break;
		//Back
		case 4:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Back));
			break;
		//LeftEar
		case 5:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.LeftEar));
			break;
		//RightEar
		case 6:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.RightEar));
			break;
		//LeftRing
		case 7:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.LeftRing));
			break;
		//RightRing
		case 8:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.RightRing));
			break;
		//Head
		case 9:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Head));
			break;
		//Shoulders
		case 10:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Shoulders));
			break;
		//Body
		case 11:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Body));
			break;
		//Hands
		case 12:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Hands));
			break;
		//Waist
		case 13:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Waist));
			break;
		//Legs
		case 14:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Legs));
			break;
		//Feet
		case 15:
			game.logicSys.post(new EquipmentChange(game.dataSys.player, EquipmentTypes.Feet));
			break;
		}
	}
	
	@Override
	protected void post(Evermore game, Event e) {
		if(equipOpen) {
			if(e instanceof MouseClick) {
				MouseClick click = (MouseClick) e;
				//Loop through itemSlots and see if mouseClicked in one
				int c = -1;
				for(ItemSlot slot : equipWindow.getEquipSlots()) {
					c++;
					//If clicked in this slot
					if(slot.rect.contains(click.x, click.y)) {
						//If the button was the left mouse button
						if(click.button == 0) {
							//If we don't have an item picked up
							if(game.logicSys.getItemPickUpController().pickedUp == null) {
								//See if the slot has an item..if so
								if(slot.item != null) {
									//Pick up the item
									game.logicSys.getItemPickUpController().pickedUp = slot.item;
									game.logicSys.getItemPickUpController().type = 1;
									game.logicSys.getItemPickUpController().index = c;
									postEquipChangeEvent(game, c);
									slot.item = null;
									
								}
								else return;
							}
							//We do have an item picked up
							else {
								//If there's an item here try to combine them
								if(slot.item != null) {
									//If they're the same item and stackable
									if(game.logicSys.getItemPickUpController().pickedUp.item_id == 
											slot.item.item_id
											&& game.logicSys.getItemPickUpController().pickedUp.isStackable()) {
										int pickedUpAmount = game.logicSys.getItemPickUpController()
												.pickedUp.getAmount();
										game.logicSys.getItemPickUpController().pickedUp.setAmount
										(slot.item.add(pickedUpAmount));
										if(game.logicSys.getItemPickUpController().pickedUp.getAmount() == 0) {
											game.logicSys.getItemPickUpController().pickedUp = null;
										}
										return;
									}
									//They're not the same item or not stackable,
									//switch them
									else{
										Item temp = game.logicSys.getItemPickUpController().pickedUp;
										game.logicSys.getItemPickUpController().pickedUp = slot.item;
										game.logicSys.getItemPickUpController().index = c;
										game.logicSys.getItemPickUpController().type = 1;
										slot.item = temp;
										postEquipChangeEvent(game, c);
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
											slot.item = newItem;
											game.logicSys.getItemPickUpController().pickedUp.remove(amountToSet);
										}

									}
									//Not shifted so place the item in the slot
									else {
										slot.item = game.logicSys.getItemPickUpController().pickedUp;
										game.logicSys.getItemPickUpController().pickedUp = null;
										postEquipChangeEvent(game, c);
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

