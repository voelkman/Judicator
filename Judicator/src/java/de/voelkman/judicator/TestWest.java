/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator;

import de.voelkman.judicator.anno.MVTestInterceptor;
import de.voelkman.utils.generator.smith.Randomizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

/**
 *
 * @author g8712
 */
@Stateless
public class TestWest {
    
    public String getTest(){return "Saucool!";}


    @Asynchronous
    @MVTestInterceptor
    public void xxx() {
        Randomizer r = new Randomizer(100);
        int x = 0;
        long l = System.currentTimeMillis() + 10000;
        Logger.getLogger(TestWest.class.getName()).log(Level.WARNING, "Start");

        while (System.currentTimeMillis() < l) {

            }
        Logger.getLogger(TestWest.class.getName()).log(Level.WARNING, "Done");

        

    }
    
}
