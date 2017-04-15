package com.ceirenthir.game.core.logic;

/*
 * Tests for player collision with game objects and activates that gameObjects
 * that implement Collidable then executes their collide method.
 */

import gameObjects.GameObject;

import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.Collidable;
import com.ceirenthir.game.core.data.Map;
import com.ceirenthir.game.core.logic.events.CollidedObject;

public class Mod_GObjectCollision_Handler extends Mod_Logic {
	private final Evermore game;

	protected Mod_GObjectCollision_Handler(Evermore game) {
		this.game = game;
	}

	@Override
	protected void update() {
		collide(game.dataSys.player.getCollisionRect());
	}

	//Test for player collision with collidables
	private void collide(Rectangle playerRect) {
		Map currentMap = game.logicSys.currentMap;
		for(GameObject object : currentMap.gameObjects) {
			if(object instanceof Collidable) {
				Collidable collidable = (Collidable) object;
				if(collidable.intersects(playerRect)) {
					collidable.collide(game);
					//Post the event of this occuring
					game.logicSys.post(new CollidedObject((GameObject)collidable));
				}
			}
		}
	}

}
