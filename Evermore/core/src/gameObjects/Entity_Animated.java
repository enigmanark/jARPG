package gameObjects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.data.Mod_Assets;
import com.ceirenthir.game.core.graphics.GraphicsSystem;
import com.ceirenthir.game.core.logic.scripts.Script;

public class Entity_Animated extends Entity {
	
	public Rectangle currentMoveRect;
	protected String spriteSheetName;
	
    protected Animation walkAnimationNorth;
    protected Animation walkAnimationSouth;
    protected Animation walkAnimationEast;
    protected Animation walkAnimationWest;
    
    protected TextureRegion[] walkFramesNorth;
    protected TextureRegion[] walkFramesSouth;
    protected TextureRegion[] walkFramesEast;
    protected TextureRegion[] walkFramesWest;
    
    protected TextureRegion currentWalkFrame;
    
    protected float walkStateTime; 
	
    //How quickly the animation runs
    protected float frameSpeed = GraphicsSystem.ANIMATION_WALKING_SPEED;
	
    private boolean moving = false;
    public boolean visible = true;
	
	public Entity_Animated(float x, float y) {
		super(x, y);
		this.scripts = new ArrayList<Script>();
		this.setSolid(true);
	}
	
	public Entity_Animated() {
		super();
		this.scripts = new ArrayList<Script>();
		this.setSolid(true);
	}
	
	public Entity_Animated(float x, float y, String spriteSheetName) {
		super(x, y);
		this.spriteSheetName = spriteSheetName;
		this.setSolid(true);
	}
	
	public void setSpriteSheetName(String name) {
		this.spriteSheetName = name;
	}
	
	//Load the actors animations and create the actors internal sprite
	public void loadEntity(Mod_Assets assets, float xPos, float yPos) {
		/*
		 * Start getting walking animations
		 */
		//Get the walking spritesheets
			//walkSheet 0 = North, 1 = South, 2 = East, 3 = West
			
			//Get the frames for North
			AtlasRegion[] walkSheet = assets.getWalkSheet(spriteSheetName);
		
			walkFramesNorth = new 
					TextureRegion[GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH];
			AtlasRegion walkRegion = walkSheet[0];
			TextureRegion[][] tmp = walkRegion.split(
					GraphicsSystem.WALKING_SPRITE_WIDTH,
					GraphicsSystem.WALKING_SPRITE_HEIGHT);
			for(int x = 0; x < GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH; x++) {
				walkFramesNorth[x] = tmp[0][x];
			}
			
			//Get the frames for South
			walkFramesSouth = new 
					TextureRegion[GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH];
			walkRegion = walkSheet[1];
			tmp = walkRegion.split(
					GraphicsSystem.WALKING_SPRITE_WIDTH,
					GraphicsSystem.WALKING_SPRITE_HEIGHT);
			for(int x = 0; x < GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH; x++) {
				walkFramesSouth[x] = tmp[0][x];
			}
			
			//Get the frames for East
			walkFramesEast = new 
					TextureRegion[GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH];
			walkRegion = walkSheet[2];
			tmp = walkRegion.split(
					GraphicsSystem.WALKING_SPRITE_WIDTH,
					GraphicsSystem.WALKING_SPRITE_HEIGHT);
			for(int x = 0; x < GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH; x++) {
				walkFramesEast[x] = tmp[0][x];
			}
			
			//Get the frames for West
			walkFramesWest = new 
					TextureRegion[GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH];
			walkRegion = walkSheet[3];
			tmp = walkRegion.split(
					GraphicsSystem.WALKING_SPRITE_WIDTH,
					GraphicsSystem.WALKING_SPRITE_HEIGHT);
			for(int x = 0; x < GraphicsSystem.WALKING_ANIMATION_FRAME_LENGTH; x++) {
				walkFramesWest[x] = tmp[0][x];
			}
		
			walkAnimationNorth = new Animation(frameSpeed, walkFramesNorth);
			walkAnimationSouth = new Animation(frameSpeed, walkFramesSouth);
			walkAnimationEast = new Animation(frameSpeed, walkFramesEast);
			walkAnimationWest = new Animation(frameSpeed, walkFramesWest);
			
			currentWalkFrame = this.getIdleFrame();
			
			this.setX(xPos);
			this.setY(yPos);
			this.setWidth(this.getIdleFrame().getRegionWidth());
			this.setHeight(this.getIdleFrame().getRegionHeight());
			this.setRect();
			
		/*
		 * End getting walking animations
		 */
	}
	
	
	
	public TextureRegion getCurrentWalkFrame() {
		return this.currentWalkFrame;
	}
	
	public void unloadActor() {
		// TODO Build unloadActor()
	}
	
	public void setSpriteFrame(TextureRegion frame) {
		this.currentWalkFrame = frame; 
	}
	
	public void setMoving(boolean is) {
		moving = is;
	}
	
	public void setSpriteWalkStateTime(float walkTime) {
		this.walkStateTime = walkTime;
	}
	
	//Returns the actor's idle frame based on directional facing
	public TextureRegion getIdleFrame() {
		if(this.isFacingNorth()) {
			return walkFramesNorth[GraphicsSystem.WALKING_ANIMATION_IDLE_COL];
		}
		else if(this.isFacingEast()) {
			return walkFramesEast[GraphicsSystem.WALKING_ANIMATION_IDLE_COL];
		}
		else if(this.isFacingWest()) {
			return walkFramesWest[GraphicsSystem.WALKING_ANIMATION_IDLE_COL];
		}
		else if(this.isFacingSouth()) {
			return walkFramesSouth[GraphicsSystem.WALKING_ANIMATION_IDLE_COL];
		}
		else {
			Gdx.app.error("CRITICAL ERROR", "Sprite's facing is wrong so did not return a proper idle frame");
			Gdx.app.exit();
			System.exit(0);
			return null;
		}
	}
	
	//returns the actor's walking animation based on directional facing
	public Animation getWalkingAnimation() {
		if(this.isFacingNorth()) return walkAnimationNorth;
		else if(this.isFacingEast()) return walkAnimationEast;
		else if(this.isFacingWest()) return walkAnimationWest;
		else if(this.isFacingSouth()) return walkAnimationSouth;
		else {
			Gdx.app.error("CRITICAL ERROR", "Actor's walk animation did not return.");
			Gdx.app.exit();
			System.exit(0);
			return null;
		}
	}
	
	//Supply x and y coordinates
	public Rectangle getMoveRect(float x, float y) {
		Rectangle rect = new Rectangle();
		rect.width = this.getRect().getWidth() / 2.5f;
		rect.height = this.getRect().getHeight() / 4;
		rect.x = (x + (GraphicsSystem.WALKING_SPRITE_WIDTH / 4));
		rect.y = y;
		currentMoveRect = rect;
		return rect;
	}
	
	public Rectangle getWalkCollisionRect() {
		Rectangle rect = new Rectangle();
		rect.width = this.getRect().getWidth() / 2.5f;
		rect.height = this.getRect().getHeight() / 4;
		rect.x = (this.getX() + (GraphicsSystem.WALKING_SPRITE_WIDTH / 4));
		rect.y = this.getY();
		
		return rect;
	}
	
	public float getWalkingAnimationStateTime() {
		return walkStateTime;
	}
	
	public void processAnimation() {
		if(isMoving()) {
			float stateTime = getWalkingAnimationStateTime() + Gdx.graphics.getDeltaTime();
			setSpriteWalkStateTime(stateTime);
			Animation a = getWalkingAnimation();
			setSpriteFrame(a.getKeyFrame(getWalkingAnimationStateTime(), true));
		} else {
			setSpriteFrame(getIdleFrame());
		}
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	@Override
	public boolean intersects(Rectangle r) {
		return this.getRect().overlaps(r);
	}
}
