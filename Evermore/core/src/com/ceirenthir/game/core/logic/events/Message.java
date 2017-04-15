package com.ceirenthir.game.core.logic.events;

/*
 * To make a message, use the method from a script or quest called this.postToLogic()
 * then make your message.
 */

public class Message extends Event {
	public String speaker;
	public String message;
	
	public Message(String speaker, String message) {
		super("Message");
		this.speaker = speaker;
		this.message = message;
	}
}
