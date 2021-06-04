package br.dev.amvs.jasked.jsf;

import java.io.Serializable;

import javax.faces.event.ComponentSystemEvent;

import br.dev.amvs.jasked.jpa.domain.User;

public interface CrudPermissionVerification<T> extends Serializable{
	
	
	    public void checkPreRenderCreate(ComponentSystemEvent event) ;
	    public void checkPreRenderEdit(ComponentSystemEvent event) ;
	    public void checkPreRenderList(ComponentSystemEvent event) ;
	    public void checkPreRenderView(ComponentSystemEvent event) ;

	    
	   
	    
	    public User getUser();
	    
		public boolean isCanCreate() ;
		public boolean isCanRead() ;
		public boolean isCanUpdate() ;
		public boolean isCanDelete() ;
		

}
