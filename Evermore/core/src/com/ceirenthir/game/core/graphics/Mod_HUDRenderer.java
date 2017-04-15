package com.ceirenthir.game.core.graphics;

import gameObjects.Actor_NPC;
import gameObjects.GameObject;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.data.ItemSlot;
import com.ceirenthir.game.core.logic.Mod_EquipmentController;
import com.ceirenthir.game.core.logic.Mod_InventoryController;
import com.ceirenthir.game.core.logic.events.Message;

public class Mod_HUDRenderer{
	final Evermore game;
	private int equipWindowX;
	private int equipWindowY;
	
	private int bagWindowX;
	private int bagWindowY;
	
	private int bagWindowWidth;
	private int bagWindowHeight;
	
	private int equipWindowWidth;
	private int equipWindowHeight;
	
	private int camX;
	private int camY;
	
	protected Mod_HUDRenderer(Evermore Evermore) {
		this.game = Evermore;

	}
	
	private void drawHUD(OrthographicCamera camera) {
		game.batch.end();
		game.shapeRender.setProjectionMatrix(camera.combined);
		game.shapeRender.setColor(0, 0, 255, 0.5f);
		game.shapeRender.begin(ShapeType.Filled);
		
		//Draw D-Pad
		if(Gdx.app.getType() == ApplicationType.Android) {
		
			int dpadX = (int) (camX - (camera.viewportWidth / 2) );
			int dpadY = (int) (camY - (camera.viewportHeight / 2) );
			
			game.logicSys.getHudController().getAndroidDPad().updateRects(dpadX, dpadY);
		
			for(Rectangle rect : game.logicSys.getHudController().getAndroidDPad().getRects()) {
				game.shapeRender.rect(rect.x, rect.y, rect.width, rect.height);
			}
		}
		
		//Draw Button Bag
		int bagX = (int) (camX - (camera.viewportWidth / 2) ) + (int)(camera.viewportWidth - 50);
		int bagY = (int) (camY - (camera.viewportHeight / 2) );
		
		game.logicSys.getHudController().getButtonBag().setPosition(bagX, bagY);
		
		game.shapeRender.rect(game.logicSys.getHudController().getButtonBag().getRect().x,
				game.logicSys.getHudController().getButtonBag().getRect().y,
				game.logicSys.getHudController().getButtonBag().getRect().width,
				game.logicSys.getHudController().getButtonBag().getRect().height);
		
		//Draw Button Equip
		int equipX = (int) (camX - (camera.viewportWidth / 2) ) + (int)(camera.viewportWidth - 100);
		int equipY = (int) (camY - (camera.viewportHeight / 2) );
		
		game.logicSys.getHudController().getButtonEquip().setPosition(equipX, equipY);
		
		game.shapeRender.rect(game.logicSys.getHudController().getButtonEquip().getRect().x,
				game.logicSys.getHudController().getButtonEquip().getRect().y,
				game.logicSys.getHudController().getButtonEquip().getRect().width,
				game.logicSys.getHudController().getButtonEquip().getRect().height);
		
		//End rect drawing
		game.shapeRender.end();
		
		//Start new batch
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.setColor(Color.WHITE);
		
		//Draw text for the buttons
		game.font.draw(game.batch, game.logicSys.getHudController().getButtonBag().getText(),
				bagX + 10, bagY + 35);
		game.font.draw(game.batch, game.logicSys.getHudController().getButtonEquip().getText(),
				equipX + 10, equipY + 35);
	}
	
	//Draw the equipment window if open
	private void drawEquipment(OrthographicCamera camera) {
		if(game.logicSys.getEquipmentController().isOpen()) {
			Mod_EquipmentController controller = 
					game.logicSys.getEquipmentController();
			
			camX = (int)camera.position.x;
			camY = (int)camera.position.y;

			equipWindowX = (int) (camX - (camera.viewportWidth / 2) + 25 );
			equipWindowY = (int) (camY - (camera.viewportHeight / 3) );
			
			controller.getEquipment().setX(equipWindowX);
			controller.getEquipment().setY(equipWindowY);
			controller.getEquipment().updateRects();
			
			equipWindowWidth = (int) controller.getEquipment().getWindowRect().width; 
			equipWindowHeight = (int) controller.getEquipment().getWindowRect().height;

			game.batch.end();

			//Draw window
			Gdx.gl.glEnable(GL20.GL_BLEND);

			game.shapeRender.setProjectionMatrix(camera.combined);
			game.shapeRender.setColor(0, 0, 255, 0.5f);
			game.shapeRender.begin(ShapeType.Filled);
			game.shapeRender.rect(equipWindowX, equipWindowY,
					equipWindowWidth, equipWindowHeight);
			
			//Draw Title Background of BackPack	
			int titleRectX = equipWindowX;
			int titleRectY = (int) (equipWindowY + 
					controller.getEquipment().getWindowRect().height);
			int titleRectWidth = 100;
			int titleRectHeight = 30;
			game.shapeRender.rect(titleRectX, titleRectY, titleRectWidth,
					titleRectHeight);
			
			//Draw ItemSlots if no item in slot
			game.shapeRender.setColor(Color.WHITE);
			for(ItemSlot slot : controller.getEquipment().getEquipSlots()) {
				if(slot.item == null) {
					game.shapeRender.rect(slot.rect.x, slot.rect.y, slot.rect.width,
						slot.rect.height);
				}
			}
			
			game.shapeRender.end();
			
			game.batch.setProjectionMatrix(camera.combined);
			game.batch.begin();
			
			//Draw Equipment Title
			game.font.draw(game.batch, "Equipment", titleRectX + 16
					, titleRectY + 20);
			
			//Draw Item Icons if Item in slot
			game.font.setColor(Color.BLACK);
			for(ItemSlot slot : controller.getEquipment().getEquipSlots()) {
				if(slot.item != null) {
					game.batch.draw(slot.item.getIcon(), slot.rect.x, slot.rect.y);
					game.font.draw(game.batch, 
						Integer.toString(slot.item.getAmount()),
						slot.rect.x, slot.rect.y + 15);
				}
			}
			game.font.setColor(Color.WHITE);
			
			//Draw picked up item
			Vector3 vector = new Vector3();
			vector.x = Gdx.input.getX();
			vector.y = Gdx.input.getY();
			vector.z = 1;
			vector = camera.unproject(vector);
			if(game.logicSys.getItemPickUpController().pickedUp != null) {
				game.batch.draw(game.logicSys.getItemPickUpController().pickedUp.getIcon(),
						vector.x, vector.y);
			}
		}
	}
	
	//Draw inventory bags if window open
	private void drawInventory(OrthographicCamera camera) {
		if(game.logicSys.getInventoryController().isBackPackOpen()) {
			Mod_InventoryController controller = 
					game.logicSys.getInventoryController();
			
			camX = (int)camera.position.x;
			camY = (int)camera.position.y;

			bagWindowX = (int) (camX - (camera.viewportWidth / 50) + bagWindowWidth / 1.4 );
			bagWindowY = (int) (camY - (camera.viewportHeight / 3) );
			
			controller.getBackPack().setX(bagWindowX);
			controller.getBackPack().setY(bagWindowY);
			controller.getBackPack().updateRects();
			
			bagWindowWidth = (int) controller.getBackPack().getInventoryRect().width; 
			bagWindowHeight = (int) controller.getBackPack().getInventoryRect().height;

			game.batch.end();

			//Draw window
			Gdx.gl.glEnable(GL20.GL_BLEND);

			game.shapeRender.setProjectionMatrix(camera.combined);
			game.shapeRender.setColor(0, 0, 255, 0.5f);
			game.shapeRender.begin(ShapeType.Filled);
			game.shapeRender.rect(bagWindowX, bagWindowY,
					bagWindowWidth, bagWindowHeight);
			
			//Draw Title Background of BackPack	
			int titleRectX = bagWindowX;
			int titleRectY = (int) (bagWindowY + 
					controller.getBackPack().getInventoryRect().height);
			int titleRectWidth = 100;
			int titleRectHeight = 30;
			game.shapeRender.rect(titleRectX, titleRectY, titleRectWidth,
					titleRectHeight);
			
			//Draw ItemSlots if no item in slot
			game.shapeRender.setColor(Color.WHITE);
			for(ItemSlot slot : controller.getBackPack().getBag().getItemSlots()) {
				if(slot.item == null) {
					game.shapeRender.rect(slot.rect.x, slot.rect.y, slot.rect.width,
						slot.rect.height);
				}
			}
			
			game.shapeRender.end();
			
			game.batch.setProjectionMatrix(camera.combined);
			game.batch.begin();
			
			//Draw BackPack Title
			game.font.draw(game.batch, "Backpack", titleRectX + 16
					, titleRectY + 20);
			
			//Draw Item Icons if Item in slot
			game.font.setColor(Color.BLACK);
			for(ItemSlot slot : controller.getBackPack().getBag().getItemSlots()) {
				if(slot.item != null) {
					game.batch.draw(slot.item.getIcon(), slot.rect.x, slot.rect.y);
					game.font.draw(game.batch, 
						Integer.toString(slot.item.getAmount()),
						slot.rect.x, slot.rect.y + 15);
				}
			}
			game.font.setColor(Color.WHITE);
			
			//Draw picked up item
			Vector3 vector = new Vector3();
			vector.x = Gdx.input.getX();
			vector.y = Gdx.input.getY();
			vector.z = 1;
			vector = camera.unproject(vector);
			if(game.logicSys.getItemPickUpController().pickedUp != null) {
				game.batch.draw(game.logicSys.getItemPickUpController().pickedUp.getIcon(),
						vector.x, vector.y);
			}
		}
	}

	//If message window open, draw it
	private void drawMessageWindow(OrthographicCamera camera) {
		if(game.logicSys.getMessageController().isOpen()) {
			camX = (int)camera.position.x;
			camY = (int)camera.position.y;

			int x = (int) (camX - (camera.viewportWidth / 1.5)) +
					(int)(camera.viewportWidth / 4);
			int y = (int) (camY - (camera.viewportHeight / 2)) + 10;

			int windowWidth = (int) (camera.viewportWidth / 1.2);
			int windowHeight = 150;

			//End current batch to start shapeRender
			game.batch.end();

			//Draw window
			Gdx.gl.glEnable(GL20.GL_BLEND);

			game.shapeRender.setProjectionMatrix(camera.combined);
			game.shapeRender.setColor(0, 0, 255, 0.5f);
			game.shapeRender.begin(ShapeType.Filled);
			game.shapeRender.rect(x, y, windowWidth, windowHeight);
			game.shapeRender.end();

			//restart batch
			game.batch.setProjectionMatrix(camera.combined);
			game.batch.begin();

			int messageX = x + 10;
			int messageY = (y + windowHeight) - 20;

			//Draw text
			Message message = game.logicSys.getMessageController().getMessage();
			game.font.draw(game.batch, message.speaker + ": " + message.message
					, messageX, messageY);
		}
	}

	//Draw the games clock
	private void drawClock(OrthographicCamera camera) {
		camX = (int)camera.position.x;
		camY = (int)camera.position.y;

		int x = (int) (camX - (camera.viewportWidth / 2)) + 10;
		int y = (int) (camY + (camera.viewportHeight / 2)) - 10;

		int x2 = x + 85;
		int y2 = y;

		game.font.draw(game.batch, "Game Time: ", x, y);
		game.font.draw(
				game.batch, Integer.toString
				(game.logicSys.getGameClock().getGameSeconds()), x2, y2);
	}
	
	//Draw the version
	private void drawVersion(OrthographicCamera camera) {
		camX = (int)camera.position.x;
		camY = (int)camera.position.y;
		
		int x = (int) (camX - (camera.viewportWidth / 2)) + 10;
		int y = (int) (camY + (camera.viewportHeight / 2)) - 50;
		
		int x2 = x + 55;
		int y2 = y;
		
		game.font.draw(game.batch, "Version: ", x, y);
		game.font.draw(game.batch, Evermore.VERSION, x2, y2);
		if(Evermore.DEV_BUILD) {
			game.font.draw(game.batch, "Development Build", x, y-20);
		}
	}
	
	//Draw 

	//Draw the FPS counter
	private void drawFPS(OrthographicCamera camera) {
		camX = (int)camera.position.x;
		camY = (int)camera.position.y;

		int x = (int) (camX - (camera.viewportWidth / 2)) + 10;
		int y = (int) (camY + (camera.viewportHeight / 2)) - 30;

		game.font.draw(game.batch, "FPS: " + Gdx.graphics.getFramesPerSecond()
				, x, y);
	}

	//Draw everything
	protected void draw(OrthographicCamera camera) {
		//Draw HUD
		//Draw the clock

		if(game.logicSys.getGameClock().isDrawing()) {
			game.batch.setProjectionMatrix(camera.combined);
			game.batch.begin();

			drawClock(camera);
			drawFPS(camera);
			drawVersion(camera);
			drawMessageWindow(camera);
			drawInventory(camera);
			drawEquipment(camera);
			drawHUD(camera);
			drawNPCNames(camera);
			
			game.batch.end();
		}
	}
	
	private void drawNPCNames(OrthographicCamera camera) {
		for(GameObject o : game.logicSys.currentMap.gameObjects) {
			if(o instanceof Actor_NPC) {
				game.font.draw(game.batch, o.getName(), o.getX(), o.getY()+o.getHeight());
			}
		}
	}

}
