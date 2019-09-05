package com.brain.pulsar;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.brain.ion.Ion;
import com.brain.ion.game_loop.RenderCall;
import com.brain.ion.game_loop.TickCall;
import com.brain.ion.graphics.VectorGraphics;
import com.brain.ion.input.Mouse;
import com.brain.ion.settings.SettingEntry;
import com.brain.ion.xml.XmlParser;
import com.brain.pulsar.empires.Empire;
import com.brain.pulsar.empires.colonies.types.BuildingType;
import com.brain.pulsar.empires.colonies.types.DistrictType;
import com.brain.pulsar.ui.Ui;
import com.brain.pulsar.universe.StarSystem;
import com.brain.pulsar.universe.types.BodyType;
import com.brain.pulsar.universe.types.StarSystemType;
import com.brain.pulsar.xml.DataContainer;

/**
 * The main class for the game. Handles initialization of the game engine as
 * well as handling game tick and render calls
 * 
 * @author Marshall Brain
 */
public class Pulsar implements TickCall, RenderCall {
	
	private Map<SettingEntry, String> settings;
	
	private StarSystem mainSystem;
	private Ui ui;
	private Mouse mouse;
	
	private Canvas screen;
	
	/**
	 * Class constructor
	 */
	public Pulsar() {
		
		screen = new Canvas();
		Ion ion = new Ion(this, this, screen);
		
		init();
		
		settings = ion.getSettings();
		ion.start();
		
	}
	
	/**
	 * Initialization of the game
	 */
	private void init() {
		
		try {
			cloneResorses();
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		
		List<DataContainer> data = new ArrayList<>();
		
		// The Type classes are there for the
		// @XmlAnyElement tag in DataContainer
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, StarSystemType.class, DistrictType.class };
		
		getXmlFiles(new File("common"), dataTypes, data);
		DataContainer common = new DataContainer(data);
		
		// Seperation into list is done this way instead of doing it by folder name
		// to allow the ability to oraganize them any way the user wants
		List<BodyType> typeBodys = common.getMatchData(BodyType.class);
		List<StarSystemType> typeSytems = common.getMatchData(StarSystemType.class);
		List<DistrictType> districtTypes = common.getMatchData(DistrictType.class);;
		List<BuildingType> buildingTypes = common.getMatchData(BuildingType.class);;
		
		Empire empire = new Empire(districtTypes, buildingTypes);
		
		mainSystem = new StarSystem(typeBodys, typeSytems);
		mainSystem.getStarList().get(0).createColony(empire);
		
		ui = new Ui(mainSystem);
		
		mouse = new Mouse();
		
		screen.addMouseListener(mouse);
		screen.addMouseMotionListener(mouse);
		screen.addMouseWheelListener(mouse);
		
	}
	
	/**
	 * Clones folders from inside the jar file into the folder the file is located
	 * 
	 * Current folders that are cloned
	 * <ul>
	 * <li>common</li>
	 * </ul>
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void cloneResorses() throws URISyntaxException, IOException {
		
		URI commonOld = PulsarBootstrap.class.getResource("/resorses/common").toURI();
		URI gfxOld = PulsarBootstrap.class.getResource("/resorses/gfx").toURI();
		
		File commonNew = new File("common");
		File gfxNew = new File("gfx");
		
		cloneFolder(commonOld, commonNew);
		cloneFolder(gfxOld, gfxNew);
		
	}
	
	/**
	 * Clone the folder to a different location, mainly should be used for cloning a
	 * folder located in the jar to a folder outside the jar.
	 * 
	 * @param jarUri
	 *            The path to the folder inside the jar
	 * @param rootPath
	 *            The destination folder
	 * @throws IOException
	 */
	private static void cloneFolder(URI jarUri, File rootPath) throws IOException {
		
		Path jarPath = getJarPath(jarUri);
		
		// Skips the first element because it crashes other wise
		try (Stream<Path> folderStream = Files.walk(jarPath, 1).skip(1)) {
			
			for (Iterator<Path> it = folderStream.iterator(); it.hasNext();) {
				
				Path p = it.next();
				
				// Determans if it is a folder or a file
				if (!p.toString().contains(".")) {
					
					File n2 = new File(rootPath.getPath() + File.separator + p.getFileName());
					cloneFolder(p.toUri(), n2);
					
				} else {
					
					File n2 = new File(rootPath.getPath() + File.separator + p.getFileName());
					n2.getParentFile().mkdirs();
					
					if (!n2.exists()) {
						Files.copy(p, n2.toPath());
					}
					
				}
				
			}
			
		}
		
	}

	/**
	 * Converts a {@link URI} to a {@link Path}
	 * 
	 * @param jarPath
	 *            The {@link URI} to conert
	 * @return The {@link Path} to the location from the {@link URI}
	 * @throws IOException
	 */
	private static Path getJarPath(URI jarPath) throws IOException {
		
		if (jarPath.getScheme().equals("jar")) {

			FileSystem fileSystem;
			
			try {
				fileSystem = FileSystems.getFileSystem(jarPath);
			} catch (FileSystemNotFoundException e) {
				fileSystem = FileSystems.newFileSystem(jarPath, Collections.<String, Object>emptyMap());
			}
			
			return fileSystem.getPath(jarPath.toString().substring(jarPath.toString().lastIndexOf("!")+1));
			
		}
		
		return Paths.get(jarPath);
		
	}
	
	/**
	 * Goes through every file in the specified folder and converts them to a list
	 * of {@link DataContainer}.
	 * 
	 * @param folder
	 *            The folder to read from
	 * @param classList
	 *            The class list for {@link XmlParser}
	 * @param dataList
	 *            The list of {@link DataContainer} to add the responses from
	 *            {@link XmlParser} to
	 * @see XmlParser
	 */
	private static void getXmlFiles(File folder, Class<?>[] classList, List<DataContainer> dataList) {
		
		for (File f : folder.listFiles()) {
			
			if (f.isDirectory()) {
				getXmlFiles(f, classList, dataList);
			} else if (f.isFile()) {
				dataList.add((DataContainer) XmlParser.getXml(f, classList));
			}
			
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.brain.ion.TickCall#tick()
	 */
	@Override
	public synchronized void tick() {
		
		mouse.poll();
		
		ui.tick(mouse);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.brain.ion.RenderCall#render()
	 */
	@Override
	public synchronized void render() {
		
		BufferStrategy bs = screen.getBufferStrategy();
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		VectorGraphics vg = new VectorGraphics(g, settings, screen.hashCode());
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
		
		ui.render(vg);
		
		g.dispose();
		bs.show();
		
	}
	
}
