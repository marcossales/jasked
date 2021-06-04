/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.dbfacade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.dev.amvs.jasked.jpa.domain.Identifiable;

/**
 *
 * @author marcossales
 */
public abstract class AbstractFacade<T> {
	

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    
   
    /**
     * Build a Comma-separeted string of IDs
     * Has to be used in native queries
     * @param arr
     * @return A string as this: 1,2,3,4,5
     */
  
    public String buildStringIn( Identifiable[] arr) {
    	StringBuilder sb = new StringBuilder();
    	int count=0;
    	for(Identifiable o: arr) {
    		if(count>0) {
    			sb.append(",");
    		}
    		Object id = o.getId();
    		if(id!=null) {
    			sb.append(id.toString());	
    		}
    		
    		count++;
    	}
    	return sb.toString();
    }

    /**
     * Build a Comma-separeted string of strings
     * Has to be used in native queries
     * @param arr
     * @return A string as this: 'a','b','c','d','e'
     */
    public String buildStringIn( String[] arr) {
    	StringBuilder sb = new StringBuilder();
    	int count=0;
    	for(String o: arr) {
    		if(count>0) {
    			sb.append(",");
    		}
    		
    		if(o!=null) {
    			sb.append("'"+o+"'");	
    		}
    		
    		count++;
    	}
    	return sb.toString();
    }
    /**
     * 
     * @param arr
     * @return 
     */
    @SuppressWarnings("rawtypes")
    public List<Object> extractIds( Identifiable[] arr) {
        List<Object> list = new ArrayList<Object>();
    	for(Identifiable o: arr) {
    		Object id = o.getId();
    		if(id!=null) {
    			list.add(id);	
    		}
    		
    	}
    	return list;
    }
    
}
