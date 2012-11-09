/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.lister.data;

import de.voelkman.judicator.data.RPGGroup;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author g8712
 */


@Entity
public class ListerFolder implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.FIELD)
    private Long id;
    private String name;

    @ManyToOne
    private RPGGroup rpggroup;

    public String getName() {
        return name;
    }

    public RPGGroup getRpggroup() {
        return rpggroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListerFolder)) {
            return false;
        }
        ListerFolder other = (ListerFolder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ListerFolder.class.getName()+" [ id=" + id + " ]";
    }

}
