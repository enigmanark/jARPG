package com.ceirenthir.game.core.logic.events;

import com.badlogic.gdx.Gdx;

//Teleport event that's handled by MapController
public class Teleport extends Event {
	public String name;
	public int xTile;
	public int yTile;
	
	public Teleport(String mapName, int x, int y) {
		super("Teleport");
		this.name = mapName;
		xTile = x;
		yTile = y;
		Gdx.app.debug("Teleport", "Teleported to xTile " + xTile + ", yTile: " + yTile);
	}
}
