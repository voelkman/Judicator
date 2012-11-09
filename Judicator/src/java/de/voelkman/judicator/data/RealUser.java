/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author g8712
 */
@Entity
public class RealUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;
    private String lastname;

    @ManyToMany
    private List<RPGGroup> groups;

    public String getFirstname() {
        return firstname;
    }

    public List<RPGGroup> getGroups() {
        return groups;
    }

    public String getLastname() {
        return lastname;
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
        if (!(object instanceof RealUser)) {
            return false;
        }
        RealUser other = (RealUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.voelkman.judicator.data.RealUser[ id=" + id + " ]";
    }

}
