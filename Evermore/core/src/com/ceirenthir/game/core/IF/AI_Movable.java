package com.ceirenthir.game.core.IF;

import com.badlogic.gdx.math.Rectangle;

/*
 * Only gameobjects that should be able to be moved around the map through the game itself
 * should implement this method. So far only classes that extend actor_humanoid can do this.
 */

public interface AI_Movable {
public boolean isMoving();
public void setMoving(boolean is);
public boolean wantsToMove();
public void setWantToMove(boolean does);
public boolean intersects(Rectangle r);
public void setX(float x);
public void setY(float y);
public float getX();
public float getY();
public float getDX();
public float getDY();
public String getFacing();
public void setFacing(String facing);
public float getSpeed();
public float getModSpeed();
public void setDX(float dx);
public void setDY(float dy);
public void setSpriteWalkStateTime(float time);
public Rectangle getMoveRect(float x, float y);
public Rectangle getRect();
public void setSpeed(float s);
}
