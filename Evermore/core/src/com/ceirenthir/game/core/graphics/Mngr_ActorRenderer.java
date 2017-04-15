package com.ceirenthir.game.core.graphics;

import java.util.ArrayList;
import java.util.Iterator;

import gameObjects.Actor;
import gameObjects.Actor_NPC;
import gameObjects.GameObject;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.ceirenthir.game.core.Evermore;

public class Mngr_ActorRenderer {
	private final Evermore game;
	protected Mngr_ActorRenderer(Evermore game) {
		this.game = game;
	}

	protected void draw(OrthographicCamera camera, ArrayList<Actor_NPC> npcs) {


		int playerPriority = 0;

		//Determine if the player is intersecting with a gameobjects
		//sprite rect. If so, determine what rendering priority
		//the player has.
		for(GameObject object : game.logicSys.currentMap.gameObjects) {
			if(game.dataSys.player.intersects(object.getRect())) {
				Rectangle playerRect = game.dataSys.player.getRect();
				Rectangle objectRect = object.getRect();

				if(playerRect.y > objectRect.y) {
					playerPriority = 1;
					break;
				}
			}
		}
		
		//Actor Priority over other actors
		Array<Actor_NPC> renderList = new Array<Actor_NPC>();
		Array<Actor_NPC> renderFirst = new Array<Actor_NPC>();
		Array<Actor_NPC> renderLast = new Array<Actor_NPC>();
		Iterator<Actor_NPC> npcIter = npcs.iterator();
		//TODO When more than 2 actors are colliding, fix
		while(npcIter.hasNext()) {
			Actor_NPC npc = npcIter.next();
			boolean rendered = false;
			for(Actor_NPC otherNPC : npcs) {
				if(npc.getRect().overlaps(otherNPC.getRect())) {
					if(npc.getRect().y > otherNPC.getRect().y) {
						renderFirst.add(npc);
						rendered = true;
						break;
					}

				}
			}
			if(!rendered)
				renderLast.add(npc);
		}

		renderList.addAll(renderFirst);
		renderList.addAll(renderLast);
		
		//Render player last
		if(playerPriority == 0) {
			for(Actor_NPC npc : renderList) {
				renderActor(npc, camera);
			}
			renderActor(game.dataSys.player, camera);
		}
		//Render player first
		else {
			renderActor(game.dataSys.player, camera);
			for(Actor_NPC npc : renderList) {
				renderActor(npc, camera);
			}
		}
	}
	
	private void renderActor(Actor a, OrthographicCamera camera) {
		if(a.visible) {
			Rectangle cameraRect = new Rectangle(); 
			cameraRect.x = camera.position.x - (camera.viewportWidth / 2) - 50;
			cameraRect.y = camera.position.y - (camera.viewportHeight / 2) - 50;
			cameraRect.width = camera.viewportWidth + 100;
			cameraRect.height = camera.viewportHeight + 100;
			if(cameraRect.contains(a.getRect())) {
				a.processAnimation();
				game.batch.draw(a.getCurrentWalkFrame(), a.getX(), a.getY() );
				if(a.getCurrentWeaponWalkFrame() != null) {
					game.batch.draw(a.getCurrentWeaponWalkFrame(), a.getX(), a.getY() );
				}
			}
		}
	}
}
