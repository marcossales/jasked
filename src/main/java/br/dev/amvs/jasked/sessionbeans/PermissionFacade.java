/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.sessionbeans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.dev.amvs.jasked.jpa.domain.Permission;
import br.dev.amvs.jasked.jpa.domain.User;

/**
 *
 * @author marcossales
 */
@Stateless
public class PermissionFacade extends AbstractFacade<Permission> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionFacade() {
        super(Permission.class);
    }

	
	
	
	@SuppressWarnings("unchecked")
	public List<Permission> findByUser(User user) {
		Query  query =  em.createQuery(" SELECT p FROM Permission p WHERE p.id.userId = :userId ");
		query.setParameter("userId", user.getId());
		return query.getResultList();
		
	}
	
	
	
    
}
