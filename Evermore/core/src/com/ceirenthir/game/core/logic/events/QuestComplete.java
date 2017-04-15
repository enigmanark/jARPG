package com.ceirenthir.game.core.logic.events;

import com.ceirenthir.game.core.data.QuestsType;

public class QuestComplete extends Event {
	public final QuestsType type;
	public final int instanceID;
	public QuestComplete(QuestsType q, int d) {
		super("QuestComplete");
		this.type = q;
		this.instanceID = d;
	}
}
