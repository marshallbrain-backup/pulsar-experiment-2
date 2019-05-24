package main.java.com.brain.ion;

/**
 * The interface that allows the main game loop to call the render method of the
 * game. Add this to the class that should be called when it is time to render a
 * frame.
 * 
 * @author Marshall Brain
 *
 */
public interface RenderCall {
	
	/**
	 * The method that is called when the game should be rendered.
	 */
	void render();
	
}
