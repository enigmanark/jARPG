package com.ceirenthir.game.core.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.logic.events.Event;

public class Mod_CameraController extends Mod_Logic {
	public final static int CAMERA_CENTERED_PLAYER = 0;
	public final static int CAMERA_TETHERED_DELTA = 1;
	public final static int CAMERA_TETHERED_LERP = 2;

	private final Evermore game;
	private OrthographicCamera camera;
	
	private int cameraStyle;
	
	//Camera Setting for Player Tethered Interpolation
	private final float lerp = 0.1f;
	
	protected Mod_CameraController(Evermore game) {
		this.game = game;
	}
	
	protected void post(Event e) {
		
	}
	
	@SuppressWarnings("static-access")
	protected void update() {
		if(game.dataSys.player != null) {
			float x = (float)game.dataSys.player.getX();
			float y = (float)game.dataSys.player.getY();

			if(cameraStyle == this.CAMERA_TETHERED_DELTA) this.useTetheredDeltaCamera(x, y);
			else if(cameraStyle == this.CAMERA_CENTERED_PLAYER) this.useCenteredOnPlayer(x, y);
			else if(cameraStyle == this.CAMERA_TETHERED_LERP) this.useTetheredInterpolationCamera(x, y);

			//keepInBounds();
			camera.update();
		}
	}
	
//	private void keepInBounds() {
		//TODO Fix Keep in Bounds in Mod_CameraController
//		float camHalfWidth = camera.viewportWidth * 0.5f;
//		float camHalfHeight = camera.viewportHeight * 0.5f;
//		int mapWidth = game.logicSys.currentMap.width;
//		int mapHeight = game.logicSys.currentMap.height;
//		
//		int mapLeft = 0;
//		int mapRight = 0 + mapWidth;
//		int mapBottom = 0;
//		int mapTop = 0 + mapHeight;
//
//		// Move camera after player as normal
//
//		float cameraLeft = camera.position.x - camHalfWidth;
//		float cameraRight = camera.position.x + camHalfWidth;
//		float cameraBottom = camera.position.y - camHalfHeight;
//		float cameraTop = camera.position.y + camHalfHeight;
//
//		// Horizontal axis
//		if(mapWidth < camera.viewportWidth)
//		{
//		    camera.position.x = mapRight / 2;
//		}
//		else if(cameraLeft <= mapLeft)
//		{
//		    camera.position.x = mapLeft + camHalfWidth;
//		}
//		else if(cameraRight >= mapRight)
//		{
//		    camera.position.x = mapRight - camHalfWidth;
//		}
//
//		// Vertical axis
//		if(mapHeight < camera.viewportHeight)
//		{
//		    camera.position.y = mapTop / 2;
//		}
//		else if(cameraBottom <= mapBottom)
//		{
//		    camera.position.y = mapBottom + camHalfHeight;
//		}
//		else if(cameraTop >= mapTop)
//		{
//		    camera.position.y = mapTop - camHalfHeight;
//		}
//	}
	
	//Camera follows player loosely with a fixed speed
	protected void useTetheredInterpolationCamera(float x, float y) {
		camera.position.x += MathUtils.round((((x - camera.position.x) 
				+ game.dataSys.player.getRect().getWidth()) * lerp));
		
		camera.position.y += MathUtils.round((((y - camera.position.y) )
				* lerp));
	}
	//Camera follows player loosely with a dynamic speed based on delta
	private void useTetheredDeltaCamera(float x, float y) {
		camera.position.x += MathUtils.round((((x - camera.position.x) 
				+ game.dataSys.player.getRect().getWidth()) * Gdx.graphics.getDeltaTime()));
		
		camera.position.y += MathUtils.round((((y - camera.position.y) )
				* Gdx.graphics.getDeltaTime()));
	}
	//Camera follows player fixed on players position
	private void useCenteredOnPlayer(float x, float y) {
		camera.position.x += MathUtils.round((x - camera.position.x)
				+ game.dataSys.player.getRect().getWidth());
		camera.position.y += MathUtils.round((y - camera.position.y));
	}
	
	protected void setCameraStyle(int s) {
		cameraStyle = s;
	}
	
	protected int getCameraStyle() {
		return cameraStyle;
	}
	
	protected void init() {
		camera = game.graphicsSys.getCamera();
	}
}
