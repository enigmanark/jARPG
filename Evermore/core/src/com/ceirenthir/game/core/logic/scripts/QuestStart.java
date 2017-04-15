package com.ceirenthir.game.core.logic.scripts;

import gameObjects.GameObject;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.Enemies;
import com.ceirenthir.game.core.data.Items;
import com.ceirenthir.game.core.data.QuestsType;
import com.ceirenthir.game.core.logic.events.AddQuest;

public class QuestStart extends Script {
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
	
	public QuestStart() {
	}

	//Item Quests
	private AddQuest deliver() {
		AddQuest q = new AddQuest();
		q.questGiver = questGiver;
		q.type = QuestsType.Deliver;
		
		if(item != null) {
			q.item = item;
			q.itemAmount = itemAmount;
		}
		
		if(item2 != null) {
			q.item2 = item2;
			q.item2Amount = item2Amount;
		}
		if(item3 != null) {
			q.item3 = item3;
			q.item3Amount = item3Amount;
		}
		
		if(reward != null) {
			q.reward = reward;
			q.rewardAmount = rewardAmount;
		}
		if(reward2 != null) {
			q.reward2 = reward2;
			q.reward2Amount = reward2Amount;
		}
		if(reward != null) {
			q.reward3 = reward3;
			q.reward3Amount = reward3Amount;
		}
		
		return q;
	}
	
	private AddQuest deliverTo() {
		AddQuest q = new AddQuest();
		q.type = QuestsType.DeliverTo;
		q.to = to;
		
		if(item != null) {
			q.item = item;
			q.itemAmount = itemAmount;
		}
		
		if(item2 != null) {
			q.item2 = item2;
			q.item2Amount = item2Amount;
		}
		if(item3 != null) {
			q.item3 = item3;
			q.item3Amount = item3Amount;
		}
		
		if(reward != null) {
			q.reward = reward;
			q.rewardAmount = rewardAmount;
		}
		if(reward2 != null) {
			q.reward2 = reward2;
			q.reward2Amount = reward2Amount;
		}
		if(reward != null) {
			q.reward3 = reward3;
			q.reward3Amount = reward3Amount;
		}
		
		return q;
	}
	
	private AddQuest deliverToAndReturn() {
		AddQuest q = new AddQuest();
		q.questGiver = questGiver;
		q.type = QuestsType.DeliverToAndReturn;
		q.to = to;
		
		if(item != null) {
			q.item = item;
			q.itemAmount = itemAmount;
		}
		
		if(item2 != null) {
			q.item2 = item2;
			q.item2Amount = item2Amount;
		}
		if(item3 != null) {
			q.item3 = item3;
			q.item3Amount = item3Amount;
		}
		
		if(reward != null) {
			q.reward = reward;
			q.rewardAmount = rewardAmount;
		}
		if(reward2 != null) {
			q.reward2 = reward2;
			q.reward2Amount = reward2Amount;
		}
		if(reward != null) {
			q.reward3 = reward3;
			q.reward3Amount = reward3Amount;
		}
		
		return q;
	}
	
	private AddQuest courierTo() {
		AddQuest q = new AddQuest();
		q.questGiver = questGiver;
		q.type = QuestsType.CourierTo;
		q.to = to;
		
		if(item != null) {
			q.item = item;
			q.itemAmount = itemAmount;
		}
		
		if(item2 != null) {
			q.item2 = item2;
			q.item2Amount = item2Amount;
		}
		if(item3 != null) {
			q.item3 = item3;
			q.item3Amount = item3Amount;
		}
		
		if(reward != null) {
			q.reward = reward;
			q.rewardAmount = rewardAmount;
		}
		if(reward2 != null) {
			q.reward2 = reward2;
			q.reward2Amount = reward2Amount;
		}
		if(reward != null) {
			q.reward3 = reward3;
			q.reward3Amount = reward3Amount;
		}
		
		return q;
	}
	
	private AddQuest courierToAndReturn() {
		AddQuest q = new AddQuest();
		q.questGiver = questGiver;
		q.type = QuestsType.CourierToAndReturn;
		q.to = to;
		
		if(item != null) {
			q.item = item;
			q.itemAmount = itemAmount;
		}
		
		if(item2 != null) {
			q.item2 = item2;
			q.item2Amount = item2Amount;
		}
		if(item3 != null) {
			q.item3 = item3;
			q.item3Amount = item3Amount;
		}
		
		if(reward != null) {
			q.reward = reward;
			q.rewardAmount = rewardAmount;
		}
		if(reward2 != null) {
			q.reward2 = reward2;
			q.reward2Amount = reward2Amount;
		}
		if(reward != null) {
			q.reward3 = reward3;
			q.reward3Amount = reward3Amount;
		}
		
		return q;
	}
	
	private AddQuest killAndReturn() {
		AddQuest q = new AddQuest();
		q.questGiver = questGiver;
		q.type = QuestsType.KillAndReturn;
		
		if(enemy != null) {
			q.enemy = enemy;
			q.enemyAmount = enemyAmount;
		}
		if(enemy2 != null) {
			q.enemy2 = enemy2;
			q.enemy2Amount = enemy2Amount;
		}
		if(enemy3 != null) {
			q.enemy3 = enemy3;
			q.enemy3 = enemy3;
		}
		
		if(reward != null) {
			q.reward = reward;
			q.rewardAmount = rewardAmount;
		}
		if(reward2 != null) {
			q.reward2 = reward2;
			q.reward2Amount = reward2Amount;
		}
		if(reward != null) {
			q.reward3 = reward3;
			q.reward3Amount = reward3Amount;
		}
		
		return q;
	}
	
	private AddQuest killAndGoTo() {
		AddQuest q = new AddQuest();
		q.questGiver = questGiver;
		q.type = QuestsType.KillAndGoTo;
		q.to = to;
		
		if(enemy != null) {
			q.enemy = enemy;
			q.enemyAmount = enemyAmount;
		}
		if(enemy2 != null) {
			q.enemy2 = enemy2;
			q.enemy2Amount = enemy2Amount;
		}
		if(enemy3 != null) {
			q.enemy3 = enemy3;
			q.enemy3 = enemy3;
		}
		
		if(reward != null) {
			q.reward = reward;
			q.rewardAmount = rewardAmount;
		}
		if(reward2 != null) {
			q.reward2 = reward2;
			q.reward2Amount = reward2Amount;
		}
		if(reward != null) {
			q.reward3 = reward3;
			q.reward3Amount = reward3Amount;
		}
		
		return q;
	}
	
	private AddQuest exploreAndReturn() {
		AddQuest q = new AddQuest();
		q.questGiver = questGiver;
		q.type = QuestsType.ExploreAndReturn;
		q.mapName = mapName;
		if(reward != null) {
			q.reward = reward;
			q.rewardAmount = rewardAmount;
		}
		if(reward2 != null) {
			q.reward2 = reward2;
			q.reward2Amount = reward2Amount;
		}
		if(reward != null) {
			q.reward3 = reward3;
			q.reward3Amount = reward3Amount;
		}
		
		return q;
	}
	
	private AddQuest exploreAndGoTo() {
		AddQuest q = new AddQuest();
		q.questGiver = questGiver;
		q.type = QuestsType.ExploreAndGoTo;
		q.mapName = mapName;
		q.to = to;
		
		if(reward != null) {
			q.reward = reward;
			q.rewardAmount = rewardAmount;
		}
		if(reward2 != null) {
			q.reward2 = reward2;
			q.reward2Amount = reward2Amount;
		}
		if(reward != null) {
			q.reward3 = reward3;
			q.reward3Amount = reward3Amount;
		}
		
		return q;
	}
	
	private AddQuest getAddQuestEvent() {
		//Item Quests
		switch(type) {
		
		default:
			Gdx.app.error("QuestStarter", "A new quest event tried to be created, but failed.");
			return null;
		
		case DeliverTo:
			Gdx.app.log("QuestStarter", "A new quest event for DeliverTo quest was created.");
			return this.deliverTo();
		
		case Deliver:
			Gdx.app.log("QuestStarter", "A new quest event for Deliver quest was created.");
			return this.deliver();
			
		case DeliverToAndReturn:
			Gdx.app.log("QuestStarter", "A new quest event for DeliverToAndReturn quest was created.");
			return this.deliverToAndReturn();

		case CourierTo:
			Gdx.app.log("QuestStarter", "A new quest event for CourierTo quest was created.");
			return this.courierTo();
			
		case CourierToAndReturn:
			Gdx.app.log("QuestStarter", "A new quest event for CourierToAndReturn quest was created.");
			return this.courierToAndReturn();
		
		case KillAndGoTo:
			Gdx.app.log("QuestStarter", "A new quest event for KillAndGoTo quest was created.");
			return this.killAndGoTo();
		
		case KillAndReturn:
			Gdx.app.log("QuestStarter", "A new quest event for KillAndReturn quest was created.");
			return this.killAndReturn();
		
		case ExploreAndReturn:
			Gdx.app.log("QuestStarter", "A new quest event for ExploreAndReturn quest was created.");
			return this.exploreAndReturn();
			
		case ExploreAndGoTo:
			Gdx.app.log("QuestStarter", "A new quest event for ExploreAndGoTo quest was created.");
			return this.exploreAndGoTo();
		}
	}
	
	@Override
	public void activate(Evermore game, Iterator<Script> scriptIter) {
		game.logicSys.post(getAddQuestEvent());

		//remove this script after it's done
		scriptIter.remove();
	}
	public void collide(Evermore game, Iterator<Script> scriptIter) {
		game.logicSys.post(getAddQuestEvent());

		//remove this script after it's done
		scriptIter.remove();
	}
}

