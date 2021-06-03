package br.dev.amvs.jasked.jsf;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.dev.amvs.jasked.exception.DatabaseException;
import br.dev.amvs.jasked.jpa.domain.Permission;
import br.dev.amvs.jasked.jpa.domain.User;
import br.dev.amvs.jasked.jsf.util.JsfUtil;
import br.dev.amvs.jasked.sessionbeans.PermissionFacade;
import br.dev.amvs.jasked.sessionbeans.RoleFacade;
import br.dev.amvs.jasked.sessionbeans.UserFacade;

@Named("sessionInfoController")
@SessionScoped
public class SessionInfoController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PermissionFacade permissionFacade;

	@EJB
	private RoleFacade roleFacade;
	@EJB
	private UserFacade userFacade;
	
	@Inject
	private AppInfoController appInfoController;
	
	

	public void putUserInSession(User user) {
		Map<String,Object> sessionMap =FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
		String userName = user.getUserName();
		String remoteHost = FacesContext.getCurrentInstance().getExternalContext().getRequestServerName();
		
		
		sessionMap.put(JsfUtil.LOGGEDIN_USERNAME_SESSION_ATTRIBUTE, userName);
		appInfoController.addSessionData(new SessionData(sessionId, userName, remoteHost, LocalDateTime.now()));
	}
	
	public String getUsernameInSession() {
		Object attr = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(JsfUtil.LOGGEDIN_USERNAME_SESSION_ATTRIBUTE);
		return attr!=null?(String)attr:null ;
	}
	public User getUserInSession() {
		String username = getUsernameInSession();
		try {
			return userFacade.findByUserName(username);
		} catch (DatabaseException e) {
			JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Messages").getString("DatabaseQueryErrorOccured"));
			e.printStackTrace();
			return null;
		}
	}
	public User getUserInSessionWithPermissions() {
		String username = getUsernameInSession();
		try {
			User u = userFacade.findByUserName(username);
			List<Permission> p = permissionFacade.findByUser(u);
			u.setPermissions(p);
			return u;
		} catch (DatabaseException e) {
			JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Messages").getString("DatabaseQueryErrorOccured"));
			e.printStackTrace();
			return null;
		}
	}
	
	
	public String logout() {
		String sessionId =FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
		appInfoController.removeSessionData(sessionId);
    	FacesContext.getCurrentInstance().getExternalContext()
    	.invalidateSession();
    	return "/Login?faces-redirect=true";
    }

	public String getLogoutButtonLabel() {
		   return ResourceBundle.getBundle("/Messages").getString("UserBar_logoutButtonLabel");
	}
	
	public String getWelcomeGreeting() {
		return ResourceBundle.getBundle("/Messages").getString("UserBar_welcomeGreeting");
	}
	

	
	
	public boolean isSuperUser() {
		try {
			User user = userFacade.findByUserName(getUsernameInSession());
			return user.isSuperUser();
			
		} catch (DatabaseException e) {
			JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Messages").getString("DatabaseQueryErrorOccured"));
			e.printStackTrace();
			return false; 
		}
	}
	
	
	

	
	
	
	
	
	
	

}
