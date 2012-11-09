/*
 * Created on 03.09.2008
 */
package de.voelkman.utils;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class FontResolver {

	public static void main(String[] args) {
		Font[] f = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		for (Font d : f) {
			System.out.println(d.getName());
		}
		for(int i = 0; i < 4096; i++){
			System.out.print(Integer.toHexString( i)+": "+(char)i+"\t");
			if(i%10 == 0){
				System.out.println();
			}
		}
	}
}
