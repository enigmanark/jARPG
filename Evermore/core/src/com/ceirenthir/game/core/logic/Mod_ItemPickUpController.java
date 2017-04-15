package com.ceirenthir.game.core.logic;

import com.badlogic.gdx.math.Vector2;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.Item;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.WindowClose;

public class Mod_ItemPickUpController extends Mod_Logic {
	public int index; //Used for placing back in equipment window
	public int type; //used to determine where it came from, bag or equipment. Bag = 0, Equip = 1
	public Vector2 place; //Used for placing back in bag
	public Item pickedUp; 
	
	@Override
	public void update() {
		
	}
	
	//TODO If we ever have dissappearing items in inventory check here
	@Override
	public void post(Evermore game, Event e) {
		if(pickedUp != null) {
			if(e instanceof WindowClose) {
				if(type == 0) {
					game.logicSys.getInventoryController().getBackPack().getBag().getFullBag()
					[(int)place.x][(int)place.y].item = pickedUp;
					pickedUp = null;
				}
				else if(type == 1) {
					game.logicSys.getEquipmentController().getEquipment().getEquipSlots()[index].item = pickedUp;
					pickedUp = null;
				}
			}
		}
	}
}
