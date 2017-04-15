package com.ceirenthir.game.core.logic;

/*
 * The Logic System manages the games logic, obviously. This means that it is in charge
 * of calling updates on particular game objects when necessary. The objects are managed
 * and stored inside of the DataSystem, and this class manipulates, creates, and acts on the
 * data being stored there.
 * 
 * The game content is not created here. The Game Content is created in the Evermore
 * via the Loader_Content class's static methods.
 */

import java.util.ArrayList;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.ceirenthir.game.core.data.Map;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.WindowClose;
import com.ceirenthir.game.core.logic.events.WindowOpen;
import com.ceirenthir.game.core.util.Loader_Content;
import com.ceirenthir.game.core.Evermore;

public class LogicSystem {
	private final Evermore game;
	
	public Map currentMap;

	private ArrayList<Mod_Logic> modules;	
	private final Mod_Input input;
	private final Mod_InputHandler inputHandler;
	private final Mod_CameraController camControl;
	private final Mod_HudController hudControl;
	private final Mod_Movement_Player movePlayer;
	private final Mod_MessageWindowController messageControl;
	private final Mod_GameClock gameClock;
	private final Mod_GObjectActivation_Handler activateHandler;
	private final Mod_MapController mapControl;
	private final Mod_GObjectCollision_Handler collideHandler;
	private final Mod_Movement_GameObjects objectMover;
	private final Mod_GObjectUpdate_Handler updateHandler;
	private final Mod_Quest_Handler questHandler;
	private final Mod_Event_Handler eventHandler;
	private final Mod_InventoryController inventoryControl;
	private final Mod_EquipmentController equipControl;
	private final Mod_ItemPickUpController pickupControl;
	private final Mod_GObject_PostHandler postHandler;
	
	public LogicSystem(Evermore game) {
		this.game = game;
		this.inputHandler = new Mod_InputHandler(game.dataSys, this, game.graphicsSys);
		this.camControl = new Mod_CameraController(game);
		this.input = new Mod_Input(game, inputHandler);
		this.movePlayer = new Mod_Movement_Player(game);
		this.messageControl = new Mod_MessageWindowController(game);
		this.gameClock = new Mod_GameClock();
		this.modules = new ArrayList<Mod_Logic>();
		this.activateHandler = new Mod_GObjectActivation_Handler(game);
		this.mapControl = new Mod_MapController(game);
		this.collideHandler = new Mod_GObjectCollision_Handler(game);
		this.objectMover = new Mod_Movement_GameObjects(game);
		this.updateHandler = new Mod_GObjectUpdate_Handler(game);
		this.questHandler = new Mod_Quest_Handler(game);
		this.eventHandler = new Mod_Event_Handler(game);
		this.inventoryControl = new Mod_InventoryController(game);
		this.equipControl = new Mod_EquipmentController(game);
		this.pickupControl = new Mod_ItemPickUpController();
		this.hudControl = new Mod_HudController();
		this.postHandler = new Mod_GObject_PostHandler();
	}
	
	public Mod_GameClock getGameClock() {
		return gameClock;
	}
	
	public void update() {
		if(game.gameLoading) {
			startGameInitialization();
		} else {
			//Update modules that accept input only
			//when a window is open
			if(windowOpen) {
				//Be careful with the order of these
				eventHandler.update(modules);
				this.input.update();
				this.messageControl.update();
				this.inventoryControl.update();
				this.equipControl.update();
			}
			//Update everything
			else {
				for(Mod_Logic mod : modules) {
					mod.update();
				}
				eventHandler.update(modules);
			}
		}
		
		//Update other objects here
	}
	
	//Called by logic modules to post events to the entire system
	//The event handler makes sure events get to their location
	//properly
	private boolean windowOpen = false;
	private int windowsOpen = 0;
	public void post(Event e) {
		if(e instanceof WindowOpen) {
			windowOpen = true;
			windowsOpen++;
		}
		else if(e instanceof WindowClose) {
			windowsOpen--;
			if(windowsOpen == 0) windowOpen = false;
		}
		eventHandler.post(e);	
	}
	
	private void startGameInitialization() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("Game", "Starting game initialization..");
		initGame();
		
		modules.add(input);
		modules.add(movePlayer);
		modules.add(camControl);
		modules.add(inputHandler);
		modules.add(messageControl);
		modules.add(gameClock);
		modules.add(activateHandler);
		modules.add(collideHandler);
		modules.add(mapControl);
		modules.add(objectMover);
		modules.add(updateHandler);
		modules.add(questHandler);
		modules.add(inventoryControl);
		modules.add(equipControl);
		modules.add(hudControl);
		modules.add(pickupControl);
		modules.add(postHandler);
		
		for(Mod_Logic mod : modules) {
			mod.init();
		}
		
		game.gameLoading = false;
		Gdx.app.log("Game", "Game load complete.");
	}
	
	public void unloadMap() {
		currentMap.unloadMap();
	}
	
	public Mod_HudController getHudController() {
		return hudControl;
	}
	
	protected Mod_CameraController getCameraController() {
		return camControl;
	}
	
	public Mod_Input getInput() {
		return input;
	}
	
	protected Mod_Movement_Player getPlayerMover() {
		return movePlayer;
	}
	
	public Mod_ItemPickUpController getItemPickUpController() {
		return pickupControl;
	}
	
	protected Mod_InputHandler getInputHandler() {
		return inputHandler;
	}
	
	public Mod_MessageWindowController getMessageController() {
		return this.messageControl;
	}
	
	public Mod_InventoryController getInventoryController() {
		return this.inventoryControl;
	}
	
	public Mod_EquipmentController getEquipmentController() {
		return equipControl;
	}
	
	private void initGame() {
		game.dataSys.init();
		
		//Load and Start game content
		Loader_Content.load(game);
		
		//Load the current map
		if(currentMap != null) currentMap.loadMap();
		else {
			Gdx.app.error("Game Load Error", "The starter map is null.");
		}
	}
	
}
