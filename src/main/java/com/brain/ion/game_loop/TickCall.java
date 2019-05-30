package com.brain.ion.game_loop;

/**
 * The interface that allows the main game loop to call the tick method of the
 * game. Add this to the class that should be called when it is time to
 * calculate a game tick.
 * 
 * @author Marshall Brain
 *
 */
public interface TickCall {
	
	/**
	 * The method that is called when a game tick should be calculated.
	 */
	void tick();
	
}
