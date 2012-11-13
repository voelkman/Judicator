/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.calendar.rest;

import de.voelkman.judicator.calendar.data.CalendarConfiguration;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author manni
 */
@Stateless
@Path("/calendar")
public class CalendarRessourceProvider {

    public class Doof {
        String eins = "";
        String zwei = "";

        public String getEins() {
            return eins;
        }

        public void setEins(String eins) {
            this.eins = eins;
        }

        public String getZwei() {
            return zwei;
        }

        public void setZwei(String zwei) {
            this.zwei = zwei;
        }
        
        public Doof(String e1, String e2){
            eins = e1;
            zwei = e2;
        }
    }

    @GET
    @Produces("text/plain")
    @Path("help")
    public String getPack() {
        return "No special things! V1.2";
    }

    @GET
    @Produces("text/plain")
    @Path("{bong}")
    public String getPack(@PathParam("bong") String call) {
        return "Special " + call;
    }

    @GET
    //@Produces("text/plain")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cal")
    public Doof getPack2() {
        Doof tm = new Doof("xhdkjdhk","zuiuiuz");
        return tm;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("config")
    public List<CalendarConfiguration> getCalendars(){
        //TODO Implement this
        
        return new LinkedList<CalendarConfiguration>();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("config/create")
    public CalendarConfiguration createCalendar(){
        return new CalendarConfiguration();
    }

    @PUT
    @Path("config/{id}")
    public String updateCalendar(@PathParam("id") String doodo){
        //jdsgkjagdskf
        return "ddd";
    }
}
