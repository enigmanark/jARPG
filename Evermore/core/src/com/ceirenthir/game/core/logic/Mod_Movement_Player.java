package com.ceirenthir.game.core.logic;

/*
 * This Module of LogicSystem handles the player's movement. It receives movement request
 * from the input handler, and it checks for collisions. If there movement is allowed,
 * this module executes that movement and updates everything accordingly. Basically, this
 * module does EVERYTHING related to the logic of player movement.
 */

import gameObjects.Entity_Animated;
import gameObjects.Actor_Player;
import gameObjects.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.Static;
import com.ceirenthir.game.core.logic.events.Event;

public class Mod_Movement_Player extends Mod_Logic {
	final Evermore game;
	Actor_Player player;
	private boolean moving = false;
	private long lastMoveTime = 0L;

	protected Mod_Movement_Player(Evermore game) {
		this.game = game;
	}

	@Override
	protected void post(Evermore game, Event e) {

	}

	//Makes the player move
	private void move(float dx, float dy) {
		this.player = game.dataSys.player;

		if(dx == 0 && dy > 0) player.facing = "North";
		else if(dx == 0 && dy < 0) player.facing = "South";
		else if(dx > 0 && dy == 0) player.facing = "East";
		else if(dx < 0 && dy == 0) player.facing = "West";
		else if(dx > 0 && dy > 0) player.facing = "North East";
		else if(dx < 0 && dy > 0) player.facing = "North West";
		else if(dx < 0 && dy < 0) player.facing = "South West";
		else if(dx > 0 && dy < 0) player.facing = "South East";
		else {
			Gdx.app.debug("Actor Error","Actors facing is wrong.");
		}
		float x = player.getX();
		float y = player.getY();
		float speed = player.getSpeed();
		float modSpeed = player.getModSpeed();
		x += (((dx * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		y += (((dy * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		player.setX(x);
		player.setY(y);
		lastMoveTime = TimeUtils.nanoTime();
	}

	private void moveX(float dx, float dy) {
		this.player = game.dataSys.player;

		if(dx == 0 && dy > 0) player.facing = "North";
		else if(dx == 0 && dy < 0) player.facing = "South";
		else if(dx > 0 && dy == 0) player.facing = "East";
		else if(dx < 0 && dy == 0) player.facing = "West";
		else if(dx > 0 && dy > 0) player.facing = "North East";
		else if(dx < 0 && dy > 0) player.facing = "North West";
		else if(dx < 0 && dy < 0) player.facing = "South West";
		else if(dx > 0 && dy < 0) player.facing = "South East";
		else {
			Gdx.app.debug("Actor Error","Actors facing is wrong.");
		}

		float x = player.getX();
		float speed = player.getSpeed();
		float modSpeed = player.getModSpeed();
		x += (((dx * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		player.setX(x);
		lastMoveTime = TimeUtils.nanoTime();

	}

	private void moveY(float dx, float dy) {
		this.player = game.dataSys.player;

		if(dx == 0 && dy > 0) player.facing = "North";
		else if(dx == 0 && dy < 0) player.facing = "South";
		else if(dx > 0 && dy == 0) player.facing = "East";
		else if(dx < 0 && dy == 0) player.facing = "West";
		else if(dx > 0 && dy > 0) player.facing = "North East";
		else if(dx < 0 && dy > 0) player.facing = "North West";
		else if(dx < 0 && dy < 0) player.facing = "South West";
		else if(dx > 0 && dy < 0) player.facing = "South East";
		else {
			Gdx.app.debug("Actor Error","Actors facing is wrong.");
		}

		float y = player.getY();
		float speed = player.getSpeed();
		float modSpeed = player.getModSpeed();
		y += (((dy * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		player.setY(y);
		lastMoveTime = TimeUtils.nanoTime();
	}
	//Give a DX and a DY and it'll calculate where the player
	//will move to and return the x and y in a 2 index array
	//with x being index 0 and y being index 1
	protected float[] getMoveToCoordinates(float dx, float dy) {
		float x = player.getX();
		float y = player.getY();
		float speed = player.getSpeed();
		float modSpeed = player.getModSpeed();
		x += (((dx * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		y += (((dy * speed) * modSpeed) * Gdx.graphics.getDeltaTime());
		float[] coords = new float[2];
		coords[0] = x;
		coords[1] = y;
		return coords;
	}
	//Call to stop the player from moving
	protected void stopMoving() {
		player.setDX(0);
		player.setDY(0);
		player.setSpriteWalkStateTime(0);
		player.setMoving(false);
	}

	@Override
	protected void init() {

	}

	//Called by InputHandler to request moving the player
	protected void requestMove(float dx, float dy) {
		float moveX = (dx);
		float moveY = (dy);
		boolean[] movePermissions = canMove(moveX, moveY);
		boolean moveBothPerm = movePermissions[0];
		boolean moveXPerm = movePermissions[1];
		boolean moveYPerm = movePermissions[2];
		if(moveBothPerm) {
			move(dx, dy);
			player.setMoving(true);
			moving = true;
		}
		else if(moveXPerm) {
			moveX(dx, dy);
			player.setMoving(true);
			moving = true;
		}
		else if(moveYPerm) {
			moveY(dx, dy);
			player.setMoving(true);
			moving = true;
		}
		//If player makes it here, no moving allowed
	}

	//checks if player can move to the requested coordinates
	//It returns 3 booleans in an array.
	//Index 0 is for using move() both x and y.
	//Index 1 is for using moveX(), can only move x
	//Index 2 is for using moveY(), can only move y
	private boolean[] canMove(float dx, float dy) {
		boolean[] movePermissions = {true, true, true};

		//test both x and y
		float[] moveToCoords = this.getMoveToCoordinates(dx, dy);
		float x = moveToCoords[0];
		float y = moveToCoords[1];
		
		//Player rect modified for movement
		Rectangle rect = player.getMoveRect(x, y);
		
		//test solid tile collision for x and y
		for(Static solid : game.logicSys.currentMap.statics) {
			if(solid.intersects(rect)) {
				movePermissions[0] = false;
				break;
			}
		}
		//test game object collision for both x and y
		if(movePermissions[0] != false) {
			for(GameObject object : game.logicSys.currentMap.gameObjects) {
				if(object.isSolid()) {
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
		}

		if(movePermissions[0] == true) {
			//Gdx.app.debug("Mod_Movement_Player", "Move permissions 0 returning, x and y movement allowed.");
			return movePermissions;
		}

		//test only x
		rect = new Rectangle();
		moveToCoords = this.getMoveToCoordinates(dx, 0);
		x = moveToCoords[0];
		y = moveToCoords[1];
		rect.width = game.dataSys.player.getRect().getWidth() / 2;
		rect.height = game.dataSys.player.getRect().getHeight() / 5;
		rect.x = (x + (rect.width/3));
		rect.y = y;
		//test solid tile collison
		for(Static solid : game.logicSys.currentMap.statics) {
			if(solid.intersects(rect)) {
				movePermissions[1] = false;
				//Gdx.app.debug("Mod_Movement_Player", "Collided with static on x axis. X not allowed.");
				break;
			}
		}
		//test game object collision for only x
		if(movePermissions[1] != false) {
			for(GameObject object : game.logicSys.currentMap.gameObjects) {
				if(object.isSolid()){
					if(object instanceof Entity_Animated) {
						Entity_Animated actor = (Entity_Animated) object;
						if(actor.getWalkCollisionRect().overlaps(rect)) {
							//Gdx.app.debug("Mod_Movement_Player", "Collided with NPC on x axis. x not allowed");
							movePermissions[1] = false;
							break;
						}
					}
					else if(object.intersects(rect)) {
						//Gdx.app.debug("Mod_Movement_Player", "Collided with object on x axis. x not allowed.");
						movePermissions[1] = false;
						break;
					}
				}
			}
		}

		if(movePermissions[1] == true) {
			movePermissions[2] = false;
			//Gdx.app.debug("Mod_Movement_Player", "Move permissions 1 returning, x allowed.");
			return movePermissions;
		}

		//test only y
		rect = new Rectangle();
		moveToCoords = this.getMoveToCoordinates(0, dy);
		x = moveToCoords[0];
		y = moveToCoords[1];
		rect.width = game.dataSys.player.getRect().getWidth() / 2;
		rect.height = game.dataSys.player.getRect().getHeight() / 5;
		rect.x = (x + (rect.width/3));
		rect.y = y;
		//test solid tile collision
		for(Static solid : game.logicSys.currentMap.statics) {
			if(solid.intersects(rect)) {
				movePermissions[2] = false;
				//Gdx.app.debug("Mod_Movement_Player", "Collided with static on y axis. Y not allowed.");
				break;
			}
		}
		//test game object collision for both x and y
		if(movePermissions[2] != false) {
			for(GameObject object : game.logicSys.currentMap.gameObjects) {
				if(object.isSolid()) {
					if(object instanceof Entity_Animated) {
						Entity_Animated actor = (Entity_Animated) object;
						if(actor.getWalkCollisionRect().overlaps(rect)) {
							movePermissions[2] = false;
							//Gdx.app.debug("Mod_Movement_Player", "Collided with NPC on Y axis. Y not allowed.");
							break;
						}
					}
					else if(object.intersects(rect)) {
						movePermissions[2] = false;
						//Gdx.app.debug("Mod_Movement_Player", "Collided with object on Y axis. Y not allowed");
						break;
					}
				}
			}
		}

		if(movePermissions[2] == true) {
			//Gdx.app.debug("Mod_Movement_Player", "Returning move permissions 2. Y axis allowed.");
			return movePermissions;
		}
		//If it makes it here, no moving allowed so return all false
		return movePermissions;

	}
	//Should only EVER be called by LogicSystem
	@Override
	protected void update() {
		/*
		 * Update player movement stuff
		 */
		this.player = game.dataSys.player;
		if(player != null) {
			//Check to stop moving
			if(moving) {
				if(TimeUtils.nanoTime() - lastMoveTime > (1000000000 / 20)) {
					moving = false;
					stopMoving();
				}
			}
		}
	}
}
