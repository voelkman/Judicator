/*
 * Created on 11.02.2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.judicator.calendar.data;

import de.voelkman.utils.MathFunction;
import java.awt.Color;
import java.util.*;

public class CalendarConfiguration {

    // private static final Logger log =
    // LoggerFactory.getLogger(CalendarTable.class);
    public enum MonthDayModes {

        cut, wrap
    };

    public enum LeapYearCollisionPolicy {

        XOR, OR
    };
    HashMap<String, Integer> months = null;
    ArrayList<Integer> leapYearConfig = new ArrayList<Integer>();
    LeapYearCollisionPolicy leapYearCollisionPolicy = LeapYearCollisionPolicy.XOR;
    MonthDayModes monthDayMode = MonthDayModes.wrap;
    ArrayList<Moon> moons = null;
    ArrayList<Constellation> constellations = null;
    ArrayList<CalendarDerivation> derivations = null;
    ArrayList<String> days = null;
    ArrayList<Day> specialDays = new ArrayList<Day>();
    ArrayList<Day> anualDays = new ArrayList<Day>();
    int mykradorianCorrectionInDays = 0;
    // Temporal results
    int maxDaysPerMonth = 0;
    double calculateddaysInYear = 0;

    public ArrayList<Day> getSpecialDays() {
        return specialDays;
    }

    public ArrayList<Day> getSpecialDaysNotNull() {
        if (specialDays == null) {
            this.specialDays = new ArrayList<Day>();
        }
        return specialDays;
    }

    public void setSpecialDays(ArrayList<Day> specialDays) {
        this.specialDays = specialDays;
    }

    public ArrayList<Day> getAnualDays() {
        return anualDays;
    }

    public void setAnualDays(ArrayList<Day> anualDays) {
        this.anualDays = anualDays;
    }

    // private boolean[] conjunction = new boolean[moons.size()];
    public void setMoons(String[][] moonconfig) {
        if (moons == null) {
            moons = new ArrayList<Moon>();
        } else {
            moons.clear();
        }
        double max = 0;
        for (int i = 0; i < moonconfig.length; i++) {
            max = Math.max(Double.parseDouble(moonconfig[i][4]), max);
        }
        for (int i = 0; i < moonconfig.length; i++) {
            Moon m = new Moon();
            m.setName(moonconfig[i][0]);
            m.setRevolutionInDays(Double.parseDouble(moonconfig[i][1]));
            m.setOffset(Double.parseDouble(moonconfig[i][2]));
            m.setTaint(new Color(Integer.parseInt(moonconfig[i][3], 16)));
            m.setSize((int) (0.5 + ((Double.parseDouble(moonconfig[i][4]) * 100) / max)));
            moons.add(m);
        }
    }

    public void setConstellation(String[][] constellationconfig) {
        if (constellations == null) {
            constellations = new ArrayList<Constellation>();
        } else {
            constellations.clear();
        }
        for (int i = 0; i < constellationconfig.length; i++) {
            Constellation m = new Constellation();
            m.setName(constellationconfig[i][0]);
            m.setRevolutionInDays(Double.parseDouble(constellationconfig[i][1]));
            m.setExtremeInDays(Double.parseDouble(constellationconfig[i][2]));
            m.setOffsetInDays(Double.parseDouble(constellationconfig[i][3]));
            constellations.add(m);
        }

    }

    public void setMonths(String[][] months) {
        calculateddaysInYear = 0;
        maxDaysPerMonth = 0;
        this.months = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < months.length; i++) {
            this.months.put(months[i][0], new Integer(months[i][1]));
            int val = Integer.parseInt(months[i][1]);
            //daysInYear += val;
            maxDaysPerMonth = Math.max(maxDaysPerMonth, val);
        }
    }

    public void setDerivations(ArrayList<CalendarDerivation> derivations) {
        this.derivations = derivations;
    }

    public void setDays(String[] dayNamesOfWeek) {
        this.days = new ArrayList<String>();
        this.days.addAll(Arrays.asList(dayNamesOfWeek));
    }

    public void setMykradorianCorrectionInDays(String mykradorianCorrectionInDays) {
        this.mykradorianCorrectionInDays = Integer.parseInt(mykradorianCorrectionInDays);
    }

    public ArrayList<Moon> getMoonObjects() {
        return moons;
    }

    public String[][] getMoons() {
        if (moons == null) {
            return null;
        }
        String[][] result = new String[moons.size()][5];
        int count = 0;
        for (Moon m : moons) {
            result[count][0] = m.getName();
            result[count][1] = Double.toString(m.getRevolutionInDays());
            result[count][2] = Double.toString(m.getOffset());
            result[count][3] = Integer.toHexString(m.getTaint().getRed())
                    + Integer.toHexString(m.getTaint().getGreen()) + Integer.toHexString(m.getTaint().getBlue());
            result[count][4] = Integer.toString(m.getSize());
            count++;
        }
        return result;
    }

    public ArrayList<Constellation> getConstellationObjects() {
        return constellations;
    }

    public String[][] getConstellation() {
        if (constellations == null) {
            return null;
        }
        String[][] result = new String[constellations.size()][4];
        int count = 0;
        for (Constellation m : constellations) {
            result[count][0] = m.getName();
            result[count][1] = Double.toString(m.getRevolutionInDays());
            result[count][2] = Double.toString(m.getExtremeInDays());
            result[count][3] = Double.toString(m.getOffsetInDays());

            count++;
        }
        return result;
    }

    public MoonConjunction getNextMoonConjunction(int currentday, int threshold) {
        double[][] pairConjunctionPeriods = new double[moons.size()][moons.size()];
        double[][] pairConjunctionstartoffs = new double[moons.size()][moons.size()];

        for (int i = 0; i < moons.size(); i++) {
            for (int j = 0; j < moons.size(); j++) {
                double x = moons.get(i).getRevolutionInDays();
                double y = moons.get(j).getRevolutionInDays();
                if (x != y) {
                    pairConjunctionPeriods[i][j] = (x * y) / (y - x);
                } else {
                    pairConjunctionPeriods[i][j] = 0;
                }// log.debug(i+" "+j+" "+mooon[i][j]);
                pairConjunctionstartoffs[i][j] = moons.get(i).getOffset() - moons.get(j).getOffset();
            }
        }

        MoonConjunction mc = new MoonConjunction();
        int daysToCome = currentday + 38800000;
        double tolerance = 1;
        for (int ix = 0; ix < pairConjunctionPeriods.length - threshold; ix++) {
            double pairmeets[] = pairConjunctionPeriods[ix];
            // double pairstaroffs[] = pairConjunctionstartoffs[ix];
            // nextConstellation(mooon[i], day, 0.1,threshold);
            boolean[] selected = new boolean[pairmeets.length];
            int count = 0;
            int dayspassed = currentday;
            int rest = 0;
            double calc;
            while (count < threshold) {
                // iter++;
                dayspassed += Math.max(rest, 1);
                count = 0;
                rest = Integer.MAX_VALUE;
                for (int iy = 0; iy < selected.length; iy++) {
                    if (pairmeets[iy] > 0) {
                        calc = (dayspassed % pairmeets[iy]);
                        selected[iy] = (calc < tolerance);
                        if (selected[iy]) {
                            count++;
                        } else {
                            rest = Math.min((int) (pairmeets[iy] - calc), rest);
                        }
                    } else {
                        selected[iy] = (pairmeets[iy] == 0);
                    }
                }
                if (dayspassed > daysToCome) {
                    break;
                }
            }
            if (count >= threshold && dayspassed < daysToCome && dayspassed < mc.getAbsoluteDaysFromZero()) {
                mc.setAbsoluteDaysFromZero(dayspassed);
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i]) {
                        mc.add(moons.get(i));
                    }
                }
            }
        }
        return mc;
    }

    public HashMap<String, Integer> getMonthsAsMap() {
        return months;
    }

    public String[][] getMonths() {
        if (months == null) {
            return null;
        }
        String[][] result = new String[months.size()][2];
        int i = 0;
        for (Map.Entry<String, Integer> mo : months.entrySet()) {
            result[i][0] = mo.getKey();
            result[i][1] = mo.getValue().toString();
            i++;
        }
        return result;
    }

    public ArrayList<String> getDaysAsList() {
        return days;
    }

    public String[] getDays() {
        return days.toArray(new String[days.size()]);
    }

    public int getMykradorianCorrection() {
        return mykradorianCorrectionInDays;
    }

    public String getMykradorianCorrectionInDays() {
        return Integer.toString(mykradorianCorrectionInDays);
    }

    public int getAbsoluteDay(int year, int month, int day) {
        int result = (int)(year * getDaysInYear()) + getMykradorianCorrection();
        Iterator<Integer> it = months.values().iterator();
        int count = 0;
        while (it.hasNext() && count < month) {
            result += it.next().intValue();
            count++;
        }
        result += day;
        // log.debug(year +"."+month+"."+day+" = "+result );
        return result;
    }

    /**
     *
     * @param absoluteDays
     * @return int[] with {year,month,day}
     */
    public int[] getSplittedDate(int absoluteDays) {
        int[] result = new int[3];
        result[0] = (int)((absoluteDays - getMykradorianCorrection()) / getDaysInYear());
        int d = (int)((absoluteDays - getMykradorianCorrection()) % getDaysInYear());
        Iterator<Integer> it = months.values().iterator();
        int count = 0;
        while (it.hasNext() && d >= 0) {
            int x = it.next().intValue();
            if (d - x < 0) {
                // Month found
                result[2] = d;
                result[1] = count;
            } else {
                count++;
            }
            d -= x;
        }
        return result;
    }

    public int getMaxDaysPerMonth() {
        return maxDaysPerMonth;
    }

    public int getFirstDayOfWeekInMonth(int year, int month) {
        // Calc differece per year.
        if (MonthDayModes.wrap.equals(monthDayMode)) {
            Integer[] xx = months.values().toArray(new Integer[months.size()]);
            int deltaMonth = 0;
            int deltaYear = (int)(year * (getDaysInYear())) % getDaysAsList().size();
            for (int i = 0; i < Math.min(month, xx.length); i++) {
                deltaMonth += xx[i];
            }
            return (deltaYear + deltaMonth) % days.size();
        } else // If cut, month starts with the first day in week
        {
            return 0;
        }
    }

    public int getDayOfWeek(int[] date) {
        return (getFirstDayOfWeekInMonth(date[0], date[1]) + date[2]) % days.size();
    }

    public int getDayOfWeek(int absoluteDays) {
        return getDayOfWeek(getSplittedDate(absoluteDays));
    }

    public MonthDayModes getMode() {
        return monthDayMode;
    }

    public String getMonthDayMode() {
        return monthDayMode.toString();
    }

    public void setMonthDayMode(String monthDayMode) {
        this.monthDayMode = MonthDayModes.valueOf(monthDayMode);
    }

    public ArrayList<CalendarDerivation> getDerivations() {
        return derivations;
    }

    public int getSeason(int d) {
        return (int)(((((d - (mykradorianCorrectionInDays % getDaysInYear())) + getDaysInYear() / 8) % getDaysInYear()) * 4) / getDaysInYear());
    }

    public double getDaysInYear() {
        if(calculateddaysInYear < 1){
            calculateddaysInYear = getLeapYearFactor();
        }
        return calculateddaysInYear;
    }

    public boolean isLeapYear(double jahr) {
        boolean result = false;
        for (int i : leapYearConfig) {
            if (jahr % i == 0) {
                switch (leapYearCollisionPolicy) {
                    case XOR: {
                        result = !result;
                        break;
                    }
                    case OR:
                        break;
                }
            }
        }
        return result;
    }
    
    private double getLeapYearFactor() {
        //calculate  Leaps
        int kgv = 1;
        int count = 0;
        for (Integer i : leapYearConfig) {
            kgv = MathFunction.LCM(kgv, i);
        }

        for (int i = 0; i < kgv; i++) {
            if (isLeapYear(i)) {
                count++;
            }
        }
        return ((double) count) / kgv;
    }
}
