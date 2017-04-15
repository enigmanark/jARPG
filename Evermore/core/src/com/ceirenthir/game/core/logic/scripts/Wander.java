package com.ceirenthir.game.core.logic.scripts;

import gameObjects.GameObject;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.utils.TimeUtils;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.AI_Movable;

/*
 * ONLY ATTACH THIS SCRIPT TO AN OBJECT THAT EXTENDS ACTOR_HUMANOID
 * This script makes the humanoid object move around randomly.
 * 
 * This script is handled by the Mod_Movement_GameObjects
 * look there for how this script is handled.
 */

//TODO This is broken somewhere. Sometimes NPCs will change their direction while still moving and will move faster

public class Wander extends Script {
	private AI_Movable host;
	private final long frequency;
	private long lastWanderTime;
	public float moveByX = 0;
	public float moveByY = 0;
	private float oldX;
	private float oldY;
	public float speed = 60f;
	
	public Wander(AI_Movable movable, int frequencyInSeconds) {
		frequency = frequencyInSeconds * 1000000000L;
		host = movable;
	}
	
	public Wander(GameObject gObject) {
		frequency = 10 * 1000000000L;
	}
	
	@Override
	public void update(Evermore game, Iterator<Script> scriptIter) {
		checkIfMoved();
		moveAgain();
	}
	
	private void checkIfMoved() {
		if(host.isMoving()) {
			host.setSpeed(speed);
			if(host.getX() >= (moveByX + oldX)  && host.getY() >= (moveByY + oldY)) {
				host.setDX(0);
				host.setDY(0);
				oldX = 0;
				oldY = 0;
				host.setMoving(false);
				host.setWantToMove(false); 
			}
		}
	}
	
	private void moveAgain() {
		if(TimeUtils.nanoTime() - lastWanderTime > frequency) {
			Random rand = new Random();
			int xOry = rand.nextInt(2);
			if(xOry == 1) {
				int leftOrRight = rand.nextInt(2);
				host.setWantToMove(true);
				moveByX = rand.nextInt(25);
				oldX = host.getX();
				if(leftOrRight == 1) host.setDX(2);
				else host.setDX(-1);
				host.setDY(0);
				moveByY = 0;
			} else {
				int UpOrDown = rand.nextInt(2);
				host.setWantToMove(true);
				moveByY = rand.nextInt(25);
				if(UpOrDown == 1) host.setDY(1);
				else host.setDY(-1);
				oldY = host.getY();
				host.setDX(0);
				moveByX = 0;
			}
			
			lastWanderTime = TimeUtils.nanoTime();
		}
	}
	
	public AI_Movable getHost() {
		return host;
	}
}
