/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.calendar.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author g8712
 */

@Entity
public class CalendarEntry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int daysOfYear = 0;
    private int annualfactor = -1;
    private int mykradorianYearCorrection = 0;
    private String name ;
    private String description;

 public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public int getAnnualfactor() {
        return annualfactor;
    }

    public void setAnnualfactor(int annualfactor) {
        this.annualfactor = annualfactor;
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
