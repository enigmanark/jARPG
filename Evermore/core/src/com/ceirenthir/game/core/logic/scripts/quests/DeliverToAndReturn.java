package com.ceirenthir.game.core.logic.scripts.quests;

import gameObjects.GameObject;

import com.badlogic.gdx.Gdx;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.Items;
import com.ceirenthir.game.core.data.QuestsType;
import com.ceirenthir.game.core.logic.events.ActivatedObject;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.Message;


public class DeliverToAndReturn extends Quest {
	public GameObject questGiver;
	public String to;
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
	public boolean itemsGiven = false;
	public boolean itemsDelivered = false;
	
	public DeliverToAndReturn(int id) {
		super(id, QuestsType.DeliverToAndReturn);
	}
	
	@Override
	public void post(Evermore game, Event e) {
		if(e instanceof ActivatedObject) {
			ActivatedObject o = (ActivatedObject)e;
			if(o.object.getName().equals(to)){
				if(hasItems(game)) {
					deliverItems(game);
				}
			}
			else if(o.object.getName().equals(questGiver.getName())) {
				turnIn(game);
			}
		}
	
	}
	
	private void turnIn(Evermore game) {
		if(itemsDelivered) {
			complete = true;
			reward(game);
		}
	}
	
	private void deliverItems(Evermore game) {
		game.dataSys.player.itemMngr.removeItemBackPack(item, itemAmount);
		if(item2 != null) {
			game.dataSys.player.itemMngr.removeItemBackPack(item2, item2Amount);
		}
		if(item3 != null) {
			game.dataSys.player.itemMngr.removeItemBackPack(item3, item3Amount);
		}
		itemsDelivered = true;
	}
	
	private boolean hasItems(Evermore game) {
		//Only if one item for turn in
		if(item != null && item2 == null && item3 == null) {
			if(game.dataSys.player.itemMngr.getItemAmountInBackPack(item) >= itemAmount)
				return true;
			else return false;
		}
		//Only if two items for turn in
		else if(item != null && item2 != null && item3 == null) {
			if(game.dataSys.player.itemMngr.getItemAmountInBackPack(item) >= itemAmount
					&& game.dataSys.player.itemMngr.getItemAmountInBackPack(item2) >= item2Amount)
				return true;
			else return false;
		}
		//If three items for turn in
		else if(item != null && item2 != null && item3 != null) {
			if(game.dataSys.player.itemMngr.getItemAmountInBackPack(item) >= itemAmount
					&& game.dataSys.player.itemMngr.getItemAmountInBackPack(item2) >= item2Amount
					&& game.dataSys.player.itemMngr.getItemAmountInBackPack(item3) >= item3Amount)
				return true;
			else return false;
		}
		
		else {
			Gdx.app.error("DeliverTo Quest ERROR", "This quests properties for item delivery are incorrect.");
			return false;
		}
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
