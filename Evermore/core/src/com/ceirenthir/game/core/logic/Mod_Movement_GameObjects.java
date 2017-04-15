package com.ceirenthir.game.core.logic;

/*
 * This Module of LogicSystem handles the moveable's movement. This module
 * checks for movement by inspecting all gameObjects on the current map that
 * implement AI_Movable. If any of these are objects are found to be "moving"
 * then it gets there DX and DY and tries to move them in the direction they
 * want to go. The module does not care how they got to be in the moving position
 * it just tries to move them. It checks for collisions. If there movement is allowed,
 * this module executes that movement and updates everything accordingly.
 */

import java.util.ArrayList;

import gameObjects.Entity_Animated;
import gameObjects.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.AI_Movable;
import com.ceirenthir.game.core.IF.Collidable;
import com.ceirenthir.game.core.IF.Static;

public class Mod_Movement_GameObjects extends Mod_Logic {
	final Evermore game;
	
	protected Mod_Movement_GameObjects(Evermore game) {
		this.game = game;
	}
	
	//Updates the objects x and y
	private boolean move(AI_Movable moveable, float dx, float dy) { 
		//Check for new facing and update
		if(dx == 0 && dy > 0) moveable.setFacing("North");
		else if(dx == 0 && dy < 0) moveable.setFacing("South");
		else if(dx > 0 && dy == 0) moveable.setFacing("East");
		else if(dx < 0 && dy == 0) moveable.setFacing("West");
		else if(dx > 0 && dy > 0) moveable.setFacing("North East");
		else if(dx < 0 && dy > 0) moveable.setFacing("North West");
		else if(dx < 0 && dy < 0) moveable.setFacing("South West");
		else if(dx > 0 && dy < 0) moveable.setFacing("South East");
		else {
			Gdx.app.debug("Actor Error","Actors facing is wrong.");
		}
		//Get the x and y obviously
		float x = moveable.getX();
		float y = moveable.getY();
		float speed = moveable.getSpeed();
		float modSpeed = moveable.getModSpeed();
		//Updat the x and y of the movable
		x += (((dx * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		y += (((dy * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		moveable.setX(x);
		moveable.setY(y);
		if(dx != 0 | dy != 0) return true;
		else return false;
	}
	
	//Only move the object's X
	private boolean moveX(AI_Movable moveable, float dx, float dy) {
		//Check for new facing
		if(dx == 0 && dy > 0) moveable.setFacing("North");
		else if(dx == 0 && dy < 0) moveable.setFacing("South");
		else if(dx > 0 && dy == 0) moveable.setFacing("East");
		else if(dx < 0 && dy == 0) moveable.setFacing("West");
		else if(dx > 0 && dy > 0) moveable.setFacing("North East");
		else if(dx < 0 && dy > 0) moveable.setFacing("North West");
		else if(dx < 0 && dy < 0) moveable.setFacing("South West");
		else if(dx > 0 && dy < 0) moveable.setFacing("South East");
		else {
			Gdx.app.debug("Actor Error","Actors facing is wrong.");
		}
		
		float x = moveable.getX();
		float speed = moveable.getSpeed();
		float modSpeed = moveable.getModSpeed();
		x += (((dx * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		moveable.setX(x);
		if(dx != 0) return true;
		else return false;
	}
	
	//Only the move the object on the y axis
	private boolean moveY(AI_Movable moveable, float dx, float dy) {	
		//Check for new facing
		if(dx == 0 && dy > 0) moveable.setFacing("North");
		else if(dx == 0 && dy < 0) moveable.setFacing("South");
		else if(dx > 0 && dy == 0) moveable.setFacing("East");
		else if(dx < 0 && dy == 0) moveable.setFacing("West");
		else if(dx > 0 && dy > 0) moveable.setFacing("North East");
		else if(dx < 0 && dy > 0) moveable.setFacing("North West");
		else if(dx < 0 && dy < 0) moveable.setFacing("South West");
		else if(dx > 0 && dy < 0) moveable.setFacing("South East");
		else {
			Gdx.app.debug("Actor Error","Actors facing is wrong.");
		}
		
		float y = moveable.getY();
		float speed = moveable.getSpeed();
		float modSpeed = moveable.getModSpeed();
		y += (((dy * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		moveable.setY(y);
		
		if(dy != 0) return true;
		else return false;
	}
	//Give a DX and a DY and it'll calculate where the moveable
	//will move to and return the x and y in a 2 index array
	//with x being index 0 and y being index 1
	protected float[] getMoveToCoordinates(AI_Movable moveable, float dx, float dy) {
		float x = moveable.getX();
		float y = moveable.getY();
		float speed = moveable.getSpeed();
		float modSpeed = moveable.getModSpeed();
		x += (((dx * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		y += (((dy * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		float[] coords = new float[2];
		coords[0] = x;
		coords[1] = y;
		return coords;
	}
	
	//Call to stop the moveable from moving
	protected void stopMoving(AI_Movable moveable) {
		moveable.setDX(0);
		moveable.setDY(0);
		moveable.setSpriteWalkStateTime(0);
		moveable.setMoving(false);
		moveable.setWantToMove(false);
	}
	
	//Normally called internally from update if an object was found to be
	//trying to move.
	protected void requestMove(AI_Movable moveable, float dx, float dy) {
		float moveX = (dx);
		float moveY = (dy);
		boolean[] movePermissions = canMove(moveable, moveX, moveY);
		boolean moveBothPerm = movePermissions[0];
		boolean moveXPerm = movePermissions[1];
		boolean moveYPerm = movePermissions[2];
		if(moveBothPerm) {
			if(move(moveable, dx, dy)) {
				moveable.setMoving(true);
			} else moveable.setMoving(false);
		}
		else if(moveXPerm) {
			if(moveX(moveable, dx, dy)) {
				moveable.setMoving(true);
			} else moveable.setMoving(false);
		}
		else if(moveYPerm) {
			if(moveY(moveable, dx, dy)) {
				moveable.setMoving(true);
			} else moveable.setMoving(false);
		}
		//If it makes it here, no moving allowed
		//set moving to false in case it was moving before
	}
	
	//checks if moveable can move to the requested coordinates
	//It returns 3 booleans in an array.
	//Index 0 is for using move() both x and y.
	//Index 1 is for using moveX(), can only move x
	//Index 2 is for using moveY(), can only move y
	private boolean[] canMove(AI_Movable moveable, float dx, float dy) {
		//TODO FIX THiS SHIT
		boolean[] movePermissions = {true, true, true};
		//moveable rect modified for movement
		
		//Get the move to coordinates for both X and Y
		float[] moveToCoords = this.getMoveToCoordinates(moveable, dx, dy);
		float x = moveToCoords[0];
		float y = moveToCoords[1];
		Rectangle rect = moveable.getMoveRect(x, y);
		
		//Now with the gotten coordinates test static tile collision
		for(Static stat : game.logicSys.currentMap.statics) {
			if(stat.intersects(rect)) {
				movePermissions[0] = false;
				break;
			}
		}

		// Now test x and y against all game objects
		if(movePermissions[0] != false) {
			for(GameObject object : game.logicSys.currentMap.gameObjects) {
				if(object.isSolid()) {
					//Make sure not checking against self
					if(!(object == moveable)) {
						if(object instanceof Entity_Animated) {
							Entity_Animated actor = (Entity_Animated) object;
							if(actor.getWalkCollisionRect().overlaps(rect)) {
								movePermissions[0] = false;
								break;
							}
						}
						else if(object.intersects(rect)) {
							movePermissions[0] = false;
							break;
						}
					}
				}
				//Do not walk over collidables
				else if(object instanceof Collidable) {
					if(object.intersects(rect)) {
						movePermissions[0] = false;
						break;
					}
				}
			}
		}
		//Now test both x and y against the player, can't have the objects walking into the
		//player
		if(movePermissions[0] != false) {
			if(rect.overlaps(game.dataSys.player.getWalkCollisionRect())) {
				movePermissions[0] = false;
			}
		}
		//if x and y collision was not found, then return the move permssions because
		//we can move wherever we want
		if(movePermissions[0] == true) {
			return movePermissions;
		}
		
		//If both x and y failed, now test only x
		rect = new Rectangle();
		moveToCoords = this.getMoveToCoordinates(moveable, dx, 0);
		x = moveToCoords[0];
		y = moveToCoords[1];
		rect.width = moveable.getRect().getWidth() / 2;
		rect.height = moveable.getRect().getHeight() / 5;
		rect.x = (x + (rect.width/3));
		rect.y = y;
		
		//With the new move coordinate consisting of only x test solid tile collison
		for(Static solid : game.logicSys.currentMap.statics) {
			if(solid.intersects(rect)) {
				movePermissions[1] = false;
				break;
			}
		}
		//test moveable x collision against other game objects
		if(movePermissions[1] != false) {
			for(GameObject object : game.logicSys.currentMap.gameObjects) {
				if(object.isSolid()) {
					//Make sure not checking against self
					if(!(object == moveable)) {
						if(object instanceof Entity_Animated) {
							Entity_Animated actor = (Entity_Animated) object;
							if(actor.getWalkCollisionRect().overlaps(rect)) {
								movePermissions[1] = false;
								break;
							}
						}
						else if(object.intersects(rect)) {
							movePermissions[1] = false;
							break;
						}
					}
				}
				//Do not walk over collidables
				else if(object instanceof Collidable) {
					if(object.intersects(rect)) {
						movePermissions[1] = false;
						break;
					}
				}
			}
		}
		//Now test x against the player, can't have the objects walking into the
		//player
		if(movePermissions[1] != false) {
			if(rect.overlaps(game.dataSys.player.getWalkCollisionRect())) {
				movePermissions[1] = false;
			}
		}
		//If x returned true, then y was teh one that failed in the x and y collision test
		//so go ahead and return now with move permission for only x
		if(movePermissions[1] == true) {
			movePermissions[2] = false;
			return movePermissions;
		}
		
		//If x failed, then now test only the y coordinate for available movement
		rect = new Rectangle();
		moveToCoords = this.getMoveToCoordinates(moveable, 0, dy);
		x = moveToCoords[0];
		y = moveToCoords[1];
		rect.width = moveable.getRect().getWidth() / 2;
		rect.height = moveable.getRect().getHeight() / 5;
		rect.x = (x + (rect.width/3));
		rect.y = y;
		
		//With the new move to coordinate with only y different test solid tile collision
		for(Static stat : game.logicSys.currentMap.statics) {
			if(stat.intersects(rect)) {
				movePermissions[2] = false;
				break;
			}
		}
		//test moveable y collision against other game objects
		if(movePermissions[2] != false) {
			for(GameObject object : game.logicSys.currentMap.gameObjects) {
				if(object.isSolid()) {
					//Make sure not checking against self
					if(!(object == moveable)) {
						if(object instanceof Entity_Animated) {
							Entity_Animated actor = (Entity_Animated) object;
							if(actor.getWalkCollisionRect().overlaps(rect)) {
								movePermissions[2] = false;
								break;
							}
						}
						else if(object.intersects(rect)) {
							movePermissions[2] = false;
							break;
						}
					}
				}
				//Do not walk over collidables
				else if(object instanceof Collidable) {
					if(object.intersects(rect)) {
						movePermissions[2] = false;
						break;
					}
				}
			}
		}
		//Now test y against the player, can't have the objects walking into the
		//player
		if(movePermissions[2] != false) {
			if(rect.overlaps(game.dataSys.player.getWalkCollisionRect())) {
				movePermissions[2] = false;
			}
		}
		//If y was allowed, then return with only y movable permission 
		if(movePermissions[2] == true) {
			return movePermissions;
		}
		//If it makes it here, no moving allowed so return all false
		return movePermissions;
		
	}
	
	//Should only EVER be called by LogicSystem
	//This is where we check for any AI_Movables that want to move.
	//If they want to move, then this module tries it's best to move them based
	//on collision rules.
	@Override
	protected void update() {
		//Create a temporary array of AI_Movables
		ArrayList<AI_Movable> moveables = new ArrayList<AI_Movable>();
		//Get all the current maps gameObjects and check if any are
		//implementing AI_Movable then add them to that array just above
		for(GameObject o : game.logicSys.currentMap.gameObjects) {
			if(o instanceof AI_Movable) moveables.add((AI_Movable)o);
		}
		//Check if the movable wants to move, if it does, then request move for it
		for(AI_Movable moveable : moveables) {
			if(moveable.wantsToMove()) {
				this.requestMove(moveable, moveable.getDX(), moveable.getDY());
			}
		}
	}
}
