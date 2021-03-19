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
import br.dev.amvs.jasked.jpa.domain.QuestionCategory;
import br.dev.amvs.jasked.jpa.domain.User;

/**
 *
 * @author marcossales
 */
@Stateless
public class QuestionCategoryFacade extends AbstractFacade<QuestionCategory> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionCategoryFacade() {
        super(QuestionCategory.class);
    }

	public List<QuestionCategory> findByFaqPath(String path, boolean onlyPublished) {
		String publishedStatusName =  ResourceBundle.getBundle("/DomainStrings").getString("ObjectStatus_publishedStatusName");
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT c FROM FaqSite f INNER JOIN f.objectStatus fs INNER JOIN f.questionCategoryCollection c INNER JOIN c.questionCollection q INNER JOIN q.objectStatus qs WHERE UPPER(f.path) = UPPER(:path) ");
		if(onlyPublished) {
			sb.append(" AND UPPER(fs.name) = UPPER(:publishedStatusName) ");
			sb.append(" AND UPPER(qs.name) = UPPER(:publishedStatusName) ");
		}
		TypedQuery<QuestionCategory> query =
				em.createQuery(sb.toString(), QuestionCategory.class);
		query.setParameter("path", path);
		if(onlyPublished) {
			query.setParameter("publishedStatusName", publishedStatusName);
		}
	    List<QuestionCategory> results = query.getResultList();
		return results;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<QuestionCategory> findRange(int[] range,User user) {
		String queryString = " SELECT c.* FROM jasked.question_category c ";
		if(!user.isSuperUser()) {
			queryString = " SELECT c.* FROM jasked.question_category c "
					+ " INNER JOIN jasked.faq_site f on(f.id=c.site_id) "
					+ " INNER JOIN jasked.user_role_faq perm on (perm.faq_site_id = f.id) WHERE perm.user_id = ?1 ";
		}
        javax.persistence.Query q = getEntityManager().createNativeQuery(queryString,QuestionCategory.class);
        if(!user.isSuperUser()) {
           q.setParameter(1, user.getId());
        }
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
}
