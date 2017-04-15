package com.ceirenthir.game.core.logic;

import java.util.Iterator;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.QuestsType;
import com.ceirenthir.game.core.logic.events.AddQuest;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.Message;
import com.ceirenthir.game.core.logic.events.QuestComplete;
import com.ceirenthir.game.core.logic.scripts.quests.CourierTo;
import com.ceirenthir.game.core.logic.scripts.quests.CourierToAndReturn;
import com.ceirenthir.game.core.logic.scripts.quests.Deliver;
import com.ceirenthir.game.core.logic.scripts.quests.DeliverTo;
import com.ceirenthir.game.core.logic.scripts.quests.DeliverToAndReturn;
import com.ceirenthir.game.core.logic.scripts.quests.ExploreAndGoTo;
import com.ceirenthir.game.core.logic.scripts.quests.ExploreAndReturn;
import com.ceirenthir.game.core.logic.scripts.quests.KillAndGoTo;
import com.ceirenthir.game.core.logic.scripts.quests.KillAndReturn;
import com.ceirenthir.game.core.logic.scripts.quests.Quest;

public class Mod_Quest_Handler extends Mod_Logic {
	private final Evermore game;
	
	public Mod_Quest_Handler(Evermore game) {
		this.game = game;
	}
	
	private void generateQuest(Evermore game, AddQuest add) {
		/*
		 * Item Quests
		 */
		
		//DeliverTo quest
		if(add.type == QuestsType.DeliverTo) {
			DeliverTo quest = new DeliverTo(0);
			quest.to = add.to;
			quest.questGiver = add.questGiver;
			//Items
			quest.item = add.item;
			quest.itemAmount = add.itemAmount;
			quest.item2 = add.item2;
			quest.item2Amount = add.item2Amount;
			quest.item3 = add.item3;
			quest.item3Amount = add.item3Amount;
			//Rewards
			quest.reward = add.reward;
			quest.rewardAmount = add.rewardAmount;
			quest.reward2 = add.reward2;
			quest.reward2Amount = add.reward2Amount;
			quest.reward3 = add.reward3;
			quest.reward3Amount = add.reward3Amount;
			game.dataSys.questDB.addNewQuest(quest);
			//End DeliverTo quest
		}
		
		//Deliver Quest
		else if(add.type == QuestsType.Deliver) {
			Deliver quest = new Deliver(0);
			quest.to = add.to;
			quest.questGiver = add.questGiver;
			//Items
			quest.item = add.item;
			quest.itemAmount = add.itemAmount;
			quest.item2 = add.item2;
			quest.item2Amount = add.item2Amount;
			quest.item3 = add.item3;
			quest.item3Amount = add.item3Amount;
			//Rewards
			quest.reward = add.reward;
			quest.rewardAmount = add.rewardAmount;
			quest.reward2 = add.reward2;
			quest.reward2Amount = add.reward2Amount;
			quest.reward3 = add.reward3;
			quest.reward3Amount = add.reward3Amount;
			game.dataSys.questDB.addNewQuest(quest);
			//End Deliver quest
		}
		//DeliverToAndReturn quest
		else if(add.type == QuestsType.DeliverToAndReturn) {
			DeliverToAndReturn quest = new DeliverToAndReturn(0);
			quest.to = add.to;
			quest.questGiver = add.questGiver;
			//Items
			quest.item = add.item;
			quest.itemAmount = add.itemAmount;
			quest.item2 = add.item2;
			quest.item2Amount = add.item2Amount;
			quest.item3 = add.item3;
			quest.item3Amount = add.item3Amount;
			//Rewards
			quest.reward = add.reward;
			quest.rewardAmount = add.rewardAmount;
			quest.reward2 = add.reward2;
			quest.reward2Amount = add.reward2Amount;
			quest.reward3 = add.reward3;
			quest.reward3Amount = add.reward3Amount;
			game.dataSys.questDB.addNewQuest(quest);
			game.dataSys.questDB.addNewQuest(quest);
			//End DeliverToAndReturn quest
		}
		//CourierTo quest
		else if(add.type == QuestsType.CourierTo) {
			CourierTo quest = new CourierTo(0);
			quest.to = add.to;
			quest.questGiver = add.questGiver;
			//Items
			quest.item = add.item;
			quest.itemAmount = add.itemAmount;
			quest.item2 = add.item2;
			quest.item2Amount = add.item2Amount;
			quest.item3 = add.item3;
			quest.item3Amount = add.item3Amount;
			//Rewards
			quest.reward = add.reward;
			quest.rewardAmount = add.rewardAmount;
			quest.reward2 = add.reward2;
			quest.reward2Amount = add.reward2Amount;
			quest.reward3 = add.reward3;
			quest.reward3Amount = add.reward3Amount;
			game.dataSys.questDB.addNewQuest(quest);
			//End CourierTo quest
		}
		//CourierToAndReturn quest
		else if(add.type == QuestsType.CourierToAndReturn) {
			CourierToAndReturn quest = new CourierToAndReturn(0);
			quest.to = add.to;
			quest.questGiver = add.questGiver;
			//Items
			quest.item = add.item;
			quest.itemAmount = add.itemAmount;
			quest.item2 = add.item2;
			quest.item2Amount = add.item2Amount;
			quest.item3 = add.item3;
			quest.item3Amount = add.item3Amount;
			//Rewards
			quest.reward = add.reward;
			quest.rewardAmount = add.rewardAmount;
			quest.reward2 = add.reward2;
			quest.reward2Amount = add.reward2Amount;
			quest.reward3 = add.reward3;
			quest.reward3Amount = add.reward3Amount;
			game.dataSys.questDB.addNewQuest(quest);
			//End CourierToAndReturn quest
		}
		
		/*
		 * Exploration Quests
		 */
		//ExploreAndReturn
		else if(add.type == QuestsType.ExploreAndReturn) {
			ExploreAndReturn quest = new ExploreAndReturn(0);
			quest.to = add.to;
			quest.questGiver = add.questGiver;
			quest.mapName = add.mapName;
			//Rewards
			quest.reward = add.reward;
			quest.rewardAmount = add.rewardAmount;
			quest.reward2 = add.reward2;
			quest.reward2Amount = add.reward2Amount;
			quest.reward3 = add.reward3;
			quest.reward3Amount = add.reward3Amount;
			game.dataSys.questDB.addNewQuest(quest);
			//End ExploreAndReturn quest
		}
		
		else if(add.type == QuestsType.ExploreAndGoTo) {
			ExploreAndGoTo quest = new ExploreAndGoTo(0);
			quest.to = add.to;
			quest.questGiver = add.questGiver;
			quest.mapName = add.mapName;
			//Rewards
			quest.reward = add.reward;
			quest.rewardAmount = add.rewardAmount;
			quest.reward2 = add.reward2;
			quest.reward2Amount = add.reward2Amount;
			quest.reward3 = add.reward3;
			quest.reward3Amount = add.reward3Amount;
			game.dataSys.questDB.addNewQuest(quest);
			//End ExploreAndGoTo quest
		}
		
		/*
		 * Kill Quests
		 */
		//KillAndGoTo quest
		else if(add.type == QuestsType.KillAndGoTo) {
			KillAndGoTo quest = new KillAndGoTo(0);
			quest.to = add.to;
			quest.questGiver = add.questGiver;
			//Enemies
			quest.enemy = add.enemy;
			quest.enemyAmount = add.enemyAmount;
			quest.enemy2 = add.enemy2;
			quest.enemy2Amount = add.enemy2Amount;
			quest.enemy3 = add.enemy3;
			quest.enemy3Amount = add.enemy3Amount;
			//Rewards
			quest.reward = add.reward;
			quest.rewardAmount = add.rewardAmount;
			quest.reward2 = add.reward2;
			quest.reward2Amount = add.reward2Amount;
			quest.reward3 = add.reward3;
			quest.reward3Amount = add.reward3Amount;
			game.dataSys.questDB.addNewQuest(quest);
			//End KillAndGoTo quest
		}
		//KillAndReturn quest
		else if(add.type == QuestsType.KillAndReturn) {
			KillAndReturn quest = new KillAndReturn(0);
			quest.to = add.to;
			quest.questGiver = add.questGiver;
			//Enemies
			quest.enemy = add.enemy;
			quest.enemyAmount = add.enemyAmount;
			quest.enemy2 = add.enemy2;
			quest.enemy2Amount = add.enemy2Amount;
			quest.enemy3 = add.enemy3;
			quest.enemy3Amount = add.enemy3Amount;
			//Rewards
			quest.reward = add.reward;
			quest.rewardAmount = add.rewardAmount;
			quest.reward2 = add.reward2;
			quest.reward2Amount = add.reward2Amount;
			quest.reward3 = add.reward3;
			quest.reward3Amount = add.reward3Amount;
			game.dataSys.questDB.addNewQuest(quest);
			game.dataSys.questDB.addNewQuest(quest);
			//End KillAndReturn quest
		}
	}
	
	//Let quests recieve events so they can handle triggers
	@Override
	public void post(Evermore game, Event e) {
		if(e instanceof AddQuest) {
			generateQuest(game, (AddQuest)e);
		}
		else {
			for(Quest quest : game.dataSys.questDB.getCurrentQuests()) {
				quest.post(game, e);
			}
		}
	}

	//Handle player's current quests
	@Override
	public void update() {
		//Get all the current quests
		Iterator<Quest> currentQuestIter = game.dataSys.questDB.getCurrentQuests().iterator();
		while(currentQuestIter.hasNext()) {
			Quest quest = currentQuestIter.next();
			//Update this current quest
			//System.out.println("updating quest");
			quest.update(game);
			//Check if it's completed
			if(((Quest) quest).isComplete()) {
				//If it's completed remove it from current quests and add it to the
				//quest DB of completed quests
				currentQuestIter.remove();
				game.dataSys.questDB.addCompletedQuest((Quest) quest);
				game.logicSys.post(new QuestComplete(quest.type, quest.instanceID));
				game.logicSys.post(new Message("Quest Engine ",
						quest.type + " completed! Good job!"));
			}
		}
	}
}
