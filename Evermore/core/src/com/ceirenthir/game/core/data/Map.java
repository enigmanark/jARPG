package com.ceirenthir.game.core.data;
/*
 * The map class is a helper class that contains information about the world. It contains
 * what NPCs are stored here, the .tmx map and among other things. The data is generated
 * by the MapLoader utility class.
 */
import java.util.ArrayList;

import gameObjects.Actor_NPC;
import gameObjects.GameObject;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.ceirenthir.game.core.IF.Static;

public class Map {
	public String mapLocation;
	public TiledMap tmxMap;
	
	public int startX;
	public int startY;
	
	public int width;
	public int height;
	
	public String name;
	public MapProperties mapProperties;
	public OrthogonalTiledMapRenderer mapRender;
	
	public Array<GameObject> gameObjects;
	public ArrayList<Actor_NPC> npcs;
	public Array<Static> statics;
	
	//All map layers
	public Array<TiledMapTileLayer> layers;
	
	//Only background layers
	public Array<TiledMapTileLayer> backgroundLayers;
	public int backgroundLayerCount = 0;
	
	//Only foreground layers
	public Array<TiledMapTileLayer> foregroundLayers;
	public int foregroundLayerCount;
	
	//The constructor takes the tmxMaps file path as the param
	public Map (String mapLocation) {
		this.mapLocation = mapLocation;
		
		gameObjects = new Array<GameObject>();
		npcs = new ArrayList<Actor_NPC>();
		statics = new Array<Static>();
		
		layers = new Array<TiledMapTileLayer>();
		backgroundLayers = new Array<TiledMapTileLayer>();
		foregroundLayers = new Array<TiledMapTileLayer>();
	}

	//Call this method from LogicSystem to load the maps assets, when you need to render 
	//the map because the player is moving to this map.
	public void loadMap() {
		tmxMap = new TmxMapLoader().load(mapLocation);
		//applyFilter();
		mapRender = new OrthogonalTiledMapRenderer(tmxMap);
	}
	//Return the MapRenderer object for drawing by the GraphicSystem
	public OrthogonalTiledMapRenderer getRenderer() {
		return mapRender;
	}
	//Called from LogicSystem because the player has left the map thus this map is no
	//longer being rendered, and the textures can be unloaded.
	public void unloadMap() {
		//TODO Unload GameObjects too
		if(tmxMap != null) tmxMap.dispose();
		if(mapRender != null) mapRender.dispose();
	}
}
