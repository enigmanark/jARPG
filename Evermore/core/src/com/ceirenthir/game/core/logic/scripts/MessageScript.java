package com.ceirenthir.game.core.logic.scripts;

import java.util.Iterator;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.Activatable;
import com.ceirenthir.game.core.logic.events.Message;

/*
 * This is a script that displays a MessageWindow. The first arg is the host object
 * that this script is attached to. The second is a Message object that contains the
 * host's name, and the message as a string. This script can be attached to a gameobject
 * that implements either activate or collide.
 */

public class MessageScript extends Script {
	public Activatable active;
	public Message message;
	
	public MessageScript(Activatable active, Message message ) {
		this.active = active;
		this.message = message;
	}
	
	//Upon activation it posts it's stored message to the post system.
	@Override
	public void activate(Evermore game, Iterator<Script> scriptIter) {
		game.logicSys.post(message);
	}
	
	//OR upon collide it posts it's message to the post system.
	@Override
	public void collide(Evermore game, Iterator<Script> scriptIter) {
		game.logicSys.post(message);
	}
}
