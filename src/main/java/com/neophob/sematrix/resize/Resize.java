/**
 * Copyright (C) 2011 Michael Vogt <michu@neophob.com>
 *
 * This file is part of PixelController.
 *
 * PixelController is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PixelController is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PixelController.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.neophob.sematrix.resize;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

/**
 * resize a larger buffer for a smaller buffer.
 *
 * @author michu
 */
public abstract class Resize {

	/**
	 * The Enum ResizeName.
	 *
	 * @author michu
	 */
	public enum ResizeName {
		
		/** The PIXE l_ resize. */
		PIXEL_RESIZE(0),
		
		/** The QUALIT y_ resize. */
		QUALITY_RESIZE(1);
		
		/** The id. */
		private int id;
		
		/**
		 * Instantiates a new resize name.
		 *
		 * @param id the id
		 */
		ResizeName(int id) {
			this.id = id;
		}
		
		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public int getId() {
			return id;
		}
	}
	
	/** The resize name. */
	private ResizeName resizeName;
	
	/**
	 * Instantiates a new resize.
	 *
	 * @param controller the controller
	 * @param resizeName the resize name
	 */
	public Resize(PixelControllerResize controller, ResizeName resizeName) {
		this.resizeName = resizeName;
		controller.addResize(this);
	}
	
	/**
	 * Gets the buffer.
	 *
	 * @param buffer the buffer
	 * @param deviceXSize the device x size
	 * @param deviceYSize the device y size
	 * @param currentXSize the current x size
	 * @param currentYSize the current y size
	 * @return the buffer
	 */
	public abstract int[] getBuffer(int[] buffer, int deviceXSize, int deviceYSize, int currentXSize, int currentYSize);
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.resizeName.getId();
	}
	
	
	/**
	 * internal use - get buffer from image.
	 *
	 * @param scaledImage the scaled image
	 * @param deviceXSize the device x size
	 * @param deviceYSize the device y size
	 * @return the pixels from image
	 */
	public static int[] getPixelsFromImage(BufferedImage scaledImage, int deviceXSize, int deviceYSize) {
		//painfull slow!
		//return scaledImage.getRGB(0, 0, deviceXSize, deviceYSize, null, 0, deviceXSize);
		DataBufferInt buf = (DataBufferInt) scaledImage.getRaster().getDataBuffer();
		return buf.getData();
	}

	/**
	 * Creates the image.
	 *
	 * @param buffer the buffer
	 * @param currentXSize the current x size
	 * @param currentYSize the current y size
	 * @return the buffered image
	 */
	public static BufferedImage createImage(int[] buffer, int currentXSize, int currentYSize) {
		BufferedImage bi = new BufferedImage(currentXSize, currentYSize, BufferedImage.TYPE_INT_RGB);
		//bi.setRGB(0, 0, currentXSize, currentYSize, buffer, 0, currentXSize);
		WritableRaster newRaster = bi.getRaster();
		newRaster.setDataElements(0, 0, currentXSize, currentYSize, buffer);
		bi.setData(newRaster);

		return bi;
	}

}
