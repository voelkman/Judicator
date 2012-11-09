/*
 * Created on 02.11.2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.utils;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

public class ImageUtils {

	public static Image getColoredImage(Image icon, Color color) {
		// Image simg = icon;

		int w = icon.getWidth(null);
		int h = icon.getHeight(null);

		BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		buff.getGraphics().drawImage(icon, 0, 0, null);

		int color_constant = Math.min(Math.min(color.getRed(), color.getBlue()), color.getGreen());
		// int color_variable = Math.max(1, Math.max(Math.max(color.getRed(),
		// color.getBlue()), color.getGreen()) - color_constant);
		int color_variable = 255;
		double dr = (color.getRed() - color_constant) / (double) color_variable;
		double dg = (color.getGreen() - color_constant) / (double) color_variable;
		double db = (color.getBlue() - color_constant) / (double) color_variable;

		int pix[] = new int[w * h];
		int index = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		int trans = 255;
		int top = 0;
		int bottom = 0;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {

				trans = buff.getRGB(x, y);
				red = (trans & (255 << 16)) >> 16;
				green = (trans & (255 << 8)) >> 8;
				blue = trans & (255 << 0);
				trans = (trans) >>> 24;

				bottom = Math.min(Math.min(green, blue), red);
				top = (int) (((Math.max(Math.max(green, blue), red)) - bottom) * 1.0);

				// Now color as
				blue = Math.min(255, (int) (top * db) + bottom);
				green = Math.min(255, (int) (top * dg) + bottom);
				red = Math.min(255, (int) (top * dr) + bottom);

				if (Math.max(Math.max(green, blue), red) < 10) {
					// Blackify
					blue = 0;
					green = 0;
					red = 0;
				}

				pix[index] = ((trans << 24) | (red << 16) | (green << 8) | blue);

				index++;
			}

		}
		Image img = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w, h, pix, 0, w));

		return img;

	}

	public static Image getLightColoredImage(Image icon, Color color) {
		// Image simg = icon;

		int w = icon.getWidth(null);
		int h = icon.getHeight(null);

		BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		buff.getGraphics().drawImage(icon, 0, 0, null);

		int color_constant = Math.min(Math.min(color.getRed(), color.getBlue()), color.getGreen());
		int color_variable = Math.max(1, Math.max(Math.max(color.getRed(), color.getBlue()), color.getGreen()) - color_constant);
		// int color_variable = 255;
		double dr = (color.getRed() - color_constant) / (double) color_variable;
		double dg = (color.getGreen() - color_constant) / (double) color_variable;
		double db = (color.getBlue() - color_constant) / (double) color_variable;

		int pix[] = new int[w * h];
		int index = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		int trans = 255;
		int top = 0;
		int bottom = 0;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {

				trans = buff.getRGB(x, y);
				red = (trans & (255 << 16)) >> 16;
				green = (trans & (255 << 8)) >> 8;
				blue = trans & (255 << 0);
				trans = (trans) >>> 24;

				bottom = Math.min(Math.min(green, blue), red);
				top = (int) (((Math.max(Math.max(green, blue), red)) - bottom) * 2.7);

				// Now color as
				blue = Math.min(255, (int) (top * db) + bottom);
				green = Math.min(255, (int) (top * dg) + bottom);
				red = Math.min(255, (int) (top * dr) + bottom);

				pix[index] = ((trans << 24) | (red << 16) | (green << 8) | blue);

				index++;
			}

		}
		Image img = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w, h, pix, 0, w));

		return img;

	}
	
}
