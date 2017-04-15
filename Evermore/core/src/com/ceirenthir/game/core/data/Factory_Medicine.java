package com.ceirenthir.game.core.data;

public class Factory_Medicine {
	public static final int POTION_STACK_AMOUNT = 16;
	
	//Healing Potions
	public static Item makeMinorPotionPoor(Mod_Assets assets, int amount) {
		Item potion = new Item(Items.Minor_Healing_Potion_Poor, "Poor Minor Healing Potion"
				,Items.Type_Medicine);
		potion.setIcon(assets.getIcon("key"));
		return stackable(potion, amount);
	}
	
	public static Item makeMinorPotion(Mod_Assets assets, int amount) {
		Item potion = new Item(Items.Minor_Healing_Potion, "Minor Healing Potion"
				,Items.Type_Medicine);
		potion.setIcon(assets.getIcon("key"));
		return stackable(potion, amount);
	}
	
	public static Item makeMinorPotionGood(Mod_Assets assets, int amount) {
		Item potion = new Item(Items.Minor_Healing_Potion_Good, "Good Minor Healing Potion"
				,Items.Type_Medicine);
		potion.setIcon(assets.getIcon("key"));
		return stackable(potion, amount);
	}
	
	//Helper method
	private static Item stackable(Item item, int amount) {
		item.setStackable(true);
		item.setStackAmount(Factory_Medicine.POTION_STACK_AMOUNT);
		item.add(amount);
		return item;
	}
}
