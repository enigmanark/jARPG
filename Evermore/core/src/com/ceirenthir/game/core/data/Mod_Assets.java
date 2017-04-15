package com.ceirenthir.game.core.data;

import java.util.HashMap;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.ceirenthir.game.core.Evermore;


 /* This Module loads, contains, and maintains all *sheets. Such as spriteSheets,
 * animationSheets and things like that. They're all loaded into TextureAtlases from
 * the packed images that are generated at startup. This is done in the init() method.
 * 
 * The init method loads and creates the TextureAtlases for the different sheets and stores
 * them in it's respective object. Also immediately after this, the method scans all files
 * in the original directory of the images to retrieve their names. It then removes the ".png"
 * from the fileName (because TextureAtlas do not use the format in the filename) to retrieve
 * the AtlasRegion of that specific image from the TextureAtlas. It then stores the AtlasRegion,
 * with the files name as the key, in a HashMap for quick retrieval.
 */

public class Mod_Assets {
	private String spriteSheetLocation = "spritesheets/";
	private String spriteSheetPackLocation = spriteSheetLocation + "packedImages/";
	private String spriteSheetPackName = "spriteSheets";
	
	private String iconPackLocation = "icons/packed/icons.pack";
	private String spriteListLocation = "data/spriteSheetList.txt";
	
	private TextureAtlas iconsAtlas;
	
	private TextureAtlas spritesAtlas;
	private HashMap<String, AtlasRegion> spriteSheets; 
	private HashMap<String, AtlasRegion> weaponSheets;
	
	private Evermore game;
	
	protected Mod_Assets(Evermore Evermore) {
		game = Evermore;
		spriteSheets = new HashMap<String, AtlasRegion>();
		weaponSheets = new HashMap<String, AtlasRegion>();
	}
	
	public AtlasRegion getIcon(String name) {
		if(name.contains(".png")) name = name.replace(".png", "");
		Gdx.app.debug("Asset Request", "Icon " + name + " requested.");
		AtlasRegion icon = iconsAtlas.findRegion(name);
		if(icon != null) return icon;
		else {
			Gdx.app.error("CRITICAL ERROR", "Icon from Mod_Assets" +
					" getIcon() returned null. Exiting game..");
			Gdx.app.exit();
			game.dispose();	
			System.exit(0);
			return null;
		}
	}
	
	public AtlasRegion[] getWalkingWeaponSheet(String name) {
		if(name.contains(".png")) name = name.replace(".png", "");
		Gdx.app.log("Asset Request", "weaponSheet" + name + " requested.");
		AtlasRegion walkWeaponNorth = weaponSheets.get("W-" + name + "-N");
		AtlasRegion walkWeaponSouth = weaponSheets.get("W-" + name + "-S");
		AtlasRegion walkWeaponWest = weaponSheets.get("W-" + name + "-W");
		AtlasRegion walkWeaponEast = weaponSheets.get("W-" + name + "-E");
		
		AtlasRegion[] weaponSheet = new AtlasRegion[4];
		weaponSheet[0] = walkWeaponNorth;
		weaponSheet[1] = walkWeaponSouth;
		weaponSheet[2] = walkWeaponEast;
		weaponSheet[3] = walkWeaponWest;
		
		if(walkWeaponNorth != null && walkWeaponSouth != null && walkWeaponWest != null &&
				walkWeaponEast != null) return weaponSheet;
		else {
			Gdx.app.error("CRITICAL ERROR", "WeaponSheet from Mod_Assets" +
					" getWalkingWeaponSheet() returned null. Exiting game..");
			Gdx.app.exit();
			game.dispose();	
			System.exit(0);
			return null;
		}
	}
	
	public AtlasRegion[] getWalkSheet(String name) {
		if(name.contains(".png")) name = name.replace(".png", "");
		
		Gdx.app.debug("Asset Request", "walksheet " + name + " requested.");
		AtlasRegion spriteSheetNorth = spriteSheets.get(name + "-N");
		AtlasRegion spriteSheetSouth = spriteSheets.get(name + "-S");
		AtlasRegion spriteSheetWest = spriteSheets.get(name + "-W");
		AtlasRegion spriteSheetEast = spriteSheets.get(name + "-E");
		
		AtlasRegion[] walkSheet = new AtlasRegion[4];
		walkSheet[0] = spriteSheetNorth;
		walkSheet[1] = spriteSheetSouth;
		walkSheet[2] = spriteSheetEast;
		walkSheet[3] = spriteSheetWest;
		
		if(spriteSheetNorth != null && spriteSheetSouth != null &&
				spriteSheetWest != null && spriteSheetEast != null) return walkSheet;
		else {
			Gdx.app.error("CRITICAL ERROR", "SpriteSheet from Mod_Assets" +
					" getSpriteSheet() returned null. Exiting game..");
			Gdx.app.exit();
			game.dispose();	
			System.exit(0);
			return null;
		}
	}
	
	public void init() {
		//Load assets into assetManager
		
		//Create TextureAtlas from packed icons
		iconsAtlas = new TextureAtlas(Gdx.files.internal(iconPackLocation));
		
		//Create a TextureAtlas from the packed spriteSheets
		spritesAtlas = new TextureAtlas(Gdx.files.internal(spriteSheetPackLocation + spriteSheetPackName + ".atlas"));

		//Start creating keys for spriteSheets
		
		//Loop over all filenames of spriteSheets and create a AtlasRegion from their names
		//Then store the atlas region in a hashmap with the names as the key
		Gdx.app.debug("Assets", "Attempting to read spriteSheetList at " + spriteListLocation);
		//Get the spriteList text document with list of all spritesheets
		FileHandle sl = Gdx.files.internal(spriteListLocation);
		//Turn the file into a large string
		String s = sl.readString();
		Scanner reader;
		reader = new Scanner(s);
		int c = 0;
		//Loop through the string from the text document
		while(reader.hasNext()) {
			c++;
			//Read the filename
			String fileName = reader.next();
			if(!fileName.equalsIgnoreCase("")) {
				/*
				 * Get the files name, then remove the ".png" from it because
				 * TextureAtlas only uses the actual files names, and not the format.
				 */
				fileName = fileName.replace(".png", "");
				if(fileName.startsWith("W-")) {
					//Is a weapons sheet
					weaponSheets.put(fileName, spritesAtlas.findRegion(fileName));
				}
				else {
					//Assume is a walking sheet
					spriteSheets.put(fileName, spritesAtlas.findRegion(fileName));
				}
			}
		}
		reader.close();
		Gdx.app.log("Assets", Integer.toString(c) + " Spritesheets loaded.");
	}
	
	public void dispose() {
		this.spritesAtlas.dispose();
	}
}
