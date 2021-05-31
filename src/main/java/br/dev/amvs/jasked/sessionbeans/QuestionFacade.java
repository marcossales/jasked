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

import br.dev.amvs.jasked.jpa.domain.Question;
import br.dev.amvs.jasked.jpa.domain.QuestionCategory;
import br.dev.amvs.jasked.jpa.domain.User;

/**
 *
 * @author marcossales
 */
@Stateless
public class QuestionFacade extends AbstractFacade<Question> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionFacade() {
        super(Question.class);
    }

	public List<Question> findByCategory(QuestionCategory qc, boolean onlyPublished) {
		String publishedStatusName =  ResourceBundle.getBundle("/DomainStrings").getString("ObjectStatus_publishedStatusName");
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT q FROM Question q INNER JOIN q.objectStatus qs INNER JOIN q.questionCategory c WHERE c.id = :categoryId ");
		if(onlyPublished) {
			sb.append(" AND UPPER(qs.name) = UPPER(:publishedStatusName) ");
		}
		TypedQuery<Question> query =
				em.createQuery(sb.toString(), Question.class);
		
		
		
		query.setParameter("categoryId", qc.getId());
		if(onlyPublished) {
			query.setParameter("publishedStatusName", publishedStatusName);	
		}
	    List<Question> results = query.getResultList();
		return results;
	}

	@SuppressWarnings("unchecked")
	public List<Question> findRange(int[] range, User user) {
		String queryString = " SELECT q.* FROM jasked.question q ";
		if(!user.isSuperUser()) {
			queryString = " SELECT q.* FROM jasked.question q "
					+ " INNER JOIN jasked.question_category c on (c.id = q.category_id)"
					+ " INNER JOIN jasked.faq_site f on(f.id=c.site_id) "
					+ " INNER JOIN jasked.permission perm on (perm.faq_site_id = f.id) WHERE perm.user_id = ?1 ";
		}
        javax.persistence.Query q = getEntityManager().createNativeQuery(queryString,Question.class);
        if(!user.isSuperUser()) {
           q.setParameter(1, user.getId());
        }
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
 
	}
	
	
    
}
