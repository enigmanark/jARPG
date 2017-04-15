package com.ceirenthir.game.core.logic;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.ceirenthir.game.core.Evermore;

public class Mod_Input extends Mod_Logic {
	final Evermore game;
	final Mod_InputHandler handler;

	private boolean Shift_Pressed = false;
	private boolean MouseClicked = false;
	private boolean I_Pressed = false;
	private boolean ESC_Pressed = false;
	private boolean C_Pressed = false;
	private boolean Space_Pressed = false;
	private boolean R_Pressed = false;
	private boolean E_Pressed = false;
	private boolean stillTouched = false;
	
	protected Mod_Input(Evermore game, Mod_InputHandler handler) {
		this.game = game;
		this.handler = handler;

	}
	
	@Override
	protected void init() {

	}

	@Override
	protected void update() {
		if(handler.getPlatformType() == Mod_InputHandler.DESKTOP) {
			desktopInput();
		}
		else if(handler.getPlatformType() == Mod_InputHandler.ANDROID) {
			androidInput();
		}
	}
	
	//Used for Android Controls
	private void androidInput() {
		//Shift key
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			if(!Shift_Pressed) {
				Shift_Pressed = true;
			}
		}else Shift_Pressed = false;

		if(Gdx.input.isTouched()) {
			Vector3 point = new Vector3();
			point.x = Gdx.input.getX();
			point.y = Gdx.input.getY();
			point.z = 0;
			if(!stillTouched) {
				stillTouched = true;
				handler.mouseClick(0, point, false);
			} else handler.clickHold(point);
		}

		if(!Gdx.input.isTouched()) {
			stillTouched = false;
		}
		
		//C Key
		if(Gdx.input.isKeyPressed(Keys.C)){
			if(!C_Pressed) {
				C_Pressed = true;
				handler.cPressed();
			}
		} else C_Pressed = false;

		//Activation
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			if(!Space_Pressed) {
				Space_Pressed = true;
				handler.spacePressed();
			}
		} else Space_Pressed = false;

		//R key
		if(Gdx.input.isKeyPressed(Keys.R)) {
			if(!(R_Pressed)) {
				R_Pressed = true;
				handler.rPressed();
			}
		} else R_Pressed = false;

		//Escape key
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			if(!(ESC_Pressed)) {
				ESC_Pressed = true;
				handler.escPressed();
			}
		} else ESC_Pressed = false;

		//I Key
		if(Gdx.input.isKeyPressed(Keys.I)) {
			if(!(I_Pressed)) {
				I_Pressed = true;
				handler.iPressed();
			}
		} else I_Pressed = false;

		//E Key
		if(Gdx.input.isKeyPressed(Keys.E)) {
			if(!(E_Pressed)) {
				E_Pressed = true;
				handler.ePressed();
			}
		} else E_Pressed = false;
	}
	
	//Used for Desktop Controls
	private void desktopInput() {
		//Shift key
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			if(!Shift_Pressed) {
				Shift_Pressed = true;
			}
		}else Shift_Pressed = false;
		
		//Left mouse button
		if(Gdx.input.isButtonPressed(0)) {
			if(!MouseClicked) {
				MouseClicked = true;
				int x = Gdx.input.getX();
				int y = Gdx.input.getY();
				handler.mouseClick(0, new Vector3(x, y, 0), Shift_Pressed);
			}
		}
		//Right mouse button
		else if(Gdx.input.isButtonPressed(1)) {
			if(!MouseClicked) {
				MouseClicked = true;
				int x = Gdx.input.getX();
				int y = Gdx.input.getY();
				handler.mouseClick(1, new Vector3(x, y, 0), Shift_Pressed);
			}
		} else MouseClicked = false;

		//W key
		if(Gdx.input.isKeyPressed(Keys.W)) {
			handler.upPressed();
		}

		//Left
		if(Gdx.input.isKeyPressed(Keys.A)) {
			handler.leftPressed();
		}

		//Down
		if(Gdx.input.isKeyPressed(Keys.S)) {
			handler.downPressed();
		}

		//Right
		if(Gdx.input.isKeyPressed(Keys.D)){
			handler.rightPressed();
		}

		//C Key
		if(Gdx.input.isKeyPressed(Keys.C)){
			if(!C_Pressed) {
				C_Pressed = true;
				handler.cPressed();
			}
		} else C_Pressed = false;

		//Activation
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			if(!Space_Pressed) {
				Space_Pressed = true;
				handler.spacePressed();
			}
		} else Space_Pressed = false;

		//R key
		if(Gdx.input.isKeyPressed(Keys.R)) {
			if(!(R_Pressed)) {
				R_Pressed = true;
				handler.rPressed();
			}
		} else R_Pressed = false;

		//Escape key
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			if(!(ESC_Pressed)) {
				ESC_Pressed = true;
				handler.escPressed();
			}
		} else ESC_Pressed = false;

		//I Key
		if(Gdx.input.isKeyPressed(Keys.I)) {
			if(!(I_Pressed)) {
				I_Pressed = true;
				handler.iPressed();
			}
		} else I_Pressed = false;
		
		//E Key
		if(Gdx.input.isKeyPressed(Keys.E)) {
			if(!(E_Pressed)) {
				E_Pressed = true;
				handler.ePressed();
			}
		} else E_Pressed = false;
	}
}
