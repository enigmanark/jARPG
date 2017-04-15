package com.ceirenthir.game.core.logic;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.logic.events.EquipmentChange;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.Message;

public class Mod_Event_Handler extends Mod_Logic{
	private ArrayList<Event> storedEvents;
	private ArrayList<Event> events;
	private final Evermore game;
	
	public Mod_Event_Handler(Evermore game) {
		this.game = game;
		storedEvents = new ArrayList<Event>();
		events = new ArrayList<Event>();
	}
	
	//Method for posting events to the logicSystem modules and ensuring the handler
	//or controller gets the Event
	private boolean processEvent(ArrayList<Mod_Logic> modules, Event e) {
		/*
		 * These events are treated with special care to make sure they
		 * get sent
		 */
		
		//If the event is a message
		if(e instanceof Message) {
			//Get the message
			Message m = (Message) e;
			//See if the MessageWindowController is available, if so send post
			//The only reason this might be possible is because the controller
			//already has a message displayed, or it just got done immediately
			//clearing one right before this
			if(game.logicSys.getMessageController().isAvailable()) {
				sendToModules(modules, e);
			}
			//Check if the controller has a message
			if(game.logicSys.getMessageController().getMessage() != null) {
				//If it has OUR message..
				if(game.logicSys.getMessageController().getMessage().
						message.equalsIgnoreCase(m.message)) {
					//Then it got our message! Return true
					return true;
				}else {
					//It didnt' get our message ;.;
					return false;
				}
			}
			
			/*
			 * End special events
			 */
			
			//MessageWindowController was not available
			else return false;
		}
		//Any events that don't require special treatment can just be sent
		else {
			sendToModules(modules, e);
			return true;
		}
	}
	
	//Helper method for sending the event to all logicSystem modules
	private void sendToModules(ArrayList<Mod_Logic> modules, Event e) {
		for(Mod_Logic mod : modules) {
			mod.post(game, e);
		}
		if(e instanceof EquipmentChange) {
			EquipmentChange ec = (EquipmentChange)e;
			Gdx.app.log("Logic System Event Handler", "Event EquipmentChange was posted with Actor " +
					ec.actor.getName() + " and EquipmentType " + ec.type.toString() );
		}
		else Gdx.app.log("Logic System Event Handler", "Event " + e.name + " posted.");
	}
	
	//called from the system to post events to the event handler
	protected void post(Event e) {
		//Add events to buffer
		storedEvents.add(e);
	}
	
	
	//Update and process events
	protected void update(ArrayList<Mod_Logic> modules) {
		//get the events from the buffer storedEvents and add them to the
		//proper events array
		for(Event e : storedEvents) {
			events.add(e);
		}
		//Clear the buffer
		storedEvents.clear();
		
		//If there are events to update
		if(events.size() > 0) {
			Iterator<Event> eventIter = events.iterator();
			//Loop over events
			while(eventIter.hasNext()) {
				Event event = eventIter.next();
				//Process the event and see if it processed correctly
				if(this.processEvent(modules, event)) {
					//If so remove the event
					eventIter.remove();
				}
				//If it did not process, we'll get it next time!
			}
		}
	}
}
