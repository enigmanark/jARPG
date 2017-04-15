package com.ceirenthir.game.core.IF;

import com.ceirenthir.game.core.Evermore;
import com.ceirenthir.game.core.logic.events.Event;

public interface Postable {
public void post(Evermore game, Event e);
}
