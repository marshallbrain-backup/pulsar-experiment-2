package main.java.com.brain.pulsar;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import main.java.com.brain.ion.Ion;
import main.java.com.brain.ion.RenderCall;
import main.java.com.brain.ion.TickCall;

public class Pulsar implements TickCall, RenderCall {
	
	private Canvas screen;
	
	public Pulsar() {
		
		screen = new Canvas();
		Ion ion = new Ion(this, this, screen);
		
		init();
		ion.start();
		
	}
	
	private void init() {
		
	}

	@Override
	public void tick() {
		
		
		
	}

	@Override
	public  void render() {
		
		BufferStrategy bs = screen.getBufferStrategy();
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(5, 5, 100, 100);
		
		g.dispose();
		bs.show();
		
	}
	
}
