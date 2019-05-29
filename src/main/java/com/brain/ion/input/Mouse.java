package main.java.com.brain.ion.input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * A class for handling Mouse Actions in a game setting.
 * 
 * @author Marshall Brain
 *
 */
public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private static final int BUTTON_COUNT = 3;
	
	private int wheelDir;
	private int wheelState;
	
	private int[] stateSpecial;
	
	private boolean[] state;
	
	private Point mousePos;
	private Point mouseChange;
	private Point currentPos;
	
	private State[] poll;
	private StateSpecial[] pollSpecial;
	
	/**
	 * Basic constructor
	 */
	public Mouse() {
		
		wheelDir = 0;
		wheelState = 0;
		
		stateSpecial = new int[BUTTON_COUNT];
		state = new boolean[BUTTON_COUNT];
		mousePos = new Point(0, 0);
		mouseChange = new Point(0, 0);
		currentPos = new Point(0, 0);
		poll = new State[BUTTON_COUNT];
		pollSpecial = new StateSpecial[BUTTON_COUNT];
		
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			poll[i] = State.RELEASED;
		}
		
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			pollSpecial[i] = StateSpecial.NONE;
		}
		
	}
	
	/**
	 * Offsets the given mouse by the coordinates.
	 * 
	 * @param m
	 *            The mouse object to offset
	 * @param offsetX
	 *            The x offset
	 * @param offsetY
	 *            The y offset
	 */
	public Mouse(Mouse m, int offsetX, int offsetY) {
		
		mousePos = new Point((int) (m.mousePos.getX()) - offsetX, (int) (m.mousePos.getY()) - offsetY);
		mouseChange = m.mouseChange;
		currentPos = m.currentPos;
		state = m.state;
		stateSpecial = m.stateSpecial;
		poll = m.poll;
		pollSpecial = m.pollSpecial;
		
	}
	
	/**
	 * 'Freezes' the current state of the mouse. This prevents the mouse from
	 * changing when processing a game tick.
	 */
	// Saves all of the variables that change to separate variables that are gotten
	// with the get methods
	public synchronized void poll() {
		
		mouseChange = new Point(mousePos.x - currentPos.x, mousePos.y - currentPos.y);
		mousePos = new Point(currentPos);
		
		wheelDir = wheelState;
		wheelState = 0;
		
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			
			if (state[i]) {
				if (poll[i] == State.RELEASED)
					poll[i] = State.ONCE;
				else
					poll[i] = State.PRESSED;
			} else {
				poll[i] = State.RELEASED;
			}
			
			switch (stateSpecial[i]) {
				case 1:
					pollSpecial[i] = StateSpecial.CLICKED;
					poll[i] = State.PRESSED;
					break;
				case 2:
					pollSpecial[i] = StateSpecial.DOUBLE;
					poll[i] = State.PRESSED;
					break;
				default:
					pollSpecial[i] = StateSpecial.NONE;
					break;
			}
			
			stateSpecial[i] = 0;
			
		}
		
	}
	
	/**
	 * @return The position of the mouse
	 */
	public Point getPosition() {
		
		return new Point(mousePos);
	}
	
	/**
	 * @return The delta of the mouse since the previous poll
	 */
	public Point getChange() {
		
		return new Point(mouseChange);
	}
	
	/**
	 * @return The direction of the mouse wheel
	 */
	public int getWheelDir() {
		
		return wheelDir;
	}
	
	/**
	 * Checks if the given button has only been held down for one pull call.
	 * <p>
	 * <ul>
	 * <li>1 ({@code BUTTON1})
	 * <li>2 ({@code BUTTON2})
	 * <li>3 ({@code BUTTON3})
	 * </ul>
	 * 
	 * @param button
	 *            The mouse button to check
	 * @return Whether the button matches the state
	 */
	public boolean buttonDownOnce(int button) {
		
		return poll[button - 1] == State.ONCE;
	}
	
	/**
	 * Checks if the given button is held down.
	 * <p>
	 * <ul>
	 * <li>1 ({@code BUTTON1})
	 * <li>2 ({@code BUTTON2})
	 * <li>3 ({@code BUTTON3})
	 * </ul>
	 * 
	 * @param button
	 *            The mouse button to check
	 * @return Whether the button matches the state
	 */
	public boolean buttonDown(int button) {
		
		return poll[button - 1] == State.ONCE || poll[button - 1] == State.PRESSED;
	}
	
	/**
	 * Checks if the given button was click.
	 * <p>
	 * <ul>
	 * <li>1 ({@code BUTTON1})
	 * <li>2 ({@code BUTTON2})
	 * <li>3 ({@code BUTTON3})
	 * </ul>
	 * 
	 * @param button
	 *            The mouse button to check
	 * @return Whether the button matches the state
	 */
	public boolean buttonClicked(int button) {
		
		return pollSpecial[button - 1] == StateSpecial.CLICKED;
	}
	
	/**
	 * Checks if the given button was doubled clicked
	 * <p>
	 * <ul>
	 * <li>1 ({@code BUTTON1})
	 * <li>2 ({@code BUTTON2})
	 * <li>3 ({@code BUTTON3})
	 * </ul>
	 * 
	 * @param button
	 *            The mouse button to check
	 * @return Whether the button matches the state
	 */
	public boolean buttonDoubleClicked(int button) {
		
		return pollSpecial[button - 1] == StateSpecial.DOUBLE;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public synchronized void mousePressed(MouseEvent e) {
		
		state[e.getButton() - 1] = true;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public synchronized void mouseReleased(MouseEvent e) {
		
		state[e.getButton() - 1] = false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public synchronized void mouseEntered(MouseEvent e) {
		
		mouseMoved(e);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public synchronized void mouseExited(MouseEvent e) {
		
		mouseMoved(e);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public synchronized void mouseDragged(MouseEvent e) {
		
		mouseMoved(e);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public synchronized void mouseMoved(MouseEvent e) {
		
		currentPos.setLocation(e.getPoint());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.
	 * MouseWheelEvent)
	 */
	public synchronized void mouseWheelMoved(MouseWheelEvent e) {
		
		wheelState = e.getWheelRotation();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		
		if (e.getClickCount() == 2 && !e.isConsumed()) {
			e.consume();
			stateSpecial[e.getButton() - 1] = 2;
		} else {
			stateSpecial[e.getButton() - 1] = 1;
		}
	}
	
}