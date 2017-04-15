package com.ceirenthir.game.core.logic;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.DataSystem;
import com.ceirenthir.game.core.graphics.GraphicsSystem;
import com.ceirenthir.game.core.logic.events.Activate;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.MouseClick;
import com.ceirenthir.game.core.logic.events.TouchHold;
import com.ceirenthir.game.core.logic.events.WindowClose;
import com.ceirenthir.game.core.logic.events.WindowOpen;

/*
 * The input handler receives input from the Mod_Input and it "handles" it. Basically
 * this handler gets the input, and it transforms the input into actual "functions,"
 * by translating the input to a command, then relays that command to the other modules.
 */

public class Mod_InputHandler extends Mod_Logic {
	public final static int DESKTOP = 0;
	public final static int ANDROID = 1;
	final DataSystem dataSys;
	final LogicSystem logicSys;
	final GraphicsSystem graphicsSys;
	private final int platformType;
	private boolean windowOpen = false;
	private int windowsOpen = 0;
	private int cameraIndex = -1;
	private int[] cameraSettings = 
		{Mod_CameraController.CAMERA_CENTERED_PLAYER,
		Mod_CameraController.CAMERA_TETHERED_DELTA,
		Mod_CameraController.CAMERA_TETHERED_LERP};
	
	protected Mod_InputHandler(DataSystem dataSys, LogicSystem logicSys, GraphicsSystem graphicsSys) {
		this.dataSys = dataSys;
		this.logicSys = logicSys;
		this.graphicsSys = graphicsSys;
		
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			platformType = DESKTOP;
		}
		else if(Gdx.app.getType() == ApplicationType.Android) {
			platformType = ANDROID;
		}
		else {
			platformType = 0;
			Gdx.app.error("Mod_InputHandler", "Platform cannot be determined. Exiting game..");
			Gdx.app.exit();
			System.exit(0);
		}
	}
	
	public int getPlatformType() {
		return platformType;
	}
	
	@Override
	protected void post(Evermore game, Event e) {
		if(e instanceof WindowOpen) {
			windowOpen = true;
			windowsOpen++;
		}
		else if(e instanceof WindowClose) {
			windowsOpen--;
			if(windowsOpen == 0) windowOpen = false;
		}
	}
	
	protected void mouseClick(int button, Vector3 point, boolean shift) {
		Vector3 touchPoint = graphicsSys.getCamera().unproject(point);
		logicSys.post(new MouseClick(button, (int)touchPoint.x, (int)touchPoint.y, shift));
	}
	
	protected void clickHold(Vector3 point) {
		Vector3 touchPoint = graphicsSys.getCamera().unproject(point);
		logicSys.post(new TouchHold((int)touchPoint.x, (int)touchPoint.y));
	}
	
	//Open inventory
	protected void iPressed() {
		if(!(logicSys.getMessageController().isOpen())) {
			if(!(logicSys.getInventoryController().isBackPackOpen()))
				logicSys.getInventoryController().openBackPack();
			else
				logicSys.getInventoryController().closeBackPack();
		}
	}
	
	//Open equipment
	protected void ePressed() {
		if(!(logicSys.getMessageController().isOpen())) {
			if(!(logicSys.getEquipmentController().isOpen()))
				logicSys.getEquipmentController().openEquipmentWindow();
			else
				logicSys.getEquipmentController().closeEquipment();
		}
	}
	
	protected void escPressed() {
		if(logicSys.getInventoryController().isBackPackOpen()) {
			logicSys.getInventoryController().closeBackPack();
		}
		else if(logicSys.getEquipmentController().isOpen()) {
			logicSys.getEquipmentController().closeEquipment();
		} else Gdx.app.exit();
	}
	
	//Toggle drawing rects
	protected void rPressed() {
		//No input allowed except spaceBar when windows open
		if(windowOpen) return;

		if(graphicsSys.showRects) {
			graphicsSys.showRects = false;
		}
		else{
			graphicsSys.showRects = true;
		}
		
	}
	
	//Move the player up
	protected void upPressed() {
		//No input allowed except spaceBar when windows open
		if(windowOpen) return;
		logicSys.getPlayerMover().requestMove(0, 1);
	}
	
	//Move the player left
	protected void leftPressed() {
		//No input allowed except spaceBar when messageWindow open
		if(windowOpen) return;
		logicSys.getPlayerMover().requestMove(-1, 0);
	}
	
	//Move the player down
	protected void downPressed() {
		//No input allowed except spaceBar when messageWindow open
		if(windowOpen) return;
		logicSys.getPlayerMover().requestMove(0, -1);
	}
	
	//Move the player right
	protected void rightPressed() {
		//No input allowed except spaceBar when messageWindow open
		if(windowOpen) return;
		logicSys.getPlayerMover().requestMove(1, 0);
	}
	
	//Changes camera settings
	protected void cPressed() {
		//No input allowed except spaceBar when messageWindow open
		if(windowOpen) return;
		cameraIndex++;
		if(cameraIndex > 3) cameraIndex = 0;
		int c = cameraSettings[cameraIndex];
		logicSys.getCameraController().setCameraStyle(c);
		
		String cameraSetting = "ERROR";
		if(cameraSettings[cameraIndex] == Mod_CameraController.CAMERA_CENTERED_PLAYER)
			cameraSetting = "Centered on Player";
		else if(cameraSettings[cameraIndex] == Mod_CameraController.CAMERA_TETHERED_DELTA)
			cameraSetting = "Player Tethered Delta";
		else if(cameraSettings[cameraIndex] == Mod_CameraController.CAMERA_TETHERED_LERP)
			cameraSetting = "Player Tethered Interpolation";
		
		Gdx.app.debug("Input Handler", "Toggling camera setting to " +
				cameraSetting);
	}

	protected void spacePressed() {
		this.logicSys.post(new Activate());
	}
	
}
