/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.dbfacade;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.dev.amvs.jasked.jpa.domain.ObjectStatus;

/**
 *
 * @author marcossales
 */
public class ObjectStatusFacade extends AbstractFacade<ObjectStatus> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager em;
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Inject	
    public ObjectStatusFacade(EntityManager em) {
        super(ObjectStatus.class);
        this.em =em;
        
    }
    
}
