/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.calendar.rest;

/**
 *
 * @author g8712
 */
public class TestCal {

    public static void main(String[] args) {
        double DAYS = 365.2509;
        for (double i = 0; i < 2200; i++) {
            double x = Math.floor(DAYS*(i+1)) - Math.floor(DAYS*i);
            if(x > 365){
                System.out.println(i + ": "+x);
            }
        }
    }


}
