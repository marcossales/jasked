/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.sessionbeans;

import br.dev.amvs.jasked.jpa.domain.ObjectStatus;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marcossales
 */
@Stateless
public class ObjectStatusFacade extends AbstractFacade<ObjectStatus> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ObjectStatusFacade() {
        super(ObjectStatus.class);
    }
    
}
