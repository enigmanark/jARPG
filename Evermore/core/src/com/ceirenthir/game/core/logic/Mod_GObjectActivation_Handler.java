package com.ceirenthir.game.core.logic;

/*
 * This Module checks for the player trying to activate something.
 * It gets the players predefined "activation rect." 
 * 
 * The activation rect is basically
 * just a Rectangle that is created on the fly directly in front of the player's
 * current facing. 
 * 
 * Then we check to see if that overlaps an activatable on the current map.
 * If it does, then we run activate on that activatable.
 */

import gameObjects.GameObject;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.Activatable;
import com.ceirenthir.game.core.data.Map;
import com.ceirenthir.game.core.logic.events.Activate;
import com.ceirenthir.game.core.logic.events.ActivatedObject;
import com.ceirenthir.game.core.logic.events.Event;

public class Mod_GObjectActivation_Handler extends Mod_Logic {
	final Evermore game;
	long lastActivationTime = 0L;
	
	protected Mod_GObjectActivation_Handler(Evermore game) {
		this.game = game;
	}
	
	@Override
	protected void update() {
		
	}
	
	//Recieve Activate event from LogicSystem.
	@Override
	protected void post(Evermore game, Event e) {
		if(e instanceof Activate) {
			//Retrieve the player's activation rect so we can check if it collides
			//with an Activatable. If it does, then we need to activate that Object.
			//So run activate. See below.
			//Make sure window is available first
			if(game.logicSys.getMessageController().isAvailable()) {
				activate(game.dataSys.player.getActivationRect());
			}
		}
	}

	//Determine if the player's activation rect
	//overlaps an activatable's rect, if it does, activate it
	private void activate(Rectangle aRect) {
		//Make sure the time has passed since the last activation
		//this is to keep multiple things from being activated at
		//the same time
		if(TimeUtils.nanoTime() - lastActivationTime > (1000000000 / 5 )) {
			Map currentMap = game.logicSys.currentMap;
			//Loop through maps activatables
			for(GameObject object : currentMap.gameObjects) {
				if(object instanceof Activatable) {
					Activatable activatable = (Activatable) object;
					//Get the activatables activatorRect which is a modified
					//version of the normal rect used specifically for this
					Rectangle rect = activatable.getActivatorRect();
					//If the player's activation rect overlaps it then activate it
					if(aRect.overlaps(rect)) {
						lastActivationTime = TimeUtils.nanoTime();
						activatable.activate(game);
						//Post the event of this occuring
						game.logicSys.post(new ActivatedObject((GameObject)activatable));
					}
				}
			}
		}
		//Nothing activated it. So ignore it.
	}
}
