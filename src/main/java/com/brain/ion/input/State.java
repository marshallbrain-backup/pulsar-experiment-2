package main.java.com.brain.ion.input;

/**
 * Basic state of input buttons.
 * 
 * @author Marshall Brain
 *
 */
public enum State {
	/**
	 * The button is not currently pressed.
	 */
	RELEASED,
	/**
	 * The button is currently pressed.
	 */
	PRESSED,
	/**
	 * The button has been currently pressed for one pull cycle.
	 */
	ONCE
}
