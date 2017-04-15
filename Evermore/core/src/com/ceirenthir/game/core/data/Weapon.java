package com.ceirenthir.game.core.data;

import com.ceirenthir.game.core.IF.Equippable;

public class Weapon extends Item implements Equippable{
	private final EquipmentTypes equipType;
	private String weaponSheetName;
	public Weapon(Items item_id, String name, Items type, EquipmentTypes equipType, String weaponSheet) {
		super(item_id, name, type);
		this.weaponSheetName = weaponSheet;
		this.equipType = equipType;
	}

	public String getWeaponSheetName() {
		return weaponSheetName;
	}
	
	@Override
	public EquipmentTypes getEquipType() {
		return equipType;
	}

}
