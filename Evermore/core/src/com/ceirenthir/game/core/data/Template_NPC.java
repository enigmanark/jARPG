package com.ceirenthir.game.core.data;

import com.ceirenthir.game.core.logic.events.Message;
import com.ceirenthir.game.core.logic.scripts.QuestStart;

/*
 * Just a helper class used by Factory_NPC.
 */

public class Template_NPC {
	public String name;
	public String spriteSheet;
	public Items mainHandWeapon;
	public Message message;
	public QuestStart quest;
	public int wander = 0;
}
