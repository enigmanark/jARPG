package com.ceirenthir.game.core.graphics;

/*
 * The GraphicSystem is the main class in charge of drawing everything onto the display.
 * It is made up of different modules that know how to draw their respective assignments.
 */


import gameObjects.Entity_Animated;
import gameObjects.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.Activatable;
import com.ceirenthir.game.core.IF.Collidable;
import com.ceirenthir.game.core.IF.Static;
import com.ceirenthir.game.core.data.DataSystem;

public class GraphicsSystem {
	public boolean showRects;
	public final static int iconSize = 40;
	public static final int TILE_SIZE = 32;
	public static final int WALKING_SPRITE_WIDTH = 40;
	public static final int WALKING_SPRITE_HEIGHT = 64;
	public static final int WALKING_ANIMATION_FRAME_LENGTH = 3;
	public static final int WALKING_ANIMATION_IDLE_COL = 1;
	public static final float ANIMATION_WALKING_SPEED = 0.35f;
	private boolean fade = false;
	
	final Evermore game;
	final DataSystem dataSys;

	private OrthographicCamera camera;
	
	private Mod_WorldRenderer worldRender;
	private Mod_HUDRenderer hudRender;
	
	private Texture logo;
	
	public GraphicsSystem(Evermore Evermore, DataSystem dataSys) {
		this.game = Evermore;
		this.dataSys = dataSys;
		worldRender = (new Mod_WorldRenderer(game));
		hudRender = (new Mod_HUDRenderer(game));
	}
	//This is the main rendering method. It calls the draw method on all of the rendering
	//modules.
	public void render() {
		if(game.gameLoading) {
			drawTitle();
			return;
		}
		//Clear the screen with a white color
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		//Update the camera
		camera.update();
		
		//Draw the screen
		draw();
		
		//Testing!
		
		//If show rects is toggled on
		if(showRects) {
			testRects();
		}
		//End Testing!
		
	}
	
	//Debug method that draws rects
	private void testRects() {
		Array<Rectangle> rects = new Array<Rectangle>();
		//Loop through and get gameObjects normal rects
		for(GameObject object : game.logicSys.currentMap.gameObjects) {
			rects.add(object.getRect());
		}
		//add the players normal rect
		rects.add(game.dataSys.player.getRect());
	
		//Now start getting moveRects
		Array<Rectangle> moveRects = new Array<Rectangle>();
		for(GameObject object : game.logicSys.currentMap.gameObjects) {
			if(object instanceof Entity_Animated) {
				Entity_Animated npc = (Entity_Animated) object;
				if(!(npc.currentMoveRect == null)) moveRects.add(npc.currentMoveRect);
			}
		}
		//Get the player's moveRect if has one
		if(game.dataSys.player.currentMoveRect != null) {
			moveRects.add(game.dataSys.player.currentMoveRect);
		}
		
		//Loop through and get activatables activatorRects, collidable rects
		Array<Rectangle> actRects = new Array<Rectangle>();
		Array<Rectangle> colRects = new Array<Rectangle>();
		for(GameObject object : game.logicSys.currentMap.gameObjects) {
			if(object instanceof Activatable) {
				Activatable act = (Activatable) object;
				actRects.add(act.getActivatorRect());
			}
			else if(object instanceof Collidable) {
				Collidable col = (Collidable) object;
				colRects.add(col.getColliderRect());
			}
		}
		
		game.shapeRender.setProjectionMatrix(camera.combined);
		game.shapeRender.begin(ShapeType.Line);
		
		//Start drawing static rects
		game.shapeRender.setColor(Color.GRAY);
		for(Static sta : game.logicSys.currentMap.statics) {
			Rectangle rect = sta.getStaticRect();
			game.shapeRender.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
		//Start drawing normal rects
		game.shapeRender.setColor(Color.WHITE);
		for(Rectangle rect : rects) {
			game.shapeRender.rect(rect.x, rect.y, rect.width, rect.height);
		}
		//Start drawing moveRects
		game.shapeRender.setColor(Color.BLUE);
		for(Rectangle rect : moveRects) {
			
			game.shapeRender.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
		//Start drawing activatoRects
		game.shapeRender.setColor(Color.RED);
		for(Rectangle rect : actRects) {
			game.shapeRender.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
		//start drawing collidable rects
		game.shapeRender.setColor(Color.ORANGE);
		for(Rectangle rect : colRects) {
			game.shapeRender.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
		//Start drawing lastPlayerActivationRect
		game.shapeRender.setColor(Color.ORANGE);
		Rectangle rect = game.dataSys.player.lastActivatationRect;
		if(rect != null)
			game.shapeRender.rect(rect.x, rect.y, rect.width, rect.height);
		
		game.shapeRender.end();
	}
	
	//Call draw on renderer modules in the correct order
	private void draw() {
		if(fade) {
			return;
		}
		//Draw the background layer of the map
		worldRender.draw(camera);
		
		//Draw HUD
		hudRender.draw(camera);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	//Init the GraphicSystem by creating the camera and all modules.
	public void init() {
		//Load logo
		String logoPath;
		if(Gdx.app.getType() == ApplicationType.Android) {
			logoPath = "images/logo.png";
		}
		else if(Gdx.app.getType() == ApplicationType.Desktop) {
			logoPath = "resources/images/logo.png";
		}
		else {
			logoPath = "";
			Gdx.app.error("Critical", "Logo could not be loaded. Platform not supported.");
			Gdx.app.exit();
			System.exit(0);
		}
		logo = new Texture(Gdx.files.internal(logoPath));
		logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
	}
	
	public void drawTitle() {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		String loading = "Loading....";
		int logoX = Gdx.graphics.getWidth() / 2;
		int logoY = Gdx.graphics.getHeight() / 2;
		int textX = (Gdx.graphics.getWidth() / 2);
		int textY = (Gdx.graphics.getHeight() / 2) - 20;
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(logo, logoX, logoY);
		game.font.draw(game.batch, loading, textX, textY);
		game.batch.end();
	}
	
	public void setFade(boolean f) {
		fade = f;
	}
}
