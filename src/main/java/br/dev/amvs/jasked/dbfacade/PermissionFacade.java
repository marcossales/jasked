/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.dbfacade;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.dev.amvs.jasked.jpa.domain.FaqSite;
import br.dev.amvs.jasked.jpa.domain.Permission;
import br.dev.amvs.jasked.jpa.domain.Role;
import br.dev.amvs.jasked.jpa.domain.User;

/**
 *
 * @author marcossales
 */
public class PermissionFacade extends AbstractFacade<Permission> implements Serializable {

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
    public PermissionFacade(EntityManager em) {
        super(Permission.class);
        this.em = em;
    }

	
	
	
	@SuppressWarnings("unchecked")
	public List<Permission> findByUser(User user) {
		Query  query =  em.createQuery(" SELECT p FROM Permission p WHERE p.user.id = :userId ");
		query.setParameter("userId", user.getId());
		return query.getResultList();
		
	}

	@SuppressWarnings("unchecked")
	public List<Permission> findByUserAndRoles(User user,Role ...roles ) {
		Query  query =  em.createQuery(" SELECT p FROM Permission p WHERE p.user.id = :userId "
				+ " AND p.role.id IN :roleIdList "
				);
		List<Object> roleIdList = extractIds(roles);
		query.setParameter("userId", user.getId());
		query.setParameter("roleIdList", roleIdList);	
		return query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Permission> findByUserAndFaqSiteAndRoles(User user, FaqSite faqSite,Role ...roles ) {
		Query  query =  em.createQuery(" SELECT p FROM Permission p WHERE p.user.id = :userId "
				
				+ " AND p.faqSite.id = :faqSiteId "
				+ " AND p.role.id IN :roleIdList ");
		List<Object> roleIdList = extractIds(roles);
		query.setParameter("userId", user.getId());
		query.setParameter("faqSiteId", faqSite.getId());
		query.setParameter("roleIdList",roleIdList);	

		return query.getResultList();
		
	}

	
	
	
	
	
    
}
