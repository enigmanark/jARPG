package com.ceirenthir.game.core;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ceirenthir.game.core.data.DataSystem;
import com.ceirenthir.game.core.graphics.GraphicsSystem;
import com.ceirenthir.game.core.logic.LogicSystem;
import com.ceirenthir.game.core.util.Asset_Finder;

/*
 * Game Engine main class. 
 */

public class Evermore extends ApplicationAdapter {
	public final static String TITLE = "Project Mythos";
	public final static String VERSION = "0.3.5.7";
	public final static boolean DEV_BUILD = true;
	
	public GraphicsSystem graphicsSys;
	public DataSystem dataSys;
	public LogicSystem logicSys;
	
	public SpriteBatch batch;
	public BitmapFont font;
	public ShapeRenderer shapeRender;
	public boolean gameLoading = true;
	private boolean dev;
	
	public Evermore(boolean dev) {
		this.dev = dev;
	}
	
	@Override
	public void create () {
		//Gdx.graphics.setDisplayMode(WIDTH, HEIGHT, FULLSCREEN);
		Gdx.graphics.setTitle(TITLE);
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRender = new ShapeRenderer();
		init();
	}
	
	//Main loop
	@Override
	public void render () {
		graphicsSys.render();
		logicSys.update();
	}
	
	//Load core systems
	private void init() {
		if(dev) {
			//If running on desktop, scan assets
			if(Gdx.app.getType() == ApplicationType.Desktop) {
				Gdx.app.log("Core", "Running Desktop in dev mode..");
				Gdx.app.log("Core", "Scanning assets..");
				Asset_Finder.findSpriteSheets();
				Asset_Finder.findTiledMaps();
				Gdx.app.log("Core", "Asset scan complete.");			
			}
			
			else if (Gdx.app.getType() == ApplicationType.Android) {
				Gdx.app.log("Core", "Not running Desktop, skipping asset scan.");
			}
		}
		else {
			Gdx.app.log("Core", "Not running in dev mode, skipping asset scan.");
		}
		dataSys = new DataSystem(this);
		graphicsSys = new GraphicsSystem(this, dataSys);
		logicSys = new LogicSystem(this);
		graphicsSys.init();
	}
	
	//Called to properly dispose of low level assets
	@Override
	public void dispose() {
		dataSys.assets.dispose();
		batch.dispose();
		font.dispose();
		shapeRender.dispose();
		logicSys.unloadMap();
	}
	
}
