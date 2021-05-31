package br.dev.amvs.jasked.jsf;

import javax.ejb.EJB;

import br.dev.amvs.jasked.jpa.domain.FaqSiteOrBelongingToIt;
import br.dev.amvs.jasked.jpa.domain.Permission;
import br.dev.amvs.jasked.jpa.domain.Role;
import br.dev.amvs.jasked.jpa.domain.User;

public abstract class BelongingToFaqSiteCrudPermissionVerifier<T extends FaqSiteOrBelongingToIt> extends BasicCrudPermissionVerifier<FaqSiteOrBelongingToIt> {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public boolean canRead(FaqSiteOrBelongingToIt selected) {
		User u = getUser();
    	if(u.isSuperUser()) {
    		return true;
    	}
		Role role = getRoleFacade().findByExactName(getReadRoleName()); 
		for(Permission p: u.getPermissions()) {
			 if( role.equals(p.getRole()) && p.getFaqSite().equals(selected.getFaqSite())  ) {
				 return true;
			 }
		 }
		return false;
	}
	@Override
	public boolean canUpdate(FaqSiteOrBelongingToIt selected) {
		User u = getUser();
    	if(u.isSuperUser()) {
    		return true;
    	}
		Role role = getRoleFacade().findByExactName(getUpdateRoleName()); 
		for(Permission p: u.getPermissions()) {
			 if( role.equals(p.getRole()) && p.getFaqSite().equals(selected.getFaqSite())  ) {
				 return true;
			 }
		 }
		return false;
	}
	@Override
	public boolean canDelete(FaqSiteOrBelongingToIt selected) {
		User u = getUser();
    	if(u.isSuperUser()) {
    		return true;
    	}
		Role role = getRoleFacade().findByExactName(getDeleteRoleName()); 
		for(Permission p: u.getPermissions()) {
			 if( role.equals(p.getRole()) && p.getFaqSite().equals(selected.getFaqSite())  ) {
				 return true;
			 }
		 }
		return false;
	}

	

}
