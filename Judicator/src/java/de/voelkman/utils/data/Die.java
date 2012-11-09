/*
 * Created on 23.09.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.utils.data;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author g8712
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Die {

    private static Pattern VERIFICATION_REGEXP = null;
    private static final String DIE_LETTER_GERMAN_UPPERCASE = "W"; //$NON-NLS-1$

    private static enum Operator {

        plus, minus, multiply, divide
    };
    protected int type = 0;
    protected int multiplier = 0;
    protected int bonus = 0;
    protected Operator o = Operator.plus;
    protected static Random generator = new Random();

    static {
        VERIFICATION_REGEXP = Pattern.compile("^([1-9][0-9]*[wWdD][1-9][0-9]*)?([+-/*]?[0-9]+)?$");

    }

    public Die() {
        type = 0;
        multiplier = 0;
        bonus = 0;
    }

    public Die(int type, int multiply, int bonus) {
        this.type = type;
        this.multiplier = multiply;
        this.bonus = bonus;
    }

    public Die(String value) {
        // $ANALYSIS-IGNORE
        this.setFromString(value);
    }

    public static boolean isDieString(String value) {
        boolean d = VERIFICATION_REGEXP.matcher(value).matches();
        return d;
    }

    public String getDescription() {
        if ((multiplier <= 0) || (type <= 0)) {
            return (Integer.toString(bonus));
        } else {
            if ((bonus == 0)) {
                return (Integer.toString(multiplier) + DIE_LETTER_GERMAN_UPPERCASE + Integer.toString(type));
            } else {
                String result = Integer.toString(multiplier) + DIE_LETTER_GERMAN_UPPERCASE + Integer.toString(type);
                switch (o) {
                    case plus: {
                        result += "+";
                        break;
                    }
                    case minus: {
                        result += "-";
                        break;
                    }
                    case multiply: {
                        result += "*";
                        break;
                    }
                    case divide: {
                        result += "/";
                        break;
                    }

                    default:
                        break;
                }
                return result + Integer.toString(bonus);
            }
        }
    }

    @Override
    public String toString() {
        return getDescription();

    }

    /**
     * Creates a new random result based on type, multiplier and bonus In
     * contrast to ResolvableDie it does not store any results
     *
     * @return new random value
     */
    public int roll() {
        int result = 0;
        for (int i = 0; i < multiplier; i++) {
            result = result + generator.nextInt(type) + 1;
        }
        switch (o) {
            case plus: {
                result += bonus;
                break;
            }
            case minus: {
                result -= bonus;
                break;
            }
            case multiply: {
                result *= bonus;
                break;
            }
            case divide: {
                result = result / Math.max(bonus, 1);
                break;
            }

            default:
                break;
        }
        return result;
    }

    public int getMaximumValue() {
        int result = multiplier * type;
        switch (o) {
            case plus: {
                result += bonus;
                break;
            }
            case minus: {
                result -= bonus;
                break;
            }
            case multiply: {
                result *= bonus;
                break;
            }
            case divide: {

                result = result / Math.max(1, bonus);
                break;
            }

            default:
                break;
        }
        return result;
    }

    public int getMinimumValue() {
        int result = multiplier;
        switch (o) {
            case plus: {
                result += bonus;
                break;
            }
            case minus: {
                result -= bonus;
                break;
            }
            case multiply: {
                result *= bonus;
                break;
            }
            case divide: {
                result = result / Math.max(bonus, 1);
                break;
            }
            default:
                break;
        }
        return result;
    }

    public final void setFromString(String dieString) throws NumberFormatException {
        String s = (dieString != null) ? dieString : "0";

        String ms = "";
        String ts = "";
        String bs = "";
        int separator1 = s.toUpperCase().indexOf(DIE_LETTER_GERMAN_UPPERCASE);
        if (separator1 < 0) {
            separator1 = s.toUpperCase().indexOf("D");
        }

        int separator2 = s.toUpperCase().indexOf("/");
        o = Operator.divide;
        if (separator2 < 0) {
            separator2 = s.toUpperCase().indexOf("-");
            o = Operator.minus;
        }
        if (separator2 < 0) {
            separator2 = s.toUpperCase().indexOf("*");
            o = Operator.multiply;
        }
        if (separator2 < 0) {
            separator2 = s.toUpperCase().indexOf("+");
            o = Operator.plus;
        }

        if (separator1 > 0) {
            ms = s.substring(0, separator1);
            if (separator2 > 0) {
                ts = s.substring(separator1 + 1, separator2);
                bs = s.substring(separator2 + 1);
            } else {
                ts = s.substring(separator1 + 1);
            }
        } else {
            bs = s;
        }
        try {
            multiplier = Integer.parseInt(ms);

        } catch (NumberFormatException e) {
            multiplier = 0;
        }
        try {
            type = Integer.parseInt(ts);

        } catch (NumberFormatException e1) {
            type = 0;
        }
        try {
            bonus = Integer.parseInt(bs);
        } catch (NumberFormatException e2) {
            bonus = 0;
        }
    }

    public int getType() {
        return type;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void setType(int type) {
        this.type = type;
    }
}
