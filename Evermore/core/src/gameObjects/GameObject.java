package gameObjects;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;
import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.graphics.GraphicsSystem;
import com.ceirenthir.game.core.logic.scripts.Script;

/*
 * Parent Class for all objects that exist on the Maps. It contains data
 * that is relevant to all children. 
 */

public class GameObject {
	protected boolean solid = false;
	private boolean wantsMove = false;
	public final static float defaultMoveSpeed = 150f;
	protected float x;
	protected float y;
	protected float dx;
	protected float dy;
	protected int width;
	protected int height;
	protected float speed = defaultMoveSpeed;
	protected float modSpeed = 1;
	protected String name;
	protected Rectangle rect;
	protected ArrayList<Script> scripts = new ArrayList<Script>();
	
	//Only called if a child implements updatable
	public void update(Evermore game) {
		if(!scripts.isEmpty()) {
			Iterator<Script> scriptIter = this.getScripts().iterator();
			while(scriptIter.hasNext()) {
				Script s = scriptIter.next();
				s.update(game, scriptIter);
			}
		}
	}
	//Only called if a child implements activatable
	public void activate(Evermore game) {
		Iterator<Script> scriptIter = this.getScripts().iterator();
		while(scriptIter.hasNext()) {
			Script s = scriptIter.next();
			s.activate(game, scriptIter);
		}
		
	}
	
	//Only Called if a child implements collidable
	public void collide(Evermore game) {
		Iterator<Script> scriptIter = this.getScripts().iterator();
		while(scriptIter.hasNext()) {
			Script s = scriptIter.next();
			s.collide(game, scriptIter);
		}
	}
	
	public Rectangle getRect() {
		return rect;
	}
	

	
	//If the object is not passable
	public boolean isSolid() {
		return solid;
	}
	
	public void setSolid(boolean is) {
		solid = is;
	}
	
	public GameObject(float x, float y) {
		this.x = x;
		this.y = y;
		this.rect = new Rectangle();
	}
	
	public GameObject() {
		this.rect = new Rectangle();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public GameObject(String name) {
		this.name = "Game Object";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Script> getScripts() {
		return scripts;
	}
	
	public void addScript(Script s){
		scripts.add(s);
	}

	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean intersects(Rectangle r) {
		if(rect.overlaps(r)) {
			return true;
		} else return false;
	}
	
	public boolean intersects(float x, float y, float width, float height) {
		Rectangle r = new Rectangle(x, y, width, height);
		if(rect.overlaps(r)) {
			return true;
		} else return false;
	}
	
	public void setRect(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rect.x = x;
		this.rect.y = y;
		this.rect.width = width;
		this.rect.height = height;
	}
	
	public void setRect(Rectangle r) {
		this.rect = r;
	}
	
	public void setRect() {
		this.rect.x = x;
		this.rect.y = y;
		this.rect.width = width;
		this.rect.height = height;
	}
	
	public void setX(float x) {
		this.rect.x = x;
		this.x = x;
	}
	
	public void setY(float y) {
		this.rect.y = y;
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
		this.rect.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
		this.rect.height = height;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public float getModSpeed() {
		return modSpeed;
	}
	
	public void setModSpeed(float modSpeed) {
		this.modSpeed = modSpeed;
	}
	
	public void setDX(float dx) {
		this.dx = dx;
	}
	
	public void setDY(float dy) {
		this.dy = dy;
	}
	
	public float getDX() {
		return dx;
	}
	
	public float getDY() {
		return dy;
	}
	
	public Actor_NPC castToNPC(GameObject object) {
		Actor_NPC npc = new Actor_NPC();
		npc.name = object.name;
		npc.scripts = object.scripts;
		return npc;
	}
	
	public Tile_Updatable castToUpdatable(GameObject object) {
		Tile_Updatable up = new Tile_Updatable();
		up.name = object.name;
		up.scripts = object.scripts;
		return up;
	}
	
	public Tile_Activatable castToActivatable(GameObject object) {
		Tile_Activatable act = new Tile_Activatable();
		act.name = object.name;
		act.scripts = object.scripts;
		return act;
	}
	
	public Tile_Collidable castToCollidable(GameObject object) {
		Tile_Collidable tele = new Tile_Collidable();
		tele.name = object.name;
		tele.scripts = object.scripts;
		return tele;
	}
	
	//Used by the MapLoader2 to align tiles correctly, do not use otherwise
	public void setAsTileWithTileCoordinates(float x, float y) {
		this.x = x * GraphicsSystem.TILE_SIZE;
		this.y = y * GraphicsSystem.TILE_SIZE;
		this.width = GraphicsSystem.TILE_SIZE;
		this.height = GraphicsSystem.TILE_SIZE;
		this.rect = new Rectangle();
		this.rect.x = this.x;
		this.rect.y = this.y;
		this.rect.width = this.width;
		this.rect.height = this.height;
	}
	
	public void setAsTile(float x, float y) {
		this.x = x;
		this.y = y;
		this.width = GraphicsSystem.TILE_SIZE;
		this.height = GraphicsSystem.TILE_SIZE;
		this.rect = new Rectangle();
		this.rect.x = x;
		this.rect.y = y;
		this.rect.width = this.width;
		this.rect.height = this.height;
	}
	
	//Set as a tile but half width and aligned to the left
	public void setAsLeftHalfWidthWithTileCoordinates(float x, float y) {
		this.x = x * GraphicsSystem.TILE_SIZE;
		this.y = y * GraphicsSystem.TILE_SIZE;
		this.width = GraphicsSystem.TILE_SIZE / 2;
		this.height = GraphicsSystem.TILE_SIZE;
		this.rect = new Rectangle();
		this.rect.x = this.x;
		this.rect.y = this.y;
		this.rect.width = this.width;
		this.rect.height = this.height;
	}
	
	//Set as a tile but half width and aligned to the right
	public void setAsRightHalfWidthWithTileCoordinates(float x, float y) {
		this.x = x * GraphicsSystem.TILE_SIZE;
		this.y = y * GraphicsSystem.TILE_SIZE;
		this.width = GraphicsSystem.TILE_SIZE / 2;
		this.height = GraphicsSystem.TILE_SIZE;
		this.rect = new Rectangle();
		this.rect.x = (this.x + GraphicsSystem.TILE_SIZE) - this.width;
		this.rect.y = this.y;
		this.rect.width = this.width;
		this.rect.height = this.height;
	}
	
	//Set as a tile but quarter width and aligned right
	public void setAsRightQuarterWidthWithTileCoordinates(float x, float y) {
		this.x = x * GraphicsSystem.TILE_SIZE;
		this.y = y * GraphicsSystem.TILE_SIZE;
		this.width = GraphicsSystem.TILE_SIZE / 4;
		this.height = GraphicsSystem.TILE_SIZE;
		this.rect = new Rectangle();
		this.rect.x = (this.x + GraphicsSystem.TILE_SIZE) - this.width;
		this.rect.y = this.y;
		this.rect.width = this.width;
		this.rect.height = this.height;
	}
	
	//Set as a tile but quarter width and aligned left
	public void setAsLeftQuarterWidthWithTileCoordinates(float x, float y) {
		this.x = x * GraphicsSystem.TILE_SIZE;
		this.y = y * GraphicsSystem.TILE_SIZE;
		this.width = GraphicsSystem.TILE_SIZE / 4;
		this.height = GraphicsSystem.TILE_SIZE;
		this.rect = new Rectangle();
		this.rect.x = this.x;
		this.rect.y = this.y;
		this.rect.width = this.width;
		this.rect.height = this.height;
	}
	
	//Set as a tile but half height and aligned up
	public void setAsUpHalfHeightWithTileCoordinates(float x, float y) {
		this.x = x * GraphicsSystem.TILE_SIZE;
		this.y = y * GraphicsSystem.TILE_SIZE;
		this.width = GraphicsSystem.TILE_SIZE;
		this.height = GraphicsSystem.TILE_SIZE / 2;
		this.rect = new Rectangle();
		this.rect.x = this.x;
		this.rect.y = (this.y + GraphicsSystem.TILE_SIZE) - this.height;
		this.rect.width = this.width;
		this.rect.height = this.height;
	}
	
	//Set as a tile but half height and aligned down
	public void setAsDownHalfHeightWithTileCoordinates(float x, float y) {
		this.x = x * GraphicsSystem.TILE_SIZE;
		this.y = y * GraphicsSystem.TILE_SIZE;
		this.width = GraphicsSystem.TILE_SIZE;
		this.height = GraphicsSystem.TILE_SIZE / 2;
		this.rect = new Rectangle();
		this.rect.x = this.x;
		this.rect.y = this.y;
		this.rect.width = this.width;
		this.rect.height = this.height;
	}
	
	//Set as a tile but quarter height and aligned down
	public void setAsDownQuarterHeightWithTileCoordinates(float x, float y) {
		this.x = x * GraphicsSystem.TILE_SIZE;
		this.y = y * GraphicsSystem.TILE_SIZE;
		this.width = GraphicsSystem.TILE_SIZE;
		this.height = GraphicsSystem.TILE_SIZE / 4;
		this.rect = new Rectangle();
		this.rect.x = this.x;
		this.rect.y = this.y;
		this.rect.width = this.width;
		this.rect.height = this.height;
	}
	
	//Set as a tile but quarter height and aligned up
	public void setAsUpQuarterHeightWithTileCoordinates(float x, float y) {
		this.x = x * GraphicsSystem.TILE_SIZE;
		this.y = y * GraphicsSystem.TILE_SIZE;
		this.width = GraphicsSystem.TILE_SIZE;
		this.height = GraphicsSystem.TILE_SIZE / 4;
		this.rect = new Rectangle();
		this.rect.x = this.x;
		this.rect.y = (this.y + GraphicsSystem.TILE_SIZE) - this.height;
		this.rect.width = this.width;
		this.rect.height = this.height;
	}
	
	public boolean wantsToMove() {
		return wantsMove;
	}
	
	//Used for player activation
	public Rectangle getActivatorRect() {
		return rect;
	}
	
	public void setWantToMove(boolean does) {
		wantsMove = does;
	}
}
