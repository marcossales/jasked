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
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.dev.amvs.jasked.exception.DatabaseException;
import br.dev.amvs.jasked.jpa.domain.User;

/**
 *
 * @author marcossales
 */
public class UserFacade extends AbstractFacade<User> implements Serializable {

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
	public UserFacade(EntityManager em) {
		super(User.class);
		this.em = em;
	}

	public User findByUserNameAndPassword(String userName, String password) throws DatabaseException {
		Query query = em.createQuery(
				" SELECT u FROM User u WHERE UPPER(u.userName) = UPPER(:userName) AND u.password = :password ");
		query.setParameter("userName", userName);
		query.setParameter("password", password);
		
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			if (e.getCause() != null && (e.getCause() instanceof NoResultException)) {
				return null;
			} else {
				throw new DatabaseException(e);
			}

		}

	}
	
	public User findByUserName(String userName) throws DatabaseException {
		Query query = em.createQuery(
				" SELECT u FROM User u WHERE UPPER(u.userName) = UPPER(:userName) ");
		query.setParameter("userName", userName);
		
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			if (e.getCause() != null && (e.getCause() instanceof NoResultException)) {
				return null;
			} else {
				throw new DatabaseException(e);
			}

		}

	}

	@SuppressWarnings("unchecked")
	public List<User> findByName(String nameOrPartOfIt) {
		Query query = em.createQuery(" SELECT u FROM User u "
				+ " WHERE UPPER(CONCAT(u.firstName,' ',u.lasName)) like UPPER(:nameOrPartOfIt) "
				+ " OR UPPER(u.firstName) like UPPER(:nameOrPartOfIt) "
				+ " OR UPPER(u.lastName) like UPPER(:nameOrPartOfIt) ");
		query.setParameter("nameOrPartOfIt", "%" + nameOrPartOfIt + "%");
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<User> findByEmail(String email) {
		Query query = em.createQuery(" SELECT u FROM User u " + " WHERE UPPER(u.email) like UPPER(:email) ");
		query.setParameter("email", email);
		return query.getResultList();

	}

}
