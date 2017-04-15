package gameObjects;

import com.badlogic.gdx.math.MathUtils;

/*
 * Parent class for all MapObjects that have facings which are for sprites and
 * whatnot.
 */

public abstract class Entity extends GameObject {	
	public String facing = "North";
	
	public Entity(float x, float y) {
		super(x, y);
		randomFace();
	}
	
	public void randomFace() {
		int random = MathUtils.random(4) + 1;
		if(random == 1) facing = "North";
		else if(random == 2) facing = "South";
		else if(random == 3) facing = "West";
		else if(random == 4) facing = "East";
	}
	
	public Entity() {
		randomFace();
	}
	
	public void setFacing(String facing) {
		this.facing = facing;
	}
	
	public boolean isFacingNorth() {
		if(facing.equalsIgnoreCase("North")) {
			return true;
		} else return false;
	}
	
	public boolean isFacingSouth() {
		if(facing.equalsIgnoreCase("South")) {
			return true;
		} else return false;
	}
	
	public boolean isFacingWest() {
		if(facing.equalsIgnoreCase("West")) {
			return true;
		} else return false;
	}
	
	public boolean isFacingEast() {
		if(facing.equalsIgnoreCase("East")) {
			return true;
		} else return false;
	}
}
