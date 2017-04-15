package com.ceirenthir.game.core.data;

public class ItemFactory {

	public static Item makeItem(Mod_Assets assets, Items item, int amount) {
		Item madeItem = checkMedicine(assets, item, amount);
		if (madeItem != null)
			return madeItem;

		madeItem = checkWeapons(assets, item, amount);
		if (madeItem != null)
			return madeItem;

		madeItem = checkArmor(assets, item, amount);
		if (madeItem != null)
			return madeItem;

		return null;
	}

	private static Item checkWeapons(Mod_Assets assets, Items i, int amount) {
		// One-handed Swords
		if (i == Items.Short_Sword_Poor)
			return Factory_Weapons.makeShortSwordPoor(assets);
		
		else if (i == Items.Short_Sword_Rusty)
			return Factory_Weapons.makeShortSwordRusty(assets);

		
		// One-handed Axes
		else if (i == Items.WarAxe_Rusty)
			return Factory_Weapons.makeWarAxeRusty(assets);
		
		else if (i == Items.WarAxe_Poor)
			return Factory_Weapons.makeWarAxePoor(assets);

		
		else
			return null;
	}

	private static Item checkArmor(Mod_Assets assets, Items i, int amount) {
		if (i == Items.ArmorLight_Leather)
			return Factory_Armor.makeLeatherArmor(assets);
		
		else if (i == Items.ArmorLight_LeatherPoor)
			return Factory_Armor.makeLeatherArmorPoor(assets);
		
		else if (i == Items.ArmorLight_LeatherGood)
			return Factory_Armor.makeLeatherArmorGood(assets);
		
		return null;
	}

	private static Item checkMedicine(Mod_Assets assets, Items i, int amount) {
		if(i == Items.Minor_Healing_Potion)
			return Factory_Medicine.makeMinorPotion(assets,amount);
		else if(i == Items.Minor_Healing_Potion_Poor)
			return Factory_Medicine.makeMinorPotionPoor(assets,amount);
		else if(i == Items.Minor_Healing_Potion_Good)
			return Factory_Medicine.makeMinorPotionGood(assets,amount);
		return null;
	}
}
