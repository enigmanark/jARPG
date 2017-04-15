package com.ceirenthir.game.core.data;

public class Factory_Weapons {
	
	//One-handed Swords
	public static Item makeShortSwordRusty(Mod_Assets assets) {
		Weapon rustySword = new Weapon(Items.Short_Sword_Rusty, "Rusty Short Sword",
				Items.Type_Weapon, EquipmentTypes.Main_Hand, "longSword");
		rustySword.setIcon(assets.getIcon("sword"));
		return unstackable(rustySword);
	}
	
	public static Item makeShortSwordPoor(Mod_Assets assets) {
		Weapon poorSword = new Weapon(Items.Short_Sword_Poor, "Poor Quality Short Sword",
				Items.Type_Weapon, EquipmentTypes.Main_Hand, "longSword");
		poorSword.setIcon(assets.getIcon("sword"));
		return unstackable(poorSword);
	}
	
	//One-handed Axes
	
	public static Item makeWarAxeRusty(Mod_Assets assets) {
		Item rustyAxe = new Item(Items.WarAxe_Rusty, "Rusty War-Axe", 
				Items.Type_Weapon);
		rustyAxe.setIcon(assets.getIcon("axe"));
		return unstackable(rustyAxe);
	}
	
	public static Item makeWarAxePoor(Mod_Assets assets) {
		Item poorAxe = new Item(Items.WarAxe_Poor, "Poor War-Axe", Items.Type_Weapon);
		poorAxe.setIcon(assets.getIcon("axe"));
		return unstackable(poorAxe);
	}
	
	//Helper method, put in an item to be unstackable and it returns it with
	//the proper settings
	private static Item unstackable(Item item) {
		item.setStackable(false);
		item.setStackAmount(1);
		item.add(1);
		return item;
	}
}
