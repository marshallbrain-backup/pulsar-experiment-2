package main.java.com.brain.ion.input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

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
		
		for(int i = 0; i < BUTTON_COUNT; ++i) {
			poll[i] = State.RELEASED;
		}
		
		for(int i = 0; i < BUTTON_COUNT; ++i) {
			pollSpecial[i] = StateSpecial.NONE;
		}
		
	}
	
	public Mouse(Mouse m, int offsetX, int offsetY) {
		
		mousePos = new Point((int) (m.mousePos.getX())-offsetX, (int) (m.mousePos.getY())-offsetY);
		mouseChange = m.mouseChange;
		currentPos = m.currentPos;
		state = m.state;
		stateSpecial = m.stateSpecial;
		poll = m.poll;
		pollSpecial = m.pollSpecial;
		
	}
	
	public synchronized void poll() {
		
		mouseChange = new Point(mousePos.x-currentPos.x, mousePos.y-currentPos.y);
		mousePos = new Point(currentPos);
		
		wheelDir = wheelState;
		wheelState = 0;
		
		for(int i = 0; i < BUTTON_COUNT; ++i) {
			
			if(state[i]) {
				if(poll[i] == State.RELEASED)
					poll[i] = State.ONCE;
				else
					poll[i] = State.PRESSED;
			} else {
				poll[i] = State.RELEASED;
			}
			
			switch(stateSpecial[i]) {
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

	public Point getPosition() {
		return mousePos;
	}

	public Point getChange() {
		return mouseChange;
	}

	public int getWheelDir() {
		return wheelDir;
	}

	public boolean buttonDownOnce(int button) {
		return poll[button-1] == State.ONCE;
	}

	public boolean buttonDown(int button) {
		return poll[button-1] == State.ONCE ||
				poll[button-1] == State.PRESSED;
	}

	public boolean buttonClicked(int button) {
		return pollSpecial[button-1] == StateSpecial.CLICKED;
	}

	public boolean buttonDoubleClicked(int button) {
		return pollSpecial[button-1] == StateSpecial.DOUBLE;
	}
  
	public synchronized void mousePressed(MouseEvent e) {
		state[e.getButton()-1] = true;
	}

	public synchronized void mouseReleased(MouseEvent e) {
		state[e.getButton()-1] = false;
	}

	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}
  
	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}
  
	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseMoved(MouseEvent e) {
		currentPos.setLocation(e.getPoint());
	}

	public synchronized void mouseWheelMoved(MouseWheelEvent e) {
		wheelState = e.getWheelRotation();
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed()) {
			e.consume();
			stateSpecial[e.getButton()-1] = 2;
		} else {
			stateSpecial[e.getButton()-1] = 1;
		}
	}
	
}