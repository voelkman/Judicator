package de.voelkman.judicator.calendar.data;

import de.voelkman.utils.MathFunction;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.persistence.*;

@Entity
public class CalendarConfiguration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // private static final Logger log =
    // LoggerFactory.getLogger(CalendarTable.class);
    public enum MonthDayModes {

        cut, wrap
    };

    public enum LeapYearCollisionPolicy {

        XOR, OR
    };
    @OneToMany
    ArrayList<Month> months = null;
    ArrayList<Integer> leapYearConfig = new ArrayList<Integer>();
    LeapYearCollisionPolicy leapYearCollisionPolicy = LeapYearCollisionPolicy.XOR;
    MonthDayModes monthDayMode = MonthDayModes.wrap;
    @OneToMany
    ArrayList<Moon> moons = null;
    @OneToMany
    ArrayList<Constellation> constellations = null;
    @OneToMany
    ArrayList<CalendarDerivation> derivations = null;
    @OneToMany
    ArrayList<String> dayNames = null;
    @OneToMany
    ArrayList<CalendarEntry> specialDays = new ArrayList<CalendarEntry>();
    @OneToMany
    ArrayList<CalendarEntry> anualDays = new ArrayList<CalendarEntry>();
    int mykradorianCorrectionInDays = 0;
    // Temporal results
    @Transient
    int maxDaysPerMonth = 0;
    @Transient
    double calculateddaysInYear = 0;

    public ArrayList<CalendarEntry> getSpecialDays() {
        return specialDays;
    }

    public ArrayList<CalendarEntry> getSpecialDaysNotNull() {
        if (specialDays == null) {
            this.specialDays = new ArrayList<CalendarEntry>();
        }
        return specialDays;
    }

    public void setSpecialDays(ArrayList<CalendarEntry> specialDays) {
        this.specialDays = specialDays;
    }

    public ArrayList<CalendarEntry> getAnualDays() {
        return anualDays;
    }

    public void setAnualDays(ArrayList<CalendarEntry> anualDays) {
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

    public void setDerivations(ArrayList<CalendarDerivation> derivations) {
        this.derivations = derivations;
    }

    public void setDays(String[] dayNamesOfWeek) {
        this.dayNames = new ArrayList<String>();
        this.dayNames.addAll(Arrays.asList(dayNamesOfWeek));
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

    public ArrayList<String> getDaysAsList() {
        return dayNames;
    }

    public String[] getDays() {
        return dayNames.toArray(new String[dayNames.size()]);
    }

    public int getMykradorianCorrection() {
        return mykradorianCorrectionInDays;
    }

    public String getMykradorianCorrectionInDays() {
        return Integer.toString(mykradorianCorrectionInDays);
    }

    public int getAbsoluteDay(int year, int month, int day) {
        int result = (int) (year * getDaysInYear()) + getMykradorianCorrection();
        Iterator<Month> it = months.iterator();
        int count = 0;
        while (it.hasNext() && count < month) {
            result += it.next().getDaysInMonth();
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
        result[0] = (int) ((absoluteDays - getMykradorianCorrection()) / getDaysInYear());
        int d = (int) ((absoluteDays - getMykradorianCorrection()) % getDaysInYear());
        Iterator<Month> it = months.iterator();
        int count = 0;
        while (it.hasNext() && d >= 0) {
            int x = it.next().getDaysInMonth();
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
            int deltaMonth = 0;
            int deltaYear = (int) (year * (getDaysInYear())) % getDaysAsList().size();
            for (Month i : months) {
                deltaMonth += i.getDaysInMonth();
            }
            return (deltaYear + deltaMonth) % dayNames.size();
        } else // If cut, month starts with the first day in week
        {
            return 0;
        }
    }

    public int getDayOfWeek(int[] date) {
        return (getFirstDayOfWeekInMonth(date[0], date[1]) + date[2]) % dayNames.size();
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
        return (int) (((((d - (mykradorianCorrectionInDays % getDaysInYear())) + getDaysInYear() / 8) % getDaysInYear()) * 4) / getDaysInYear());
    }

    public double getDaysInYear() {
        if (calculateddaysInYear < 1) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static CalendarConfiguration readConfiguration(String path){
        CalendarConfiguration cc = new CalendarConfiguration();

        return cc;
    }
}
