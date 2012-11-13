/*
 * Created on 30.01.2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.judicator.calendar.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Constellation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	private String name = "MyConstellation";
	private double revolutionInDays = 0;
	private double extremeInDays = 0;
	private double offsetInDays = 0;
	private double randomize = 0;

	public String getName() {
		return name;
	}

	public int getConstellationDegree(double daysSinceZero) {
		int result;
		double transition = revolutionInDays / 2 - extremeInDays;
		double x = (daysSinceZero + offsetInDays) % revolutionInDays;
		if (x < extremeInDays) {
			// REMOTE
			result = 0;
		} else if (x < (extremeInDays + transition)) {
			// WAXING
			result = (int) (((x - extremeInDays) * 180) / transition);
		} else if (x < (revolutionInDays - transition)) {
			// COTERMINOUS
			result = 180;
		} else {
			// WANING
			result = (int) (((x - 2 * extremeInDays) * 180) / transition);
		}
		return result;
	}

	public double getRevolutionInDays() {
		return revolutionInDays;
	}

	public double getExtremeInDays() {
		return extremeInDays;
	}

	public double getOffsetInDays() {
		return offsetInDays;
	}

	public double getRandomize() {
		return randomize;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRevolutionInDays(double revolutionInDays) {
		this.revolutionInDays = revolutionInDays;
	}

	public void setExtremeInDays(double extremeInDays) {
		this.extremeInDays = extremeInDays;
	}

	public void setOffsetInDays(double startInDays) {
		this.offsetInDays = startInDays;
	}

	public void setRandomize(double randomize) {
		this.randomize = randomize;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
