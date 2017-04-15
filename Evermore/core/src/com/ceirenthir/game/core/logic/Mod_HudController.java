package com.ceirenthir.game.core.logic;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.logic.events.Event;
import com.ceirenthir.game.core.logic.events.MouseClick;
import com.ceirenthir.game.core.logic.events.TouchHold;

public class Mod_HudController extends Mod_Logic{
	private DPad dpad;
	private Button bag;
	private Button equip;
	
	public Mod_HudController() {
		dpad = new DPad();
		bag = new Button("Bag", 50, 50);
		equip = new Button("Equip", 50, 50);
	}
	
	public DPad getAndroidDPad() {
		return dpad;
	}
	
	public Button getButtonBag() {
		return bag;
	}
	
	public Button getButtonEquip() {
		return equip;
	}
	
	@Override
	protected void post(Evermore game, Event e) {
		if(e instanceof MouseClick) {
			MouseClick click = (MouseClick)e;
			if(bag.getRect().contains(click.x, click.y)) {
				game.logicSys.getInputHandler().iPressed();
			}
			else if(equip.getRect().contains(click.x, click.y)) {
				game.logicSys.getInputHandler().ePressed();
			}
			else if(dpad.getRects()[DPad.ACT].contains(click.x, click.y)) {
				game.logicSys.getInputHandler().spacePressed();
			}
		}
		else if(e instanceof TouchHold) {
			TouchHold hold = (TouchHold)e;
			for(int i = 0; i < dpad.getRects().length; i++) {
				if(dpad.getRects()[i].contains(hold.x, hold.y)) {
					if(i == DPad.DOWN) {
						game.logicSys.getInputHandler().downPressed();
					}
					else if(i == DPad.UP) {
						game.logicSys.getInputHandler().upPressed();
					}
					else if(i == DPad.LEFT) {
						game.logicSys.getInputHandler().leftPressed();
					}
					else if(i == DPad.RIGHT) {
						game.logicSys.getInputHandler().rightPressed();
					}
				}
			}
		}
	}
	
	@Override
	protected void update() {
			
	}
	
	@Override
	protected void init() {
		
	}
}
