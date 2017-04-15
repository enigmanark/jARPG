package com.ceirenthir.game.core.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.ceirenthir.game.core.Evermore;

public class AndroidLauncher extends AndroidApplication {
	private boolean dev = false;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Evermore(dev), config);
	}
}
