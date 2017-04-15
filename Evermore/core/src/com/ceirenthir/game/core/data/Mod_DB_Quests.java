package com.ceirenthir.game.core.data;

import java.util.ArrayList;

import com.ceirenthir.game.core.logic.scripts.quests.*;

/*
 * DataBase for quests. It contains completed quests and
 * is able to generate new quests. Current quests are not stored here.
 * They stare stored in LogicSys, but, LogicSys receives those quests
 * from here, because it generates them.
 */

public class Mod_DB_Quests {
	private ArrayList<Quest> completedQuests;
	private ArrayList<Quest> currentQuests;
	
	public ArrayList<Quest> getCurrentQuests() {
		return currentQuests;
	}
	
	public void addNewQuest(Quest quest) {
		currentQuests.add(quest);
	}
	
	public Mod_DB_Quests() {
		completedQuests = new ArrayList<Quest>();
		currentQuests = new ArrayList<Quest>();
	}
	
	public void addCompletedQuest(Quest completed) {
		completedQuests.add(completed);
	}
	
	public ArrayList<Quest> getCompletedQuests() {
		return completedQuests;
	}
	
	public int getCompletedQuestAmount() {
		return completedQuests.size();
	}
}
