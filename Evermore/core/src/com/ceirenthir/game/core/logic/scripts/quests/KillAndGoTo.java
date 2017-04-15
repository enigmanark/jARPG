package com.ceirenthir.game.core.logic.scripts.quests;

import gameObjects.GameObject;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.Enemies;
import com.ceirenthir.game.core.data.Items;
import com.ceirenthir.game.core.data.QuestsType;
import com.ceirenthir.game.core.logic.events.ActivatedObject;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.Message;

public class KillAndGoTo extends Quest{
	public GameObject questGiver;
	public String to;
	public Enemies enemy;
	public int enemyAmount;
	public Enemies enemy2;
	public int enemy2Amount;
	public Enemies enemy3;
	public int enemy3Amount;
	public Items reward;
	public int rewardAmount;
	public Items reward2;
	public int reward2Amount;
	public Items reward3;
	public int reward3Amount;
	
	public KillAndGoTo(int id) {
		super(id, QuestsType.KillAndGoTo);
	}
	
	@Override
	public void post(Evermore game, Event e) {
		//TODO Properly make KillandGoTo
		if(e instanceof ActivatedObject) {
			ActivatedObject o = (ActivatedObject)e;
			if(o.object.getName().equals(questGiver)){
				turnIn(game);
			}
		}
	
	}
	
	private void turnIn(Evermore game) {
		complete = true;
		reward(game);
	}
	
	private void reward(Evermore game) {
		if(reward != null) {
			game.dataSys.player.itemMngr.addToBackPack(reward, rewardAmount);
		}
		if(reward2 != null) {
			game.dataSys.player.itemMngr.addToBackPack(reward2, reward2Amount);
		}
		if(reward3 != null) {
			game.dataSys.player.itemMngr.addToBackPack(reward3, reward3Amount);
		}
		
		String rewardMessage = "";
		//One reward only
		if(reward != null && reward2 == null && reward3 == null) {
			rewardMessage = "Recieved " + Integer.toString(rewardAmount) + " " + reward;
		}
		//Two reward only
		else if(reward != null && reward2 != null && reward3 == null) {
			rewardMessage = "Recieved " + Integer.toString(rewardAmount) + " " + reward +
					" and " + Integer.toString(reward2Amount) + " " + reward2;
		}
		//Three reward only
		else if(reward != null && reward2 != null && reward3 != null) {
			rewardMessage = "Recieved " + Integer.toString(rewardAmount) + " " + reward +
					" and " + Integer.toString(reward2Amount) + " " + reward2 + " and " +
					Integer.toString(reward3Amount) + " " + reward3;
		}
		
		game.logicSys.post(new Message("", rewardMessage));
	}
}
