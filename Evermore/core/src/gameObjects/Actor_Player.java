package gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.IF.Postable;
import com.ceirenthir.game.core.logic.Mngr_Item;
import com.ceirenthir.game.core.logic.events.EquipmentChange;
import com.ceirenthir.game.core.logic.events.Event;

public class Actor_Player extends Actor implements Postable{
	public Mngr_Item itemMngr;
	public Rectangle lastActivatationRect;
	
	public Actor_Player(Evermore game, float x, float y) {
		super(x, y);
		setName("Player");
		itemMngr = new Mngr_Item(game);
	}
	
	public Actor_Player(Evermore game, float x, float y, String spriteSheetName) {
		super(x, y, spriteSheetName);
		itemMngr = new Mngr_Item(game);
		setName("Player");
	}
	
	public Actor_Player(Evermore game) {
		itemMngr = new Mngr_Item(game);
		setName("Player");
	}
	
	//Get a rectangle in front of the player to test for
	//collision for Activation
	public Rectangle getActivationRect() {

		int width = 10;
		int height = 10;
		int x = width / 2;
		int y = 0;
		if(this.isFacingNorth()) {
			x += this.getX();
			y = (int) this.getY();
			y += height;
		}
		else if(this.isFacingSouth()) {
			x += this.getX();
			y = (int)this.getY();
			y -= height;
		}
		else if(this.isFacingEast()) {
			x += this.getX();
			x += width;
			y += this.getY();
		}
		else if(this.isFacingWest()) {
			x = (int)this.getX();
			x -= width;
			y = (int)this.getY();
		}
		Rectangle rect = new Rectangle();
		rect.x = x;
		rect.y = y;
		rect.width = width;
		rect.height = height;
		lastActivatationRect = rect;
		return rect;
	}
	
	public Rectangle getCollisionRect() {
		return this.getWalkCollisionRect();
	}
	

	@Override
	public void post(Evermore game, Event e) {
		if(e instanceof EquipmentChange) {
			EquipmentChange c = (EquipmentChange)e;
			if(((EquipmentChange) e).actor instanceof Actor_Player) {
				switch(c.type) {
				default:
					Gdx.app.error("Actor_Player", "Equipment was changed for " + 
					((EquipmentChange) e).actor.getName() + " but EquipmentType is "
					+ "either wrong for item or equipment type is not yet supported.");
					break;
				case Main_Hand:
					setMainHand(game.logicSys.getEquipmentController().getMainHand(), game.dataSys.assets);
					break;
				case Off_Hand:
					updateOffHand(game.dataSys.assets);
					break;
				case Head:
					updateHead(game.dataSys.assets);
					break;
				case Shoulders:
					updateShoulders(game.dataSys.assets);
					break;
				case Back:
					updateBack(game.dataSys.assets);
					break;
				case Body:
					updateBody(game.dataSys.assets);
					break;
				case Hands:
					updateHands(game.dataSys.assets);
					break;
				case Waist:
					updateWaist(game.dataSys.assets);
					break;
				case Legs:
					updateLegs(game.dataSys.assets);
					break;
				case Feet:
					updateFeet(game.dataSys.assets);
					break;
				}	
			}
		}	
	}
}
