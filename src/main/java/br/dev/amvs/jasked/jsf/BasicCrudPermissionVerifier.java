package br.dev.amvs.jasked.jsf;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import br.dev.amvs.jasked.jpa.domain.FaqSiteOrBelongingToIt;
import br.dev.amvs.jasked.jpa.domain.Permission;
import br.dev.amvs.jasked.jpa.domain.Role;
import br.dev.amvs.jasked.jpa.domain.User;
import br.dev.amvs.jasked.sessionbeans.RoleFacade;

public abstract class BasicCrudPermissionVerifier<T> implements CrudPermissionVerification<T> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public abstract RoleFacade getRoleFacade();
	
	
	public abstract User getUser();
	
	protected abstract T getSelected();
	
	protected abstract String getCreateRoleName();
	protected abstract String getReadRoleName();
	protected abstract String getUpdateRoleName();
	protected abstract String getDeleteRoleName();
	protected abstract String getAccessDeniedPageOutcome();
	
	 
	@Override
    public void checkPreRenderCreate(ComponentSystemEvent event) {
    	if(!isCanCreate() ) {
    		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
    		.handleNavigation(FacesContext.getCurrentInstance(), null, getAccessDeniedPageOutcome());
    	}
    }
    @Override
    public void checkPreRenderEdit(ComponentSystemEvent event) {
    	if(!canUpdate(getSelected()) ) {
    		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
    		.handleNavigation(FacesContext.getCurrentInstance(), null, getAccessDeniedPageOutcome());
    	}
    }
    

	@Override
    public void checkPreRenderList(ComponentSystemEvent event) {
    	if(!isCanRead() ) {
    		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
    		.handleNavigation(FacesContext.getCurrentInstance(), null, getAccessDeniedPageOutcome());
    	}
    }
    @Override
    public void checkPreRenderView(ComponentSystemEvent event) {
    	if(!canRead(getSelected())) {
    		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
    		.handleNavigation(FacesContext.getCurrentInstance(), null, getAccessDeniedPageOutcome());
    	}
    }

    @Override
	public boolean isCanCreate() {
    	User u = getUser();
    	if(u.isSuperUser()) {
    		return true;
    	}
		Role role = getRoleFacade().findByExactName(getCreateRoleName()); 
		for(Permission p: u.getPermissions()) {
			 if( role.equals(p.getRole())) {
				 return true;
			 }
		 }
		return false;
	}
	@Override
	public boolean isCanRead() {
		User u = getUser();
    	if(u.isSuperUser()) {
    		return true;
    	}
		Role role = getRoleFacade().findByExactName(getReadRoleName()); 
		for(Permission p: u.getPermissions()) {
			 if( role.equals(p.getRole())) {
				 return true;
			 }
		 }
		return false;
	}
	@Override
	public boolean isCanUpdate() {
		User u = getUser();
    	if(u.isSuperUser()) {
    		return true;
    	}
		Role role = getRoleFacade().findByExactName(getUpdateRoleName()); 
		for(Permission p: u.getPermissions()) {
			 if( role.equals(p.getRole())) {
				 return true;
			 }
		 }
		return false;

	}
	@Override
	public boolean isCanDelete() {
		User u = getUser();
    	if(u.isSuperUser()) {
    		return true;
    	}
		Role role = getRoleFacade().findByExactName(getDeleteRoleName()); 
		for(Permission p: u.getPermissions()) {
			 if( role.equals(p.getRole())) {
				 return true;
			 }
		 }
		return false;
	}

	public abstract boolean canRead(T selected);
	public abstract boolean canUpdate(T selected);
	public abstract boolean canDelete(T selected);
	
	

	

}
