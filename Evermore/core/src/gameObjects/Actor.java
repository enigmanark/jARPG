package gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.ceirenthir.game.core.data.Armor;
import com.ceirenthir.game.core.data.EquipmentTypes;
import com.ceirenthir.game.core.data.Mod_Assets;
import com.ceirenthir.game.core.data.Weapon;
import com.ceirenthir.game.core.graphics.GraphicsSystem;

public class Actor extends Entity_Animated {
	private Weapon mainHand;
	private Armor offhand;
	private Armor head;
	private Armor shoulders;
	private Armor back;
	private Armor body;
	private Armor hands;
	private Armor waist;
	private Armor legs;
	private Armor feet;
	
    protected Animation walkingWeaponAnimationNorth;
    protected Animation walkingWeaponAnimationSouth;
    protected Animation walkingWeaponAnimationEast;
    protected Animation walkingWeaponAnimationWest;
    
    protected TextureRegion[] walkingWeaponFramesNorth;
    protected TextureRegion[] walkingWeaponFramesSouth;
    protected TextureRegion[] walkingWeaponFramesEast;
    protected TextureRegion[] walkingWeaponFramesWest;

    protected TextureRegion currentWeaponWalkFrame; 
    
	public Actor(float x, float y) {
		super(x, y);
	}
	
	public Actor(float x, float y, String spriteSheetName) {
		super(x, y, spriteSheetName);
	}
	
	public Actor() {
		
	}
	
	public TextureRegion getCurrentWeaponWalkFrame() {
		return currentWeaponWalkFrame;
	}
	
	protected void updateMainHand(Mod_Assets assets) {
		if(mainHand != null) {
			/*
			 * Start getting walking animations
			 */
			//Get the walking spritesheets
				//walkSheet 0 = North, 1 = South, 2 = East, 3 = West
				
				//Get the frames for North
				AtlasRegion[] weaponSheet = assets.getWalkingWeaponSheet(mainHand.getWeaponSheetName());
			
				walkingWeaponFramesNorth = new 
						TextureRegion[GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH];
				AtlasRegion weaponRegion = weaponSheet[0];
				TextureRegion[][] tmp = weaponRegion.split(
						GraphicsSystem.WALKING_SPRITE_WIDTH,
						GraphicsSystem.WALKING_SPRITE_HEIGHT);
				for(int x = 0; x < GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH; x++) {
					walkingWeaponFramesNorth[x] = tmp[0][x];
				}
				
				//Get the frames for South
				walkingWeaponFramesSouth = new 
						TextureRegion[GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH];
				weaponRegion = weaponSheet[1];
				tmp = weaponRegion.split(
						GraphicsSystem.WALKING_SPRITE_WIDTH,
						GraphicsSystem.WALKING_SPRITE_HEIGHT);
				for(int x = 0; x < GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH; x++) {
					walkingWeaponFramesSouth[x] = tmp[0][x];
				}
				
				//Get the frames for East
				walkingWeaponFramesEast = new 
						TextureRegion[GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH];
				weaponRegion = weaponSheet[2];
				tmp = weaponRegion.split(
						GraphicsSystem.WALKING_SPRITE_WIDTH,
						GraphicsSystem.WALKING_SPRITE_HEIGHT);
				for(int x = 0; x < GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH; x++) {
					walkingWeaponFramesEast[x] = tmp[0][x];
				}
				
				//Get the frames for West
				walkingWeaponFramesWest = new 
						TextureRegion[GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH];
				weaponRegion = weaponSheet[3];
				tmp = weaponRegion.split(
						GraphicsSystem.WALKING_SPRITE_WIDTH,
						GraphicsSystem.WALKING_SPRITE_HEIGHT);
				for(int x = 0; x < GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH; x++) {
					walkingWeaponFramesWest[x] = tmp[0][x];
				}
			
				walkingWeaponAnimationNorth = new Animation(frameSpeed, walkingWeaponFramesNorth);
				walkingWeaponAnimationSouth = new Animation(frameSpeed, walkingWeaponFramesSouth);
				walkingWeaponAnimationEast = new Animation(frameSpeed, walkingWeaponFramesEast);
				walkingWeaponAnimationWest = new Animation(frameSpeed, walkingWeaponFramesWest);
				
				currentWeaponWalkFrame = getWeaponIdleFrame();
				Gdx.app.log("Actor", "MainHand set to " + mainHand.name);
			/*
			 * End getting walking animations
			 */
		}
	}
	
	protected void updateOffHand(Mod_Assets assets) {
		if(offhand != null) {
			//TODO Make offhand viewable
		}
	}
	
	protected void updateHead(Mod_Assets assets) {
		if(head != null) {
			//TODO Make head viewable
		}
	}

	protected void updateShoulders(Mod_Assets assets) {
		if(shoulders != null) {
			//TODO Make shoulders viewable
		}
	}
	
	protected void updateBack(Mod_Assets assets) {
		if(back != null) {
			//TODO Make back viewable
		}
	}
	
	protected void updateBody(Mod_Assets assets) {
		if(body != null) {
			//TODO Make body viewable
		}
	}
	
	protected void updateHands(Mod_Assets assets) {
		if(hands != null) {
			//TODO Make hands viewable
		}
	}
	
	protected void updateWaist(Mod_Assets assets) {
		if(waist != null) {
			//TODO Make waist viewable
		}
	}
	
	protected void updateLegs(Mod_Assets assets) {
		if(legs != null) {
			//TODO Make legs viewable
		}
	}
	
	protected void updateFeet(Mod_Assets assets) {
		if(feet != null) {
			//TODO Make feet viewable
		}
	}
	
	public void setWeaponSpriteFrame(TextureRegion frame) {
		this.currentWeaponWalkFrame = frame;
	}
	
	@Override
	public void loadEntity(Mod_Assets assets, float xPos, float yPos) {
		super.loadEntity(assets, xPos, yPos);
		updateMainHand(assets);
		updateOffHand(assets);
		updateHead(assets);
		updateShoulders(assets);
		updateBody(assets);
		updateBack(assets);
		updateHands(assets);
		updateWaist(assets);
		updateLegs(assets);
		updateFeet(assets);
	}
	
	public void setMainHand(Weapon weapon, Mod_Assets assets) {
		if(weapon.getEquipType() == EquipmentTypes.Main_Hand) {
			if(mainHand != null) {
				if(!weapon.equals(mainHand)) {
					mainHand = weapon;
					updateMainHand(assets);
				}
				else Gdx.app.log("Actor", "MainHand is the same, not switching weapons.");
			}
			else {
				mainHand = weapon;
				updateMainHand(assets);
			}
		}
		else
			Gdx.app.error("Actor", "Tried to equip a weapon that was not mainHand in main hand.");
	}
	
	@Override
	public void processAnimation() {
		if(isMoving()) {
			float stateTime = getWalkingAnimationStateTime() + Gdx.graphics.getDeltaTime();
			setSpriteWalkStateTime(stateTime);
			Animation a = getWalkingAnimation();
			setSpriteFrame(a.getKeyFrame(getWalkingAnimationStateTime(), true));
			
		} else {
			setSpriteFrame(getIdleFrame());	
		}
		processWeaponWalkAnimation();
	}
	
	private void processWeaponWalkAnimation() {
		if(mainHand != null) {
			if(isMoving()) {
				Animation w = getWeaponWalkingAnimation();
				setWeaponSpriteFrame(w.getKeyFrame(getWalkingAnimationStateTime(), true) );
			}
			else
				setWeaponSpriteFrame(getWeaponIdleFrame());
		}
	}
	
	//Returns the actor's idle weapon frame based on directional facing
	public TextureRegion getWeaponIdleFrame() {
		if(this.isFacingNorth()) {
			return walkingWeaponFramesNorth[GraphicsSystem.WALKING_ANIMATION_IDLE_COL];
		}
		else if(this.isFacingEast()) {
			return walkingWeaponFramesEast[GraphicsSystem.WALKING_ANIMATION_IDLE_COL];
		}
		else if(this.isFacingWest()) {
			return walkingWeaponFramesWest[GraphicsSystem.WALKING_ANIMATION_IDLE_COL];
		}
		else if(this.isFacingSouth()) {
			return walkingWeaponFramesSouth[GraphicsSystem.WALKING_ANIMATION_IDLE_COL];
		}
		else {
			Gdx.app.error("CRITICAL ERROR", "Sprite's facing is wrong so did not return a "
					+ "proper weapon idle frame");
			Gdx.app.exit();
			System.exit(0);
			return null;
		}
	}
	
	//returns the actor's walking weapon animation based on directional facing
	public Animation getWeaponWalkingAnimation() {
		if(this.isFacingNorth()) return walkingWeaponAnimationNorth;
		else if(this.isFacingEast()) return walkingWeaponAnimationEast;
		else if(this.isFacingWest()) return walkingWeaponAnimationWest;
		else if(this.isFacingSouth()) return walkingWeaponAnimationSouth;
		else {
			Gdx.app.error("CRITICAL ERROR", "Actor's walking weapon animation did not return.");
			Gdx.app.exit();
			System.exit(0);
			return null;
		}
	}
}
