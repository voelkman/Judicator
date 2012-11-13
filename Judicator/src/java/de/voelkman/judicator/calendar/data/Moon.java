/*
 * Created on 29.01.2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.judicator.calendar.data;

import java.awt.Color;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This Class provides any Information needed for it's location
 * 
 * @author g8712
 * @version 29.01.2009
 */

@Entity
public class Moon implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


	private Color taint = new Color(255, 255, 128);
	private double revolutionInDays = 1;
	private double offset = 0;
	private String name = "Moon";
	private int width = 15;

	/**
	 * Color the Moon has
	 * 
	 * @return
	 */
	public Color getTaint() {
		return taint;
	}

	/**
	 * Time passed from New Moon to New Moon
	 * 
	 * @return
	 */
	public double getRevolutionInDays() {
		return revolutionInDays;
	}

	/**
	 * Time passed since the last New Moon on Time 0
	 * 
	 * @return
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * The name of the Moon
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setTaint(Color taint) {
		this.taint = taint;
	}

	public void setRevolutionInDays(double revolutionInDays) {
		this.revolutionInDays = revolutionInDays;
	}

	/**
	 * the starting constellation on day zero
	 * 
	 * @param start
	 */
	public void setOffset(double start) {
		this.offset = start;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRadiansForDay(double daysSinceZero) {
		double x = (((daysSinceZero - offset + revolutionInDays) % revolutionInDays) * 2 * Math.PI) / revolutionInDays;
		return x;
	}

	public int getSize() {
		return width;
	}

	public void setSize(int size) {
		this.width = size;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
