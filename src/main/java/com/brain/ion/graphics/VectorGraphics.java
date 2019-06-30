package com.brain.ion.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.brain.ion.graphics.vectors.Circle;
import com.brain.ion.graphics.vectors.Image;
import com.brain.ion.graphics.vectors.Path;
import com.brain.ion.graphics.vectors.Rectangle;
import com.brain.ion.graphics.vectors.Text;
import com.brain.ion.graphics.vectors.VectorGroup;
import com.brain.ion.settings.SettingEntry;
import com.brain.ion.xml.IonXmlRoot;
import com.brain.ion.xml.XmlParser;

/**
 * A graphics class for dealing with vectors form an xml.
 * 
 * @author Marshall Brain
 *
 */
public class VectorGraphics {
	
	private int hashcode;
	
	private Graphics2D graphics;
	private AffineTransform currentTransform;
	private Area trackArea;
	
	private Map<SettingEntry, String> settings;
	private Map<String, BufferedImage> imageMap;
	
	/**
	 * Base constructor
	 * 
	 * @param g
	 *            The graphics to draw with
	 * @param s
	 *            The settings for the game
	 * @param hashcode 
	 */
	
	//TODO fix doc
	public VectorGraphics(Graphics2D g, Map<SettingEntry, String> s, int hashcode) {
		
		graphics = g;
		settings = s;
		this.hashcode = hashcode;
		
		g.clipRect(0, 0, getWindowWidth(), getWindowHeight());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
	}

	public static Map<String, BufferedImage> loadImages(File gfx) {
		
		Map<String, BufferedImage> imageMap = new HashMap<>();
		
		loadImages(gfx, imageMap);
		
		return imageMap;
		
	}

	private static void loadImages(File gfx, Map<String, BufferedImage> imageMap) {
		
		for(File f: gfx.listFiles()) {
			if(f.isFile()) {
				try {
					BufferedImage bi = ImageIO.read(f);
					if(bi != null) {
						imageMap.put(f.getPath(), bi);
					}
				} catch (IOException e) {
				}
			} else {
				loadImages(f, imageMap);
			}
		}
		
	}
	
	/**
	 * Loads the vectors from the given file.
	 * 
	 * @param gfx
	 *            The file to read the xml's from
	 * @return The map of VectorGroups
	 */
	public static Map<String, VectorGroup> loadVectors(File gfx) {
		
		Map<String, VectorGroup> vectors = new HashMap<>();
		Class<?>[] classes = { IonXmlRoot.class, VectorGroup.class, Circle.class, Rectangle.class, Text.class, Path.class, Image.class };
		
		readFiles(gfx, vectors, classes);
		
		return vectors;
		
	}
	
	/**
	 * Goes through every file in the specified folder and converts them to a map of
	 * {@link VectorGroup}.
	 * 
	 * @param folder
	 *            The folder to read from
	 * @param vectors
	 *            The map of {@link VectorGroup} to add the responses from
	 *            {@link XmlParser} to
	 * @param classList
	 *            The class list for {@link XmlParser}
	 * @see XmlParser
	 */
	private static void readFiles(File folder, Map<String, VectorGroup> vectors, Class<?>[] classes) {
		
		for (File f : folder.listFiles()) {
			
			if (f.isDirectory()) {
				readFiles(f, vectors, classes);
			}
			if (f.isFile()) {
				IonXmlRoot xr = (IonXmlRoot) XmlParser.getXml(f, classes);
				
				// TODO find all text vectors and preload their font metrics
				if(xr != null) {
					for (VectorGroup vg : xr.getVectorGroups()) {
						vectors.put(vg.getPath(), vg);
					}
				}
				
			}
			
		}
		
	}

	public void loadImages(Map<String, BufferedImage> map) {
		imageMap = map;
	}
	
	/**
	 * @return The width of the window
	 */
	public int getWindowWidth() {
		
		return Integer.parseInt(settings.get(SettingEntry.WINDOWED_WIDTH));
	}
	
	/**
	 * @return The height of the window
	 */
	public int getWindowHeight() {
		
		return Integer.parseInt(settings.get(SettingEntry.WINDOWED_HEIGHT));
	}
	
	/**
	 * Draws the given shape to the screen in the given style.
	 * 
	 * @param s
	 *            The shape to draw
	 * @param style
	 *            The style map to draw the shape with
	 */
	public void draw(Shape s, Map<String, String> style) {
		
		s = currentTransform.createTransformedShape(s);
		
		if (style != null) {
			
			String fill = style.get("fill");
			if (!(fill == null || fill.equals("none"))) {
				
				String alpha = style.get("fill-opacity");
				if (alpha == null)
					alpha = "1";
				
				graphics.setColor(getColor(fill, alpha));
				graphics.fill(s);
				
			}
			
			String stroke = style.get("stroke");
			if (!(stroke == null || stroke.equals("none"))) {
				
				String alpha = style.get("stroke-opacity");
				if (alpha == null)
					alpha = "1";
				
				String width = style.get("stroke-width");
				if (width == null)
					width = "1";
				BasicStroke bs = new BasicStroke(Integer.parseInt(width));
				
				graphics.setColor(getColor(stroke, alpha));
				graphics.setStroke(bs);
				graphics.draw(s);
				
			}
			
			if(trackArea != null) {
				trackArea.add(new Area(s));
			}
			
		}
		
	}

	public void draw(Image image) {
		if(imageMap != null) {
			BufferedImage i = imageMap.get(image.getImagePath());
			graphics.drawImage(i, image.getXform(), null);
		}
	}
	
	/**
	 * Sets the current {@link AffineTransform} of the screen to the position.
	 * 
	 * @param position
	 *            The new position of the screen
	 */
	public void setTranslate(ScreenPosition position) {
		
		currentTransform = new AffineTransform();
		
		switch (position) {
			case ZERO:
				currentTransform.translate(0, 0);
				break;
			case CENTER:
				currentTransform.translate(getWindowWidth() / 2, getWindowHeight() / 2);
				break;
			default:
				return;
		}
		
	}
	
	/**
	 * Moves the current {@link AffineTransform} of the screen.
	 * 
	 * @param x
	 *            The delta x
	 * @param y
	 *            The delta y
	 */
	public void moveTranslate(double x, double y) {
		
		currentTransform.translate(x, y);
		
	}

	public void moveTranslate(Point origin) {
		currentTransform.translate(origin.getX(), origin.getY());
	}
	
	/**
	 * Converts a string representation of a color in hex into a {@link Color}.
	 * 
	 * @param hex
	 *            The hex string of the color
	 * @param alpha
	 *            The alpha of the color
	 * @return The {@link Color}
	 */
	private static Color getColor(String hex, String alpha) {
		
		Color c = null;
		
		if (hex.startsWith("#")) {
			
			hex = hex.substring(1);
			
			int r = Integer.parseInt(hex.substring(0, 2), 16);
			int g = Integer.parseInt(hex.substring(2, 4), 16);
			int b = Integer.parseInt(hex.substring(4, 6), 16);
			int a = Math.round(Float.parseFloat(alpha) * 255);
			
			c = new Color(r, g, b, a);
			
		}
		
		return c;
		
	}
	
	/**
	 * @return The current AffineTransform of the screen
	 */
	public AffineTransform getAffineTransform() {
		
		return new AffineTransform(currentTransform);
	}
	
	/**
	 * @return The base graphics object
	 */
	public Graphics2D getGraphics() {
		
		return (Graphics2D) graphics.create();
	}
	
	public int getParentCode() {
		return hashcode;
	}

	public void beginAreaRendering() {
		trackArea = new Area();
	}

	public Area resetAreaRendering() {
		Area clone = new Area(trackArea);
		trackArea = null;
		return clone;
	}
	
}
