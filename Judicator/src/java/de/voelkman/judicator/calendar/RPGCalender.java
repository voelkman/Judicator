/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.calendar;

import de.voelkman.judicator.calendar.data.CalendarConfiguration;
import java.util.Map;

/**
 *
 * @author g8712
 */
public class RPGCalender {

    private int currentDay = 0;
    private int selectedDay = 0;

    private String config = null;

    private static final Map<String,CalendarConfiguration> CALENDARCONFIGS;

    static{

       CALENDARCONFIGS = null;
    }

    private static CalendarConfiguration loadConfig(String name){
        CalendarConfiguration res = new CalendarConfiguration();
        return res;
    }

}
