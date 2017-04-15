package com.ceirenthir.game.core.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;

/*
 * Utility class for desktop only. This utility must be run at least ONCE after any asset changes
 * are made. This class scans most of the assets in the game and makes lists of them that have to
 * be used for loading those assets. This cannot be done on Android and iOS so it must be done
 * on desktop.
 */

public class Asset_Finder {
	public final static String spriteSheetLocation = "/home/johnathan/workspace/Evermore/android/assets/spritesheets";
	public final static String mapLocation = "/home/johnathan/workspace/Evermore/android/assets/tiledmaps/";
	public final static String dataLocation = "/home/johnathan/workspace/Evermore/android/assets/data/";
	
	public static void findTiledMaps() {
		File tiledMapDir = new File(Gdx.files.internal(mapLocation).path());
		if(tiledMapDir.isDirectory()) {
			File[] listOfMaps = tiledMapDir.listFiles();
			File mapList = Gdx.files.absolute(dataLocation + "mapList.txt").file();
			mapList.delete();
			try {
				mapList.createNewFile();
				FileWriter fw = new FileWriter(mapList);
				BufferedWriter writer = new BufferedWriter(fw);
				for(int i = 0; i < listOfMaps.length; i++) {
					if(listOfMaps[i].isFile()) {
						/*
						 * Get the files name, then remove the ".png" from it because
						 * TextureAtlas only uses the actual files names, and not the format.
						 */
						String fileName = listOfMaps[i].getName();
						//Now write the filename to the spriteList file
						writer.write(fileName);
						writer.newLine();
					}
				}
				writer.close();
				Gdx.app.log("Assets", "Maps scanned and list written to android assets data.");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			Gdx.app.error("CRITICAL ERROR", tiledMapDir.getAbsolutePath() +
					" is not the correct file path to the spritesheet directory.");
			Gdx.app.error("CRITICAL ERROR",
					"Spritesheet directory isn't correct. Exiting game..");
			Gdx.app.exit();
			System.exit(0);
		}
		//End creating keys for spriteSheets
	}
	
	public static void findSpriteSheets() {
		File charSheetDir = new File(spriteSheetLocation);
		if(charSheetDir.isDirectory()) {
			File[] listOfCharsheets = charSheetDir.listFiles();
			File spriteList = Gdx.files.absolute
					(dataLocation + "spriteSheetList.txt").file();
			spriteList.delete();
			try {
				spriteList.createNewFile();
				FileWriter fw = new FileWriter(spriteList);
				BufferedWriter writer = new BufferedWriter(fw);
				for(int i = 0; i < listOfCharsheets.length; i++) {
					if(listOfCharsheets[i].isFile()) {
						/*
						 * Get the files name, then remove the ".png" from it because
						 * TextureAtlas only uses the actual files names, and not the format.
						 */
						String fileName = listOfCharsheets[i].getName();
						//Now write the filename to the spriteList file
						writer.write(fileName);
						writer.newLine();
					}
				}
				writer.close();
				Gdx.app.log("Assets", "Spritesheets scanned and list written to android assets data.");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			Gdx.app.error("CRITICAL ERROR", charSheetDir.getAbsolutePath() +
					" is not the correct file path to the spritesheet directory.");
			Gdx.app.error("CRITICAL ERROR",
					"Spritesheet directory isn't correct. Exiting game..");
			Gdx.app.exit();
			System.exit(0);
		}
		//End creating keys for spriteSheets
	}
}
