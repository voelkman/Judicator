/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.calendar.data;

/**
 *
 * @author g8712
 */
public class Day {

    private int daysOfYear = 0;
    private int repeat = -1;
    private int mykradorianYearCorrection = 0;
    private String name ;
    private String description;

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getDaysOfYear() {
        return daysOfYear;
    }

    public void setDaysOfYear(int daysOfYear) {
        this.daysOfYear = daysOfYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMykradorianYearCorrection() {
        return mykradorianYearCorrection;
    }

    public void setMykradorianYearCorrection(int mykradorianYear) {
        this.mykradorianYearCorrection = mykradorianYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
