package com.ceirenthir.game.core.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.ceirenthir.game.core.Evermore;

public class DesktopLauncher {
	private static String spriteSheetLocation = "/home/johnathan/workspace/Evermore/android/assets/spritesheets/";
	private static String spriteSheetPackLocation = spriteSheetLocation + "packedImages/";
	private static String spriteSheetPackName = "spriteSheets";
	private static boolean dev = true;
	
	public static void main (String[] arg) {
		if(dev) {
			//Pack spritesheets into texture atlas
			TexturePacker.process(spriteSheetLocation, spriteSheetPackLocation, spriteSheetPackName);
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new Evermore(dev), config);
	}
}
