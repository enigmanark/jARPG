package com.ceirenthir.game.core.data;

public class Factory_Armor {
	
	public static Item makeLeatherArmor(Mod_Assets assets) {
		Item armor = new Item(Items.ArmorLight_Leather, "Leather Armor", Items.Type_Armor);
		armor.setIcon(assets.getIcon("branch"));
		return unstackable(armor);
	}
	
	public static Item makeLeatherArmorPoor(Mod_Assets assets) {
		Item armor = new Item(Items.ArmorLight_LeatherPoor, "Poor Leather Armor",
				Items.Type_Armor);
		armor.setIcon(assets.getIcon("branch"));
		return unstackable(armor);
	}
	
	public static Item makeLeatherArmorGood(Mod_Assets assets) {
		Item armor = new Item(Items.ArmorLight_LeatherGood, "Good Leather Armor",
				Items.Type_Armor);
		armor.setIcon(assets.getIcon("branch"));
		return unstackable(armor);
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
