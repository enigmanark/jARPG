package com.ceirenthir.game.core.graphics;

/*
 * This module is in charge of drawing the world maps. It renders the map layers 
 * by using internal rendering units. The maps are .tmx maps from Tiled. 
 * The class splits up the map into layers and by a predefined protocol, it renders them.
 */

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.ceirenthir.game.core.Evermore;

public class Mod_WorldRenderer{
	private final Evermore game;
	private final Mngr_ActorRenderer npcRender;
	
	protected Mod_WorldRenderer(Evermore Evermore) {
		this.game = Evermore;
		npcRender = new Mngr_ActorRenderer(game);
	}
	
	protected void draw(OrthographicCamera camera) {
		if(game.logicSys.currentMap != null) {
			drawBackgroundLayer(camera);

			game.batch.setProjectionMatrix(camera.combined);
			game.batch.begin();
			
			npcRender.draw(camera, game.logicSys.currentMap.npcs);

			game.batch.end();

			drawForegroundLayer(camera);
		}
	}
	
	private void drawBackgroundLayer(OrthographicCamera camera) {
		if(game.logicSys.currentMap != null) {
			OrthogonalTiledMapRenderer renderer = game.logicSys.currentMap.getRenderer();
			renderer.setView(camera);
			int backgroundLayerCount = game.logicSys.currentMap.backgroundLayerCount;
			int[] backgroundLayers = new int[backgroundLayerCount];
			for(int i = 0; i < backgroundLayerCount; i++) {
				backgroundLayers[i] = i;
			}
			renderer.render(backgroundLayers);
		}
	}
	
	private void drawForegroundLayer(OrthographicCamera camera) {
		if(game.logicSys.currentMap != null) {
			OrthogonalTiledMapRenderer renderer = game.logicSys.currentMap.getRenderer();
			renderer.setView(camera);
			int foregroundLayerCount = game.logicSys.currentMap.foregroundLayerCount;
			int[] foregroundLayers = new int[foregroundLayerCount];
			for(int i = 0; i < foregroundLayerCount; i++) {
				foregroundLayers[i] = i + game.logicSys.currentMap.backgroundLayerCount;
			}
			renderer.render(foregroundLayers);
		}
	}
}
