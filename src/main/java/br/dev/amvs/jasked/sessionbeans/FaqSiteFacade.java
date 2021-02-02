/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.sessionbeans;

import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.dev.amvs.jasked.jpa.domain.FaqSite;

/**
 *
 * @author marcossales
 */
@Stateless
public class FaqSiteFacade extends PublishableObjectFacade<FaqSite> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FaqSiteFacade() {
        super(FaqSite.class);
    }

	@Override
	public List<FaqSite> findAllPublished() {
		String publishedStatusName =  ResourceBundle.getBundle("/DomainStrings").getString("ObjectStatus_publishedStatusName");
		TypedQuery<FaqSite> query =
				em.createQuery("SELECT f FROM FaqSite f INNER JOIN f.objectStatus s WHERE UPPER(s.name) = UPPER(:publishedStatusName)", FaqSite.class);
		query.setParameter("publishedStatusName", publishedStatusName);
	    List<FaqSite> results = query.getResultList();
		return results;
	}
    
}
