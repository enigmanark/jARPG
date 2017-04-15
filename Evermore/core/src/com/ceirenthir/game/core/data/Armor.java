package com.ceirenthir.game.core.data;

import com.ceirenthir.game.core.IF.Equippable;

public class Armor extends Item implements Equippable{
	private final EquipmentTypes equipType;
	
	public Armor(Items item_id, String name, Items type, EquipmentTypes equipType) {
		super(item_id, name, type);
		this.equipType = equipType;
	}

	@Override
	public EquipmentTypes getEquipType() {
		return equipType;
	}

}
