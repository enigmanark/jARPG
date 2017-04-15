package com.ceirenthir.game.core.logic.events;

import gameObjects.GameObject;

import com.ceirenthir.game.core.data.Enemies;
import com.ceirenthir.game.core.data.Items;
import com.ceirenthir.game.core.data.QuestsType;

public class AddQuest extends Event{
	public GameObject questGiver;
	public QuestsType type;
	public String to; //GameObjects name this should be completed at, normally an NPC
	
	public Items item;
	public int itemAmount;
	public Items item2;
	public int item2Amount;
	public Items item3;
	public int item3Amount;
	
	public Items reward;
	public int rewardAmount;
	public Items reward2;
	public int reward2Amount;
	public Items reward3;
	public int reward3Amount;
	
	public String mapName;

	public Enemies enemy;
	public int enemyAmount;
	public Enemies enemy2;
	public int enemy2Amount;
	public Enemies enemy3;
	public int enemy3Amount;

	public AddQuest() {
		super("AddQuest");
	}
}
