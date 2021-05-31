/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.sessionbeans;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.dev.amvs.jasked.jpa.domain.Role;

/**
 *
 * @author marcossales
 */
@Stateless
public class RoleFacade extends AbstractFacade<Role> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleFacade() {
        super(Role.class);
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
