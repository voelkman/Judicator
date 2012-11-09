/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.data;

import de.voelkman.judicator.lister.data.ListerFolder;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author g8712
 */
@Entity
public class RPGGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany
    private List<RealUser> users;

    @OneToMany
    private List<ListerFolder> lists;

    // @OneToMany
    // private List<User> users;
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
        if (!(object instanceof RPGGroup)) {
            return false;
        }
        RPGGroup other = (RPGGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.voelkman.judicator.data.RPGGroup[ id=" + id + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
