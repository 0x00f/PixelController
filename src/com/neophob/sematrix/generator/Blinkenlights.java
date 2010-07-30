package com.neophob.sematrix.generator;

import java.util.logging.Logger;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.lib.blinken.BlinkenLibrary;

import com.neophob.sematrix.glue.Collector;

/**
 * @author mvogt
 *
 */
public class Blinkenlights extends Generator implements PConstants {

	static Logger log = Logger.getLogger(Blinkenlights.class.getName());

	private BlinkenLibrary blinken;
	private PImage tmp;

	public Blinkenlights(String filename) {
		super(GeneratorName.BLINKENLIGHTS);
		PApplet parent = Collector.getInstance().getPapplet();
		tmp=parent.createImage( this.getInternalBufferXSize(), this.getInternalBufferYSize(), RGB);
		blinken = new BlinkenLibrary(parent, filename);
		blinken.setIgnoreFileDelay(true);
		blinken.loop();
	}

	/**
	 * load a new file
	 * @param file
	 */
	public void loadFile(String file) {
		PApplet parent = Collector.getInstance().getPapplet();
		blinken.dispose();
		blinken = new BlinkenLibrary(parent, file);
		blinken.setIgnoreFileDelay(true);
		blinken.loop();
	}
	
	@Override
	public void update() {
		tmp.copy(blinken, 0, 0, blinken.getWidth(), blinken.getHeight(), 0, 0, this.getInternalBufferXSize(), this.getInternalBufferYSize());
		System.arraycopy(tmp.pixels, 0, this.internalBuffer, 0, tmp.pixels.length);
	}

	@Override
	public void close() {
		blinken.dispose();
	}
}