package com.ceirenthir.game.core.util;

import gameObjects.Actor_NPC;
import gameObjects.GameObject;
import gameObjects.Tile_Activatable;
import gameObjects.Tile_Collidable;
import gameObjects.Tile_Static;
import gameObjects.Tile_Updatable;

import java.util.Iterator;
import java.util.Scanner;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.utils.Array;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.AI_Movable;
import com.ceirenthir.game.core.IF.Activatable;
import com.ceirenthir.game.core.data.DataSystem;
import com.ceirenthir.game.core.data.Items;
import com.ceirenthir.game.core.data.Map;
import com.ceirenthir.game.core.data.QuestsType;
import com.ceirenthir.game.core.data.Enemies;
import com.ceirenthir.game.core.logic.events.Message;
import com.ceirenthir.game.core.logic.scripts.MessageScript;
import com.ceirenthir.game.core.logic.scripts.QuestStart;
import com.ceirenthir.game.core.logic.scripts.Script;
import com.ceirenthir.game.core.logic.scripts.TeleportScript;
import com.ceirenthir.game.core.logic.scripts.Wander;
/*
 * The Map Loader is the main content generator of the gae. The Map Loader scans all maps created
 * in tiled from assets/TiledMaps. It first makes map objects from the the scanned map. It then
 * divides the maps tiled layers into Background and Foreground layers. Then the loader scans
 * the Object Layer to find all map objects such as NPCS, and teleport tiles and other things
 * like that. When it finds map objects, it checks the objects properties and determines by
 * a predefined methodology, what these properties are. For instance you can assign scripts
 * to NPCs from Tiled by using the proper protocol. When it finds these map objects and properties
 * it automatically creates MapObjects such as NPCs and whatnot from this scanned data and injects
 * them into the game.
 */
public class MapLoader2 {

	private static String mapLocation = Asset_Finder.mapLocation;
	private static String mapListLocation = "resources/data/mapList.txt";

	//Scan the tiledmap folder and find all maps then begin construction of them.
	public static void load(Evermore game) {
		Gdx.app.log("Map Loader", "Loading maps...");
		
		if(Gdx.app.getType() == ApplicationType.Android) {
			Gdx.app.log("Map Loader", "Platform is Android. Changing asset locations.");
			mapLocation = Gdx.files.internal("tiledmaps/").path();
			mapListLocation = Gdx.files.internal("data/mapList.txt").path();
		}
		else {
			Gdx.app.log("Map Loader", "Platform is Desktop. Leaving asset locations at default.");
		}
		//Get the text document containing the list of all tiledmaps
		FileHandle mapList = Gdx.files.internal(mapListLocation);
		Scanner reader = new Scanner(mapList.readString());
		//Loop through each line of the mapList doc
		while(reader.hasNext()) {
			//Read the map name
			String mapFileName = reader.next();
			//use the map name and maps location to load the maps
			loadMap(game.dataSys, new Map(mapLocation + "/" + mapFileName), mapFileName);
		}
		//Close the scanner
		reader.close();
		//Get the starter map
		loadCurrentMap(game);
		Gdx.app.log("Map Loader", "Map loading complete.");
	}

	//Load the starter map
	private static void loadCurrentMap(Evermore game) {
		//Loop over fully constructed maps
		for(Map map : game.dataSys.mapDB.getMaps()) {
			//Find the first occurence of a map with the startermap property and set it
			//as the first map
			if(map.mapProperties.get("StarterMap") != null) {
				game.logicSys.currentMap = map;
			}
		}
	}

	//Loads the maps and calls all the methods to find all of the cool stuff.
	//Unloads the maps assets at the end then automatically adds the map
	//to the database for you.
	private static void loadMap(DataSystem dataSys, Map map, String mapFileName) {
		TmxMapLoader mapLoader = new TmxMapLoader();
		Parameters params = new Parameters();
		//FIlter the maps textures
		params.textureMagFilter = TextureFilter.Nearest;
		params.textureMinFilter = TextureFilter.Linear;
		//Load the tmxMap
		map.tmxMap = mapLoader.load(map.mapLocation, params);
		//Retrieve the maps name from the the mapFileName
		String mapName = mapFileName.split(".tmx")[0];
		map.name = mapName;
		//Retrieve the map properties and store them
		map.mapProperties = map.tmxMap.getProperties();
		map.width = Integer.parseInt(map.mapProperties.get("width").toString());
		map.height = Integer.parseInt(map.mapProperties.get("height").toString());

		//Get background and foreground layers
		populateLayers(map);
		//Get all static tiles
		findStaticSolids(map);
		//Find MapObjects and create them and store them
		findObjects(dataSys, map);
		
		//Dispose of the tmxMap because it's an asset that may not be needed right now.
		map.unloadMap();

		//Add the map to the map database
		dataSys.mapDB.add(map);
	}

	//Loop through backgroundLayers and foreground Layers and find any tiles in the cells 
	//that contain the property Solid or kind thereof, then create Tile_Collidable objects with the 
	//coordinates of the cell, set it as Solid and add it to the statics array of the map.
	private static void findStaticSolids(Map map) {
		//Loop through background layers looking for static objects
		for(TiledMapTileLayer layer : map.backgroundLayers) {
			int row = layer.getWidth();
			int column = layer.getHeight();
			for(int x = 0; x < row; x++) {
				for(int y = 0; y < column; y++) {
					//Get the current looped cell
					Cell cell = layer.getCell(x, y);
					//Make sure the cell is something
					if(cell != null) {
						//If it contains the property solid..
						if(cell.getTile().getProperties().containsKey("Solid")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsTileWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property LSolidHalfWidth
						else if(cell.getTile().getProperties().containsKey("LSolidHalfWidth")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsLeftHalfWidthWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property RSolidHalfWidth
						else if(cell.getTile().getProperties().containsKey("RSolidHalfWidth")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsRightHalfWidthWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property USolidHalfHeight
						else if(cell.getTile().getProperties().containsKey("USolidHalfHeight")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsUpHalfHeightWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property DSolidHalfHeight
						else if(cell.getTile().getProperties().containsKey("DSolidHalfHeight")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsDownHalfHeightWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property DSolidQuarterHeight
						else if(cell.getTile().getProperties().containsKey("DSolidQuarterHeight")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsDownQuarterHeightWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property USolidQuarterHeight
						else if(cell.getTile().getProperties().containsKey("USolidQuarterHeight")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsUpQuarterHeightWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property LSolidQuarterWidth
						else if(cell.getTile().getProperties().containsKey("LSolidQuarterWidth")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsLeftQuarterWidthWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property RSolidQuarterWidth
						else if(cell.getTile().getProperties().containsKey("RSolidQuarterWidth")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsRightQuarterWidthWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
					}
				}
			}
		}
		//Now loop over and check the foreground layers for statics
		for(TiledMapTileLayer layer : map.foregroundLayers) {
			int row = layer.getWidth();
			int column = layer.getHeight();
			//This was row - 1 and column - 1, putting that here in case this breaks
			for(int x = 0; x < row; x++) {
				for(int y = 0; y < column; y++) {
					Cell cell = layer.getCell(x, y);
					//Make sure the cell is something
					if(cell != null) {
						//If it contains the property solid..
						if(cell.getTile().getProperties().containsKey("Solid")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsTileWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property LSolidHalfWidth
						else if(cell.getTile().getProperties().containsKey("LSolidHalfWidth")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsLeftHalfWidthWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property RSolidHalfWidth
						else if(cell.getTile().getProperties().containsKey("RSolidHalfWidth")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsRightHalfWidthWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property USolidHalfHeight
						else if(cell.getTile().getProperties().containsKey("USolidHalfHeight")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsUpHalfHeightWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property DSolidHalfHeight
						else if(cell.getTile().getProperties().containsKey("DSolidHalfHeight")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsDownHalfHeightWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property DSolidQuarterHeight
						else if(cell.getTile().getProperties().containsKey("DSolidQuarterHeight")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsDownQuarterHeightWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property USolidQuarterHeight
						else if(cell.getTile().getProperties().containsKey("USolidQuarterHeight")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsUpQuarterHeightWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property LSolidQuarterWidth
						else if(cell.getTile().getProperties().containsKey("LSolidQuarterWidth")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsLeftQuarterWidthWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
						//If it contains the property RSolidQuarterWidth
						else if(cell.getTile().getProperties().containsKey("RSolidQuarterWidth")) {
							//Create a virtual representation of the tile so we can
							//check for collision against it
							Tile_Static staticSolid= new Tile_Static();
							staticSolid.setAsRightQuarterWidthWithTileCoordinates(x, y);
							//Then add it to the statics array for quick access for collision
							//testing
							map.statics.add(staticSolid);
						}
					}
				}
			}
		}
		Gdx.app.debug("Map Loader","Map " + map.name + " Statics: " + map.statics.size);
	}

	private static void processWalkables(Map map, Array<MapObject> walkables) {
		int count = 0;
		for(int i = 0; i < walkables.size; i++) {
			//Get the objects xposition
			String xString = walkables.get(i).getProperties().get("x").toString();
			float x = Float.parseFloat(xString);
			//Get the objects yposition
			String yString = walkables.get(i).getProperties().get("y").toString();
			float y = Float.parseFloat(yString);
			for(int j = 0; j < map.statics.size; j++) {
				if(map.statics.get(j).getStaticRect().contains(x + 1, y + 1)) {
					count++;
					map.statics.removeIndex(j);
				}
			}
		}
		Gdx.app.log("Map Loader", "Map " + map.name + 
				" Made " + Integer.toString(count) + " tiles walkable.");
	}
	
	//Find all objects such as NPCs and whatnot. Create those objects, then
	//load them into the databases and the maps arrays for quick reference.
	//Then at the end if any of the gameobjects were of type Walkable, add them
	//to an array and process them to remove the static rectangles in the same location.
	private static void findObjects(DataSystem dataSys, Map map) {
		//Get the object layer
		MapLayer objectLayer = map.tmxMap.getLayers().get("Objects");
		//Make sure there is an object layer
		if(objectLayer == null) return;
		//Array for storing walkable gameobjects
		Array<MapObject> walkables = new Array<MapObject>();
		//Loop over game objects
		for(MapObject object : objectLayer.getObjects()) {
			//Get the object's name and the object's type
			String objectName = object.getName().toString();
			String objectType = object.getProperties().get("type").toString();
			//Get the objects xposition
			String xString = object.getProperties().get("x").toString();
			float x = Float.parseFloat(xString);
			//Get the objects yposition
			String yString = object.getProperties().get("y").toString();
			float y = Float.parseFloat(yString);

			//Create a generic GameObject from the found object
			GameObject gObject = new GameObject();
			//Set the object's name for id
			gObject.setName(objectName);

			//Objects with type "Position"
			if(objectType.equalsIgnoreCase("Position")) {
				//Set map starting position for testing
				if(objectName.equalsIgnoreCase("Start")) {
					map.startX = (int)x;
					map.startY = (int)y;
				}
			}
			
			if(objectType.equalsIgnoreCase("Walkable")) {
				walkables.add(object);
			}
			
			//Objects with type "NPC"
			else if(objectType.equalsIgnoreCase("NPC")) {
				//We don't support GameObject's with the same name yet
				if(dataSys.npcDB.npcExistsByName(objectName)) {
					Gdx.app.error("Map Loader", "Map " + map.name + 
							" Using the same NPC twice is not supported yet.");
				}
				else {
					//Since this has been determined to be an NPC use the method in GameObject
					//to copy the GameObjects so far found data into an NPC Object and return it
					Actor_NPC npc = gObject.castToNPC(gObject);
					
					//Check if it's a template
					if(object.getProperties().get("Template") != null) {
						npc = dataSys.npcDB.generateNPCByName(npc.getName());
						npc.loadEntity(dataSys.assets, x, y);
						dataSys.npcDB.addNPC(npc);
						map.gameObjects.add(npc);
						map.npcs.add(npc);
					}
					else {
						//Get the object's scripts
						getScripts(object, npc);

						//Get the NPCs spritesheet and set it
						npc.setSpriteSheetName(
								object.getProperties().get("Spritesheet").toString());
						//Load the NPC's assets like animations and stuff
						npc.loadEntity(dataSys.assets, x, y);

						//add the NPC to the npc DB then add it to the map's arrays for quick
						//access
						dataSys.npcDB.addNPC(npc);
						//add to gameobjects because it's parent is game object
						map.gameObjects.add(npc);
						//add to npcs because duh
						map.npcs.add(npc);
					}
				}
			}
			//If the object is of type Collide
			else if(objectType.equalsIgnoreCase("Collidable")) {
				//Cast the GameObject to a collide object from GameObjects cast to collide
				//object method, because we have determined specifically what this gameobject
				//is
				Tile_Collidable collide = gObject.castToCollidable(gObject);
				
				//Get the object's scripts
				getScripts(object, collide);
				
				//Propertly set the new Collide objects position by using the built in
				//setAsTile(x,y) method
				collide.setAsTile(x, y);
				if(object.getProperties().get("Solid") != null) {
					collide.setSolid(true);
				}
				//Add collidable to gameobjects because it's top parent is gameObject
				map.gameObjects.add(collide);
			}
			
			else if(objectType.equalsIgnoreCase("Activatable")) {
				//Cast the GameObject to a Activatable object from GameObjects cast to
				//activatable method, we have determined that this gameobject is one
				Tile_Activatable act = gObject.castToActivatable(gObject);
				
				//Get the object's scripts
				getScripts(object, act);
				
				act.setAsTile(x, y);
				if(object.getProperties().get("Solid") != null) {
					act.setSolid(true);
				}
				map.gameObjects.add(act);
			}
			
			else if(objectType.equalsIgnoreCase("Updatable")) {
				Tile_Updatable up = gObject.castToUpdatable(gObject);
				
				//Get the object's scripts
				getScripts(object, up);
				
				up.setAsTile(x, y);
				
				if(object.getProperties().get("Solid") != null) {
					up.setSolid(true);
				}
				map.gameObjects.add(up);
			}
		}
		//Process walkables
		processWalkables(map, walkables);
	}

	//Find the game objects scripts
	private static void getScripts(MapObject object, GameObject gObject) {
		//We're looking for Script1 first
		String script1 = "Script";
		//If it has script1
		if(object.getProperties().get(script1) != null) {
			//Get the script from the object and run determienScript to figure out
			//what the script is
			Script s = determineScript(object, script1, 
					object.getProperties().get("Script").toString(), gObject);
			//Make sure we get it! then add it
			if(s != null)gObject.addScript(s);
		}
		//Now we're looking for script2
		String script2 = "Script2";
		//If it has script2..
		if(object.getProperties().get(script2) != null) {
			//Get the script and again figure out what the script is
			Script s2 = determineScript(object, script2,
					object.getProperties().get("Script2").toString(), gObject);
			if(s2 != null)gObject.addScript(s2);
		}
		//^
		String script3 = "Script3";
		if(object.getProperties().get(script3) != null) {
			//^
			Script s3 = determineScript(object, script3,
					object.getProperties().get("Script3").toString(), gObject);
			//^
			if(s3 != null)gObject.addScript(s3);
		}
		String script4 = "Script4";
		if(object.getProperties().get(script4) != null) {
			Script s4 = determineScript(object, script4,
					object.getProperties().get("Script4").toString(), gObject);
			if(s4 != null)gObject.addScript(s4);
		}
		String script5 = "Script5";
		if(object.getProperties().get(script5) != null) {
			Script s5 = determineScript(object, script5,
					object.getProperties().get("Script5").toString(), gObject);
			if(s5 != null)gObject.addScript(s5);
		}
	}


	//Determine GameObject script and return it. This is a helper
	//method for getScripts()
	private static Script determineScript(MapObject object, String scriptNum, String s, GameObject gObject) {
		
		//We think it's a Message SCript because it starts with Message
		if(s.startsWith("Message:")) {
			if(gObject instanceof Activatable) {
				Activatable gObjectActivatable = (Activatable) gObject;
				//First get the string line from the Script's value
				String messageSplit = object.getProperties().get(scriptNum).toString();
				//Cut off the "Message: " part of the string so we only have the message itself
				String messageText = messageSplit.split(": ")[1];
				//Create the message script object and pass in the object's name and the message
				Message message = new Message(gObject.getName(), messageText);
				//Return the message to the getScripts() method
				return new MessageScript(gObjectActivatable, message);
			}

		}
		//We think it might be a wander script because it starts with "Wander: "
		else if(s.startsWith("Wander: ")) {
			//Make sure that the object this script is going to be attached to is of type
			//NPC because if it's not, well, we can't have that because the script depends
			//on NPC data right now.
			if(gObject instanceof AI_Movable) {
				AI_Movable gObjectAIMovable = (AI_Movable) gObject; 
				//Get the scripts' value string line
				String wanderSplit = object.getProperties().get(scriptNum).toString();
				//Split the stringline at :  so we only have the frequency value left.
				//Then parse it to get the actual value.
				int wanderFreq = Integer.parseInt(wanderSplit.split(": ")[1]);
				//Return the new wander script to getScripts()
				return new Wander(gObjectAIMovable, wanderFreq);
			} else {
				Gdx.app.error("Map Loader Script Error", 
						"Map " + object.getName() + 
						" Wander scripts can only be attached to objects that implement" +
						" AI_Movable.");
				return null;
			}

		}
		//Think it's a Teleport script
		else if(s.startsWith("Teleport: ")) {
			String teleSplit = object.getProperties().get(scriptNum).toString();
			//Split the string to remove the Teleport: part
			teleSplit = teleSplit.split("Teleport: ")[1];
			//Split the coordinates to get the map:MAPNAME toX:NUM and toY:NUM
			String[] teleCoords = teleSplit.split(" ");
			
			//Split the teleCoords to remove the toX: and toY:
			//then parse the integer to retrieve the coordinates
			String mapName = teleCoords[0].split("map:")[1];
			int toX = Integer.parseInt(teleCoords[1].split("toX:")[1]);
			int toY = Integer.parseInt(teleCoords[2].split("toY:")[1]);
			return new TeleportScript(mapName, toX, toY);
		}
	
		else if(s.startsWith("QuestStarter: ")) {
			String line = object.getProperties().get(scriptNum).toString();
			//Remove the script id part
			line = line.split("QuestStarter: ")[1];
			QuestStart starter = new QuestStart();
			starter.questGiver = gObject;
			String[] params = line.split(" ");
			for(String param : params) {
				if(param.startsWith("to:")) {
					String[] temp = param.split("to:");
					starter.to = temp[1];
				}
				else if(param.startsWith("type:")) {
					String[] temp = param.split("type:");
					starter.type = QuestsType.valueOf(temp[1]);
				}
				else if(param.startsWith("item:")) {
					String[] temp = param.split("item:");
					temp = temp[1].split("-");
					starter.item = Items.valueOf(temp[0]);
					starter.itemAmount = Integer.valueOf(temp[1]);
				}
				else if(param.startsWith("item2:")) {
					String[] temp = param.split("item2:");
					temp = temp[1].split("-");
					starter.item2 = Items.valueOf(temp[0]);
					starter.item2Amount = Integer.valueOf(temp[1]);
				}
				else if(param.startsWith("item3:")) {
					String[] temp = param.split("item3:");
					temp = temp[1].split("-");
					starter.item3 = Items.valueOf(temp[0]);
					starter.item3Amount = Integer.valueOf(temp[1]);
				}
				else if(param.startsWith("enemy:")) {
					String[] temp = param.split("enemy:");
					temp = temp[1].split("-");
					starter.enemy = Enemies.valueOf(temp[0]);
					starter.enemyAmount = Integer.valueOf(temp[1]);
				}
				else if(param.startsWith("enemy2:")) {
					String[] temp = param.split("enemy2:");
					temp = temp[1].split("-");
					starter.enemy2 = Enemies.valueOf(temp[0]);
					starter.enemy2Amount = Integer.valueOf(temp[1]);
				}
				else if(param.startsWith("enemy3:")) {
					String[] temp = param.split("enemy3:");
					temp = temp[1].split("-");
					starter.enemy3 = Enemies.valueOf(temp[0]);
					starter.enemy3Amount = Integer.valueOf(temp[1]);
				}
				else if(param.startsWith("reward:")) {
					String[] temp = param.split("reward:");
					temp = temp[1].split("-");
					starter.reward = Items.valueOf(temp[0]);
					starter.rewardAmount = Integer.valueOf(temp[1]);
				}
				else if(param.startsWith("reward2:")) {
					String[] temp = param.split("reward2:");
					temp = temp[1].split("-");
					starter.reward2 = Items.valueOf(temp[0]);
					starter.reward2Amount = Integer.valueOf(temp[1]);
				}
				else if(param.startsWith("reward3:")) {
					String[] temp = param.split("reward3:");
					temp = temp[1].split("-");
					starter.reward3 = Items.valueOf(temp[0]);
					starter.reward3Amount = Integer.valueOf(temp[1]);
				}
				else if(param.startsWith("map:")) {
					String[] temp = param.split("map:");
					starter.mapName = temp[1];
				}
			}
			return starter;
		}
		
		else {
			Gdx.app.error("MapLoader Script Error", "Map " + object.getName() + 
					" Could not determine script while loading map.");
			return null;
		}
		return null;

	}

	//Loop through the tmxMaps layers and find all layers that have either
	//Background or Foreground and add them to the respective arrays.
	private static void populateLayers(Map map) {
		Iterator<MapLayer> iter = map.tmxMap.getLayers().iterator();
		while(iter.hasNext()) {
			//Get the layer and add it to the all layers array
			try{
				TiledMapTileLayer layer = (TiledMapTileLayer) iter.next();
				map.layers.add(layer);

				//Check for background layer and add it to background Array
				if(layer.getProperties().containsKey("Background")) {
					map.backgroundLayers.add(layer);
					map.backgroundLayerCount++;
				}
				//Check for foreground layer and add it to foreground Array
				else if(layer.getProperties().containsKey("Foreground")) {
					map.foregroundLayers.add(layer);
					map.foregroundLayerCount++;
				}
			} catch(ClassCastException cast) { }
		}
		Gdx.app.log("Map Loader", "Map " + map.name + 
				" Layers: " + map.layers.size);
		Gdx.app.log("Map Loader","Map " + map.name + 
				" BackgroundLayers: " + map.backgroundLayers.size);
		Gdx.app.log("Map Loader","Map " + map.name + 
				" ForegroundLayers: " + map.foregroundLayers.size);

	}
}
