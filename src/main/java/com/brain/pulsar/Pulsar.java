package main.java.com.brain.pulsar;

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
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

import main.java.com.brain.ion.Ion;
import main.java.com.brain.ion.RenderCall;
import main.java.com.brain.ion.TickCall;
import main.java.com.brain.ion.XmlParser;

public class Pulsar implements TickCall, RenderCall {
	
	private Canvas screen;
	
	public Pulsar() {
		
		screen = new Canvas();
		Ion ion = new Ion(this, this, screen);
		
		init();
		ion.start();
		
	}
	
	private void init() {
		
		try {
			cloneResorses();
		} catch (ClassNotFoundException | URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		
//		XmlParser.getXml(fileName, classList);
		
	}

	@Override
	public synchronized void tick() {
		
		
		
	}

	@Override
	public synchronized void render() {
		
		BufferStrategy bs = screen.getBufferStrategy();
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
		
		g.dispose();
		bs.show();
		
	}
	
	private void cloneResorses() throws URISyntaxException, IOException, ClassNotFoundException {
		
		URI commonOld = getClass().getResource("/resorses/common/").toURI();
		File commonNew = new File("common");
		
		cloneFolder(commonOld, commonNew);
		
	}
	
	private void cloneFolder(URI o, File n) throws IOException {
		
		Path old = getJarPath(o, n);
		
		try(Stream<Path> walk = Files.walk(old, 1).skip(1)) {
			
			for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
				
				Path p = it.next();
				
				if(!p.toString().contains(".")) {
					
					File n2 = new File(n.getPath() + File.separator + p.getFileName());
					cloneFolder(p.toUri(), n2);
					
				} else {
					
					File n2 = new File(n.getPath() + File.separator + p.getFileName());
					n2.getParentFile().mkdirs();
					
					if(!n2.exists()) {
//						StandardCopyOption.REPLACE_EXISTING
						Files.copy(p, n2.toPath());
					}
					
				}
				
			}
			
		}
		
	}
	
	private Path getJarPath(URI o, File n) throws IOException {
		
		if(!n.exists())
			n.mkdirs();
		
		if (o.getScheme().equals("jar")) {
			
			FileSystem fileSystem;
			
			try {
				fileSystem = FileSystems.getFileSystem(o);
			} catch (FileSystemNotFoundException e) {
				fileSystem = FileSystems.newFileSystem(o, Collections.<String, Object>emptyMap());
			}
			
			return fileSystem.getPath(o.toString().substring(o.toString().lastIndexOf("!")+1));
			
		} else {
		    return Paths.get(o);
		}
		
	}
	
}
