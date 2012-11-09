/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.calendar.data;

/**
 *
 * @author g8712
 */
public class CalendarDerivation {

    private String name;

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public int getCorrection() {
        return correction;
    }

    public void setCorrection(int correction) {
        this.correction = correction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private String abreviation;
    private int correction;
}
