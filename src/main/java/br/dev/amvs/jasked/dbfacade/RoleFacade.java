/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.dbfacade;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.dev.amvs.jasked.jpa.domain.Role;

/**
 *
 * @author marcossales
 */
public class RoleFacade extends AbstractFacade<Role> implements Serializable{

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
    public RoleFacade(EntityManager em) {
        super(Role.class);
        this.em = em;
    }

	
	
	
	@SuppressWarnings("unchecked")
	public List<Role> findByName(String nameOrPartOfIt) {
		Query  query =  em.createQuery(" SELECT r FROM Role r WHERE UPPER(r.name) like UPPER(:nameOrPartOfIt) ");
		query.setParameter("name", "%"+nameOrPartOfIt+"%");
		return query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> findEachByExactName(String ...name) {
		Query  query =  em.createQuery(" SELECT r FROM Role r WHERE r.name IN :rolesNames ");
		
		query.setParameter("rolesNames", Arrays.asList(name));
		return query.getResultList();
		
	}
	public Role findByExactName(String name) {
		Query  query =  em.createQuery(" SELECT r FROM Role r WHERE r.name = :rolesName ");
		
		query.setParameter("rolesName", name);
		return (Role) query.getSingleResult();
		
	}
}
