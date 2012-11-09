/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator;

import de.voelkman.judicator.anno.TransactionRequired;
import de.voelkman.judicator.data.RPGGroup;
import de.voelkman.utils.generator.smith.IGeneratorLibrary;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author g8712
 */
@Named(value = "TestJSF")
@Dependent
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GeneratorBean {

    @PersistenceContext(name = "JudicatorPU")
    private EntityManager entityManager;
    @Inject
    private IGeneratorLibrary gen;
    @Inject
    private TestWest w;
    private RPGGroup group;

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public GeneratorBean() {



    }

    public String getGeneratedString() {

        w.xxx();
        Logger.getLogger(GeneratorBean.class.getName()).log(Level.WARNING, "Hi");
        return gen.getGenerator("Orc Names").evaluate();
    }

    @TransactionRequired
    public RPGGroup createGroup() {
        group = new RPGGroup();
        group.setName("Test");
        if (entityManager != null) {
            entityManager.persist(group);
        }

        return group;
    }

    public RPGGroup getGroup() {
        TypedQuery<RPGGroup> q = entityManager.createQuery("select c from RPGGroup c", RPGGroup.class);
        List<RPGGroup> grps = q.getResultList();

        for(RPGGroup g: grps){
        Logger.getLogger(RPGGroup.class.getName()).severe("Found " + g.getName() +" ("+g.getId()+")");

        }
        RPGGroup gr = entityManager.find(RPGGroup.class, 1l);
        return gr;
    }
}
