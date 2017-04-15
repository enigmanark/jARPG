package gameObjects;

import java.util.Iterator;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.AI_Movable;
import com.ceirenthir.game.core.IF.Activatable;
import com.ceirenthir.game.core.IF.Updatable;
import com.ceirenthir.game.core.logic.scripts.Script;

public class Actor_NPC extends Actor implements Updatable, Activatable,
	AI_Movable{
	
	@Override
	public String getFacing() {
		return this.facing;
	}
	
	//Override activate so that the NPC changes it direction to face the player
	//whenever the player talks to the NPC, and also stops trying to move,  
	//as well as of course keeping the origional functionality.
	@Override
	public void activate(Evermore game) {
		//stop moving
		this.setMoving(false);
		this.setWantToMove(false);
		//Make face player
		facePlayer(game);
		Iterator<Script> scriptIter = this.getScripts().iterator();
		while(scriptIter.hasNext()) {
			Script s = scriptIter.next();
			s.activate(game, scriptIter);
		}
		
	}
	
	public void facePlayer(Evermore game) {
		Actor_Player player = game.dataSys.player;
		
		if(player.isFacingNorth()) {
			this.setFacing("South");
		}
		else if(player.isFacingSouth()) {
			this.setFacing("North");
		}
		else if(player.isFacingEast()) {
			this.setFacing("West");
		}
		else if(player.isFacingWest()) {
			this.setFacing("East");
		}
	}

}
