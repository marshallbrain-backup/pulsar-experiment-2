package main.java.com.brain.ion.input;

/**
 * Special states of input buttons.
 * 
 * @author Marshall Brain
 *
 */
public enum StateSpecial {
	/**
	 * The button has no special state
	 */
	NONE,
	/**
	 * The button is clicked as dictated by mouseClicked() and keyTyped().
	 */
	CLICKED,
	/**
	 * The button was clicked twice
	 */
	DOUBLE
}
