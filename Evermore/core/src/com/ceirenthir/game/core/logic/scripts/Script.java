package com.ceirenthir.game.core.logic.scripts;

import java.util.Iterator;

import com.ceirenthir.game.core.Evermore;

/*
 * Parent class for all scripts. Scripts are created and are able to do many things.
 * Typically they work by accepting the host object and modifying it in some way.
 * 
 * Sometimes Modules from LogicSystem are able to detect changes made by the script
 * then are able to act on it some way. Then when the script detects that it's change
 * to it's host has happened, then the script may stop or it may do something else.
 * 
 * To make the scripts work, the attached gameObject has to implement the correct interface.
 * If the script depends on being activated, but it is attached to a gameObject that does not
 * implement Activatable, then the script will never run. Likewise if you attach a script that
 * works on update, and you attach it to an object that does not implement Updatable, likewise,
 * it will not run.
 * 
 * Activate is handled by Mod_Activation_Handler.
 * 
 * Update is handled by Mod_Update_Handler or Mod_Quest_Handler.
 * 
 * Collide is handled by Mod_Collide_Handler.
 */



public class Script {
	//Pass Iterator in case script needs to modify the script array
	public void update(Evermore game, Iterator<Script> scriptIter) {
		
	}
	
	public void activate(Evermore game, Iterator<Script> scriptIter) {
		
	}
	
	public void collide(Evermore game, Iterator<Script> scriptIter) {
		
	}
}
