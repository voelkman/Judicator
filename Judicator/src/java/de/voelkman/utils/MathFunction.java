/*
 * Created on 13.02.2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.utils;

import java.awt.Dimension;

public final class MathFunction {

    public static int LCM(int val1, int val2) {
        return (val1 * val2) / GCD(val1, val2);
    }

       

    public static int GCD(int aValue1, int aValue2) {
        int iter = 0;
        int retVal = 1;

        aValue1 = StrictMath.abs(aValue1);
        aValue2 = StrictMath.abs(aValue2);

        int tmpMax = StrictMath.max(aValue1, aValue2);
        int tmpMin = StrictMath.min(aValue1, aValue2);

        while (tmpMin > 0 || retVal >= 1) {
            iter++;
            retVal = tmpMin;
            tmpMin = tmpMax % tmpMin;
            tmpMax = retVal;
            if (tmpMin < 1) {
                break;
            }
        }
        System.out.println("1: "+iter);
        return retVal > 1 ? retVal : 1;
    }

    public static int minmax(int value, int min, int max) {
        return Math.min(max, Math.max(min, value));
    }

    public static double minmax(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     *
     * @param amount minimum number of cells needed
     * @param ratio the value width/height of the desired grid
     * @return Dimension of the grid
     */
    public static Dimension calculateDisplayGridForItems(int amount, double ratio) {
        Dimension d = new Dimension(1, 1);
        while (d.width * d.height < amount) {
            if (0 >= (((ratio * d.getHeight())) - d.getWidth())) {
                d.height++;
            } else {
                d.width++;
            }
        }
        return d;
    }
}
