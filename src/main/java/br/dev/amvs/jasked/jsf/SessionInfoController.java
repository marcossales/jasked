package br.dev.amvs.jasked.jsf;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.dev.amvs.jasked.exception.DatabaseException;
import br.dev.amvs.jasked.jpa.domain.User;
import br.dev.amvs.jasked.jsf.util.JsfUtil;
import br.dev.amvs.jasked.sessionbeans.PermissionFacade;
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
	
	public String getUserInSession() {
		Object attr = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(JsfUtil.LOGGEDIN_USERNAME_SESSION_ATTRIBUTE);
		return attr!=null?(String)attr:null ;
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
	
	public boolean isAllowedToListOpenSessions() {
		return isSuperUser();
		
	}
	public boolean isAllowedToListUsers() {
		return isSuperUser();
		
	}
	
	public boolean isAllowedToListPermissions() {
		return isSuperUser();
	}
	
	public boolean isSuperUser() {
		try {
			User user = userFacade.findByUserName(getUserInSession());
			return user.isSuperUser();
			
		} catch (DatabaseException e) {
			e.printStackTrace();
			return false; //TODO  make better,maybe throw an UnexpectedBeahivorException and intercept it in some layer
		}
	}
	

}
